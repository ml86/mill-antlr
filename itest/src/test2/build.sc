import mill._
import mill.define._
import mill.scalalib._
import $exec.plugins
import net.mlbox.millantlr.AntlrModule

object main extends AntlrModule {
  def scalaVersion = "2.13.1"

  override def antlrGrammarSources = T.sources {
    Seq(os.pwd/"grammar1.g4", os.pwd/"grammar2.g4").map(PathRef(_))
  }

  override def antlrPackage: Option[String] = Some("net.mlbox")

  override def antlrGenerateVisitor: Boolean = true

}

def verify(): Command[Unit] = T.command {
  val g1Exists = main.generatedSources().exists(_.path.segments.toList.last == "grammar1Visitor.java")
  if (!g1Exists) {
    throw new RuntimeException("grammar1Visitor.java does not exist.")
  }
  val g2Exists = main.generatedSources().exists(_.path.segments.toList.last == "grammar2Visitor.java")
  if (!g2Exists) {
    throw new RuntimeException("grammar2Visitor.java does not exist.")
  }
  ()
}
