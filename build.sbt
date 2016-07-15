name := """algorithms-service"""

version := "0.0.1-SNAPSHOT"

scalaVersion := "2.11.5"

mainClass in (Compile, run) := Some("WebServer") 

crossScalaVersions := Seq("2.9.2", "2.11.5")

resolvers += Resolver.mavenLocal

libraryDependencies ++= Seq(
	  "com.github.finagle" %% "finch-core" % "0.5.0",
    "io.github.willb611" % "algorithms" % "0.1-SNAPSHOT"
)

