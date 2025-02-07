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
            jvmTarget.set(JvmTarget.JVM_17)
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
        }
    }

    sourceSets {
        androidMain.dependencies {
            implementation(compose.preview)
            implementation(libs.androidx.activity.compose)

            implementation(compose.uiTooling)
            implementation(libs.ktor.client.okhttp)
            implementation("com.github.chuckerteam.chucker:library:4.1.0")
        }

        commonMain.dependencies {
            implementation(compose.ui)
            implementation(compose.runtime)
            implementation(compose.material3)
            implementation(compose.foundation)
            implementation(compose.components.resources)
            implementation(libs.androidx.lifecycle.viewmodel)
            implementation(compose.components.uiToolingPreview)
            implementation(libs.androidx.lifecycle.runtime.compose)

            implementation(libs.retrofit)

            implementation(libs.mvvm.core)

            implementation(libs.kotlinx.datetime)

            implementation(compose.materialIconsExtended)

            implementation(libs.kotlinx.serialization.json.v141)
            implementation(libs.kotlinx.coroutines.core.v164)
            implementation(libs.sdp.ssp.compose.multiplatform)

            implementation(libs.bundles.koin)
            implementation(libs.bundles.ktor)
            implementation(libs.bundles.coil)
            api(libs.bundles.moko.permissions)
            implementation(libs.bundles.paging)
            implementation(libs.bundles.voyager)
            implementation(libs.bundles.compottie)
            implementation(libs.bundles.connectivity)
            implementation(libs.bundles.androidx.room)
            implementation(libs.bundles.compose.settings)
            implementation(libs.bundles.multiplatform.settings)

            implementation(libs.sonner)
            implementation(libs.composeIcons.featherIcons)

            implementation(libs.kermit)
            implementation(libs.kstore)

            implementation(libs.kmp.date.time.picker)
            implementation(libs.cmp.image.pick.n.crop)

            implementation(libs.kmpauth.google)
            implementation(libs.kmpauth.uihelper)

            implementation("com.github.skydoves:orbital:0.4.0")
            implementation(project(":color"))
        }

        iosMain.dependencies {
            implementation(libs.ktor.client.darwin)
            implementation("app.cash.paging:paging-runtime-uikit:3.3.0-alpha02-0.5.1")
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
        isCoreLibraryDesugaringEnabled = true
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
}

dependencies {
    debugImplementation(compose.uiTooling)
}


buildConfig {
    // BuildConfig configuration here.
    buildConfigField("APP_NAME", project.project.name)
    buildConfigField("APP_VERSION_CODE", project.version.toString())
    buildConfigField("APP_VERSION_NAME", project.version.toString())
    buildConfigField("BASE_URL", "https://grednet-production.up.railway.app")
}

ksp {
    arg("room.schemaLocation", "${projectDir}/schemas")
}

dependencies {
    coreLibraryDesugaring(libs.desugar)
}

dependencies {

    implementation(libs.androidx.material3.android)
    implementation(libs.androidx.ui.android)
    // Android
    add("kspAndroid", libs.room.compiler)

    // iOS
    add("kspIosSimulatorArm64", libs.room.compiler)
    add("kspIosX64", libs.room.compiler)
    add("kspIosArm64", libs.room.compiler)
}