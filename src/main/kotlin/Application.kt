import ui.mapper.error.ConsoleErrorMessage
import ui.mapper.error.KoreanErrorMessage

fun main() {
    val koreanApp = AppConfig(KoreanErrorMessage(), ConsoleErrorMessage())
    koreanApp.createConsoleMain().run()
}
