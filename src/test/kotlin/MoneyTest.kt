import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.Test

@JvmInline
value class Money(private val value: Int) {
    fun purchase(price: Int): Money {
        require(value >= price)
        return Money(value - price)
    }
}

class MoneyTest {
    @Test
    fun `구입하는 금액이 돈보다 높으면 예외 처리`() {
        val money = Money(2000)
        assertThatThrownBy { money.purchase(2300) }.isInstanceOf(IllegalArgumentException::class.java)
    }
}
