plugins {
    `java-library`
    id("net.minecrell.plugin-yml.bukkit") version "0.5.3"
}

java {
    toolchain.languageVersion.set(JavaLanguageVersion.of(17))
    withSourcesJar()
    withJavadocJar()
}

repositories {
    mavenLocal()
    mavenCentral()
    maven {
        url = uri("https://maven.pkg.github.com/aerulion/erenos")
        credentials {
            username = project.findProperty("gpr.user") as String? ?: System.getenv("GITHUB_ACTOR")
            password = project.findProperty("gpr.token") as String? ?: System.getenv("GITHUB_TOKEN")
        }
    }
    maven {
        url = uri("https://repo.papermc.io/repository/maven-public/")
    }
}

dependencies {
    compileOnly("io.papermc.paper:paper-api:1.19.3-R0.1-SNAPSHOT")
    compileOnly("net.aerulion:erenos:2.3.75")
}

tasks {
    compileJava {
        options.encoding = Charsets.UTF_8.name()
        options.release.set(17)
    }
    javadoc {
        options.encoding = Charsets.UTF_8.name()
    }
    processResources {
        filteringCharset = Charsets.UTF_8.name()
    }
}

group = "net.aerulion"
version = "1.3.0"

bukkit {
    name = "MemberSurvey"
    main = "net.aerulion.membersurvey.Main"
    version = getVersion().toString()
    author = "aerulion"
    apiVersion = "1.19"
    depend = listOf("Erenos", "Vault")
    commands {
        register("survey") {
            description = "Verwaltung der Umfragen."
            aliases = listOf("umfrage")
        }
    }
    permissions {
        register("membersurvey.admin") {
            description = "Mit dieser Permission koennen die Umfragen verwaltet werden."
            default = net.minecrell.pluginyml.bukkit.BukkitPluginDescription.Permission.Default.OP
        }
        register("membersurvey.member") {
            description = "Mit dieser Permission koennen die Umfragen genutzt werden."
        }
    }
}
