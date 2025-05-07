package ui.mapper.error

interface LanguageErrorMessage {
    fun create(e: Throwable): String
}
