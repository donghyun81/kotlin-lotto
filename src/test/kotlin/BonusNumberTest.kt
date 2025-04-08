import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.Test

class BonusNumberTest {
    @Test
    fun `보너스 숫자는 로또 숫자들과 중복이 있을 경우 예외 처리`() {
        assertThatThrownBy {
            BonusNumber(
                LottoNumber(1),
                Lotto(1, 2, 3, 4, 5, 6),
            )
        }.isInstanceOf(IllegalArgumentException::class.java)
    }
}
