// 1. Immutable Product (CPE / Wi-Fi Router)
data class CPE(
    val model: String,
    val brand: String,
    val hasWifi6: Boolean,
    val lanPorts: Int,
    val usbPorts: Int,
    val externalAntennas: Boolean
)

// 2. Builder (Modified for chaining)
interface CPEBuilder {
    fun model(model: String): CPEBuilder
    fun brand(brand: String): CPEBuilder
    fun wifi6Support(hasWifi6: Boolean): CPEBuilder
    fun lanPorts(lanPorts: Int): CPEBuilder
    fun usbPorts(usbPorts: Int): CPEBuilder
    fun externalAntennas(externalAntennas: Boolean): CPEBuilder
    fun build(): CPE
}

// 3. ConcreteBuilder (Modified for chaining)
class BasicCPEBuilder : CPEBuilder {
    private var model: String = ""
    private var brand: String = ""
    private var hasWifi6: Boolean = false
    private var lanPorts: Int = 0
    private var usbPorts: Int = 0
    private var externalAntennas: Boolean = false

    override fun model(model: String): CPEBuilder {
        this.model = model
        return this
    }

    override fun brand(brand: String): CPEBuilder {
        this.brand = brand
        return this
    }

    override fun wifi6Support(hasWifi6: Boolean): CPEBuilder {
        this.hasWifi6 = hasWifi6
        return this
    }

    override fun lanPorts(lanPorts: Int): CPEBuilder {
        this.lanPorts = lanPorts
        return this
    }

    override fun usbPorts(usbPorts: Int): CPEBuilder {
        this.usbPorts = usbPorts
        return this
    }

    override fun externalAntennas(externalAntennas: Boolean): CPEBuilder {
        this.externalAntennas = externalAntennas
        return this
    }

    override fun build(): CPE {
        return CPE(model, brand, hasWifi6, lanPorts, usbPorts, externalAntennas)
    }
}

// 4. Director (Modified to use chaining)
class CPEConfigurationDirector(private val builder: CPEBuilder) {
    fun configureBasicCPE() {
        builder.model("BasicRouter 100")
            .brand("GenericNetworking")
            .wifi6Support(false)
            .lanPorts(4)
            .usbPorts(1)
            .externalAntennas(false)
    }
}

