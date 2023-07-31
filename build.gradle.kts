import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.springframework.boot.gradle.tasks.bundling.BootJar

plugins {
    id("org.springframework.boot") version "3.1.0"
    id("io.spring.dependency-management") version "1.1.2"
    val kotlinVer = "1.7.22"
    kotlin("jvm") version kotlinVer
    kotlin("plugin.spring") version kotlinVer
    kotlin("plugin.jpa") version kotlinVer
    kotlin("plugin.allopen") version kotlinVer
    kotlin("plugin.noarg") version kotlinVer
}

allprojects {
    repositories {
        mavenCentral()
    }
}

subprojects {
    apply(plugin = "org.springframework.boot")
    apply(plugin = "io.spring.dependency-management")
    apply(plugin = "kotlin")
    apply(plugin = "kotlin-jpa")
    apply(plugin = "kotlin-spring")

    group = "com.usktea"
    version = "0.0.1-SNAPSHOT"

    java {
        sourceCompatibility = JavaVersion.VERSION_17
    }

    allOpen {
        annotation("jakarta.persistence.Entity")
        annotation("jakarta.persistence.Embeddable")
        annotation("jakarta.persistence.MappedSuperclass")
    }

    noArg {
        annotation("jakarta.persistence.Entity")
        annotation("jakarta.persistence.Embeddable")
        annotation("jakarta.persistence.MappedSuperclass")
    }

    dependencies {
        implementation("org.springframework.boot:spring-boot-starter-data-jpa") {
            exclude("org.hibernate.orm", "hibernate-core")
        }
        implementation("org.springframework.boot:spring-boot-starter-security")
        implementation("org.springframework.boot:spring-boot-starter-webflux")
        implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
        implementation("io.projectreactor.kotlin:reactor-kotlin-extensions")
        implementation("org.jetbrains.kotlin:kotlin-reflect")
        implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor")

        // reactive
        implementation("org.hibernate.reactive:hibernate-reactive-core-jakarta:1.1.9.Final")
        implementation("com.linecorp.kotlin-jdsl:spring-data-kotlin-jdsl-hibernate-reactive-jakarta:2.2.1.RELEASE")
        implementation("io.vertx:vertx-mysql-client:4.4.1")
        implementation("io.smallrye.reactive:mutiny-kotlin:2.2.0")

        // mac os
        // https://mvnrepository.com/artifact/io.netty/netty-resolver-dns-native-macos/4.1.92.Final
        implementation("io.netty:netty-resolver-dns-native-macos:4.1.92.Final:osx-aarch_64")

        // h2, mysql db reactive
        implementation("mysql:mysql-connector-java:8.0.33")
        implementation("com.h2database:h2")
        implementation("io.agroal:agroal-pool:2.0")
        implementation("io.vertx:vertx-jdbc-client:4.3.7")

        // test
        testImplementation("org.springframework.boot:spring-boot-starter-test") {
            exclude(module = "mockito-core")
        }
        testImplementation("com.ninja-squad:springmockk:4.0.2")
        testImplementation("io.mockk:mockk:1.13.3")
        testImplementation("io.kotest:kotest-runner-junit5:5.6.2")
        testImplementation("io.kotest:kotest-assertions-core:5.6.2")

        testImplementation("io.projectreactor:reactor-test")
        testImplementation("org.springframework.security:spring-security-test")
        developmentOnly("org.springframework.boot:spring-boot-devtools")
    }

    tasks.withType<KotlinCompile> {
        kotlinOptions {
            freeCompilerArgs += "-Xjsr305=strict"
            jvmTarget = "17"
        }
    }

    tasks.withType<Test> {
        useJUnitPlatform()
    }

    tasks.test {
        jvmArgs(
            "--add-opens", "java.base/java.lang.reflect=ALL-UNNAMED"
        )
    }

    tasks.named<Test>("test") {
        useJUnitPlatform()
        testLogging {
            showExceptions = true
            showStandardStreams = true
        }
    }
}

project(":application") {
    dependencies {
        api(project(":library"))
    }
    tasks.named<Jar>("jar") { enabled = false }
    tasks.named<BootJar>("bootJar") {
        enabled = true
        launchScript()
    }
}

project(":admin") {
    dependencies {
        api(project(":library"))
    }
    tasks.named<Jar>("jar") { enabled = false }
    tasks.named<BootJar>("bootJar") {
        enabled = true
        launchScript()
    }
}
