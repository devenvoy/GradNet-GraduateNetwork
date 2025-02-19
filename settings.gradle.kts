rootProject.name = "GradNet-GraduateNetwork"
enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

pluginManagement {
    repositories {
        google {
            mavenContent {
                includeGroupAndSubgroups("androidx")
                includeGroupAndSubgroups("com.android")
                includeGroupAndSubgroups("com.google")
            }
        }
        mavenCentral()
        gradlePluginPortal()
        maven {
            url = uri("https://www.jitpack.io" )
        }
    }
}

dependencyResolutionManagement {
    repositories {
        google {
            mavenContent {
                includeGroupAndSubgroups("androidx")
                includeGroupAndSubgroups("com.android")
                includeGroupAndSubgroups("com.google")
            }
        }
        mavenCentral()
        maven {
            url = uri("https://www.jitpack.io" )
        }
    }
}

include(":features:posts")
include(":features:profile")
include(":features:events")
include(":features:jobs")
include(":features:search")
include(":features:onBoard")
include(":composeApp")
include(":shared")
include(":calendar")
include(":sheetCore")
include(":color")
include("commons")
include("Domain")
include("Data")
