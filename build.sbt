

JsEngineKeys.engineType := JsEngineKeys.EngineType.Node

name := "playframework"
version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala, NewRelic)

libraryDependencies ++= Seq(
  "com.typesafe.play" %% "play-doc" % "1.8.2",
  "org.eclipse.jgit" % "org.eclipse.jgit" % "3.0.0.201306101825-r",
  "mysql" % "mysql-connector-java" % "5.1.47",
  "com.damnhandy" % "handy-uri-templates" % "2.1.7",
  "org.webjars" % "jquery" % "1.8.3",
  "org.webjars" % "html5shiv" % "3.7.3",
  "org.webjars" % "prettify" % "4-Mar-2013-1",
  "org.webjars" % "clipboard.js" % "1.5.5",
  "org.playframework.anorm" %% "anorm" % "2.6.2",
  guice,
  jdbc,
  ehcache,
  evolutions,
  filters,
  ws,
  specs2 % "test"
)

scalaVersion := "2.12.8"

scalacOptions ++= Seq(
  "-feature",
  "-deprecation"
)

routesGenerator := InjectedRoutesGenerator

StylusKeys.useNib in Assets := true
StylusKeys.compress in Assets := true

pipelineStages := Seq(digest, gzip)

sourceGenerators in Compile += Def.task {
  val siteVersionFile = crossTarget.value / "version" / "SiteVersion.scala"
  val gitHash = "git rev-parse HEAD".!!.trim
  if (!siteVersionFile.exists || !IO.read(siteVersionFile).contains(gitHash)) {
    IO.write(siteVersionFile,
      """package utils
        |
        |object SiteVersion {
        |  val hash = "%s"
        |}
      """.stripMargin.format(gitHash))
  }
  Seq(siteVersionFile)
}.taskValue

managedSourceDirectories in Compile += crossTarget.value / "version"
sources in (Compile, doc) := Seq.empty
publishArtifact in (Compile, packageDoc) := false

// NewRelic settings
newrelicVersion := "4.9.0"
newrelicAppName := "playframework.com"
