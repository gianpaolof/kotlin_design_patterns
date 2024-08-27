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

