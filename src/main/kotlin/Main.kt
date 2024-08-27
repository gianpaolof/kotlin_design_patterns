package org.example

import BasicCPEBuilder
import BasicRouter
import CPEConfigurationDirector
import FactoryRouterCreator
import MyBasicRouter
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

    val factory = FactoryRouterCreator(routerType)

    val router = when (routerType) {
        RouterFactory.TYPE.Basic -> factory.createBasicRouter()
        RouterFactory.TYPE.Gaming -> factory.createGamingRouter()
        RouterFactory.TYPE.Enterprise -> factory.createEnterpriseRouter()
    }

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
}