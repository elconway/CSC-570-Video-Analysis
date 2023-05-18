/*
 * This file was generated by the Gradle 'init' task.
 *
 * This is a general purpose Gradle build.
 * Learn more about Gradle by exploring our samples at https://docs.gradle.org/8.1.1/samples
 * This project uses @Incubating APIs which are subject to change.
 */


plugins {
    java
    application
}

repositories {
    mavenCentral();
}

dependencies {
    implementation("org.java-websocket:Java-WebSocket:1.5.3") // https://github.com/TooTallNate/Java-WebSocket
    implementation("org.json:json:20230227") // https://mvnrepository.com/artifact/org.json/json
    implementation(group = "org.openpnp", name = "opencv", version = "4.7.0-0")

}

//run {
//    systemProperty("java.library.path", "/build/lib")
//}

application {
    mainClass.set("Main")
}


