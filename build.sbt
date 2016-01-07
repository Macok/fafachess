name := "fafachess"

version := "1.0"

scalaVersion := "2.11.7"


mainClass in assembly := Some("fafa.Main")

libraryDependencies += "com.typesafe.akka" %% "akka-actor" % "2.4.1"
libraryDependencies += "org.scalatest" %% "scalatest" % "2.2.5"
libraryDependencies += "ch.qos.logback" % "logback-classic" % "1.1.3"
libraryDependencies += "com.typesafe.akka" % "akka-slf4j_2.11" % "2.4.1"

