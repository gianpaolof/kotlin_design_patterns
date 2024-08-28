/*
Explanation

RemoteControl is the abstraction, and Device is the implementor.
BasicRemote and AdvancedRemote are refined abstractions that delegate operations to the Device implementor.
TV and DVDPlayer are concrete implementors.
The client code creates a specific remote control and device combination and uses the remote to control the device.

Benefits

Decoupling: Separates the abstraction and implementation, allowing them to evolve independently.

Flexibility: You can easily add new types of remote controls or devices without affecting the existing code.
Reusability: The same implementation (e.g., TV) can be used with different abstractions (e.g., BasicRemote, AdvancedRemote).

When to Use

    When you want to avoid a permanent binding between an abstraction and its implementation.
    When both the abstraction and its implementation need to be extended using subclasses.

When changes in the implementation of an abstraction should not impact the client code.
When you want to share an implementation among multiple objects and hide this fact from the client.
*/

// Abstraction
interface RemoteControl {
    fun on()
    fun off()
    fun setChannel(channel: Int)
}

// Refined Abstraction
class BasicRemote(private val device: Device) : RemoteControl {
    override fun on() = device.on()
    override fun off() = device.off()
    override fun setChannel(channel: Int) = device.setChannel(channel)
}

class AdvancedRemote(private val device: Device) : RemoteControl {
    // ... additional methods like volume control
    override fun on() {

    }

    override fun off() {

    }

    override fun setChannel(channel: Int) {

    }
}

// Implementor
interface Device {
    fun on()
    fun off()
    fun setChannel(channel: Int)
}

// Concrete Implementors
class TV : Device {
    // ... implementation specific to TV
    override fun on() {

    }

    override fun off() {

    }

    override fun setChannel(channel: Int) {

    }
}

class DVDPlayer : Device {
    // ... implementation specific to DVD Player
    override fun on() {

    }

    override fun off() {

    }

    override fun setChannel(channel: Int) {

    }
}
