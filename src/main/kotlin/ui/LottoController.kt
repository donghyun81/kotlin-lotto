package ui

import domain.LottoSession
import domain.ManualLottoMachine
import domain.RandomLottoMachine
import domain.model.LottoNumbers
import domain.service.LottoService
import ui.event.ConsoleLottoEvent
import ui.mapper.ConsoleErrorMessageMapper
import ui.util.Retry
import ui.view.InputView
import ui.view.OutputView

class LottoController(
    private val inputView: InputView = InputView(),
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
        val manualCount = readManualLottoCount(lottoSession)
        val manualLottos = readManualLottoNumbers(manualCount)
        purchaseLottos(lottoSession, manualLottos)
        printPurchaseSummary(lottoSession, manualCount)
    }

    private fun readManualLottoCount(session: LottoSession): Int =
        retry.retryEvent {
            val count = inputView.readManualLottoCount() ?: return@retryEvent null
            session.validatePurchasable(count)
            count
        }

    private fun readManualLottoNumbers(count: Int): List<LottoNumbers> {
        outputView.printManualLottoNumbers()
        return List(count) { retry.retryEvent { LottoNumbers(inputView.readLottoNumbers() ?: return@retryEvent null) } }
    }

    private fun purchaseLottos(
        session: LottoSession,
        manualLottos: List<LottoNumbers>,
    ) {
        session.purchase(manualLottos.size, ManualLottoMachine(manualLottos))
        val autoCount = session.autoPurchaseCount()
        session.purchase(autoCount, RandomLottoMachine(autoCount))
    }

    private fun printPurchaseSummary(
        session: LottoSession,
        manualCount: Int,
    ) {
        val autoCount = session.autoPurchaseCount()
        outputView.printPurchaseLottoCount(manualCount, autoCount)
        outputView.printPurchaseLottoNumbers(session.lottoTickets())
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
