package domain.model

import domain.exception.AmountException

@JvmInline
value class Money(val value: Int) {
    init {
        require(value >= 0)
    }

    fun purchase(price: Int): Money {
        if (value < price) throw AmountException.InvalidPurchaseException(price, value)
        return Money(value - price)
    }

    fun purchasableCount(price: Int) = value / price
}
