import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.springframework.boot.gradle.tasks.bundling.BootJar

plugins {
	id("org.springframework.boot") version "3.0.9"
	id("io.spring.dependency-management") version "1.1.2"

	val kotlinVer = "1.7.22"
	kotlin("jvm") version kotlinVer
	kotlin("plugin.spring") version kotlinVer
	kotlin("plugin.jpa") version kotlinVer
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
		implementation("org.springframework.boot:spring-boot-starter-webflux")
		implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
		implementation("io.projectreactor.kotlin:reactor-kotlin-extensions")
		implementation("org.jetbrains.kotlin:kotlin-reflect")
		implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor")

		// reactive
		// https://mvnrepository.com/artifact/org.hibernate.reactive/hibernate-reactive-core-jakarta/1.1.9.Final
		implementation("org.hibernate.reactive:hibernate-reactive-core-jakarta:1.1.9.Final")
		// https://mvnrepository.com/artifact/com.linecorp.kotlin-jdsl/spring-data-kotlin-jdsl-hibernate-reactive-jakarta/2.2.1.RELEASE
		implementation("com.linecorp.kotlin-jdsl:spring-data-kotlin-jdsl-hibernate-reactive-jakarta:2.2.1.RELEASE")

		// https://mvnrepository.com/artifact/io.vertx/vertx-mysql-client
		implementation("io.vertx:vertx-mysql-client:4.4.1")
		implementation("mysql:mysql-connector-java:8.0.33")

		implementation("io.smallrye.reactive:mutiny-kotlin:2.2.0")

		// mac os
		// https://mvnrepository.com/artifact/io.netty/netty-resolver-dns-native-macos/4.1.92.Final
		implementation("io.netty:netty-resolver-dns-native-macos:4.1.92.Final:osx-aarch_64")


		// h2 db reactive
		implementation("io.agroal:agroal-pool:2.0")
		implementation("com.h2database:h2")
		implementation("io.vertx:vertx-jdbc-client:4.3.7")

		testImplementation("org.springframework.boot:spring-boot-starter-test")
		testImplementation("io.projectreactor:reactor-test")
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
