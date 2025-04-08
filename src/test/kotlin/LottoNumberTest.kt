import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

class LottoNumberTest {
    @ParameterizedTest
    @ValueSource(ints = [0, 46, -3])
    fun `로또 숫자는 1~45가 아닐 경우 예외 처리`(number: Int) {
        assertThatThrownBy { LottoNumber(number) }.isInstanceOf(IllegalArgumentException::class.java)
    }
}
