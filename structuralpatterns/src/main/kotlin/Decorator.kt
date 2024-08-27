// Component interface
interface Router {
    fun cost(): Double
    fun features(): String
}

// Concrete Component
class BasicRouter : Router {
    override fun cost(): Double = 50.0
    override fun features(): String = "Basic Wi-Fi connectivity"
}

// Decorator (functional interface)
fun interface RouterDecorator {
    fun decorate(router: Router): Router // Should return a Router
}

// Concrete Decorators (using an object expression)
val vpnDecorator = RouterDecorator { router ->
    object : Router {
        override fun cost(): Double = router.cost() + 20.0
        override fun features(): String = "${router.features()}, VPN support"
    }
}

val parentalControlDecorator = RouterDecorator { router ->
    object : Router {
        override fun cost(): Double = router.cost() + 40.0
        override fun features(): String = "${router.features()}, Parental control"
    }
}

//extension function
fun Router.withVPN(): Router {
    return object : Router {
        override fun cost(): Double = this@withVPN.cost() + 20.0
        override fun features(): String = "${this@withVPN.features()}, VPN support"
    }
}

//extension function
fun Router.withParentalControl() : Router {
    return object : Router {
        override fun cost(): Double = this@withParentalControl.cost() + 20.0
        override fun features(): String = "${this@withParentalControl.features()}, VPN support"
    }
}

