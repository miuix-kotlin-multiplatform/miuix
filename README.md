## MiuiX

MiuiX is a shared UI component based on [Compose Multiplatform](https://www.jetbrains.com/compose-multiplatform/). 
Currently supports Android / iOS / Desktop / WebAssembly / macOS.

### Start

```
implementation("top.yukonga.miuix.kmp:miuix:<version>")
```

[![Maven Central](https://img.shields.io/maven-central/v/top.yukonga.miuix.kmp/miuix)](https://search.maven.org/search?q=g:top.yukonga.miuix.kmp)

### Usage

```
@Composable
fun App() {
   MiuixTheme(
        colorScheme = if (isSystemInDarkTheme()) darkColorScheme() else lightColorScheme()
    ) {
       // Other content...
    }
}
```
