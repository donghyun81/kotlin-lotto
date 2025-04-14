package domain

import domain.event.LottoEvent
import domain.exception.MoneyException
import domain.model.Buyer
import domain.model.Money
import domain.model.WinningLotto

class LottoSession(
    private val lottoEvent: LottoEvent,
    private val initMoney: Money = lottoEvent.onInitMoney(LOTTO_PRICE),
    private val buyer: Buyer = Buyer(initMoney),
    private val _winningLotto: WinningLotto? = null,
) {
    val winningLotto get() = requireWinningLotto()
    val lottoTickets get() = buyer.lottoTickets
    val usedMoney get() = calculateUseMoney()

    private fun calculateUseMoney() = initMoney.value - buyer.money.value

    private fun requireWinningLotto() = checkNotNull(_winningLotto) { VALIDATE_WINNING_INIT_MESSAGE }

    fun initWinning(): LottoSession {
        val currentLottoSession = LottoSession(lottoEvent, initMoney, buyer, generateWinningLotto())
        currentLottoSession.validateInitWinning()
        return currentLottoSession
    }

    private fun generateWinningLotto(): WinningLotto {
        val winningNumbers = lottoEvent.onWinningNumbers()
        val bonusNumber = lottoEvent.onBonusNumber(winningNumbers)
        return WinningLotto(winningNumbers, bonusNumber)
    }

    private fun validateInitWinning() {
        check(isWinningReady()) { VALIDATE_WINNING_INIT_MESSAGE }
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

    fun purchaseManual(count: Int): LottoSession {
        val lottoMachine = lottoEvent.onManualLottoMachine(count)
        return LottoSession(
            lottoEvent,
            initMoney,
            generatePurchasedBuyer(count, lottoMachine),
            _winningLotto,
        )
    }

    fun purchaseRandom(count: Int): LottoSession {
        val lottoMachine = lottoEvent.onRandomLottoMachine(count)
        return LottoSession(
            lottoEvent,
            initMoney,
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
        private const val VALIDATE_WINNING_INIT_MESSAGE = "당첨 번호가 초기화 되지 않았습니다."
    }
}
