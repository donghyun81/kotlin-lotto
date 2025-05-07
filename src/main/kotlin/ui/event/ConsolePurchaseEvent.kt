package ui.event

import domain.LottoMachine
import domain.ManualLottoMachine
import domain.RandomLottoMachine
import domain.event.PurchaseEvent
import domain.exception.MoneyException
import domain.model.LottoNumbers
import domain.model.Money
import ui.util.Retry
import ui.view.InputView
import ui.view.OutputView

class ConsolePurchaseEvent(
    private val inputView: InputView,
    private val outputView: OutputView,
    private val retry: Retry,
) : PurchaseEvent {
    override fun onInitMoney(minMoney: Int): Money =
        retry.retryEvent {
            val purchaseAmount = inputView.readPurchaseAmount() ?: return@retryEvent null
            validatePurchaseAmount(purchaseAmount, minMoney)
            Money(purchaseAmount)
        }

    override fun onManualLottoMachine(count: Int): LottoMachine {
        outputView.printManualLottoNumbers()
        val lottoNumbers =
            List(count) { retry.retryEvent { LottoNumbers(inputView.readLottoNumbers() ?: return@retryEvent null) } }
        return ManualLottoMachine(lottoNumbers)
    }

    override fun onRandomLottoMachine(count: Int): LottoMachine = RandomLottoMachine(count)

    private fun validatePurchaseAmount(
        purchaseAmount: Int,
        minMoney: Int,
    ) {
        if (purchaseAmount < minMoney) throw MoneyException.InvalidInitMoneyException(minMoney)
    }
}
