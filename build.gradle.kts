plugins {
    alias(libs.plugins.jetbrains.kotlin)
    alias(libs.plugins.jetbrains.compose.kotlin)
    alias(libs.plugins.jetbrains.compose.core)
    alias(libs.plugins.jetbrains.serialization)
    alias(libs.plugins.conveyor)
}

group = "io.github.alaksion.kompressor"
version = "1.0.0"

repositories {
    mavenCentral()
    maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    google()
}

dependencies {
    // Compose
    implementation(compose.desktop.currentOs)
    implementation(compose.materialIconsExtended)
    implementation(compose.components.resources)
    implementation(libs.conveyor.controll)

    // Libs
    implementation(libs.compose.navigation)
    implementation(libs.compose.viewmodel)
    implementation(libs.kotlin.serialization)
    implementation(libs.kotlin.coroutines.swing)
}

compose.desktop {
    application {
        mainClass = "io.github.alaksion.kompressor.MainKt"
    }
}
