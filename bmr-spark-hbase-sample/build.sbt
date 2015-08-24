import AssemblyKeys._

name := "bmr-spark-hbase-sample"

version := "0.0.1-SNAPSHOT"

scalaVersion := "2.10.4"

organization := "com.baidu.bce"

resolvers ++= Seq(
      "Typesafe Repository" at "http://repo.typesafe.com/typesafe/releases/"
    )
    
libraryDependencies ++= {
  val sparkV = "1.4.1"
  val hbaseV = "0.98.0-hadoop2"
  Seq(
    "org.apache.spark" % "spark-core_2.10" % sparkV % "provided",
    "org.apache.hbase" % "hbase-common" % hbaseV,
    "org.apache.hbase" % "hbase-client" % hbaseV,
    "org.apache.hbase" % "hbase-server" % hbaseV,
    "org.apache.hbase" % "hbase-protocol" % hbaseV,
    "org.slf4j" % "slf4j-api" % "1.6.1",
    "org.slf4j" % "slf4j-log4j12" % "1.6.1",
    "org.specs2" %% "specs2" % "2.3.4" % "test",
    "org.scalatest" % "scalatest_2.10" % "2.0" % "test"
  )
}

scalacOptions ++= Seq("-unchecked", "-deprecation")

logLevel := Level.Info

EclipseKeys.createSrc := EclipseCreateSrc.Default + EclipseCreateSrc.Resource


assemblySettings

jarName in assembly := "bmr-spark-hbase-sample.jar"

test in assembly := {}

mergeStrategy in assembly <<= (mergeStrategy in assembly) { (old) =>
  {
    case PathList("javax", "servlet", xs @ _*) => MergeStrategy.first
    case PathList("javax", "xml","stream", xs @ _*) => MergeStrategy.first
    case PathList("org","apache","jasper", xs @ _*) => MergeStrategy.first
    case x => old(x)
  }
}
