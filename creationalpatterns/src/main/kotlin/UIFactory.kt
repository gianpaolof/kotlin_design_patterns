interface Router // Represents a generic router

abstract class BasicRouter : Router
abstract class GamingRouter : Router
abstract class EnterpriseRouter : Router

// Abstract Factory
interface RouterFactory {
    fun createRouter(): Router // Single method to create a router

    enum class TYPE {
        Basic, Gaming, Enterprise
    }

    companion object {
        // "static" method that supplies concrete factory
        fun createFactory(type: TYPE): RouterFactory {
            return when (type) {
                TYPE.Basic -> BasicRouterFactory()
                TYPE.Gaming -> GamingRouterFactory()
                TYPE.Enterprise -> EnterpriseRouterFactory()
            }
        }
    }
}


// Concrete Factories (implementing createRouter)
internal class BasicRouterFactory : RouterFactory {
    override fun createRouter(): Router = BasicNetgearRouter()
}

internal class GamingRouterFactory : RouterFactory {
    override fun createRouter(): Router = GamingAsusRouter()
}

internal class EnterpriseRouterFactory : RouterFactory {
    override fun createRouter(): Router = EnterpriseCiscoRouter()
}

// Concrete Products (no changes)
internal class BasicNetgearRouter : BasicRouter() { /* ... implementation ... */ }
internal class GamingAsusRouter : GamingRouter() { /* ... implementation ... */ }
internal class EnterpriseCiscoRouter : EnterpriseRouter() { /* ... implementation ... */ }


