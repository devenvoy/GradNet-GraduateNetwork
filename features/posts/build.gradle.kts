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
            baseName = "posts"
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

            implementation(libs.sonner)
            implementation(libs.bundles.coil)
            implementation(libs.bundles.koin)
            implementation(libs.bundles.paging)
            implementation(libs.bundles.voyager)
            implementation(compose.materialIconsExtended)
            implementation(libs.bundles.compose.settings)
            implementation(libs.sdp.ssp.compose.multiplatform)

            implementation(projects.shared)
            implementation(projects.commons)
            implementation(projects.data)
            implementation(projects.domain)


        }
    }
}

android {
    namespace = "com.sdjic.posts"
    compileSdk = libs.versions.android.compileSdk.get().toInt()

    defaultConfig {
        minSdk = libs.versions.android.minSdk.get().toInt()
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
}

dependencies {
    implementation(libs.androidx.material3.android)
    implementation(libs.androidx.ui.android)
}