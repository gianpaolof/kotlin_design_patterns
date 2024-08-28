/*

* Subsystem Classes: `WifiManager`, `PortForwardingManager`, and `DHCPManager` represent the network configuration subsystem.
* `NetworkConfigurationFacade`: Provides a simplified interface with methods like `setupBasicNetwork()` and `setupGamingNetwork()` to encapsulate common network configuration tasks.
* Client Code: Uses the `NetworkConfigurationFacade` to configure the network without directly interacting with the individual subsystem components.

**Key Points**

* The Facade pattern helps in simplifying the interaction with complex systems, providing a higher-level interface.
* It promotes loose coupling between the client code and the subsystem, making the code more maintainable and easier to understand.


 */


// Subsystem Classes
class WifiManager {
    fun enableWifi() { println("Wi-Fi enabled") }
    fun disableWifi() { println("Wi-Fi disabled") }
}

class PortForwardingManager {
    fun addPortForwardingRule(port: Int, protocol: String, targetIP: String) {
        println("Added port forwarding rule: port $port, protocol $protocol, target IP $targetIP")
    }

    fun removePortForwardingRule(port: Int, protocol: String) {
        println("Removed port forwarding rule: port $port, protocol $protocol")
    }
}

class DHCPManager {
    fun enableDHCP() { println("DHCP enabled") }
    fun disableDHCP() { println("DHCP disabled") }
    fun setDHCPPool(startIP: String, endIP: String) {
        println("DHCP pool set: start IP $startIP, end IP $endIP")
    }
}

// Facade Class
class NetworkConfigurationFacade(
    private val wifiManager: WifiManager,
    private val portForwardingManager: PortForwardingManager,
    private val dhcpManager: DHCPManager
) {
    fun setupBasicNetwork() {
        wifiManager.enableWifi()
        dhcpManager.enableDHCP()
        dhcpManager.setDHCPPool("192.168.1.100", "192.168.1.200")
    }

    fun setupGamingNetwork() {
        wifiManager.enableWifi()
        portForwardingManager.addPortForwardingRule(3074, "UDP", "192.168.1.50") // Example for a gaming port
        dhcpManager.enableDHCP()
    }
}
