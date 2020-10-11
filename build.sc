import mill._
import mill.scalalib._
import publish._

object `mill-antlr` extends ScalaModule with PublishModule {
  def scalaVersion = "2.13.1"
  def millSourcePath = os.pwd

  def ivyDeps = Agg(
    ivy"com.lihaoyi::mill-scalalib:0.8.0",
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

import $ivy.`de.tototec::de.tobiasroeser.mill.integrationtest:0.3.3`
import de.tobiasroeser.mill.integrationtest._

object itest extends MillIntegrationTestModule {

  def millTestVersion = "0.7.4"

  def pluginsUnderTest = Seq(`mill-antlr`)
}
