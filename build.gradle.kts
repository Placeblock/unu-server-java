plugins {
    id("java")
    id("com.github.johnrengelman.shadow") version "8.1.1"
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

tasks {
    jar {
        manifest {
            attributes["Main-Class"] = "de.placeblock.unuserver.Main"
        }
    }
}

dependencies {
    compileOnly("org.projectlombok:lombok:1.18.28")
    annotationProcessor("org.projectlombok:lombok:1.18.28")

    implementation("com.fasterxml.jackson.core:jackson-databind:2.15.2")
    implementation("org.eclipse.jetty.websocket:websocket-server:9.4.51.v20230217")
    implementation("org.eclipse.jetty.websocket:websocket-api:9.4.51.v20230217")
}

tasks.test {
    useJUnitPlatform()
}