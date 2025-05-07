package ui.event.factory

import domain.event.LottoEvent
import domain.event.factory.LottoEventFactory
import ui.event.ConsolePurchaseEvent
import ui.event.ConsoleWinningEvent
import ui.util.Retry
import ui.view.InputView
import ui.view.OutputView

class ConsoleLottoEventFactory(
    private val inputView: InputView,
    private val outputView: OutputView,
    private val retry: Retry,
) : LottoEventFactory {
    override fun create(): LottoEvent {
        val purchaseEvent = ConsolePurchaseEvent(inputView, outputView, retry)
        val winningEvent = ConsoleWinningEvent(inputView, outputView, retry)
        return LottoEvent(purchaseEvent, winningEvent)
    }
}
