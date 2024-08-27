package org.example

import BasicCPEBuilder
import BasicRouter
import CPEConfigurationDirector
import ViewCreator
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
    val view = ViewCreator(UIElementFactory.THEME.Light)
    val btn = view.createButton()
    println(btn)


    //decorator pattern
    val basicRouter = BasicRouter()
    println("Basic Router: ${basicRouter.features()}, Cost: $${basicRouter.cost()}")

    val routerWithVPN = vpnDecorator.decorate(basicRouter)
    println("Router with VPN: ${routerWithVPN.features()}, Cost: $${routerWithVPN.cost()}")

    val routerWithVPNAndParentalControls = parentalControlDecorator.decorate(routerWithVPN)
    println("Router with VPN & Parental Controls: ${routerWithVPNAndParentalControls.features()}, Cost: $${routerWithVPNAndParentalControls.cost()}")

    //decorator with extension
    basicRouter.withVPN().withParentalControl()
}