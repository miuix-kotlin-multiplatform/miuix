## Miuix

Miuix is a shared UI component based on [Compose Multiplatform](https://www.jetbrains.com/compose-multiplatform/).

Now Supported: **Android** / **Desktop(JVM)** / **iOS** / **WasmJs** / **Js** / **macOS**.

### Start

```
kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation("top.yukonga.miuix.kmp:miuix:<version>")
            // Other dependencies...
        }
        // Other sourceSets...
    }
    // Other configurations...
}
```

[![Maven Central](https://img.shields.io/maven-central/v/top.yukonga.miuix.kmp/miuix)](https://search.maven.org/search?q=g:top.yukonga.miuix.kmp)

### Usage

```
@Composable
fun App() {
    MiuixTheme(
        colors = if (isSystemInDarkTheme()) darkColorScheme() else lightColorScheme()
    ) {
        Scaffold(
            topBar = {
                // TopBar
            },
            bottomBar = {
                // BottomBar
            },
            floatingActionButton = {
                // FloatingActionButton
            },
        ) {
            // Other Content...
        }
    }
}
```

### Screenshot

[![demo](https://github.com/miuix-kotlin-multiplatform/miuix/blob/main/screenshot/demo.png?raw=true)](#)