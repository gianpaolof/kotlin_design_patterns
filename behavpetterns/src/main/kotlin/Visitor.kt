/*
Core Idea

The Visitor pattern allows you to add new operations to a hierarchy of classes without changing the classes themselves. It achieves this by decoupling the operation from the object structure, making it easier to add new operations without modifying existing classes.

When to Use

    You have a stable hierarchy of classes (elements) and want to add new operations to them without changing their code.
    The operations you want to perform on the elements are complex or vary significantly across different element types.
    You want to keep the element classes focused on their core responsibilities and avoid cluttering them with unrelated operations.

Key Components

    Visitor: An interface or abstract class defining the visit methods for each concrete element type in the hierarchy.
    ConcreteVisitor: Implements the Visitor interface, providing specific implementations for the visit methods.
    Element: An interface or abstract class representing the elements in the hierarchy. It declares an accept(visitor: Visitor) method.
    ConcreteElement: Implements the Element interface and the accept method, which typically calls the appropriate visit method on the visitor based on its concrete type.
 */

interface RouterVisitor {
    fun visit(router: SillyRouter)
}

class BandwidthMonitor : RouterVisitor {
    override fun visit(router: SillyRouter) {
        println("Monitoring bandwidth usage on ${router.name}...")
        // ... (Logic to collect and analyze bandwidth data)
    }
}

class SecurityScanner : RouterVisitor {
    override fun visit(router: SillyRouter) {
        println("Performing security scan on ${router.name}...")
        // ... (Logic to scan for vulnerabilities)
    }
}

class FirmwareUpdater : RouterVisitor {
    override fun visit(router: SillyRouter) {
        println("Checking for firmware updates on ${router.name}...")
        // ... (Logic to check and install updates)
    }
}
// Modify Router class to use performAction
class SillyRouter(val name: String)  {
    // ... (other methods remain the same)

    fun performAction(visitor: RouterVisitor) {
        visitor.visit(this)
    }

}
