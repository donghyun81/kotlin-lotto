package ui.event

import domain.event.WinningEvent
import domain.exception.LottoException
import domain.model.LottoNumber
import domain.model.LottoNumbers
import ui.util.Retry
import ui.view.InputView
import ui.view.OutputView

class ConsoleWinningEvent(
    private val inputView: InputView,
    private val outputView: OutputView,
    private val retry: Retry,
) : WinningEvent {
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
