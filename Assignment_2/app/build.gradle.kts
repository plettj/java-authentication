plugins {
    application
}

repositories {
    mavenCentral()
}

dependencies {
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(20))
    }
}

// Default main class (not directly used in the custom tasks)
application {
    mainClass.set("Assignment_2.app.src.main.java.PrintServerMain")
}

// Custom task to run the RMI server
tasks.register<JavaExec>("runServer") {
    group = "application"
    description = "Run the RMI Server"
    classpath = sourceSets["main"].runtimeClasspath
    mainClass.set("Assignment_2.app.src.main.java.PrintServerMain")
}

// Custom task to run the RMI client
tasks.register<JavaExec>("runClient") {
    group = "application"
    description = "Run the RMI Client"
    classpath = sourceSets["main"].runtimeClasspath
    mainClass.set("Assignment_2.app.src.main.java.PrintClientMain")
}
