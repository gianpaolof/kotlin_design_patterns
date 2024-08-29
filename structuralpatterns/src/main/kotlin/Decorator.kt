/*
**Decorator Pattern Implementation for Router Customization**

**Purpose**

This code demonstrates the Decorator pattern in Kotlin, allowing the dynamic addition of features (VPN support, Parental Controls) to a basic router without modifying its core structure.

**Structure**

* **`Router` Interface:** Defines the core component (router) with methods for `cost()` and `features()`.
* **`BasicRouter` Class:** A concrete implementation of `Router` representing a basic router with basic Wi-Fi connectivity.
* **`RouterDecorator` Functional Interface:** Represents a decorator for a router. It defines a single function `decorate(router: Router): Router` that takes a router and returns a decorated router.
* **`vpnDecorator` and `parentalControlDecorator`:** Concrete decorators implemented as lambda expressions that create anonymous objects implementing the `Router` interface. They override the `cost()` and `features()` methods to add the respective feature's cost and description.

**Functionality**

* **`BasicRouter`:** Provides basic Wi-Fi connectivity at a cost of $50.
* **`vpnDecorator`:**
   * Takes a `Router` as input and returns a new `Router` object.
   * The new router adds VPN support to the features of the original router.
   * The cost is increased by $20 to reflect the added VPN functionality.
* **`parentalControlDecorator`:**
   * Similar to `vpnDecorator`, it takes a `Router` and returns a new `Router`.
   * Adds Parental Controls to the features.
   * Increases the cost by $10.

**Client Code**

* Creates a `BasicRouter`.
* Decorates the basic router with VPN support using `vpnDecorator`.
* Further decorates the router with Parental Controls using `parentalControlDecorator`.
* Prints the features and cost of each router variation, demonstrating how decorators dynamically add functionality and adjust the cost.

**Key Points**

* The Decorator pattern allows flexible and dynamic addition of features to objects without subclassing.
* Functional interfaces in Kotlin provide a concise way to define decorators.
* Decorators can be chained to create combinations of features.
* This implementation promotes code reusability and maintainability by keeping the core `Router` implementation separate from additional features.

**Potential Enhancements**

* You can add more decorators for other features (e.g., guest network, firewall, etc.).
* Consider using a factory or builder pattern to manage the creation of decorated routers, especially if you have many potential combinations.
* Explore error handling or validation within decorators to ensure proper configuration and feature compatibility.

This documentation provides a basic overview of the code's structure and purpose. Feel free to add more details or specific explanations as needed for your documentation purposes.


 */
// Component interface
interface IRouter {
    fun cost(): Double
    fun features(): String
}

// Concrete Component
class MyBasicIRouter : IRouter {
    override fun cost(): Double = 50.0
    override fun features(): String = "Basic Wi-Fi connectivity"
}

// Decorator (functional interface)
fun interface RouterDecorator {
    fun decorate(router: IRouter): IRouter // Should return a Router
}

// Concrete Decorators (using an object expression)
val vpnDecorator = RouterDecorator { router ->
    object : IRouter {
        override fun cost(): Double = router.cost() + 20.0
        override fun features(): String = "${router.features()}, VPN support"
    }
}

val parentalControlDecorator = RouterDecorator { router ->
    object : IRouter {
        override fun cost(): Double = router.cost() + 40.0
        override fun features(): String = "${router.features()}, Parental control"
    }
}

//extension function
fun IRouter.withVPN(): IRouter {
    return object : IRouter {
        override fun cost(): Double = this@withVPN.cost() + 20.0
        override fun features(): String = "${this@withVPN.features()}, VPN support"
    }
}

//extension function
fun IRouter.withParentalControl() : IRouter {
    return object : IRouter {
        override fun cost(): Double = this@withParentalControl.cost() + 20.0
        override fun features(): String = "${this@withParentalControl.features()}, VPN support"
    }
}

