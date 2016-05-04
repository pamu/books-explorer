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

import sbt._

object Settings {

  lazy val resolvers =
    Seq(
      "Maven Central Server" at "http://repo1.maven.org/maven2",
      DefaultMavenRepository,
      "jcenter" at "http://jcenter.bintray.com",
      "47 Degrees Bintray Repo" at "http://dl.bintray.com/47deg/maven",
      Resolver.typesafeRepo("releases"),
      Resolver.typesafeRepo("snapshots"),
      Resolver.typesafeIvyRepo("snapshots"),
      Resolver.sonatypeRepo("releases"),
      Resolver.sonatypeRepo("snapshots"),
      Resolver.defaultLocal,
      "Scalaz Bintray Repo" at "http://dl.bintray.com/scalaz/releases",
      "zxing Android Minimal" at "https://raw.github.com/embarkmobile/zxing-android-minimal/mvn-repo/maven-repository/",
      "crashlytics" at "https://maven.fabric.io/public"
    )

  lazy val proguardCommons = Seq(
    "-ignorewarnings",
    "-keep class scala.Dynamic",
    "-keep class twitter4j.** { *; }",
    "-keep class org.joda.** { *; }",
    "-keep class org.ocpsoft.prettytime.i18n.**",
    "-keep class macroid.** { *; }",
    "-keep class com.google.** { *; }",
    "-keep class com.localytics.android.** { *; }",
    "-keep class com.google.android.gms.ads.** { *; }",
    "-keep class * extends java.util.ListResourceBundle { protected Object[][] getContents(); }",
    "-keep public class com.google.android.gms.common.internal.safeparcel.SafeParcelable { public static final *** NULL; }",
    "-keepnames @com.google.android.gms.common.annotation.KeepName class *",
    "-keepclassmembernames class * { @com.google.android.gms.common.annotation.KeepName *; }",
    "-keepnames class * implements android.os.Parcelable { public static final ** CREATOR; }",
    "-keep class com.crashlytics.** { *; }",
    "-dontwarn com.crashlytics.**")

}