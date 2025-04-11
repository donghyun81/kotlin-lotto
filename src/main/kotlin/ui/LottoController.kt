package ui

import domain.LottoSession
import domain.RandomLottoMachine
import domain.event.LottoEvent
import domain.exception.LottoException
import domain.exception.MoneyException
import domain.model.LottoNumber
import domain.model.LottoNumbers
import domain.model.LottoTicket
import domain.model.Money
import domain.service.LottoService

class LottoController(
    private val inputView: InputView = InputView(),
    private val outputView: OutputView = OutputView(),
) {
    private val lottoService = LottoService()
    private val lottoEvent = lottoEvent()

    fun run() {
        val lottoSession = LottoSession(lottoEvent)
        onPurchase(lottoSession)
        onWinning(lottoSession)
        onWinningResult(lottoSession)
    }

    private fun onPurchase(lottoSession: LottoSession) {
        val autoPurchaseCount = lottoSession.autoPurchaseCount()
        val randomLottoMachine = RandomLottoMachine(autoPurchaseCount)
        lottoSession.purchase(autoPurchaseCount, randomLottoMachine, lottoEvent)
    }

    private fun onWinning(lottoSession: LottoSession) {
        lottoSession.initWinning(lottoEvent)
    }

    private fun onWinningResult(lottoSession: LottoSession) {
        if (!lottoSession.isWinningReady()) {
            outputView.printWinningNumberMissing()
            return
        }
        val ranks = lottoService.ranks(lottoSession)
        val yield = lottoService.yield(lottoSession)
        outputView.printWinningResults(ranks.value)
        outputView.printTotalReturns(yield)
    }

    private fun lottoEvent() =
        object : LottoEvent {
            override fun onInitMoney(minMoney: Int): Money =
                retryEvent {
                    val purchaseAmount = inputView.readPurchaseAmount() ?: return@retryEvent null
                    if (purchaseAmount < minMoney) throw MoneyException.InvalidInitMoneyException(minMoney)
                    Money(purchaseAmount)
                }

            override fun onLottoInit(lottoTickets: List<LottoTicket>) {
                lottoTickets.forEach { lotto ->
                    outputView.printPurchaseLottoNumbers(lotto.numbers)
                }
            }

            override fun onWinningNumbers(): LottoNumbers {
                outputView.printWinningNumbers()
                val lottoNumbers =
                    retryEvent {
                        val lottoNumbers = inputView.readLottoNumbers() ?: return@retryEvent null
                        if (lottoNumbers.toSet().size != lottoNumbers.size) {
                            throw LottoException.DuplicateNumberException(
                                lottoNumbers,
                            )
                        }
                        LottoNumbers(lottoNumbers)
                    }
                return lottoNumbers
            }

            override fun onBonusNumber(lottoNumbers: LottoNumbers): LottoNumber {
                outputView.printBonusNumber()
                val bonusNumber =
                    retryEvent {
                        val bonusNumber = inputView.readBonusNumber() ?: return@retryEvent null
                        if (lottoNumbers.contains(LottoNumber(bonusNumber))) {
                            throw LottoException.BonusOverlapException(
                                lottoNumbers.value,
                                bonusNumber,
                            )
                        }
                        LottoNumber(bonusNumber)
                    }
                return bonusNumber
            }
        }

    private fun <T> retryEvent(event: () -> T?): T {
        while (true) {
            runCatching { event() }
                .onSuccess { result ->
                    if (result != null) return result
                    outputView.printInvalidMessage()
                }
                .onFailure { e ->
                    val message = mapErrorMessage(e)
                    outputView.printErrorMessage(message)
                }
        }
    }

    private fun mapErrorMessage(e: Throwable): String =
        when (e) {
            is LottoException.InvalidNumbersSizeException -> "입력하신 번호는${e.numbers} 입니다. 로또 번호는 6개입니다."
            is LottoException.InvalidNumberRangeException -> "${e.number}는 1~45 사이여야 해요."
            is LottoException.DuplicateNumberException -> "중복된 번호가 있어요: ${e.numbers}"
            is LottoException.BonusOverlapException -> "당첨 번호${e.winningNumbers}와 보너스 번호(${e.bonusNumber})가 중복됐어요"
            is MoneyException.InvalidInitMoneyException -> "시작 금액은 최소 ${e.minValue}원 이상이어야 해요."
            is MoneyException.InvalidPurchaseException -> "보유하신 ${e.money}로 총 구매가격 ${e.price}원을 구매할 수 없습니다."
            else -> "알 수 없는 오류가 발생했어요."
        }
}
