package ui.util

import ui.mapper.error.ErrorMessage
import ui.view.OutputView

class Retry(
    private val outputView: OutputView,
    private val errorMessage: ErrorMessage,
) {
    fun <T> retryEvent(event: () -> T?): T {
        while (true) {
            runCatching { event() }
                .onSuccess { result ->
                    if (result != null) return result
                    outputView.printInvalidMessage()
                }
                .onFailure { exception ->
                    val message = errorMessage.create(exception)
                    outputView.printErrorMessage(message)
                    rethrowIfUnknown(exception)
                }
        }
    }

    private fun rethrowIfUnknown(exception: Throwable) {
        if (exception !is IllegalArgumentException) throw exception
    }
}
