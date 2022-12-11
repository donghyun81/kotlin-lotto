package lotto

import lotto.model.Lotto
import lotto.model.LottoTickets
import lotto.model.Statistic
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class StatisticTest {
    lateinit var statistic: Statistic

    @BeforeEach
    fun setup() {
        val lottoTickets = listOf(
            Lotto(listOf(1, 2, 3, 4, 5, 6)),
            Lotto(listOf(1, 2, 3, 4, 5, 7)),
            Lotto(listOf(1, 2, 3, 4, 5, 8)),
            Lotto(listOf(1, 2, 3, 4, 5, 8))
        )
        val winningNumbers = listOf(1, 2, 3, 4, 5, 6)
        val bonusNumber = 7
        statistic = Statistic(lottoTickets, winningNumbers, bonusNumber)
    }

    @Test
    fun `당첨 통계 반환 기능 테스트`() {
        Assertions.assertThat(statistic.winning().keys).contains(Pair(5, true))
        Assertions.assertThat(statistic.winning().keys).contains(Pair(6, false))
        Assertions.assertThat(statistic.winning().keys).contains(Pair(5, false))
        Assertions.assertThat(statistic.winning()[Pair(5, true)]).isEqualTo(1)
        Assertions.assertThat(statistic.winning()[Pair(5, false)]).isEqualTo(2)
        Assertions.assertThat(statistic.winning()[Pair(6, false)]).isEqualTo(1)
    }
}