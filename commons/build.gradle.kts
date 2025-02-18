plugins {

    kotlin("multiplatform")
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.compose)
    alias(libs.plugins.composeCompiler)
    alias(libs.plugins.kotlin.serialization)
}

kotlin {
    androidTarget()

    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "shared"
            isStatic = true
        }
    }

    sourceSets {

        commonMain.dependencies {
            implementation(compose.ui)
            implementation(compose.runtime)
            implementation(compose.material3)
            implementation(compose.foundation)
            implementation(compose.components.resources)
            implementation(compose.components.uiToolingPreview)
            implementation(libs.androidx.lifecycle.runtime.compose)

            implementation(libs.kotlinx.datetime)

            implementation(libs.bundles.koin)
            implementation(libs.sonner)
            implementation(libs.bundles.coil)
            implementation(libs.bundles.paging)
            implementation(libs.bundles.voyager)
            implementation(libs.bundles.compottie)
            implementation(libs.bundles.connectivity)
            implementation(compose.materialIconsExtended)
            implementation(libs.composeIcons.featherIcons)
            implementation(libs.sdp.ssp.compose.multiplatform)

            implementation(project(":shared"))
        }
    }
}

android {
    namespace = "com.sdjic.shared"
    compileSdk = libs.versions.android.compileSdk.get().toInt()

    defaultConfig {
        minSdk = libs.versions.android.minSdk.get().toInt()
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
}

compose.resources {
    publicResClass = true
    packageOfResClass = "com.sdjic.shared.resources"
    generateResClass = auto
}


dependencies {
    implementation(libs.androidx.material3.android)
    implementation(libs.androidx.ui.android)
}