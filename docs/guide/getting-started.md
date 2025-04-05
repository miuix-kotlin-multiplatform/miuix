# Getting Started

Supported platforms: **Android** / **Desktop (JVM)** / **iOS** / **WasmJs** / **Js** / **macOS (Native)**

::: warning
This library is experimental, and APIs may change in future versions without notice.
:::

## Adding Dependencies

To use Miuix in your project, follow these steps to add dependencies:

### Gradle (Kotlin DSL)

1. Add the following to the root `settings.gradle.kts` file (usually already included):
```kotlin
repositories {
    mavenCentral()
}
```

2. Check the latest version on Maven Central:
[![Maven Central](https://img.shields.io/maven-central/v/top.yukonga.miuix.kmp/miuix)](https://search.maven.org/search?q=g:top.yukonga.miuix.kmp)

3. Add dependencies to your project's `build.gradle.kts`:

- For Compose Multiplatform projects:
```kotlin
kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation("top.yukonga.miuix.kmp:miuix:<version>")
        }
    }
}
```

- For Android Compose projects:
```kotlin
dependencies {
    implementation("top.yukonga.miuix.kmp:miuix-android:<version>")
}
```

- For other projects, add platform-specific dependencies as needed:
```kotlin
implementation("top.yukonga.miuix.kmp:miuix-android:<version>")
implementation("top.yukonga.miuix.kmp:miuix-iosarm64:<version>")
implementation("top.yukonga.miuix.kmp:miuix-iosx64:<version>")
implementation("top.yukonga.miuix.kmp:miuix-iossimulatorarm64:<version>")
implementation("top.yukonga.miuix.kmp:miuix-macosx64:<version>")
implementation("top.yukonga.miuix.kmp:miuix-macosarm64:<version>")
implementation("top.yukonga.miuix.kmp:miuix-desktop:<version>")
implementation("top.yukonga.miuix.kmp:miuix-wasmjs:<version>")
implementation("top.yukonga.miuix.kmp:miuix-js:<version>")
```

## Basic Usage

### Applying the Miuix Theme

```kotlin
@Composable
fun App() {
    val colors = if (isSystemInDarkTheme()) {
        darkColorScheme()
    } else {
        lightColorScheme()
    }
    MiuixTheme(
        colors = colors
    ) {
        // Other Content...
    }
}
```

### Using the Miuix Scaffold

```kotlin
Scaffold(
    topBar = {
        // TopBar
    },
    bottomBar = {
        // BottomBar
    },
    floatingActionButton = {
        // FloatingActionButton
    }
) {
    // Content...
}
```

::: warning
The Scaffold component provides a suitable container for cross-platform popup windows. Components such as `SuperDialog`, `SuperDropdown`, `SuperSpinner`, and `ListPopup` are all implemented based on this and therefore need to be wrapped by this component.
:::

## API Documentation
- View the [API Documentation](/miuix/dokka/index.html){target="_blank"}, generated using Dokka, which contains detailed information about all APIs.
