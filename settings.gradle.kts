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
include(":feature_dashboard")
include(":session_module")
include(":feature:register:domain")
include(":feature:register:session")
include(":feature:register:http")
include(":feature:register:presenter")
include(":sharing:session_user")
include(":feature:login:domain")
include(":feature:login:http")
include(":feature:login:presenter")
