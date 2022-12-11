package lotto

import lotto.model.Lotto
import lotto.model.LottoTickets
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.RepeatedTest
import org.junit.jupiter.api.RepetitionInfo
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource

class LottoTicketsTest {
    lateinit var lottoTickets: LottoTickets

    @BeforeEach
    fun setup() {
        lottoTickets = LottoTickets()
    }

    @ParameterizedTest
    @CsvSource(
        "3000,3",
        "0,0",
        "33000,33",
    )
    fun `구매한 로또 티켓의 개수를 구하는 기능 테스트`(price: Int, count: Int) {
        Assertions.assertThat(lottoTickets.count(price)).isEqualTo(count)
    }
}