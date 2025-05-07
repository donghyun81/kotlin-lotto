package domain

import common.LottoRank
import domain.model.LottoRanks
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import java.math.BigDecimal

class LottoRanksTest {
    @Test
    fun `총 당첨 금액을 정확히 계산한다`() {
        // given
        val rankCount =
            mapOf(
                // 2,000,000,000
                LottoRank.FIRST to 1,
                // 30,000,000 * 2 = 60,000,000
                LottoRank.SECOND to 2,
                // 1,500,000 * 3 = 4,500,000
                LottoRank.THIRD to 3,
                // 50,000 * 4 = 200,000
                LottoRank.FOURTH to 4,
                // 5,000 * 5 = 25,000
                LottoRank.FIFTH to 5,
                // 0
                LottoRank.NONE to 10,
            )

        val lottoRanks = LottoRanks(rankCount)

        // when
        val total = lottoRanks.totalPrize()

        // then
        val expected = BigDecimal("2064725000") // 합계
        assertThat(total).isEqualByComparingTo(expected)
    }
}
