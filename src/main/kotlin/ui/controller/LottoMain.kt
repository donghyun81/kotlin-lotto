package ui.controller

import domain.LottoSession
import domain.event.LottoEvent
import domain.service.LottoService
import ui.util.Retry
import ui.view.InputView
import ui.view.OutputView

class LottoMain(
    private val inputView: InputView,
    private val outputView: OutputView,
    private val retry: Retry,
    private var lottoSession: LottoSession,
    private val lottoEvent: LottoEvent,
    private val lottoService: LottoService,
) {
    fun run() {
        lottoSession = LottoSession(lottoEvent)
        onPurchase()
        onWinning()
        onWinningResult()
    }

    private fun onPurchase() {
        val manualCount = readManualLottoCount()
        purchaseManualLottoTickets(manualCount)
        val autoCount = lottoSession.autoPurchaseCount()
        purchaseAutoLottoTickets(autoCount)
        printPurchaseSummary(manualCount, autoCount)
    }

    private fun readManualLottoCount(): Int =
        retry.retryEvent {
            val count = inputView.readManualLottoCount() ?: return@retryEvent null
            lottoSession.requirePurchasable(count)
            count
        }

    private fun purchaseManualLottoTickets(manualCount: Int) {
        lottoSession = lottoSession.purchaseManual(manualCount)
    }

    private fun purchaseAutoLottoTickets(autoCount: Int) {
        lottoSession = lottoSession.purchaseRandom(autoCount)
    }

    private fun printPurchaseSummary(
        manualCount: Int,
        autoCount: Int,
    ) {
        outputView.printPurchaseLottoCount(manualCount, autoCount)
        outputView.printPurchaseLottoNumbers(lottoSession.lottoTickets)
    }

    private fun onWinningResult() {
        ensureWinning()
        val ranks = lottoService.ranks(lottoSession.lottoTickets, lottoSession.winningLotto)
        val totalPrize = lottoService.prize(ranks)
        val yield = lottoService.yield(totalPrize, lottoSession.usedMoney)
        outputView.printWinningResults(ranks.value)
        outputView.printTotalReturns(yield)
    }

    private fun ensureWinning() {
        if (!lottoSession.isWinningReady()) {
            outputView.printWinningNumberMissing()
            onWinning()
        }
    }

    private fun onWinning() {
        lottoSession = lottoSession.initWinning()
    }
}
