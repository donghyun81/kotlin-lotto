import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.Test

class MoneyTest {
    @Test
    fun `돈이 음수일 경우 예외 처리`() {
        assertThatThrownBy { Money(-1) }.isInstanceOf(IllegalArgumentException::class.java)
    }

    @Test
    fun `구입하는 금액이 돈보다 높으면 예외 처리`() {
        val money = Money(2000)
        assertThatThrownBy { money.purchase(2300) }.isInstanceOf(IllegalArgumentException::class.java)
    }
}
