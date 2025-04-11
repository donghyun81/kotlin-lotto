package domain

import common.LottoRank
import domain.model.LottoNumber
import domain.model.LottoNumbers
import domain.model.LottoTicket
import domain.model.WinningLotto
import domain.service.LottoService
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import java.math.BigDecimal

class LottoServiceTest {
    private val winningLotto =
        WinningLotto(
            LottoNumbers(1, 2, 3, 4, 5, 6),
            LottoNumber(7),
        )

    private val tickets =
        listOf(
            // 1등
            LottoTicket(1, 2, 3, 4, 5, 6),
            // 2등
            LottoTicket(1, 2, 3, 4, 5, 7),
            // 3등
            LottoTicket(1, 2, 3, 4, 5, 8),
            // 5등
            LottoTicket(1, 2, 3, 9, 16, 11),
            // 5등
            LottoTicket(1, 2, 3, 10, 11, 12),
            // NONE
            LottoTicket(10, 11, 12, 13, 14, 15),
        )

    private val service = LottoService()

    @Test
    fun `등수별 카운트를 계산할 수 있다`() {
        val ranks = service.ranks(tickets, winningLotto)
        assertThat(ranks.value[LottoRank.FIRST]).isEqualTo(1)
        assertThat(ranks.value[LottoRank.SECOND]).isEqualTo(1)
        assertThat(ranks.value[LottoRank.THIRD]).isEqualTo(1)
        assertThat(ranks.value[LottoRank.FIFTH]).isEqualTo(2)
        assertThat(ranks.value[LottoRank.NONE]).isEqualTo(1)
    }

    @Test
    fun `총 상금을 계산할 수 있다`() {
        val total = service.prize(tickets, winningLotto)
        val expected =
            LottoRank.FIRST.prize.toBigDecimal() +
                LottoRank.SECOND.prize.toBigDecimal() +
                LottoRank.THIRD.prize.toBigDecimal() +
                LottoRank.FIFTH.prize.toBigDecimal() +
                LottoRank.FIFTH.prize.toBigDecimal()
        assertThat(total).isEqualTo(expected)
    }

    @Test
    fun `수익률을 소수점 둘째자리까지 계산한다`() {
        val totalPrize = LottoRank.FIFTH.prize.toBigDecimal()
        val yield = service.yield(totalPrize, 3000)
        assertThat(yield).isEqualTo(BigDecimal("1.67"))
    }
}
