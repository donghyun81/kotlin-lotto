package lotto

import lotto.model.Lotto
import lotto.model.LottoTickets
import lotto.model.Statistic
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Assertions.assertArrayEquals
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll

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
        assertAll(
            { assertEquals(statistic.winning()[Pair(5, false)],2)},
            { assertEquals(statistic.winning()[Pair(5, true)],1)},
            { assertEquals(statistic.winning()[Pair(6, false)],1)},
        )
    }
}