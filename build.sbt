name := """algorithms-service"""

version := "0.0.1-SNAPSHOT"

ThisBuild / scalaVersion := "2.11.12"

Compile/mainClass := Some("WebServer")
// mainClass in (Compile, run)

crossScalaVersions := Seq("2.9.2", "2.11.8", "2.11.12")

ThisBuild / resolvers += Resolver.mavenLocal

libraryDependencies ++= Seq(
	  "com.github.finagle" %% "finch-core" % "0.8.0",
    "io.github.willb611" % "algorithms" % "0.1-SNAPSHOT"
)

