name := """ProxyClientFacebook"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayJava)

scalaVersion := "2.11.7"

routesGenerator := InjectedRoutesGenerator

unmanagedResourceDirectories in Test <+= baseDirectory ( _ /"target/web/public/test" )

libraryDependencies ++= Seq(
  javaJdbc,
  cache,
  javaWs
)
libraryDependencies += "org.mockito" % "mockito-all" % "1.10.19"