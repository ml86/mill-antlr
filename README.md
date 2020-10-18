# mill-antlr
ANTLR support for mill builds.

## Usage
### Mandatory

Add dependency and import AntlrModule class in build.sc:
```
import $ivy.`net.mlbox::mill-antlr:0.1.0`
import net.mlbox.millantlr.AntlrModule
```

Mixin `AntlrModule` trait into your project object:
```
object foo extends ScalaModule with AntlrModule {...}
```

Specify ANTLR grammar files or directory:
```
... with AntlrModule {
  override def antlrGrammarSources = T.sources {
    Seq(os.pwd/"grammar1.g4", os.pwd/"grammar2.g4", os.pwd/"grammarDir").map(PathRef(_))
  }
}
```

### Optional

Generate ANTLR visitor/listener interface:
```
... with AntlrModule {
  override def antlrGenerateVisitor: Boolean = true
  override def antlrGenerateListener: Boolean = true
}
```

Specify package name of genrate ANTLR parser classes:
```
... with AntlrModule {
  override def antlrPackage: Option[String] = Some("net.mlbox")
}
```

