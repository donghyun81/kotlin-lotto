import domain.LottoSession
import domain.event.factory.LottoEventFactory
import domain.service.LottoService
import ui.controller.LottoMain
import ui.event.factory.ConsoleLottoEventFactory
import ui.mapper.error.ErrorMessage
import ui.mapper.error.LanguageErrorMessage
import ui.mapper.error.PlatformErrorMessage
import ui.util.Retry
import ui.view.InputView
import ui.view.OutputView

class AppConfig(
    private val languageErrorMessage: LanguageErrorMessage,
    private val platformErrorMessage: PlatformErrorMessage,
) {
    fun createConsoleMain(): LottoMain {
        val inputView = InputView()
        val outputView = OutputView()
        val errorMessage = ErrorMessage(languageErrorMessage, platformErrorMessage)
        val retry = Retry(outputView, errorMessage)
        val lottoEventFactory: LottoEventFactory = ConsoleLottoEventFactory(inputView, outputView, retry)
        val lottoEvent = lottoEventFactory.create()
        val initLottoSession = LottoSession(lottoEvent)
        val lottoService = LottoService()

        return LottoMain(
            inputView = inputView,
            outputView = outputView,
            retry = retry,
            lottoSession = initLottoSession,
            lottoEvent = lottoEvent,
            lottoService = lottoService,
        )
    }
}
