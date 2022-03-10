import mill._
import mill.scalalib._
import publish._

import $ivy.`de.tototec::de.tobiasroeser.mill.vcs.version::0.1.4`
import de.tobiasroeser.mill.vcs.version.VcsVersion

import $ivy.`de.tototec::de.tobiasroeser.mill.integrationtest::0.4.2`
import de.tobiasroeser.mill.integrationtest._

import scala.collection.immutable.ListMap

val millVersions = Seq("0.9.3", "0.10.0")
val millBinaryVersions = millVersions.map(millBinaryVersion)

def millBinaryVersion(millVersion: String) = millVersion.split('.').take(2).mkString(".")
def millVersion(binaryVersion: String) =
  millVersions.find(v => millBinaryVersion(v) == binaryVersion).get

val millItestVersions = Seq(
  "0.9.12", "0.10.1"
)

object `mill-antlr` extends mill.Cross[`mill-antlrCross`](millBinaryVersions: _*)
class `mill-antlrCross`(val millBinaryVersion: String) extends ScalaModule with PublishModule {
  override def scalaVersion = "2.13.8"
  override def compileIvyDeps = Agg(
    ivy"com.lihaoyi::mill-main:${millVersion(millBinaryVersion)}",
    ivy"com.lihaoyi::mill-scalalib:${millVersion(millBinaryVersion)}",
  )

  def millSourcePath = os.pwd

  def ivyDeps = Agg(
    ivy"org.antlr:antlr4:4.8-1",
  )

  def publishVersion = VcsVersion.vcsState().format()

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
  override def millSourcePath: os.Path = super.millSourcePath / os.up
  override def millTestVersion = millItestVersion
  override def pluginsUnderTest = Seq(`mill-antlr`(millBinaryVersion(millItestVersion)))
}
