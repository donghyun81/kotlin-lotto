package ui.mapper

interface ErrorMessageMapper {
    fun koreanErrorMessage(e: Throwable): String
}
