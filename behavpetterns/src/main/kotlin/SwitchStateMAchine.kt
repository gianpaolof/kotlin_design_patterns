import java.time.LocalTime
import java.time.format.DateTimeFormatter

interface ISwitchState {
    fun powerOn()
    fun powerOff()
    fun connect()
    fun disconnect()
    fun checkStatus()
    fun configurePort(portNumber: Int, vlanId: Int)
}

internal class PowerOffStateS(private val context: SwitchContext) : ISwitchState {
    override fun powerOn() {
        context.state = BootingStateS(context)
        context.log("Transitioning to Booting state.")
    }

    override fun powerOff() {
        println("Already powered off.")
    }

    override fun connect() {
        println("Cannot connect while powered off.")
    }

    override fun disconnect() {
        println("Cannot disconnect while powered off.")
    }

    override fun checkStatus() {
        println("Switch is powered off.")
    }

    override fun configurePort(portNumber: Int, vlanId: Int) {
        println("Cannot configure port while powered off.")
    }
}

internal class BootingStateS(private val context: SwitchContext) : ISwitchState {
    init {
        context.log("Booting...")
        // Simulate booting process (replace with actual logic)
        Thread.sleep(2000)
        context.state = DisconnectedStateS(context)
        context.log("Boot complete. Switch is now disconnected.")
    }

    override fun powerOn() {
        println("Already powering on.")
    }

    override fun powerOff() {
        println("Cannot power off while booting.")
    }

    override fun connect() {
        println("Cannot connect while booting.")
    }

    override fun disconnect() {
        println("Cannot disconnect while booting.")
    }

    override fun checkStatus() {
        println("Switch is booting up.")
    }

    override fun configurePort(portNumber: Int, vlanId: Int) {
        println("Cannot configure port while booting.")
    }
}

internal class DisconnectedStateS(private val context: SwitchContext) : ISwitchState {
    override fun powerOn() {
        println("Already powered on.")
    }

    override fun powerOff() {
        context.state = PowerOffStateS(context)
        context.log("Transitioning to Power Off state.")
    }

    override fun connect() {
        context.state = ConnectingStateS(context)
        context.log("Transitioning to Connecting state.")
    }

    override fun disconnect() {
        println("Already disconnected.")
    }

    override fun checkStatus() {
        println("Switch is disconnected.")
    }

    override fun configurePort(portNumber: Int, vlanId: Int) {
        println("Cannot configure port while disconnected.")
    }
}

internal class ConnectingStateS(private val context: SwitchContext) : ISwitchState {
    init {
        context.log("Establishing network connections...")
        // Simulate connection process (replace with actual logic)
        Thread.sleep(1000)
        context.state = ConnectedStateS(context)
        context.log("Connection established. Switch is now connected.")
    }

    override fun powerOn() {
        println("Already powered on.")
    }

    override fun powerOff() {
        println("Cannot power off while connecting.")
    }

    override fun connect() {
        println("Already connecting.")
    }

    override fun disconnect() {
        println("Cannot disconnect while connecting.")
    }

    override fun checkStatus() {
        println("Switch is connecting...")
    }

    override fun configurePort(portNumber: Int, vlanId: Int) {
        println("Cannot configure port while connecting.")
    }
}

internal class ConnectedStateS(private val context: SwitchContext) : ISwitchState {
    override fun powerOn() {
        println("Already powered on.")
    }

    override fun powerOff() {
        context.state = PowerOffStateS(context)
        context.log("Transitioning to Power Off state.")
    }

    override fun connect() {
        println("Already connected.")
    }

    override fun disconnect() {
        context.state = DisconnectedStateS(context)
        context.log("Transitioning to Disconnected state.")
    }

    override fun checkStatus() {
        println("Switch is connected.")
        // ... (Potentially provide more detailed status information)
    }

    override fun configurePort(portNumber: Int, vlanId: Int) {
        println("Configuring port $portNumber with VLAN ID $vlanId")
        // ... (Implementation to configure the port)
    }
}

class SwitchContext {
    var state: ISwitchState = PowerOffStateS(this)

    fun powerOn() {
        log("Action: Power On")
        state.powerOn()
    }

    fun powerOff() {
        log("Action: Power Off")
        state.powerOff()
    }

    fun connect() {
        log("Action: Connect")
        state.connect()
    }

    fun disconnect() {
        log("Action: Disconnect")
        state.disconnect()
    }

    fun checkStatus() {
        log("Action: Check Status")
        state.checkStatus()
    }

    fun configurePort(portNumber: Int, vlanId: Int) {
        log("Action: Configure Port $portNumber with VLAN ID $vlanId")
        state.configurePort(portNumber, vlanId)
    }

    fun log(message: String) {
        val currentTime = LocalTime.now()
        println("[${currentTime.format(DateTimeFormatter.ofPattern("HH:mm:ss"))}] $message")
    }
}

