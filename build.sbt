import sbt.Keys._
import sbt._
import UnidocKeys._

name := "commercetools-sunrise"

organization in ThisBuild := "com.commercetools.sunrise"

scalaVersion in ThisBuild := "2.11.8"

javacOptions in ThisBuild ++= Seq("-source", "1.8", "-target", "1.8")

resolvers in ThisBuild ++= Seq (
  Resolver.sonatypeRepo("releases"),
  Resolver.sonatypeRepo("snapshots"),
  Resolver.mavenLocal
)

Release.publishSettings

Heroku.deploySettings

Version.generateVersionInfo

lazy val sunriseThemeVersion = "0.61.1"

lazy val jvmSdkVersion = "1.5.0"

lazy val jacksonVersion = "2.7.5"

val childProjects: List[sbt.ProjectReference] =
  List(common, `product-catalog`, `shopping-cart`, `my-account`, `move-to-sdk`, `sbt-tasks`)

lazy val `commercetools-sunrise` = (project in file("."))
  .enablePlugins(PlayJava)
  .settings(javadocSettings ++ Release.disablePublish: _*)
  .settings(sunriseDefaultThemeDependencies)
  .aggregate(childProjects: _*)
  .dependsOn(`product-catalog`, `shopping-cart`, `my-account`)

lazy val common = project
  .enablePlugins(PlayJava)
  .configs(IntegrationTest, TestCommon.PlayTest)
  .settings(TestCommon.defaultSettings: _*)
  .settings(jvmSdkDependencies ++ themeDependencies ++ sunriseModuleDependencies ++ baseDependencies: _*)
  .dependsOn(`move-to-sdk`)

lazy val `product-catalog` = project
  .enablePlugins(PlayJava)
  .configs(IntegrationTest, TestCommon.PlayTest)
  .settings(TestCommon.defaultSettings: _*)
  .dependsOn(commonWithTests)

lazy val `shopping-cart` = project
  .enablePlugins(PlayJava)
  .configs(IntegrationTest, TestCommon.PlayTest)
  .settings(TestCommon.defaultSettings: _*)
  .dependsOn(commonWithTests)

lazy val `my-account` = project
  .enablePlugins(PlayJava)
  .configs(IntegrationTest, TestCommon.PlayTest)
  .settings(TestCommon.defaultSettings: _*)
  .dependsOn(commonWithTests)

lazy val `sbt-tasks` = project
  .enablePlugins(PlayJava)
  .configs(IntegrationTest)
  .settings(TestCommon.settingsWithoutPlayTest: _*)
  .settings(unmanagedBase in Test := baseDirectory.value / "test" / "lib")

lazy val `move-to-sdk` = project
  .enablePlugins(PlayJava)
  .configs(IntegrationTest)
  .settings(TestCommon.settingsWithoutPlayTest ++ jvmSdkDependencies: _*)

lazy val commonWithTests: ClasspathDep[ProjectReference] = common % "compile;test->test;it->it;pt->pt"

lazy val javadocSettings = javaUnidocSettings ++ Seq (
  unidocProjectFilter in (JavaUnidoc, unidoc) := inProjects(childProjects: _*)
)

lazy val jvmSdkDependencies = Seq (
  libraryDependencies ++= Seq (
    "com.commercetools.sdk.jvm.core" % "commercetools-models" % jvmSdkVersion,
    "com.commercetools.sdk.jvm.core" % "commercetools-java-client" % jvmSdkVersion,
    "com.commercetools.sdk.jvm.core" % "commercetools-convenience" % jvmSdkVersion
  ),
  dependencyOverrides ++= Set (
    "com.fasterxml.jackson.core" % "jackson-annotations" % jacksonVersion,
    "com.fasterxml.jackson.core" % "jackson-core" % jacksonVersion,
    "com.fasterxml.jackson.core" % "jackson-databind" % jacksonVersion,
    "com.fasterxml.jackson.module" % "jackson-module-parameter-names" % jacksonVersion,
    "com.fasterxml.jackson.datatype" % "jackson-datatype-jsr310" % jacksonVersion
  )
)

lazy val themeDependencies = Seq (
  libraryDependencies ++= Seq (
    "org.webjars" %% "webjars-play" % "2.5.0-2",
    "com.github.jknack" % "handlebars" % "4.0.5"
  )
)

lazy val sunriseModuleDependencies = Seq (
  libraryDependencies ++= Seq (
    "com.commercetools.sunrise.cms" % "cms-api" % "0.1.0"
  )
)

lazy val baseDependencies = Seq (
  libraryDependencies ++= Seq (
    filters,
    cache,
    "commons-beanutils" % "commons-beanutils" % "1.9.2",
    "commons-io" % "commons-io" % "2.4"
  )
)

lazy val sunriseDefaultThemeDependencies = Seq (
  resolvers += Resolver.bintrayRepo("commercetools", "maven"),
  libraryDependencies ++= Seq (
    "com.commercetools.sunrise" % "commercetools-sunrise-theme" % sunriseThemeVersion
  )
)