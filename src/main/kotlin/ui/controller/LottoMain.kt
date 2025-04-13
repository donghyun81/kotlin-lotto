package ui.controller

import domain.LottoSession
import domain.ManualLottoMachine
import domain.RandomLottoMachine
import domain.model.LottoNumbers
import domain.service.LottoService
import ui.event.ConsoleLottoEvent
import ui.mapper.ConsoleErrorMessage
import ui.util.Retry
import ui.view.InputView
import ui.view.OutputView

class LottoMain(
    private val inputView: InputView = InputView(),
    private val outputView: OutputView = OutputView(),
) {
    private val lottoService = LottoService()
    private val retry = Retry(outputView, ConsoleErrorMessage())
    private val lottoEvent = ConsoleLottoEvent(inputView, outputView, retry)
    private lateinit var lottoSession: LottoSession

    fun run() {
        lottoSession = LottoSession(lottoEvent)
        onPurchase()
        onWinning()
        onWinningResult()
    }

    private fun onPurchase() {
        val manualCount = readManualLottoCount()
        purchaseManualLottos(manualCount)
        val autoCount = lottoSession.autoPurchaseCount()
        purchaseAutoLottos(autoCount)
        printPurchaseSummary(manualCount, autoCount)
    }

    private fun readManualLottoCount(): Int =
        retry.retryEvent {
            val count = inputView.readManualLottoCount() ?: return@retryEvent null
            lottoSession.requirePurchasable(count)
            count
        }

    private fun readManualLottoNumbers(count: Int): List<LottoNumbers> {
        outputView.printManualLottoNumbers()
        return List(count) { retry.retryEvent { LottoNumbers(inputView.readLottoNumbers() ?: return@retryEvent null) } }
    }

    private fun purchaseManualLottos(manualCount: Int) {
        val manualLottos = readManualLottoNumbers(manualCount)
        lottoSession = lottoSession.purchase(manualLottos.size, ManualLottoMachine(manualLottos))
    }

    private fun purchaseAutoLottos(autoCount: Int) {
        lottoSession = lottoSession.purchase(autoCount, RandomLottoMachine(autoCount))
    }

    private fun printPurchaseSummary(
        manualCount: Int,
        autoCount: Int,
    ) {
        outputView.printPurchaseLottoCount(manualCount, autoCount)
        outputView.printPurchaseLottoNumbers(lottoSession.lottoTickets)
    }

    private fun onWinningResult() {
        if (!lottoSession.isWinningReady()) {
            outputView.printWinningNumberMissing()
            onWinning()
        }
        val ranks = lottoService.ranks(lottoSession.lottoTickets, lottoSession.winningLotto)
        val totalPrize = lottoService.prize(lottoSession.lottoTickets, lottoSession.winningLotto)
        val yield = lottoService.yield(totalPrize, lottoSession.payMoney.value)
        outputView.printWinningResults(ranks.value)
        outputView.printTotalReturns(yield)
    }

    private fun onWinning() {
        lottoSession = lottoSession.initWinning()
    }
}
