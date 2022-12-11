package lotto

import lotto.view.validate.InputValidate
import org.junit.jupiter.api.BeforeEach

class InputValidateTest {
    lateinit var inputValidate: InputValidate

    @BeforeEach
    fun setup() {
        inputValidate = InputValidate()
    }
}