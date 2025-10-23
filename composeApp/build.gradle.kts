import org.gradle.kotlin.dsl.implementation
import org.jetbrains.compose.desktop.application.dsl.TargetFormat
import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import java.io.FileInputStream
import java.util.Properties

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
    alias(libs.plugins.composeHotReload)
}

kotlin {
    androidTarget {
        @OptIn(ExperimentalKotlinGradlePluginApi::class)
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_11)
        }
    }
    
    jvm()
    
    sourceSets {
        androidMain.dependencies {
            implementation(compose.preview)
            implementation(libs.androidx.activity.compose)
            implementation("com.google.accompanist:accompanist-permissions:0.30.1")
            implementation("androidx.datastore:datastore-preferences:1.1.1")
            implementation("com.russhwolf:multiplatform-settings-datastore:1.1.1")

        }
        commonMain.dependencies {
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material3)
            implementation(compose.ui)
            implementation(compose.components.resources)
            implementation(compose.components.uiToolingPreview)
            implementation(libs.androidx.lifecycle.viewmodelCompose)
            implementation(libs.androidx.lifecycle.runtimeCompose)

            implementation("cafe.adriel.voyager:voyager-navigator:1.1.0-beta02")

            implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.10.2")

            // Preferences -------->>
            implementation("com.russhwolf:multiplatform-settings:1.1.1")

            implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.6.0")

            implementation("org.jetbrains.kotlinx:kotlinx-datetime:0.6.1")
        }
        commonTest.dependencies {
            implementation(libs.kotlin.test)
        }
        jvmMain.dependencies {
            implementation(compose.desktop.currentOs)
            implementation(libs.kotlinx.coroutinesSwing)
        }
    }
}

val keystorePropertiesFile = rootProject.file("local.properties")
val keystoreProperties = Properties()
keystoreProperties.load(FileInputStream(keystorePropertiesFile))

android {
    signingConfigs {
        create("release") {
            keyAlias = keystoreProperties["keyAlias"] as String
            keyPassword = keystoreProperties["keyPassword"] as String
            storeFile = file(keystoreProperties["storeFile"] as String)
            storePassword = keystoreProperties["storePassword"] as String
        }
    }
    namespace = "com.samuel.oremoschanganapt"
    compileSdk = libs.versions.android.compileSdk.get().toInt()

    defaultConfig {
        applicationId = "com.samuel.oremoschanganapt"
        minSdk = libs.versions.android.minSdk.get().toInt()
        targetSdk = libs.versions.android.targetSdk.get().toInt()
        versionCode = 9
        versionName = "5.1"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(
                getDefaultProguardFile("proguard-android.txt"),
                "proguard-rules.pro"
            )

            ndk {
                debugSymbolLevel = "FULL" //Generate full debug symbols
            }
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
            excludes += "**/*.version"
        }
        jniLibs {
            useLegacyPackaging = false // Importante para compatibilidade
        }
    }
    lint {
        disable += "NullSafeMutableLiveData"
    }
//    signingConfig = signingConfigs.getByName("release")
}

dependencies {
    debugImplementation(compose.uiTooling)
}

compose.desktop {
    application {
        mainClass = "com.samuel.oremoschanganapt.MainKt"

        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "Oremos Changana PT"
            packageVersion = "5.0.1"

            windows {
                iconFile.set(project.file("src/jvmMain/resources/windowsIcon.ico"))
                shortcut = true
                menuGroup = "Education"
            }
            macOS {
                iconFile.set(project.file("src/commonMain/composeResources/drawable/icon.webp"))
            }
            linux {
                shortcut = true
                appCategory = "Education"
                iconFile.set(project.file("src/jvmMain/resources/linuxIcon.png"))

                menuGroup = "Education"
            }
        }
    }
}
