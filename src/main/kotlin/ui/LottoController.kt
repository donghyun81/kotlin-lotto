package ui

import domain.LottoSession
import domain.RandomLottoMachine
import domain.service.LottoService
import ui.event.ConsoleLottoEvent
import ui.mapper.ConsoleErrorMessageMapper
import ui.util.Retry
import ui.view.InputView
import ui.view.OutputView

class LottoController(
    inputView: InputView = InputView(),
    private val outputView: OutputView = OutputView(),
) {
    private val lottoService = LottoService()
    private val retry = Retry(outputView, ConsoleErrorMessageMapper())
    private val lottoEvent = ConsoleLottoEvent(inputView, outputView, retry)

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
        val ranks = lottoService.ranks(lottoSession.lottoTickets(), lottoSession.winningLotto())
        val totalPrize = lottoService.prize(lottoSession.lottoTickets(), lottoSession.winningLotto())
        val yield = lottoService.yield(totalPrize, lottoSession.payMoney.value)
        outputView.printWinningResults(ranks.value)
        outputView.printTotalReturns(yield)
    }
}
