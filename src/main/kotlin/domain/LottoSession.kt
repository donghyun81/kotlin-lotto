package domain

import domain.event.LottoEvent
import domain.exception.MoneyException
import domain.model.Buyer
import domain.model.LottoTicket
import domain.model.WinningLotto

class LottoSession(
    lottoEvent: LottoEvent,
) {
    val payMoney = lottoEvent.onInitMoney(LOTTO_PRICE)
    private var buyer: Buyer = Buyer(payMoney)
    private var winningLotto: WinningLotto? = null

    fun initWinning(lottoEvent: LottoEvent) {
        val winningNumbers = lottoEvent.onWinningNumbers()
        val bonusNumber = lottoEvent.onBonusNumber(winningNumbers)
        winningLotto = WinningLotto(winningNumbers, bonusNumber)
    }

    fun isWinningReady(): Boolean = winningLotto != null

    fun lottoTickets(): List<LottoTicket> = buyer.lottoTickets

    fun winningLotto(): WinningLotto = checkNotNull(winningLotto) { "초기화되었는지 확인하고 사용" }

    fun autoPurchaseCount() = buyer.purchasableCount(LOTTO_PRICE)

    fun validatePurchasable(count: Int) {
        if (!buyer.purchasable(count * LOTTO_PRICE)) {
            throw MoneyException.InvalidPurchaseException(
                count * LOTTO_PRICE,
                buyer.money.value,
            )
        }
    }

    fun purchase(
        count: Int,
        lottoMachine: LottoMachine,
    ) {
        buyer = buyer.purchase(LOTTO_PRICE * count, lottoMachine.create())
    }

    companion object {
        private const val LOTTO_PRICE = 1000
    }
}
