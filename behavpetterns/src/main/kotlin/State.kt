interface IRouterState {
    fun connect()
    fun disconnect()
    fun configure()
    fun reboot()
}

class ConnectedState(private val context: IRouterContext) : IRouterState {
    override fun connect() {
        println("Already connected.")
    }

    override fun disconnect() {
        context.state = DisconnectedState(context)
        println("Disconnected.")
    }

    override fun configure() {
        println("Configuring settings...")
    }

    override fun reboot() {
        context.state = RebootingState(context)
        println("Rebooting...")
    }
}

class DisconnectedState(private val context: IRouterContext) : IRouterState {
    override fun connect() {
        context.state = ConnectedState(context)
        println("Connecting...")
    }

    override fun disconnect() {
        println("Already disconnected.")
    }

    override fun configure() {
        println("Cannot configure while disconnected.")
    }

    override fun reboot() {
        println("Cannot reboot while disconnected.")
    }
}

class RebootingState(private val context: IRouterContext) : IRouterState {
    override fun connect() {
        println("${System.currentTimeMillis()} Cannot connect while rebooting.")
    }

    override fun disconnect() {
        println("Cannot disconnect while rebooting.")
    }

    override fun configure() {
        println("Cannot configure while rebooting.")
    }

    override fun reboot() {
        println("Already rebooting.")
    }

    init {
        println("Current Thread: ${System.currentTimeMillis()} ${Thread.currentThread().name}")
        // Simulate rebooting process (replace with actual logic)
        println("Rebooting in progress...")
        Thread.sleep(3000) // Simulate a 3-second delay
        context.state = DisconnectedState(context)
        println("Reboot complete. Router is now disconnected.")
    }
}

class IRouterContext {
    var state: IRouterState = DisconnectedState(this)

    fun connect() = state.connect()
    fun disconnect() = state.disconnect()
    fun configure() = state.configure()
    fun reboot() = state.reboot()
}
