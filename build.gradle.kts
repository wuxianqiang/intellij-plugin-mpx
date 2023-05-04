plugins {
  id("java")
  id("org.jetbrains.kotlin.jvm") version "1.5.10"
  id("org.jetbrains.intellij") version "1.10.1"
}

group = "com.hxz"
version = "2.1"

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

// Configure Gradle IntelliJ Plugin - read more: https://github.com/JetBrains/gradle-intellij-plugin
intellij {
  version.set("2023.1.1")
  type.set("IU") // Target IDE Platform
  pluginName.set("Mpx")
  downloadSources.set(true)
  updateSinceUntilBuild.set((false))
  plugins.set(listOf("JavaScript", "com.intellij.css", "intellij.webpack", "org.jetbrains.plugins.sass", "org.jetbrains.plugins.less", "org.jetbrains.plugins.stylus:231.8109.91", "intellij.prettierJS"))
}

tasks {
  // Set the JVM compatibility versions
  withType<JavaCompile> {
    sourceCompatibility = "11"
    targetCompatibility = "11"
  }
  withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    kotlinOptions.jvmTarget = "17"
    kotlinOptions.freeCompilerArgs = listOf("-Xjvm-default=compatibility")
  }

  patchPluginXml {
    sinceBuild.set("231.8109.197")
    untilBuild.set("231.*")
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
