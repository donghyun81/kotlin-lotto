package domain.service

import domain.model.LottoRanks
import domain.model.LottoTicket
import domain.model.LottosRanking
import domain.model.WinningLotto
import java.math.BigDecimal
import java.math.RoundingMode

class LottoService(
    private val lottosRanking: LottosRanking = LottosRanking(),
) {
    fun ranks(
        lottoTickets: List<LottoTicket>,
        winningLotto: WinningLotto,
    ): LottoRanks = lottosRanking.ranks(lottoTickets, winningLotto)

    fun prize(
        lottoTickets: List<LottoTicket>,
        winningLotto: WinningLotto,
    ) = lottosRanking.ranks(lottoTickets, winningLotto).totalPrize()

    fun yield(
        totalPrize: BigDecimal,
        payMoney: Int,
    ): BigDecimal = totalPrize.divide(payMoney.toBigDecimal(), 2, RoundingMode.HALF_UP)
}
