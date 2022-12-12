package lotto

import lotto.model.Lotto
import lotto.model.Statistic
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll


class StatisticTest {
    lateinit var statistic: Statistic

    @BeforeEach
    fun setup() {
        val lottoTickets = listOf(
            Lotto(listOf(10,20,30,40,45,1)),
            Lotto(listOf(7,5,4,3,2,1)),
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
            { assertEquals(statistic.winning()[Pair(1, false)],1)},
        )
    }

    @Test
    fun `당첨 금액을 구하는 기능 테스트`(){
        assertAll(
            { assertEquals(statistic.getRevenue(Pair(2,true)),0)} ,
            { assertEquals(statistic.getRevenue(Pair(3,true)),5000)} ,
            { assertEquals(statistic.getRevenue(Pair(4,false)),50_000)} ,
            { assertEquals(statistic.getRevenue(Pair(4,true)),50_000)} ,
            { assertEquals(statistic.getRevenue(Pair(5,false)),1_500_000)} ,
            { assertEquals(statistic.getRevenue(Pair(5,true)),30_000_000)} ,
            { assertEquals(statistic.getRevenue(Pair(6,false)),2_000_000_000)} ,
        )
    }

    @Test
    fun `총 수익률을 반환하는 기능 테스트`() {
        assertAll(
            { assertEquals(statistic.rateOfTotalRevenue(4_000),"825000.0%")} ,
            { assertEquals(statistic.rateOfTotalRevenue(10_000),"330000.0%")} ,
            { assertEquals(statistic.rateOfTotalRevenue(2_000),"1650000.0%")} ,
            { assertEquals(statistic.rateOfTotalRevenue(1_000),"3300000.0%")} ,
        )
    }
}