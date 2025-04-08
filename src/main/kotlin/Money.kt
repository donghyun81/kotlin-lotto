@JvmInline
value class Money(private val value: Int) {
    fun purchase(price: Int): Money {
        require(value >= price)
        return Money(value - price)
    }
}
