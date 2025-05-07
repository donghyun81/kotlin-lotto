package domain.model

import domain.exception.MoneyException

@JvmInline
value class Money(val value: Int) {
    init {
        require(value >= MIN_VALUE)
    }

    fun purchase(price: Int): Money {
        validatePurchase(price)
        return Money(value - price)
    }

    private fun validatePurchase(price: Int) {
        if (!purchasable(price)) throw MoneyException.InvalidPurchaseException(price, value)
    }

    fun purchasableCount(price: Int) = value / price

    fun purchasable(price: Int): Boolean = value >= price

    companion object {
        private const val MIN_VALUE = 0
    }
}
