package org.example

import AdvancedRemote
import BasicCPEBuilder
import BasicRemote
import CPEConfigurationDirector
import Cellphone
import Charger
import DHCPManager
import DVDPlayer
import EUPlugToUSPlugAdapter
import EuropeanCellphone
import IRouter2
import MiniUSBCharger
import ModernRouterAdapter
import MyBasicIRouter
import NetworkConfigurationFacade
import NewTPlinkRouter
import OldNetgearRouter
import PortForwardingManager
import RealRouter
import SecureRouterProxy
import TV
import USOutlet
import WifiManager
import vpnDecorator
import withParentalControl
import withVPN


fun main() {

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
            val flyweight = NetworkPacketFactory.getPacket(packetData.sourceIP, packetData.destinationIP, packetData.protocol)
            flyweight.process(packetData.payload)
    }



    //proxy
    val r: IRouter2 = SecureRouterProxy(RealRouter())

    r.configureSettings() // Output: Configuring router settings...
    r.reboot() // Output: Rebooting router...
}