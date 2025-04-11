package ui.event

import domain.event.LottoEvent
import domain.exception.LottoException
import domain.exception.MoneyException
import domain.model.LottoNumber
import domain.model.LottoNumbers
import domain.model.LottoTicket
import domain.model.Money
import ui.util.Retry
import ui.view.InputView
import ui.view.OutputView

class ConsoleLottoEvent(
    private val inputView: InputView,
    private val outputView: OutputView,
    private val retry: Retry,
) : LottoEvent {
    override fun onInitMoney(minMoney: Int): Money =
        retry.retryEvent {
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
            retry.retryEvent {
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
            retry.retryEvent {
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
