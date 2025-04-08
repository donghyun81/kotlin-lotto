@JvmInline
value class Money(private val value: Int) {
    init {
        require(value >= 0)
    }

    fun purchase(price: Int): Money {
        require(value >= price)
        return Money(value - price)
    }
}
