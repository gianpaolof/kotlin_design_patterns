package org.example

import AdminDashboard
import AdminDashboard2

import AdvancedRemote
import BandwidthMonitor
import BasicCPEBuilder
import BasicRemote
import CPEConfigurationDirector
import Cellphone
import Charger
import Computer
import ConcreteObserver
import ConcreteSubject
import CreditCardPaymentStrategy
import DHCPManager
import DVDPlayer
import EUPlugToUSPlugAdapter
import EasyBoxGPRouter
import EnableWifiCommand
import EuropeanCellphone
import FirmwareUpdater
import Graph
import IGPRouter
import IRouter
import IRouter2
import IRouterContext
import Level1SupportHandler
import Level2SupportHandler
import Level3SupportHandler
import MiniUSBCharger
import ModernRouterAdapter
import MyBasicIRouter
import NetworkAdminConsole
import NetworkConfigurationFacade
import NetworkManager
import NetworkMonitor
import NetworkMonitor2

import NewTPlinkRouter
import Node
import OldNetgearRouter
import PayPalPaymentStrategy
import PaymentProcessor
import PortForwardingManager
import RealRouter
import Router
import Router666
import RouterStateManager
import SecureRouterProxy
import SecurityScanner
import SetPasswordCommand
import SillyRouter
import SupportRequest
import SwitchContext
import TV
import USOutlet
import WifiManager
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import vpnDecorator
import withParentalControl
import withVPN
import java.time.Clock


fun main() {

    creationalPatterns()

    structuralPatterns()

    behavPatterns()
}

private fun structuralPatterns() {
    //decorator pattern
    val basicRouter = MyBasicIRouter()
    println("Basic Router: ${basicRouter.features()}, Cost: $${basicRouter.cost()}")

    val vpnDecorator = vpnDecorator.decorate(basicRouter)
    println(vpnDecorator.features())

    //decorator with extension
    basicRouter.withVPN().withParentalControl()


    //adpter pattern
    val legacyRouter = OldNetgearRouter()
    legacyRouter.connect("MyLegacyNetwork", "oldpassword")
    legacyRouter.disconnect()

    val modernRouter = NewTPlinkRouter()
    val adapter = ModernRouterAdapter(modernRouter)
    adapter.connect("MyModernNetwork", "newpassword")
    adapter.disconnect()

    //adapter 2
    val europeanCellphone = EuropeanCellphone()
    val usOutlet = USOutlet()
    val miniUSBCharger = MiniUSBCharger()

    val cellphone = Cellphone(miniUSBCharger)

    // Direct connection (not possible)
    // val charger = Charger(europeanCellphone) // Type mismatch

    // Using the adapter
    val ada = EUPlugToUSPlugAdapter(europeanCellphone)
    val charger = Charger(ada)
    charger.plugIn()

    cellphone.charge()

    //bridge
    val tv = TV()
    val basicRemote = BasicRemote(tv)
    basicRemote.on()
    basicRemote.setChannel(5)

    val dvd = DVDPlayer()
    val advancedRemote = AdvancedRemote(dvd)
    advancedRemote.on()

    //facade
    val wifiManager = WifiManager()
    val portForwardingManager = PortForwardingManager()
    val dhcpManager = DHCPManager()

    val networkConfigFacade = NetworkConfigurationFacade(wifiManager, portForwardingManager, dhcpManager)

    networkConfigFacade.setupBasicNetwork()
    // ... or
    networkConfigFacade.setupGamingNetwork()

    //flyweight
    data class PacketData(val sourceIP: String, val destinationIP: String, val protocol: String, val payload: ByteArray)

    val receivedPackets = listOf(
        PacketData("192.168.0.10", "192.168.0.1", "TCP", byteArrayOf(1, 2, 3, 4)),
        PacketData("192.168.0.20", "192.168.0.1", "UDP", byteArrayOf(5, 6, 7, 8)),
        PacketData("192.168.0.10", "192.168.0.1", "TCP", byteArrayOf(9, 10, 11, 12)),
        PacketData("10.0.0.50", "192.168.0.1", "TCP", byteArrayOf(13, 14, 15, 16)),
        PacketData("192.168.0.30", "192.168.0.1", "UDP", byteArrayOf(17, 18, 19, 20))
    )

    for (packetData in receivedPackets) {
        val flyweight =
            NetworkPacketFactory.getPacket(packetData.sourceIP, packetData.destinationIP, packetData.protocol)
        flyweight.process(packetData.payload)
    }


    //proxy
    val r: IRouter2 = SecureRouterProxy(RealRouter())

    r.configureSettings() // Output: Configuring router settings...
    r.reboot() // Output: Rebooting router...

    //composite
    val rootNode = Node("A", isRoot = true) // Create the root node
    val graph = Graph(rootNode) // Create the graph with the root node

    val nodeB = Node("B")
    val nodeC = Node("C")
    val nodeD = Node("D")

    rootNode.addConnection(nodeB)
    rootNode.addConnection(nodeC)
    nodeB.addConnection(nodeD)

    graph.display()
}

private fun behavPatterns(){
    //startegy
    val paymentProcessor = PaymentProcessor(CreditCardPaymentStrategy())
    paymentProcessor.processPayment(100.0) // Output: Paying 100.0 using Credit Card

    paymentProcessor.setPaymentStrategy(PayPalPaymentStrategy())
    paymentProcessor.processPayment(50.0)  // Output: Paying 50.0 using PayPal


    //state
    val router = IRouterContext()

    router.configure() // Output: Cannot configure while disconnected.
    router.connect()   // Output: Connecting...
    router.configure() // Output: Configuring settings...
    router.reboot()    // Output: Rebooting...
    println("Main Current Thread: ${System.currentTimeMillis()} ${Thread.currentThread().name}")
    router.connect()   // Output: Cannot connect while rebooting.

    //state machine for a switch
    val networkSwitch = SwitchContext()

    networkSwitch.connect()    // Cannot connect while powered off.
    networkSwitch.powerOn()    // Powering on...
    networkSwitch.connect()    // Cannot connect while booting.
    networkSwitch.disconnect() // Cannot disconnect while booting.
    networkSwitch.powerOff()   // Cannot power off while booting.

    // After boot complete (simulated delay)
    networkSwitch.connect()    // Connecting...
    networkSwitch.checkStatus()       // Switch is connected.
    networkSwitch.configurePort(1, 10) // Configuring port 1 with VLAN ID 10
    networkSwitch.powerOff()          // Powering off...

    //command pattern
    val r = IGPRouter()
    val console = NetworkAdminConsole()

    console.executeCommand(EnableWifiCommand(r))
    console.executeCommand(SetPasswordCommand(r, "new_secure_password"))

    console.undoLastCommand() // Output: Password reverted to old_password (undo)
    console.undoLastCommand() // Output: Wi-Fi disabled (undo)

    //chain of command
    val level3Handler = Level3SupportHandler(null)
    val level2Handler = Level2SupportHandler(level3Handler)
    val level1Handler = Level1SupportHandler(level2Handler)

    val request1 = SupportRequest("Printer not working", "basic")
    val request2 = SupportRequest("Network outage", "complex")
    val request3 = SupportRequest("Network on fire", "highest")

    level1Handler.handleRequest(request1) // Handled by Level 1
    level1Handler.handleRequest(request2) // Escalated to Level 2, then Level 3
    level1Handler.handleRequest(request3) // escalate from 1 to 3

    //mediator
    val networkManager = NetworkManager()
    val router1 = Router666("Main Router", networkManager)
    val computer1 = Computer("Alice's Computer", networkManager)
    val computer2 = Computer("Bob's Computer", networkManager)

    networkManager.registerDevice(router1)
    networkManager.registerDevice(computer1)
    networkManager.registerDevice(computer2)

    computer1.establishConnection(router1)
    computer2.establishConnection(router1)

    computer1.sendMessage("Hello from Alice!")
    computer2.sendMessage("Hi Alice, it's Bob!")

    println(networkManager.checkDeviceStatus("Main Router"))

    router1.goDown()
    computer1.sendMessage("Are you there, Bob?")

    router1.comeUp()

    //memento pattern for a router state
    val ebr = EasyBoxGPRouter()
    val stateManager = RouterStateManager()

    ebr.enableWifi()
    ebr.setPassword("securePassword")
    ebr.setSetting("firewall", "enabled")

    stateManager.saveMemento(ebr.createMemento())
    println("Initial state: $ebr")

    ebr.disableWifi()
    ebr.setPassword("new_password")
    ebr.setSetting("firewall", "disabled")

    println("Modified state: $ebr")

    val memento = stateManager.undo()
    memento?.let { ebr.restoreFromMemento(it) }
    println("Restored state: $ebr")

    //visitor pattern
    val bandwidthMonitor = BandwidthMonitor()
    val securityScanner = SecurityScanner()
    val firmwareUpdater = FirmwareUpdater()

    val rg= SillyRouter("silly")
    rg.performAction(bandwidthMonitor)
    rg.performAction(securityScanner)
    rg.performAction(firmwareUpdater)

    //observer from scratch
    val networkMonitor = NetworkMonitor()
    val adminDashboard = AdminDashboard(networkMonitor)
    // ... (You can add other observers here)

    networkMonitor.newDeviceConnected("Router1")
    networkMonitor.deviceDisconnected("Switch2")

    //observer with rxjava
    val networkMonitor2 = NetworkMonitor2()
    val adminDashboard2 = AdminDashboard2(networkMonitor2)
    // ... (You can add other observers here)

    networkMonitor2.newDeviceConnected("Router1 rx java")
    networkMonitor2.deviceDisconnected("Switch2 rx java")


    //obs kotlinx
    val subject = ConcreteSubject()
    val observer1 = ConcreteObserver()
    val observer2 = ConcreteObserver()

    subject.registerObserver(observer1)
    subject.registerObserver(observer2)

    subject.someActionThatChangesState("New data!")

    subject.removeObserver(observer2)

    subject.someActionThatChangesState("Another update!")
}

private fun creationalPatterns() {
    //builder pattern
    val cpeBuilder = BasicCPEBuilder()
    val director = CPEConfigurationDirector(cpeBuilder)
    director.configureBasicCPE()
    val cpe = cpeBuilder.build()
    println(cpe)


    //factory pattern
    val routerType = RouterFactory.TYPE.Gaming

    val factory = RouterFactory.createFactory(routerType)

    val router = factory.createRouter() // Client only needs to call createRouter

    // Use the created router
    //router.configure()
}