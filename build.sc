import mill._
import mill.scalalib._
import publish._

import $ivy.`de.tototec::de.tobiasroeser.mill.integrationtest:0.3.3`
import de.tobiasroeser.mill.integrationtest._

import scala.collection.immutable.ListMap

trait Deps {
  def millVersion = "0.7.0"
  def scalaVersion = "2.13.2"

  val millMain = ivy"com.lihaoyi::mill-main:${millVersion}"
  val millScalalib = ivy"com.lihaoyi::mill-scalalib:${millVersion}"
}
object Deps_0_7 extends Deps
object Deps_0_6 extends Deps {
  override def millVersion = "0.6.0"
  override def scalaVersion = "2.12.10"
}

val millApiVersions: Map[String, Deps] = ListMap(
  "0.7" -> Deps_0_7,
  "0.6" -> Deps_0_6
)

val millItestVersions = Seq(
  "0.7.4", "0.7.3", "0.7.2", "0.7.1", "0.7.0",
  "0.6.3", "0.6.2", "0.6.1", "0.6.0"
)

object `mill-antlr` extends mill.Cross[`mill-antlrCross`](millApiVersions.keysIterator.toSeq: _*)
class `mill-antlrCross`(val millApiVersion: String) extends CrossScalaModule with PublishModule {
  def deps: Deps = millApiVersions(millApiVersion)
  override def crossScalaVersion = deps.scalaVersion
  override def compileIvyDeps = Agg(
    deps.millMain,
    deps.millScalalib,
  )

  def millSourcePath = os.pwd

  def ivyDeps = Agg(
    ivy"org.antlr:antlr4:4.8-1",
  )

  def publishVersion = "0.0.1"

  def artifactName = "mill-antlr"
 
  def pomSettings = PomSettings(
    description = "Antlr support for mill builds.",
    organization = "net.mlbox",
    url = "https://github.com/ml86/mill-antlr",
    licenses = Seq(License.MIT),
    versionControl = VersionControl.github("ml86", "mill-antlr"),
    developers = Seq(
      Developer("ml86", "Markus Lottmann", "https://github.com/ml86")
    )
  )
}

object itest extends Cross[ItestCross](millItestVersions: _*)
class ItestCross(millItestVersion: String)  extends MillIntegrationTestModule {
  val millApiVersion = millItestVersion.split("[.]").take(2).mkString(".")
  override def millSourcePath: os.Path = super.millSourcePath / os.up
  override def millTestVersion = millItestVersion
  override def pluginsUnderTest = Seq(`mill-antlr`(millApiVersion))
}
