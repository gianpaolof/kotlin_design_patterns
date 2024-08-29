interface PaymentStrategy {
    fun pay(amount: Double)
}

class CreditCardPaymentStrategy : PaymentStrategy {
    override fun pay(amount: Double) {
        println("Paying $amount using Credit Card")
    }
}

class PayPalPaymentStrategy : PaymentStrategy {
    override fun pay(amount: Double) {
        println("Paying $amount using PayPal")
    }
}

class BankTransferPaymentStrategy : PaymentStrategy {
    override fun pay(amount: Double) {
        println("Paying $amount using Bank Transfer")
    }
}

class PaymentProcessor(private var strategy: PaymentStrategy) {
    fun processPayment(amount: Double) {
        strategy.pay(amount)
    }

    fun setPaymentStrategy(strategy: PaymentStrategy) {
        this.strategy = strategy
    }
}
