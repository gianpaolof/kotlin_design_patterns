package org.example

import BasicCPEBuilder
import CPEConfigurationDirector
import ModernRouterAdapter
import MyBasicRouter
import NewTPlinkRouter
import OldNetgearRouter
import parentalControlDecorator
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
    val basicRouter = MyBasicRouter()
    println("Basic Router: ${basicRouter.features()}, Cost: $${basicRouter.cost()}")

    val routerWithVPN = vpnDecorator.decorate(basicRouter)
    println("Router with VPN: ${routerWithVPN.features()}, Cost: $${routerWithVPN.cost()}")

    val routerWithVPNAndParentalControls = parentalControlDecorator.decorate(routerWithVPN)
    println("Router with VPN & Parental Controls: ${routerWithVPNAndParentalControls.features()}, Cost: $${routerWithVPNAndParentalControls.cost()}")

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
}