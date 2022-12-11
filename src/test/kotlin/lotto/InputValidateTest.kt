package lotto

import lotto.view.validate.InputValidate
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource
import org.junit.jupiter.params.provider.ValueSource

class InputValidateTest {
    lateinit var inputValidate: InputValidate

    @BeforeEach
    fun setup() {
        inputValidate = InputValidate()
    }

    @ParameterizedTest
    @ValueSource(strings = ["1,2,4,5,6","1,56,12,34,55,0","1,2,3,4,5,f","1,2,3,,4,5,6"])
    fun `당첨 번호 입력 형식 테스트` (input:String){
        assertThrows<IllegalArgumentException>() {
            inputValidate.winningNumbersForm(input)
        }
    }

}