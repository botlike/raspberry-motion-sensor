name := "rasp-check"

version := "2.0-open"

scalaVersion := "2.11.8"

lazy val akkaVersion = "2.4.3"

libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-actor" % akkaVersion,
  "com.typesafe.akka" %% "akka-testkit" % akkaVersion,
  "com.pi4j" % "pi4j-core" % "1.0",
  "com.pi4j" % "pi4j-gpio-extension" % "1.0",
  "com.pi4j" % "pi4j-device" % "1.0",
  "com.typesafe.akka" % "akka-http-core-experimental_2.11" % "2.0.3",
  "com.typesafe.akka" %% "akka-http-spray-json-experimental" % "2.4.3"
)

assemblyMergeStrategy in assembly := {
  case PathList("META-INF", xs @ _*) => MergeStrategy.discard
  case PathList("reference.conf") => MergeStrategy.concat
  case x => MergeStrategy.first
}

    