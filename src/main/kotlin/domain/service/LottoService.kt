package domain.service

import domain.LottoSession
import domain.model.LottoRanks
import domain.model.LottosRanking
import java.math.BigDecimal
import java.math.RoundingMode

class LottoService {
    private val lottosRanking = LottosRanking()

    fun ranks(session: LottoSession): LottoRanks = lottosRanking.ranks(session.lottoTickets(), session.winningLotto())

    fun yield(session: LottoSession): BigDecimal {
        val totalPrize = lottosRanking.ranks(session.lottoTickets(), session.winningLotto()).totalPrize()
        return totalPrize.divide(session.payMoney.value.toBigDecimal(), 2, RoundingMode.HALF_UP)
    }
}
