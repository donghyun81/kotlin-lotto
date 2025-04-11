package domain.model

import domain.exception.MoneyException

@JvmInline
value class Money(val value: Int) {
    init {
        require(value >= 0)
    }

    fun purchase(price: Int): Money {
        if (!purchasable(price)) throw MoneyException.InvalidPurchaseException(price, value)
        return Money(value - price)
    }

    fun purchasableCount(price: Int) = value / price

    fun purchasable(totalPrice: Int): Boolean = value >= totalPrice
}
