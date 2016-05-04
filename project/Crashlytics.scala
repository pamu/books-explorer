import ReplacePropertiesGenerator._
import sbt.Keys._
import sbt._

object Crashlytics {

  val crashlyticsEnabled = settingKey[Boolean]("If Crashlytics is enabled")

  def createFiles = Def.task[Seq[File]] {
    if (crashlyticsEnabled.value) {
      val log = streams.value.log
      log.info("Creating crashlytics files")
      try {
        val templates = loadTemplates(baseDirectory.value / "crashlytics" / "templates")
        templates map { file =>
          val target = baseDirectory.value / "crashlytics" / file.getName
          replaceContent(file, target)
          target
        }
      } catch {
        case e: Throwable =>
          log.error("An error occurred loading creating files")
          throw e
      }
    } else {
      Seq.empty
    }
  }

  /*
   * Removes the namespace from the crashlytics auto-generated file.
   * This is a common problem of the com.android.tools.build:builder and crashlytics
   * that can be solved by simply removing the namespace declaration and the attributes
   * with namespace
   */
  def fixNameSpace = Def.task[Unit] {
    if (crashlyticsEnabled.value) {
      crashlyticsCodeGen.value
      val file = baseDirectory.value / "src/main/res/values/com_crashlytics_export_strings.xml"
      if (file.exists()) {
        val xml = scala.xml.XML.loadFile(file)

        val rewriteRule = new scala.xml.transform.RewriteRule {
          override def transform(node: scala.xml.Node) = node match {
            case elem: scala.xml.Elem if elem.label == "resources" =>
              elem.copy(child = fixChilds(elem.child))
            case x => x
          }
        }

        scala.xml.XML.save(
          filename = file.getAbsolutePath,
          node = rewriteRule(xml),
          enc = "UTF-8",
          xmlDecl = true)
      }
    }
  }

  def crashlyticsPreBuild = Def.task[Unit] {
    if (crashlyticsEnabled.value) {
      val log = streams.value.log
      log.info("Crashlytics pre build")

      // Cleanup
      crashlyticsCleanupResources.value

      // Upload deobs - Disabled
      // crashlyticsUploadDeobs.value
    }
  }

  def crashlyticsCodeGen = Def.task[Unit] {
    if (crashlyticsEnabled.value) {
      val log = streams.value.log
      log.info("Crashlytics code gen")

      // Generate resources
      crashlyticsGenerateResources.value
    }
  }

  def crashlyticsPostPackage = Def.task[Unit] {
    if (crashlyticsEnabled.value) {
      val log = streams.value.log
      log.info("Crashlytics post package")

      // Store deobs - Disabled
      // crashlyticsStoreDeobs.value

      // Upload deobs - Disabled
      // crashlyticsUploadDeobs.value

      // Cleanup
      crashlyticsCleanupResources.value
    }
  }

  def crashlyticsCleanupResources = Def.task[Unit] {
    crashlyticsTask(
      log = streams.value.log,
      task = Crashlytics.CleanupResources,
      projectPath = baseDirectory.value.getAbsolutePath,
      enabled = crashlyticsEnabled.value)
  }

  def crashlyticsGenerateResources = Def.task[Unit] {
    crashlyticsTask(
      log = streams.value.log,
      task = Crashlytics.GenerateResources,
      projectPath = baseDirectory.value.getAbsolutePath,
      extraArgs = Seq(
        "-buildEvent",
        "-tool", "com.crashlytics.tools.ant",
        "-version", "1.20.0"),
      enabled = crashlyticsEnabled.value)
  }

  def crashlyticsStoreDeobs = Def.task[Unit] {
    crashlyticsTask(
      log = streams.value.log,
      task = Crashlytics.StoreDeobs,
      projectPath = baseDirectory.value.getAbsolutePath,
      extraArgs = Seq(
        s"${(baseDirectory.value / "proguard-mapping.txt").getAbsolutePath}",
        "-obfuscating",
        "-obfuscator",
        "proguard",
        "-obVer",
        "4.7",
        "-verbose"),
      enabled = crashlyticsEnabled.value)
  }

  def crashlyticsUploadDeobs = Def.task[Unit] {
    crashlyticsTask(
      log = streams.value.log,
      task = Crashlytics.UploadDeobs,
      projectPath = baseDirectory.value.getAbsolutePath,
      extraArgs = Seq("-verbose"),
      enabled = crashlyticsEnabled.value)
  }

  object Crashlytics {

    sealed trait Task {
      def param: String
    }

    case object CleanupResources extends Task {
      val param = "-cleanupResourceFile"
    }

    case object GenerateResources extends Task {
      val param = "-generateResourceFile"
    }

    case object StoreDeobs extends Task {
      val param = "-storeDeobs"
    }

    case object UploadDeobs extends Task {
      val param = "-uploadDeobs"
    }

  }

  private[this] def crashlyticsTask(
    log: Logger,
    task: Crashlytics.Task,
    projectPath: String,
    extraArgs: Seq[String] = Seq.empty,
    enabled: Boolean) = {

    if (enabled) {
      log.info(s"Crashlytics task: ${task.toString}")

      val args = Seq(
        "-projectPath", projectPath,
        "-androidManifest", s"$projectPath/crashlytics/CrashlyticsManifest.xml",
        "-androidRes", s"$projectPath/src/main/res",
        "-androidAssets", s"$projectPath/src/main/assets",
        task.param,
        "-properties", s"$projectPath/crashlytics/fabric.properties") ++ extraArgs

      log.debug(s"Arguments: $args")
      try {
        com.crashlytics.tools.android.DeveloperTools.main(args.toArray)
      } catch {
        case e: Throwable =>
          log.error(s"Error executing crashlytics task: ${e.getMessage}")
          throw e
      }
    }
    
  }

  private[this] def loadTemplates(folder: File): Seq[File] = {
    folder.listFiles().toSeq
  }

  private[this] def fixChilds(child: Seq[scala.xml.Node]): Seq[scala.xml.Node] = {
    child map {
      case elem: scala.xml.Elem =>
        elem.copy(
          scope = scala.xml.TopScope,
          attributes = elem.attributes.filter { a =>
            Option(a.getNamespace(elem)).isEmpty
          })
      case x => x
    }
  }

}