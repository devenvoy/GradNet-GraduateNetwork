import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)

//   added
    alias(libs.plugins.ksp)
//    alias(libs.plugins.swiftklib)
    alias(libs.plugins.buildConfig)
    alias(libs.plugins.kotlinCocoapods)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.google.gms.google.services)
}

kotlin {
   androidTarget()

    cocoapods {
        summary = "Some description for the Shared Module"
        homepage = "Link to the Shared Module homepage"
        version = "1.0"
        ios.deploymentTarget = "16.0"
        podfile = project.file("../iosApp/Podfile")

        framework {
            baseName = "composeApp"
            isStatic = true
            export(libs.kmpNotifier)
            @OptIn(ExperimentalKotlinGradlePluginApi::class)
            transitiveExport = true

            linkerOpts.add("-lsqlite3")
//            linkerOpts.add("-Xbinary=bundleId=com.example")
//            binaryOption("bundleId", "com.example")

        }
    }

    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            export(libs.kmpNotifier)
            baseName = "ComposeApp"
            isStatic = true
            linkerOpts.add("-lsqlite3")
        }
//        iosTarget.compilations {
//            val main by getting {
//                cinterops {
//                    create("IosHelper")
//                }
//            }
//        }
    }

    sourceSets {
        androidMain.dependencies {
            implementation(compose.preview)
            implementation(libs.androidx.activity.compose)

            implementation(compose.uiTooling)
            implementation(libs.ktor.client.okhttp)
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
//            implementation(libs.bundles.paging)
            implementation(libs.bundles.voyager)
            implementation(libs.bundles.compottie)
            implementation(libs.bundles.connectivity)
            implementation(libs.bundles.androidx.room)
            implementation(libs.bundles.compose.settings)
            implementation(libs.bundles.multiplatform.settings)

            implementation(libs.sonner)
            implementation(libs.composeIcons.fontAwesome)

            implementation(libs.kermit)
            implementation(libs.kstore)

            implementation(libs.kmp.date.time.picker)
            implementation(libs.cmp.image.pick.n.crop)

            implementation(libs.kmpauth.google)
            implementation(libs.kmpauth.uihelper)
            implementation(libs.richeditor.compose)

            implementation(project(":color"))
            implementation(project(":calendar"))
            implementation (libs.zoomable)

            api(libs.kmpNotifier)

            implementation("app.cash.paging:paging-common:3.3.0-alpha02-0.5.1")
            implementation("app.cash.paging:paging-compose-common:3.3.0-alpha02-0.5.1")
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
        debug {
//            resValue("string", "PORT_NUMBER", "8088")
        }
       release {
           isMinifyEnabled = false
       }
    }
    compileOptions {
        isCoreLibraryDesugaringEnabled = true
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    dependencies {
        debugImplementation(compose.uiTooling)
        implementation("com.github.chuckerteam.chucker:library:4.1.0")
        implementation("com.github.amitshekhariitbhu.Android-Debug-Database:debug-db:1.0.7")
    }
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
    implementation(libs.androidx.material3.android)
    implementation(libs.androidx.ui.android)
    // Android
    add("kspAndroid", libs.room.compiler)

    // iOS
    add("kspIosSimulatorArm64", libs.room.compiler)
    add("kspIosX64", libs.room.compiler)
    add("kspIosArm64", libs.room.compiler)
}

/*
swiftklib {
    create("IosHelper") {
        path = file("../iosApp/iosApp/helpers")
        packageName("com.sdjic.ioshelper")
    }
}*/
