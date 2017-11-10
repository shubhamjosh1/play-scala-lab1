name := """play-scala-form"""
organization := "com.hudsondata"

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.12.3"

libraryDependencies += guice
libraryDependencies += "org.scalatestplus.play" %% "scalatestplus-play" % "3.1.2" % Test


libraryDependencies ++= Seq(
  jdbc,
  "mysql" % "mysql-connector-java" % "5.1.41"
)

// Adds additional packages into Twirl
//TwirlKeys.templateImports += "com.hudsondata.controllers._"

// Adds additional packages into conf/routes
// play.sbt.routes.RoutesKeys.routesImport += "com.hudsondata.binders._"
