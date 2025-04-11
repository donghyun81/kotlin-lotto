package ui.util

import ui.mapper.ErrorMessageMapper
import ui.view.OutputView

class Retry(
    private val outputView: OutputView,
    private val errorMessageMapper: ErrorMessageMapper,
) {
    fun <T> retryEvent(event: () -> T?): T {
        while (true) {
            runCatching { event() }
                .onSuccess { result ->
                    if (result != null) return result
                    outputView.printInvalidMessage()
                }
                .onFailure { e ->
                    val message = errorMessageMapper.koreanErrorMessage(e)
                    outputView.printErrorMessage(message)
                }
        }
    }
}
