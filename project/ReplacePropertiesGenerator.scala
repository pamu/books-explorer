/*
 *  Copyright (C) 2015 47 Degrees, LLC http://47deg.com hello@47deg.com
 *
 *  Licensed under the Apache License, Version 2.0 (the "License"); you may
 *  not use this file except in compliance with the License. You may obtain
 *  a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

import java.io.{File, FileInputStream}
import java.util.Properties

import android.Keys._
import sbt.KeyRanks._
import sbt.Keys._
import sbt._

import scala.annotation.tailrec
import scala.collection.JavaConverters._
import scala.collection.immutable.Iterable

object ReplacePropertiesGenerator {

  val debugPropertiesFile = "debug.properties"

  val releasePropertiesFile = "release.properties"

  var debug = true

  def propertiesMap(): Map[String, String] = {
    (loadPropertiesFile map { file =>
      val properties = new Properties()
      properties.load(new FileInputStream(file))
      properties.asScala.toMap
    }) getOrElse Map.empty
  }

  private def namePropertyInConfig(name: String) = s"$${$name}"

  private def loadPropertiesFile: Option[File] = {
    val file = new File(if (debug) debugPropertiesFile else releasePropertiesFile)
    if (file.exists()) Some(file) else None
  }

  def replaceContent(origin: File, target: File) = {
    val content = IO.readLines(origin) map (replaceLine(propertiesMap, _))
    IO.write(target, content.mkString("\n"))
  }

  private def replaceLine(properties: Map[String, String], line: String) = {
    @tailrec
    def replace(properties: Map[String, String], line: String): String = {
      if (properties.isEmpty) {
        line
      } else {
        val (key, value) = properties.head
        val name = namePropertyInConfig(key)
        replace(properties.tail, if (line.contains(name)) line.replace(name, value) else line)
      }
    }
    replace(properties, line)
  }

  def replaceValuesTask = Def.task[Seq[File]] {
    val log = streams.value.log
    println("Replacing values")
    try {
      val dir: (File, File) = (collectResources in Android).value
      val valuesFile: File =  new File(dir._2, "/values/values.xml")
      replaceContent(valuesFile, valuesFile)
      Seq(valuesFile)
    } catch {
      case e: Throwable =>
        log.error("An error occurred replacing values")
        throw e
    }
  }

  def setDebugTask(debug: Boolean) = Def.task[Unit] {
    this.debug = debug
  }

}
