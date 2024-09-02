/*
The Mediator pattern defines an object that encapsulates how a set of objects interact. It promotes loose coupling by keeping objects from referring to each other explicitly, and it lets you vary their interaction independently.

When to Use

    When a set of objects communicate in a well-defined but complex way, making the code difficult to understand and maintain.
    When reusing an object is difficult because it refers to and communicates with many other objects.
    When you want to centralize complex communication and control logic between objects.

Key Components

    Mediator: An interface or abstract class defining the communication methods between colleagues.
    ConcreteMediator: Implements the Mediator interface, coordinating the interaction between colleagues.
    Colleague: Classes that communicate with each other through the Mediator.


    Example below is based on com between element in a network

**1. Centralized Communication and Decoupling:**

* **Without Mediator:** In a network without a mediator, each device would need to maintain direct connections and communication channels with every other device it needs to interact with. This creates a tightly coupled system where changes in one device could ripple through the entire network, making it difficult to manage and scale.

* **With Mediator (`NetworkManager`)**: The `NetworkManager` acts as the central communication hub. Devices only need to interact with the mediator, not directly with each other. This significantly reduces dependencies and promotes loose coupling. Changes in one device or its communication logic won't directly impact other devices, as long as they adhere to the `NetworkDevice` interface and communicate through the mediator.

**2. Simplified Communication and Coordination:**

* **Broadcast Messages:** The `broadcastMessage` function in the `NetworkManager` allows any device to send a message to all other registered devices on the network. This simplifies the process of disseminating information or notifications across the network without each device needing to manage its own broadcast mechanism.

* **Connection Management:** The `requestConnection` function centralizes the logic of establishing connections between devices. This ensures a consistent and controlled way of handling connection requests, preventing potential conflicts or errors.

* **Device Status Checks:** The `checkDeviceStatus` function provides a unified way to query the status of any registered device. This centralizes the status information and eliminates the need for each device to expose its internal state directly to others.

**3. Flexibility and Maintainability:**

* **Adding New Devices:** Introducing new types of network devices is simplified, as they only need to implement the `NetworkDevice` interface and communicate through the mediator. There's no need to modify existing devices or their communication logic.

* **Modifying Communication Logic:** If the communication protocol or behavior needs to change, you only need to update the `NetworkManager` (the mediator), leaving the individual device implementations untouched. This improves maintainability and reduces the risk of introducing bugs.

* **Testability:** The Mediator pattern enhances testability. You can easily mock or stub the `NetworkManager` to isolate and test individual device behaviors without relying on the entire network setup.

**In summary, the Mediator pattern in this network scenario offers the following key advantages:**

* Decouples network devices, promoting flexibility and maintainability
* Centralizes communication and coordination logic, simplifying network management
* Enhances testability by providing a clear separation between devices and the communication mechanism

Feel free to ask if you have any further questions or would like to discuss other design patterns or scenarios!

 */

interface NetworkMediator {
    fun broadcastMessage(message: String, sender: NetworkDevice)
    fun requestConnection(device: NetworkDevice, targetDevice: NetworkDevice)
    fun registerDevice(device: NetworkDevice)
    fun unregisterDevice(device: NetworkDevice)
    fun checkDeviceStatus(deviceName: String): String?
}

class NetworkManager : NetworkMediator {
    private val devices = mutableMapOf<String, NetworkDevice>()

    override fun broadcastMessage(message: String, sender: NetworkDevice) {
        for (device in devices.values) {
            if (device != sender) {
                device.receiveMessage(message)
            }
        }
    }

    override fun requestConnection(device: NetworkDevice, targetDevice: NetworkDevice) {
        if (targetDevice in devices.values) {
            if (targetDevice is Router666 && device is Computer) {
                targetDevice.acceptConnection(device)
                device.connectedRouter = targetDevice
                println("${device.name} (Computer): Connected to ${targetDevice.name}")
            } else {
                println("Invalid connection request.")
            }
        } else {
            println("Device ${targetDevice.name} not found on the network.")
        }
    }

    override fun registerDevice(device: NetworkDevice) {
        devices[device.name] = device
        broadcastMessage("Device ${device.name} has joined the network.", device)
    }

    override fun unregisterDevice(device: NetworkDevice) {
        devices.remove(device.name)
        broadcastMessage("Device ${device.name} has left the network.", device)
    }

    override fun checkDeviceStatus(deviceName: String): String? {
        val device = devices[deviceName]
        return device?.checkStatus()
    }
}

abstract class NetworkDevice(val name: String, protected val mediator: NetworkMediator) {
    abstract fun sendMessage(message: String)
    abstract fun receiveMessage(message: String)
    abstract fun establishConnection(targetDevice: NetworkDevice)
    abstract fun acceptConnection(device: NetworkDevice)
    abstract fun checkStatus(): String
}

class Router666(name: String, mediator: NetworkMediator) : NetworkDevice(name, mediator) {
    private val connectedDevices = mutableListOf<NetworkDevice>()
    private var isUp = true

    override fun sendMessage(message: String) {
        if (isUp) {
            println("$name (Router): Sending message - $message")
            mediator.broadcastMessage(message, this)
        } else {
            println("$name (Router): Cannot send message, router is down.")
        }
    }

    override fun receiveMessage(message: String) {
        println("$name (Router): Received message - $message")
    }

    override fun establishConnection(targetDevice: NetworkDevice) {
        if (isUp) {
            println("$name (Router): Establishing connection to ${targetDevice.name}")
            connectedDevices.add(targetDevice)
        } else {
            println("$name (Router): Cannot establish connection, router is down.")
        }
    }

    override fun acceptConnection(device: NetworkDevice) {
        if (isUp) {
            println("$name (Router): Accepting connection from ${device.name}")
            connectedDevices.add(device)
        }
    }

    override fun checkStatus(): String {
        return if (isUp) "Up" else "Down"
    }

    fun goDown() {
        isUp = false
        println("$name (Router): Going down!")
        mediator.broadcastMessage("$name (Router) is down!", this)
    }

    fun comeUp() {
        isUp = true
        println("$name (Router): Coming back up!")
        mediator.broadcastMessage("$name (Router) is back up!", this)
    }
}

class Computer(name: String, mediator: NetworkMediator) : NetworkDevice(name, mediator) {
    var connectedRouter: Router666? = null

    override fun sendMessage(message: String) {
        connectedRouter?.let {
            println("$name (Computer): Sending message through $it - $message")
            it.sendMessage(message)
        } ?: println("$name (Computer): Not connected to any router.")
    }

    override fun receiveMessage(message: String) {
        println("$name (Computer): Received message - $message")
    }
    override fun establishConnection(targetDevice: NetworkDevice) {
        if (targetDevice is Router666 && connectedRouter != targetDevice) { // Check if already connected
            println("$name (Computer): Requesting connection to ${targetDevice.name}")
            mediator.requestConnection(this, targetDevice)
        } else if (targetDevice is Router666) {
            println("$name (Computer): Already connected to ${targetDevice.name}")
        } else {
            println("$name (Computer): Cannot connect to non-router devices.")
        }
    }


    override fun acceptConnection(device: NetworkDevice) {
        if (device is Router666) {
            connectedRouter = device
            println("$name (Computer): Connected to ${device.name}")
        }
    }

    override fun checkStatus(): String {
        return if (connectedRouter != null) "Connected to ${connectedRouter?.name}" else "Disconnected"
    }
}

fun main() {

}