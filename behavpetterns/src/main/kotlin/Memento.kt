/*
Core Idea

    The Memento pattern allows you to capture and externalize an object's internal state so that the object can be restored to this state later.
    It achieves this without violating encapsulation by providing a way for the object (originator) to save its state in a memento object, which can then be stored and restored by a caretaker object.

When to Use

    When you need to save and restore the state of an object to a previous point in time.
    When direct access to an object's internal state would violate encapsulation.
    When you want to implement undo/redo functionality, checkpoints, or rollback mechanisms.

Key Components

    Originator: The object whose state needs to be saved and restored.
    Memento: A simple object that stores the originator's internal state. It should be immutable to prevent external modification of the saved state.
    Caretaker: Responsible for storing and managing mementos. It can request the originator to create a memento, store it, and later restore the originator's state from a memento.
 */

// Memento (immutable)
data class RouterStateMemento(val isWifiEnabled: Boolean, val password: String, val otherSettings: Map<String, String>)

// Originator (Router)
class EasyBoxGPRouter {
    private var isWifiEnabled: Boolean = false
    private var password: String = ""
    private val otherSettings = mutableMapOf<String, String>()

    fun enableWifi() {
        isWifiEnabled = true
    }

    fun disableWifi() {
        isWifiEnabled = false
    }

    fun setPassword(newPassword: String) {
        password = newPassword
    }

    fun setSetting(key: String, value: String) {
        otherSettings[key] = value
    }

    fun createMemento(): RouterStateMemento {
        return RouterStateMemento(isWifiEnabled, password, otherSettings.toMap()) // Create a copy of the map
    }

    fun restoreFromMemento(memento: RouterStateMemento) {
        isWifiEnabled = memento.isWifiEnabled
        password = memento.password
        otherSettings.clear()
        otherSettings.putAll(memento.otherSettings)
    }

    override fun toString(): String {
        return "Router State: Wi-Fi Enabled: $isWifiEnabled, Password: $password, Other Settings: $otherSettings"
    }
}

// Caretaker
class RouterStateManager {
    private val mementos = mutableListOf<RouterStateMemento>()

    fun saveMemento(memento: RouterStateMemento) {
        mementos.add(memento)
    }

    fun undo(): RouterStateMemento? {
        if (mementos.isNotEmpty()) {
            return mementos.removeAt(mementos.size - 1)
        }
        return null
    }
}
