plugins {

    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.kotlin.parcelize)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.dagger.hilt.android)
    alias(libs.plugins.google.devtools.ksp)
//    alias(libs.plugins.androidx.room)
}

android {

    namespace = "io.bash_psk.empty_media"
    compileSdk = 34

    defaultConfig {

        applicationId = "io.bash_psk.empty_media"
        minSdk = 26
        targetSdk = 34
        versionCode = 1
        versionName = "1.0.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        vectorDrawables {

            useSupportLibrary = true
        }
    }

    buildTypes {

        all {

            isCrunchPngs = false
        }

        release {

            isMinifyEnabled = false
            isShrinkResources = false
            isDebuggable = false

            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    /*room {

        schemaDirectory("$projectDir/schemas")
    }*/

    /*ksp {

        arg(RoomSchemaArgProvider(File(projectDir, "schemas")))
    }*/

    /*sourceSets {

        getByName("androidTest").assets.srcDir("$projectDir/schemas")
    }*/

    splits {

        abi {

            isEnable = project.hasProperty("noSplits").not()
            reset()
            include("x86", "x86_64", "armeabi-v7a", "arm64-v8a")
            isUniversalApk = true
        }
    }

    compileOptions {

        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlinOptions {

        jvmTarget = "17"
    }

    buildFeatures {

        compose = true
    }

    composeOptions {

        kotlinCompilerExtensionVersion = libs.versions.kotlin.compiler.extension.get()
    }

    packaging {

        resources {

            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }

        jniLibs {

            useLegacyPackaging = true
        }
    }
}

dependencies {

//  Default     :
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.lifecycle.runtime.compose)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

//  Navigation      :
    implementation(libs.androidx.navigation.compose)

//  Icon        :
    implementation(libs.androidx.material.icons.extended)

//  DataStore       :
    implementation(libs.androidx.datastore.preferences)

//  Room            :
    implementation(libs.androidx.room.ktx)
    implementation(libs.androidx.room.runtime)
    implementation(libs.androidx.room.paging)
    ksp(libs.androidx.room.compiler)
    testImplementation(libs.androidx.room.testing)

//  Ktor    :
    implementation(libs.ktor.client.core)
    implementation(libs.ktor.client.android)
    implementation(libs.ktor.client.logging)
    implementation(libs.ktor.client.cio)
    implementation(libs.ktor.client.content.negotiation)
    implementation(libs.ktor.client.serialization)
    implementation(libs.ktor.serialization.kotlinx.json)
    implementation(libs.ktor.serialization.kotlinx.xml)
    implementation(libs.ktor.serialization.kotlinx.cbor)
    implementation(libs.ktor.serialization.kotlinx.protobuf)

//  Serialization       :
    implementation(libs.kotlinx.serialization.json)

//  Hilt        :
    implementation(libs.dagger.hilt.android)
    ksp(libs.dagger.hilt.android.compiler)
    implementation(libs.androidx.hilt.navigation.compose)
    ksp(libs.androidx.hilt.compiler)

//  Worker      :
    implementation(libs.androidx.work.runtime.ktx)
    implementation(libs.androidx.hilt.work)

//  Splash      :
    implementation(libs.androidx.core.splashscreen)

//  Media3      :
    implementation(libs.androidx.media3.common)
    implementation(libs.androidx.media3.container)
    implementation(libs.androidx.media3.decoder)
    implementation(libs.androidx.media3.datasource)
    implementation(libs.androidx.media3.exoplayer)
    implementation(libs.androidx.media3.session)
    implementation(libs.androidx.media3.ui)

//  Module      :
    implementation(project(":aria2c"))
    implementation(project(":commons"))
    implementation(project(":downloader"))
    implementation(project(":ffmpeg"))
    implementation(project(":formatter"))
    implementation(project(":permission"))
    implementation(project(":preference"))
    implementation(project(":psk"))
    implementation(project(":storage"))
    implementation(project(":thumbnail"))
    implementation(project(":utils"))
    implementation(project(":window"))
}