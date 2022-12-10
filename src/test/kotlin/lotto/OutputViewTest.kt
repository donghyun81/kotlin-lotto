package lotto

import lotto.view.io.OutputView
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource
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

    @ParameterizedTest
    @CsvSource(
        "8,8개를 구매했습니다.",
        "3,3개를 구매했습니다."
    )
    fun `로또 구입 개수 출력 기능 테스트`(amount:Int,outputPurchaseCount:String) {
        outputView.purchaseCount(amount)
        Assertions.assertThat(outputStream.toString().trim()).isEqualTo(outputPurchaseCount)
    }

}