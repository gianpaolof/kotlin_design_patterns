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
