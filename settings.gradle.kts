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
include(":login_module")
//include(":register_module")
include(":feature_dashboard")
include(":session_module")
include(":feature:register:register_domain")
include(":feature:register:register_cache")
include(":feature:register:register_composite")
include(":feature:register:register_http")
include(":feature:register:register_presenter")
include(":feature:register:register_view")
include(":sharing:session_user")
include(":login_cache")
include(":login_domain")
include(":login_http")
