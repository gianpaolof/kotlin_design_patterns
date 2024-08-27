interface Router // Represents a generic router

abstract class BasicRouter : Router
abstract class GamingRouter : Router
abstract class EnterpriseRouter : Router

// Abstract Factory
interface RouterFactory {
    fun createBasicRouter(): BasicRouter
    fun createGamingRouter(): GamingRouter
    fun createEnterpriseRouter(): EnterpriseRouter

    enum class TYPE {
        Basic, Gaming, Enterprise
    }
}

// class hiding concrete factories, but implementing the same interface
class FactoryRouterCreator(type: RouterFactory.TYPE) : RouterFactory {

    private val factory = when (type) {
        RouterFactory.TYPE.Basic -> BasicRouterFactory()
        RouterFactory.TYPE.Gaming -> GamingRouterFactory()
        RouterFactory.TYPE.Enterprise -> EnterpriseRouterFactory()
    }

    override fun createBasicRouter(): BasicRouter = factory.createBasicRouter()
    override fun createGamingRouter(): GamingRouter = factory.createGamingRouter()
    override fun createEnterpriseRouter(): EnterpriseRouter = factory.createEnterpriseRouter()
}


// Concrete Factories
internal class BasicRouterFactory : RouterFactory {
    override fun createBasicRouter(): BasicRouter = BasicNetgearRouter()
    override fun createGamingRouter(): GamingRouter = throw UnsupportedOperationException()
    override fun createEnterpriseRouter(): EnterpriseRouter = throw UnsupportedOperationException()
}

internal class GamingRouterFactory : RouterFactory {
    override fun createBasicRouter(): BasicRouter = throw UnsupportedOperationException()
    override fun createGamingRouter(): GamingRouter = GamingAsusRouter()
    override fun createEnterpriseRouter(): EnterpriseRouter = throw UnsupportedOperationException()
}

internal class EnterpriseRouterFactory : RouterFactory {
    override fun createBasicRouter(): BasicRouter = throw UnsupportedOperationException()
    override fun createGamingRouter(): GamingRouter = throw UnsupportedOperationException()
    override fun createEnterpriseRouter(): EnterpriseRouter = EnterpriseCiscoRouter()
}

// Concrete Products
internal class BasicNetgearRouter : BasicRouter() { /* ... implementation ... */ }
internal class GamingAsusRouter : GamingRouter() { /* ... implementation ... */ }
internal class EnterpriseCiscoRouter : EnterpriseRouter() { /* ... implementation ... */ }

