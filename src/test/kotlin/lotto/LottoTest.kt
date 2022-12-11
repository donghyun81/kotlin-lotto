package lotto

import lotto.model.Lotto
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows


class LottoTest {

    lateinit var lotto:Lotto

    @BeforeEach
    fun setup(){
        lotto = Lotto(listOf(1, 2, 3, 4, 5, 6,))
    }
    @Test
    fun `로또 번호의 개수가 6개가 넘어가면 예외가 발생한다`() {
        assertThrows<IllegalArgumentException> {
            Lotto(listOf(1, 2, 3, 4, 5, 6, 7))
        }
    }

    // TODO: 이 테스트가 통과할 수 있게 구현 코드 작성
    @Test
    fun `로또 번호에 중복된 숫자가 있으면 예외가 발생한다`() {
        assertThrows<IllegalArgumentException> {
            Lotto(listOf(1, 2, 3, 4, 5, 5))
        }
    }

    @Test
    fun `당첨 번호와 몇개가 일치하는지 구하는 기능 테스트`() {
        Assertions.assertThat(lotto.matches(Lotto(listOf(1,2,3,4,5,8)))).isEqualTo(5)
    }
}
