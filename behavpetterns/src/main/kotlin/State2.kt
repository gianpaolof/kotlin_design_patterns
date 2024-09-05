import kotlinx.coroutines.delay

enum class IRouterState2(
    val connect: (IRouterContext2) -> Unit,
    val disconnect: (IRouterContext2) -> Unit,
    val reboot: suspend (IRouterContext2) -> Unit,
) {
    CONNECTED(
        connect = {
            println("Already connected.")
        },
        disconnect = { router ->
            println("Disconnected.")
            router.state = DISCONNECTED
        },
        reboot = { router ->
            router.state = IRouterState2.REBOOTING
            println("Rebooting")
            // Perform long-running initialization here
            delay(5000) // Simulate a 2-second operation
            router.state = IRouterState2.CONNECTED

        }
    ),
    DISCONNECTED(
        connect = { router ->
            println("Connecting...")
            router.state = CONNECTED

        },
        disconnect = {
            println("Already disconnected.")
        },
        reboot = {
            println("Cannot reboot while disconnected.")
        }
    ),
    REBOOTING(
        connect = {
            println("cannot connect while rebooting")

        },
        disconnect = {
            println("Cannot disconnect while rebooting.")
        },
        reboot = {
            // Perform long-running initialization here
            //delay(2000) // Simulate a 2-second operation
            println("already rebooting")
        }
    )
}

class IRouterContext2 {
    var state: IRouterState2 = IRouterState2.DISCONNECTED
    fun connect() = state.connect(this)
    fun disconnect() = state.disconnect(this)
    suspend fun reboot() = state.reboot(this)
}