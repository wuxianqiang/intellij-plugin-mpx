plugins {
  id("java")
  id("org.jetbrains.kotlin.jvm") version "1.5.10"
  id("org.jetbrains.intellij") version "1.4.0"
}

dependencies {
  implementation("org.jetbrains.kotlin:kotlin-stdlib:1.5.10")
}

group = "com.hxz"
version = "1.0-SNAPSHOT"

repositories {
  mavenCentral()
}

sourceSets {
  main {
    java {
      srcDirs("src")
      srcDirs("gen")
    }
    resources {
      srcDirs("resources")
    }
  }
}
//"intellij.prettierJS:212.4746.2"
// Configure Gradle IntelliJ Plugin - read more: https://github.com/JetBrains/gradle-intellij-plugin
intellij {
  version.set("2021.2")
  type.set("IU") // Target IDE Platform
  pluginName.set("Mpx")
  downloadSources.set(true)
  updateSinceUntilBuild.set((false))
  plugins.set(listOf("JavaScriptLanguage", "JSIntentionPowerPack", "JavaScriptDebugger", "CSS", "HtmlTools",
    "org.jetbrains.plugins.sass", "org.jetbrains.plugins.less", "org.jetbrains.plugins.stylus",
    "org.intellij.plugins.postcss:212.4746.2",
    "com.jetbrains.plugins.Jade:212.4746.2",
    "intellij.prettierJS:212.4746.2"))
}

tasks {
  // Set the JVM compatibility versions
  withType<JavaCompile> {
    sourceCompatibility = "11"
    targetCompatibility = "11"
  }
  withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    kotlinOptions.jvmTarget = "11"
    kotlinOptions.freeCompilerArgs = listOf("-Xjvm-default=compatibility")
  }

  patchPluginXml {
    sinceBuild.set("212")
    untilBuild.set("222.*")
  }

  signPlugin {
    certificateChain.set(System.getenv("CERTIFICATE_CHAIN"))
    privateKey.set(System.getenv("PRIVATE_KEY"))
    password.set(System.getenv("PRIVATE_KEY_PASSWORD"))
  }

  publishPlugin {
    token.set(System.getenv("PUBLISH_TOKEN"))
  }
}

