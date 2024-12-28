import org.jetbrains.compose.desktop.application.dsl.TargetFormat
import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)

// added
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.ksp)
    alias(libs.plugins.buildConfig)
}

kotlin {
    androidTarget {
        @OptIn(ExperimentalKotlinGradlePluginApi::class)
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_11)
        }
    }

    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "ComposeApp"
            isStatic = true
            linkerOpts.add("-lsqlite3")
            linkerOpts.add("-framework AVFoundation")
        }
    }

    sourceSets {
        androidMain.dependencies {
            implementation(compose.preview)
            implementation(libs.androidx.activity.compose)

            implementation(compose.uiTooling)
            implementation(libs.ktor.client.okhttp)
        }

        commonMain.dependencies {
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.ui)
            implementation(compose.components.resources)
            implementation(compose.components.uiToolingPreview)
            implementation(libs.androidx.lifecycle.viewmodel)
            implementation(libs.androidx.lifecycle.runtime.compose)

            // Navigator
            implementation(libs.voyager.navigator)
            implementation(libs.voyager.screenModel)
            implementation(libs.voyager.transitions)

            implementation(libs.koin.core)
            implementation(libs.koin.compose)

            implementation(compose.material3)
            implementation(compose.materialIconsExtended)
            implementation(libs.mvvm.core)

//            implementation("network.chaintech:cmp-preference:1.0.0")

            // #1 - Basic settings
            implementation(libs.multiplatform.settings.no.arg)

            // #2 - For custom class serialization
            implementation(libs.kotlinx.serialization.json.v141)
            implementation(libs.multiplatform.settings.serialization)

            // #3 - For observing values as flows
            implementation(libs.kotlinx.coroutines.core.v164)
            implementation(libs.multiplatform.settings.coroutines)

            implementation(libs.kotlinx.datetime)

            implementation(libs.sdp.ssp.compose.multiplatform)
            implementation(libs.cmptoast)

            implementation(libs.room.runtime)
            implementation(libs.sqlite.bundled)

            implementation(libs.retrofit)

            implementation(libs.coil.compose)
            implementation(libs.coil.network.ktor)

            implementation(libs.ktor.client.core)
            implementation(libs.ktor.client.logging)
            implementation(libs.ktor.serialization.kotlinx.json)
            implementation(libs.ktor.client.content.negotiation)
            implementation(libs.ktor.client.encoding)
            implementation(libs.ktor.client.serialization)

            implementation(libs.materialcolors)
            implementation (libs.fontawesomecompose)
            implementation(libs.composeIcons.featherIcons)

            api(libs.moko.permissions)
            api(libs.moko.permissions.compose)

            implementation(libs.kermit)
            implementation(libs.kstore)

            implementation(libs.compottie)
            implementation(libs.compottie.dot)
            implementation(libs.compottie.network)
            implementation(libs.compottie.resources)

            implementation(libs.kmp.date.time.picker)

            implementation(libs.peekaboo.ui)
            implementation(libs.peekaboo.image.picker)

        }

        iosMain.dependencies {
            implementation(libs.ktor.client.darwin)
        }
    }
}

android {
    namespace = "com.sdjic.gradnet"
    compileSdk = libs.versions.android.compileSdk.get().toInt()

    defaultConfig {
        applicationId = "com.sdjic.gradnet"
        minSdk = libs.versions.android.minSdk.get().toInt()
        targetSdk = libs.versions.android.targetSdk.get().toInt()
        versionCode = 1
        versionName = "1.0"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

dependencies {
    debugImplementation(compose.uiTooling)
}


buildConfig {
    // BuildConfig configuration here.
    // https://github.com/gmazzo/gradle-buildconfig-plugin#usage-in-kts
}

ksp {
    arg("room.schemaLocation", "${projectDir}/schemas")
}

dependencies {

    // Android
    add("kspAndroid", libs.room.compiler)

    // iOS
    add("kspIosSimulatorArm64", libs.room.compiler)
    add("kspIosX64", libs.room.compiler)
    add("kspIosArm64", libs.room.compiler)
}