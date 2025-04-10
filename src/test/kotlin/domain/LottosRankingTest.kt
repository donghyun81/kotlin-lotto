package domain

import common.LottoRank
import domain.model.LottoNumber
import domain.model.LottoNumbers
import domain.model.LottoTicket
import domain.model.LottosRanking
import domain.model.WinningLotto
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class LottosRankingTest {
    private lateinit var winningLotto: WinningLotto
    private lateinit var lottosRanking: LottosRanking

    @BeforeEach
    fun setUp() {
        winningLotto = WinningLotto(LottoNumbers(1, 2, 3, 4, 5, 6), LottoNumber(7))
        lottosRanking = LottosRanking()
    }

    @Test
    fun `로또 랭킹 결과를 집계한다`() {
        val tickets =
            listOf(
                // FIRST
                LottoTicket(1, 2, 3, 4, 5, 6),
                // SECOND
                LottoTicket(1, 2, 3, 4, 5, 7),
                // THIRD
                LottoTicket(1, 2, 3, 4, 5, 45),
                // FOURTH
                LottoTicket(1, 2, 3, 4, 44, 45),
                // FIFTH
                LottoTicket(1, 2, 3, 40, 41, 42),
                // NONE
                LottoTicket(1, 2, 40, 41, 42, 43),
            )

        val ranking = LottosRanking()
        val result = ranking.ranks(tickets, winningLotto).value

        assertThat(result).containsExactlyInAnyOrderEntriesOf(
            mapOf(
                LottoRank.FIRST to 1,
                LottoRank.SECOND to 1,
                LottoRank.THIRD to 1,
                LottoRank.FOURTH to 1,
                LottoRank.FIFTH to 1,
                LottoRank.NONE to 1,
            ),
        )
    }
}
