name := "my-risk-assessment-sample"

organization := "com.example"

version := "1.0"

scalaVersion := "2.11.8"

// Change this to another test framework if you prefer
libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-actor" % "2.4.11",
  "org.scalatest" %% "scalatest" % "3.0.0" % "test"
)
