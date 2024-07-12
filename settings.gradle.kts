@file:Suppress("UnstableApiUsage")

enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

pluginManagement {
    includeBuild("build-logic")
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "LearnOfLegends"
include(":app")
include(":core:common")
include(":core:data")
include(":core:datastore")
include(":core:datastore-proto")
include(":core:domain")
include(":core:model")
include(":core:network")
include(":core:ui")

include(":feature:champion")
include(":feature:champions")
include(":feature:items")
include(":feature:settings")
