package domain

import domain.event.LottoEvent
import domain.exception.MoneyException
import domain.model.Buyer
import domain.model.Money
import domain.model.WinningLotto

class LottoSession(
    private val lottoEvent: LottoEvent,
    val payMoney: Money = lottoEvent.onInitMoney(LOTTO_PRICE),
    private val buyer: Buyer = Buyer(payMoney),
    private val _winningLotto: WinningLotto? = null,
) {
    val winningLotto get() = requireWinningLotto()
    val lottoTickets get() = buyer.lottoTickets

    private fun requireWinningLotto() = checkNotNull(_winningLotto) { "당첨 번호가 초기화 되지 않았습니다." }

    fun initWinning(): LottoSession {
        val winningNumbers = lottoEvent.onWinningNumbers()
        val bonusNumber = lottoEvent.onBonusNumber(winningNumbers)
        val currentLottoSession = LottoSession(lottoEvent, payMoney, buyer, WinningLotto(winningNumbers, bonusNumber))
        currentLottoSession.validateInitWinning()
        return currentLottoSession
    }

    private fun validateInitWinning() {
        check(isWinningReady()) { "당첨 번호가 초기화 되지 않았습니다." }
    }

    fun isWinningReady(): Boolean = _winningLotto != null

    fun autoPurchaseCount(): Int = buyer.purchasableCount(LOTTO_PRICE)

    fun requirePurchasable(count: Int) {
        val price = count * LOTTO_PRICE
        validatePurchasable(price)
    }

    private fun validatePurchasable(price: Int) {
        if (!buyer.purchasable(price)) {
            throw MoneyException.InvalidPurchaseException(
                price,
                buyer.money.value,
            )
        }
    }

    fun purchase(
        count: Int,
        lottoMachine: LottoMachine,
    ): LottoSession {
        return LottoSession(
            lottoEvent,
            payMoney,
            generatePurchasedBuyer(count, lottoMachine),
            _winningLotto,
        )
    }

    private fun generatePurchasedBuyer(
        count: Int,
        lottoMachine: LottoMachine,
    ) = buyer.purchase(LOTTO_PRICE * count, lottoMachine.create())

    companion object {
        private const val LOTTO_PRICE = 1000
    }
}
