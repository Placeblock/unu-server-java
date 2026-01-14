plugins {
    id("java")
    id("com.gradleup.shadow") version "9.3.1"
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    compileOnly("org.projectlombok:lombok:1.18.42")
    annotationProcessor("org.projectlombok:lombok:1.18.42")

    implementation("com.fasterxml.jackson.core:jackson-databind:2.20.1")
    implementation("org.eclipse.jetty.websocket:websocket-server:9.4.58.v20250814")
    implementation("org.eclipse.jetty.websocket:websocket-api:9.4.58.v20250814")
}

tasks.jar {
    manifest {
        attributes["Main-Class"] = "de.placeblock.unuserver.Main"
    }
}

tasks.test {
    useJUnitPlatform()
}