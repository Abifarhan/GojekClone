pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "GojekClone"
include(":app")
include(":component")
include(":session_module")
include(":feature:register:domain")
include(":feature:register:session")
include(":feature:register:http")
include(":feature:register:presenter")
include(":feature:login:domain")
include(":feature:login:http")
include(":feature:login:presenter")
include("feature:login:session")
include(":feature:dashboard:view")
include(":core")
