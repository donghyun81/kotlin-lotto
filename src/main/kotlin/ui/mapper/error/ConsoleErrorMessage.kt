package ui.mapper.error

class ConsoleErrorMessage : PlatformErrorMessage {
    override fun convert(message: String): String = message
}
