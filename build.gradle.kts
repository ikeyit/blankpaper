plugins {
    java
}

group = "io.ikeyit.blankpaper"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation(platform("org.junit:junit-bom:5.9.1"))
    implementation(platform("org.springframework:spring-framework-bom:5.3.25"))
    implementation(platform("com.fasterxml.jackson:jackson-bom:2.14.2"))
    implementation("ch.qos.logback:logback-classic:1.4.8")
    implementation("org.slf4j:slf4j-api:2.0.7")
    implementation("ch.qos.logback:logback-core:1.4.8")
    implementation("com.google.code.findbugs:jsr305:3.0.2")
    implementation("com.google.guava:guava:31.1-jre")
    implementation("io.netty:netty-all:4.1.87.Final")
    implementation("org.springframework:spring-expression")
    implementation("org.springframework:spring-context")
    implementation("com.fasterxml.jackson.core:jackson-databind")
    testImplementation("org.junit.jupiter:junit-jupiter-api")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine")
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}