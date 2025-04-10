package domain

import domain.model.LottoNumber
import domain.model.LottoTicket
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource

class LottoTicketTest {
    @Test
    fun `로또 넘버가 6개가 아닐 경우 예외 처리`() {
        assertThatThrownBy { LottoTicket(1, 2, 3, 4, 5, 6, 7) }.isInstanceOf(IllegalArgumentException::class.java)
    }

    @Test
    fun `로또 넘버가 중복된 숫자를 포함하고 6개가 아닐 경우 예외 처리`() {
        assertThatThrownBy { LottoTicket(1, 2, 3, 5, 5, 6, 6) }.isInstanceOf(IllegalArgumentException::class.java)
    }

    @ParameterizedTest
    @CsvSource(
        "1, true",
        "34, false",
    )
    fun `로또 번호 포함 여부 확인`(
        number: Int,
        actual: Boolean,
    ) {
        val lottoTicket = LottoTicket(1, 2, 3, 4, 5, 6)
        assertThat(lottoTicket.contains(LottoNumber(number))).isEqualTo(actual)
    }
}
