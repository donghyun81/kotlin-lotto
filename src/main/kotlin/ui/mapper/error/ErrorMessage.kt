package ui.mapper.error

class ErrorMessage(
    private val languageErrorMessage: LanguageErrorMessage,
    private val platformErrorMessage: PlatformErrorMessage,
) {
    fun create(e: Throwable): String {
        val message = languageErrorMessage.create(e)
        return platformErrorMessage.convert(message)
    }
}
