plugins {
    java
    id("org.springframework.boot") version "4.0.1"
    id("io.spring.dependency-management") version "1.1.7"
}

group = "com.taspa.studios"
version = "0.0.1-SNAPSHOT"
description = "bot"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(17)
    }
}

configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
}

repositories {
    mavenCentral()
}

dependencies {
    // AWS S3
    implementation("software.amazon.awssdk:s3:2.41.1")

    // Telegram
    implementation("org.telegram:telegrambots-client:8.3.0")
    implementation("org.telegram:telegrambots-springboot-longpolling-starter:8.3.0")

    // Spring Core
    implementation("org.springframework.boot:spring-boot-starter-web")
    testImplementation("org.springframework.boot:spring-boot-starter-test")

    // Data Jpa
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")

    // Postgre SQL
    implementation("org.postgresql:postgresql:42.7.7")

    testRuntimeOnly("org.junit.platform:junit-platform-launcher")

    implementation("org.springframework.boot:spring-boot-devtools:4.0.1")

    // Lombok
    compileOnly("org.projectlombok:lombok")
    annotationProcessor("org.projectlombok:lombok")
}

tasks.withType<Test> {
    useJUnitPlatform()
}
