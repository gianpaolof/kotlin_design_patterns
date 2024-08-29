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
import MiniUSBCharger
import ModernRouterAdapter
import MyBasicIRouter
import NetworkConfigurationFacade
import NewTPlinkRouter
import OldNetgearRouter
import PortForwardingManager
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
}