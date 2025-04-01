

# 快速开始

## 使用须知

当前支持的平台: **Android** / **Desktop(JVM)** / **iOS** / **WasmJs** / **Js** / **macOS(Native)**

::: warning 注意
此库处于实验阶段，API 可能会在未来版本中变更而不另行通知
:::

## 添加依赖

要在您的项目中使用 Miuix，请按照以下步骤添加依赖：

### Gradle (Kotlin DSL) 

1. 在根目录的 settings.gradle.kts 添加（正常情况应已包含）：
```kotlin
repositories {
    mavenCentral()
}
```

2. 检查 Maven Central 当前最新版本：
[![Maven Central](https://img.shields.io/maven-central/v/top.yukonga.miuix.kmp/miuix)](https://search.maven.org/search?q=g:top.yukonga.miuix.kmp)

3. 在项目的 build.gradle.kts 中添加依赖：

- 在 Compose Multiplatform 项目目录的 build.gradle.kts 中：
```kotlin
kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation("top.yukonga.miuix.kmp:miuix:<version>")
        }
    }
}

```

- 在 Android Compose 项目目录的 build.gradle.kts 中：
```kotlin
dependencies {
    implementation("top.yukonga.miuix.kmp:miuix-android:<version>")
}
```

- 在其他常规项目中使用，则只需要根据需要添加对应平台后缀的依赖即可：
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

## 基本用法

### 1. 应用 Miuix 主题

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

### 2. 使用 Miuix 脚手架

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

## 下一步

- 查看[组件库](/components/index)了解所有可用组件
- 浏览[示例应用](https://github.com/YuKongA/miuix-kotlin-multiplatform/tree/main/example)获取实际使用案例
