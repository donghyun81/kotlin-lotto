import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.Test

class LottoTest {
    @Test
    fun `로또 넘버가 6개가 아닐 경우 예외 처리`() {
        assertThatThrownBy { Lotto(1, 2, 3, 4, 5, 6, 7) }.isInstanceOf(IllegalArgumentException::class.java)
    }

    @Test
    fun `로또 넘버가 중복된 숫자를 포함하고 6개가 아닐 경우 예외 처리`() {
        assertThatThrownBy { Lotto(1, 2, 3, 5, 5, 6, 6) }.isInstanceOf(IllegalArgumentException::class.java)
    }
}
