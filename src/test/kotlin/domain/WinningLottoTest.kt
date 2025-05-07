package domain

import domain.model.LottoNumber
import domain.model.LottoNumbers
import domain.model.LottoTicket
import domain.model.WinningLotto
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class WinningLottoTest {
    private lateinit var winningLotto: WinningLotto

    @BeforeEach
    fun setUp() {
        winningLotto = WinningLotto(LottoNumbers(1, 2, 3, 4, 5, 6), LottoNumber(7))
    }

    @Test
    fun `보너스 번호와 당첨 번호가 중복이 있을 경우 예외 처리`() {
        assertThatThrownBy {
            WinningLotto(
                LottoNumbers(listOf(1, 2, 3, 4, 5, 6)),
                LottoNumber(1),
            )
        }.isInstanceOf(IllegalArgumentException::class.java)
    }

    @Test
    fun `로또 번호 일치 개수를 계산한다`() {
        val ticket = LottoTicket(1, 2, 3, 7, 8, 9)
        val matchCount = winningLotto.matchCount(ticket)
        assertThat(matchCount).isEqualTo(3)
    }

    @Test
    fun `보너스 번호가 포함된 경우 true를 반환한다`() {
        val ticket = LottoTicket(7, 8, 9, 10, 11, 12)
        val hasBonus = winningLotto.hasBonus(ticket)
        assertThat(hasBonus).isTrue()
    }

    @Test
    fun `보너스 번호가 포함되지 않은 경우 false를 반환한다`() {
        val ticket = LottoTicket(3, 8, 9, 10, 11, 12)
        val hasBonus = winningLotto.hasBonus(ticket)
        assertThat(hasBonus).isFalse()
    }
}
