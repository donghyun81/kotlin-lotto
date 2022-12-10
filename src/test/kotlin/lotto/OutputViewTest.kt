package lotto

import lotto.view.io.OutputView
import org.junit.jupiter.api.BeforeEach
import java.io.ByteArrayOutputStream
import java.io.OutputStream
import java.io.PrintStream

class OutputViewTest {
    lateinit var outputView:OutputView
    private val outputStream:OutputStream = ByteArrayOutputStream()

    @BeforeEach
    fun `setup`() {
        outputView = OutputView()
        System.setOut(PrintStream(outputStream))
    }

}