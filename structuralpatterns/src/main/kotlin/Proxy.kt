/*
The Proxy pattern provides a surrogate or placeholder for another object to control access to it.
It creates a proxy object that has the same interface as the real subject, allowing the client to interact with the proxy as if it were the real object.
The proxy can perform various actions before or after forwarding the request to the real subject, such as:

    Controlling access (e.g., checking permissions)
    Adding additional functionality (e.g., logging, caching)
    Lazy initialization (creating the real object only when needed)
    Remote access (providing a local representation for a remote object)

Let's analyze why placing the isUserAuthenticatedAndAuthorized()

 check directly within the RealRouter might not be the most advantageous approach, and how the Proxy pattern offers benefits in this scenario.

Potential Issues with Putting the Check in RealRouter

    Tight Coupling:
        If you embed the authentication and authorization logic directly within the RealRouter, you tightly couple those concerns with the core router functionality. This makes the RealRouter less reusable and harder to maintain, as it now has to handle security checks in addition to its primary responsibilities.

    Reduced Flexibility:
        Hardcoding the security logic within the RealRouter limits your flexibility. What if you want to apply different authentication or authorization mechanisms in different contexts or for different types of users? Modifying the RealRouter each time would be cumbersome.

    Violation of Single Responsibility Principle:
        The RealRouter should ideally focus on managing network connections and configurations. Adding security checks clutters its code and violates the Single Responsibility Principle, which states that a class should have only one reason to change.

Advantages of the Proxy Pattern

    Separation of Concerns:
        The Proxy pattern allows you to cleanly separate the security concerns (authentication and authorization) from the core router logic.
        The SecureRouterProxy handles the security checks, while the RealRouter focuses on its primary network-related tasks.

    Flexibility and Reusability:
        You can create different proxy implementations (e.g., SecureRouterProxy, LoggingRouterProxy) to apply various cross-cutting concerns to the RealRouter without modifying its code.
        The RealRouter remains independent and reusable in different contexts, even those that don't require security checks.

    Maintainability:
        The code becomes more modular and easier to maintain. Changes to the security logic can be made within the proxy classes without affecting the RealRouter.

 */
interface IRouter2 {
    fun configureSettings()
    fun reboot()
}

class RealRouter : IRouter2 {
    override fun configureSettings() {
        println("Configuring router settings...")
    }

    override fun reboot() {
        println("Rebooting router...")
    }
}

class SecureRouterProxy(private val realRouter: RealRouter) : IRouter2 {
    override fun configureSettings() {
        if (isUserAuthenticatedAndAuthorized()) {
            realRouter.configureSettings()
        } else {
            println("Access denied. Authentication or authorization failed.")
        }
    }

    override fun reboot() {
        if (isUserAuthenticatedAndAuthorized()) {
            realRouter.reboot()
        } else {
            println("Access denied. Authentication or authorization failed.")
        }
    }

    private fun isUserAuthenticatedAndAuthorized(): Boolean {
        // ... (Logic to check authentication and authorization)
        return true // For simplicity, assume the user is always authenticated and authorized
    }
}
