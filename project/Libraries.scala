import sbt._

object Libraries {

  def onCompile(dep: ModuleID): ModuleID = dep % "compile"
  def onTest(dep: ModuleID): ModuleID = dep % "test"

  object scala {

    lazy val scalaReflect = "org.scala-lang" % "scala-reflect" % Versions.scalaV
    lazy val scalap = "org.scala-lang" % "scalap" % Versions.scalaV
  }

  object android {

    def androidDep(module: String) = "com.android.support" % module % Versions.androidV

    lazy val multiDexLib = "com.google.android" % "multidex" % Versions.multiDexV

    lazy val androidSupportv4 = androidDep("support-v4")
    lazy val androidAppCompat = androidDep("appcompat-v7")
    lazy val androidRecyclerview = androidDep("recyclerview-v7")
    lazy val androidCardView = androidDep("cardview-v7")
  }

  object macroid {

    def macroid(module: String = "") =
      "org.macroid" %% s"macroid${if(!module.isEmpty) s"-$module" else ""}" % Versions.macroidV

    lazy val macroidRoot = macroid()
    lazy val macroidExtras = "com.fortysevendeg" %% "macroid-extras" % Versions.macroidExtrasV
  }

  object json {
    lazy val playJson = "com.typesafe.play" %% "play-json" % Versions.playJsonV
  }

  object net {
    lazy val okHttp = "com.squareup.okhttp" % "okhttp" % Versions.okHttpV
  }
  
  object test {
    lazy val specs2 = "org.specs2" %% "specs2-core" % Versions.specs2V % "test"
    lazy val androidTest = "com.google.android" % "android" % "4.1.1.4" % "test"
    lazy val mockito = "org.specs2" % "specs2-mock_2.11" % Versions.mockitoV % "test"
  }

  object graphics {
    lazy val picasso = "com.squareup.picasso" % "picasso" % Versions.picassoV
  }

  object social {
    lazy val twitter4j = "org.twitter4j" % "twitter4j-core" % Versions.twitter4jV
  }

  object date {
    lazy val prettytime = "org.ocpsoft.prettytime" % "prettytime" % Versions.prettytimeV
  }

  object qr {
    lazy val zxingCore = "com.google.zxing" % "core" % Versions.zxingCoreV
    lazy val zxingAndroid = "com.embarkmobile" % "zxing-android-minimal" % Versions.zxingAndroidV
  }

  /**
  object playServices {

    def playServicesDep(module: String) = "com.google.android.gms" % module % Versions.playServicesV
    
    lazy val playServicesMaps = playServicesDep("play-services-maps")
    // Google Actions, Google Analytics and Google Cloud Messaging
    lazy val playServicesBase = playServicesDep("play-services-base")
  } **/

  object debug {
    lazy val crashlytics = "com.crashlytics.sdk.android" % "crashlytics" % Versions.crashlyticsV
  }

}
