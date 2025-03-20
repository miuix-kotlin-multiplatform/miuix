## Miuix

**Miuix** is a shared UI library for [Compose Multiplatform](https://www.jetbrains.com/compose-multiplatform/).

Now Supported: **Android** / **Desktop(JVM)** / **iOS** / **WasmJs** / **Js** / **macOS(Native)**.

> This library is experimental, any API would be changed in the future without any notification.

[![License](https://img.shields.io/github/license/miuix-kotlin-multiplatform/miuix)](LICENSE)
[![Maven Central](https://img.shields.io/maven-central/v/top.yukonga.miuix.kmp/miuix)](https://search.maven.org/search?q=g:top.yukonga.miuix.kmp)

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

[![screenshot](https://github.com/miuix-kotlin-multiplatform/miuix/blob/main/assets/screenshot.webp?raw=true)]()
