organization := "com.optrak"

name := "raptureExample"

version := "0.1-SNAPSHOT"

resolvers ++= Seq(
  "snapshots" at "http://oss.sonatype.org/content/repositories/snapshots",
  "sonatype.releases" at "https://oss.sonatype.org/content/repositories/releases/",
  "Typesafe Repository" at "http://repo.typesafe.com/typesafe/releases/",
  "SS" at "http://oss.sonatype.org/content/repositories/snapshots/",
  "Scalaz Bintray Repo" at "http://dl.bintray.com/scalaz/releases")

scalaVersion := "2.11.2"

libraryDependencies ++= {
  Seq(
    "org.slf4j" % "slf4j-api" % "1.7.7",
    "ch.qos.logback" % "logback-classic" % "1.1.2", 
    "org.clapper" %% "grizzled-slf4j" % "1.0.2",
    //"org.json4s" %% "json4s-native" % "3.2.10",*/
    "com.propensive" %% "rapture-json-json4s" % "1.0.6",
    "org.scalaz" %% "scalaz-core" % "7.1.0",
    "com.propensive" %% "rapture-core-scalaz" % "1.0.0",
    // testing libraries
    "org.specs2" %% "specs2" % "2.4.6" % "test"  
  )
}

scalacOptions ++= Seq("-feature", "-deprecation")

parallelExecution in Test := false

exportJars := true
