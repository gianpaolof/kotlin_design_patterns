/*
Explanation:

    LegacyRouter and ModernRouter: Define the interfaces for legacy and modern routers, respectively.

    NetworkConfig: A data class to hold network configuration details for modern routers.

    OldNetgearRouter and NewTPlinkRouter: Concrete implementations of legacy and modern routers.

    ModernRouterAdapter:
        Implements the LegacyRouter interface.
        Holds a reference to a ModernRouter object.
        Adapts the connect and disconnect methods of the legacy interface to the corresponding methods of the modern interface, handling any necessary data transformations (e.g., creating a NetworkConfig object).

    Client Code:
        Demonstrates using both a legacy router directly and a modern router through the adapter.
        The adapter allows the modern router to be used seamlessly in a system designed for legacy routers.

Key Points:

    The Adapter pattern enables the integration of incompatible interfaces, promoting code reusability and flexibility.
    It acts as a bridge between existing code and new components, allowing them to work together without major modifications.
    In this example, the adapter allows modern routers to be used in a system that expects legacy routers.

 */
// Legacy Interface (Target)
interface LegacyRouter {
    fun connect(ssid: String, password: String)
    fun disconnect()
}

// Modern Interface (Adaptee)
interface ModernRouter {
    fun establishConnection(networkConfig: NetworkConfig)
    fun terminateConnection()
}

// Data Class for Modern Router Configuration
data class NetworkConfig(val ssid: String, val password: String)

// Concrete Legacy Router
class OldNetgearRouter : LegacyRouter {
    override fun connect(ssid: String, password: String) {
        println("Connecting to $ssid with password $password using legacy protocol...")
    }

    override fun disconnect() {
        println("Disconnecting from legacy router...")
    }
}

// Concrete Modern Router
class NewTPlinkRouter : ModernRouter {
    override fun establishConnection(networkConfig: NetworkConfig) {
        println("Establishing connection to ${networkConfig.ssid} with modern protocol...")
    }

    override fun terminateConnection() {
        println("Terminating connection from modern router...")
    }
}

// Adapter (adapts ModernRouter to LegacyRouter)
class ModernRouterAdapter(private val modernRouter: ModernRouter) : LegacyRouter {
    override fun connect(ssid: String, password: String) {
        val networkConfig = NetworkConfig(ssid, password)
        modernRouter.establishConnection(networkConfig)
    }

    override fun disconnect() {
        modernRouter.terminateConnection()
    }
}


/*
Interfaces: We define interfaces for EUPlug, USPlug, and MiniUSBPlug to represent the different plug types.
Cellphone and Charger: The Cellphone class requires a MiniUSBPlug to charge, and the Charger class needs a USPlug to connect to the outlet.
Concrete Implementations: We have EuropeanCellphone, USOutlet, and MiniUSBCharger as concrete implementations of the respective interfaces.
Adapter: The EUPlugToUSPlugAdapter implements the USPlug interface but internally uses an EUPlug. It adapts the providePower() method of the EU plug to the supplyPower() method expected by the US charger.
*/
// Interfaces for different plug types
interface EUPlug {
    fun providePower()
}

interface USPlug {
    fun supplyPower()
}

interface MiniUSBPlug {
    fun charge()
}

// Cellphone and Charger classes
class Cellphone(private val plug: MiniUSBPlug) {
    fun charge() {
        plug.charge()
        println("Cellphone is charging...")
    }
}

class Charger(private val plug: USPlug) {
    fun plugIn() {
        plug.supplyPower()
        println("Charger is plugged in and ready.")
    }
}

// Concrete implementations
class EuropeanCellphone : EUPlug {
    override fun providePower() {
        println("Providing power from EU plug.")
    }
}

class USOutlet : USPlug {
    override fun supplyPower() {
        println("Supplying power from US outlet.")
    }
}

class MiniUSBCharger : MiniUSBPlug {
    override fun charge() {
        println("Charging via Mini USB.")
    }
}

// Adapter (EUPlug to USPlug)
class EUPlugToUSPlugAdapter(private val euPlug: EUPlug) : USPlug {
    override fun supplyPower() {
        euPlug.providePower()
        println("Adapting EU plug to US outlet.")
    }
}
