pluginManagement {

    repositories {

        google {

            content {

                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }

        mavenCentral()
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {

    repositoriesMode.set(RepositoriesMode.PREFER_SETTINGS)

    repositories {

        google()
        mavenCentral()
        maven(url = "https://jitpack.io")
    }
}

rootProject.name = "Empty Media"
include(":app")
include(":aria2c")
include(":commons")
include(":downloader")
include(":ffmpeg")
include(":formatter")
include(":permission")
include(":preference")
include(":psk")
include(":storage")
include(":thumbnail")
include(":utils")
include(":window")
