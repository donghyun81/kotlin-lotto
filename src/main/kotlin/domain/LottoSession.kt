package domain

import domain.event.LottoEvent
import domain.model.Buyer
import domain.model.LottoTicket
import domain.model.WinningLotto

class LottoSession(
    private val lottoEvent: LottoEvent,
) {
    val payMoney = lottoEvent.onInitMoney(LOTTO_PRICE)
    private var buyer: Buyer = Buyer(payMoney)
    private var winningLotto: WinningLotto? = null

    fun initWinning() {
        val winningNumbers = lottoEvent.onWinningNumbers()
        val bonusNumber = lottoEvent.onBonusNumber(winningNumbers)
        winningLotto = WinningLotto(winningNumbers, bonusNumber)
    }

    fun isWinningReady(): Boolean = winningLotto != null

    fun lottoTickets(): List<LottoTicket> = buyer.lottoTickets

    fun winningLotto(): WinningLotto = checkNotNull(winningLotto) { "초기화되었는지 확인하고 사용" }

    fun autoPurchaseCount() = buyer.purchasableCount(LOTTO_PRICE)

    fun purchase(
        count: Int,
        lottoMachine: LottoMachine,
    ) {
        buyer = buyer.purchase(LOTTO_PRICE * count, lottoMachine.create())
        lottoEvent.onLottoInit(buyer.lottoTickets)
    }

    companion object {
        private const val LOTTO_PRICE = 1000
    }
}
