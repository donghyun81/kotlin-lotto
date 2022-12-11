package lotto

import lotto.model.LottoTickets
import org.junit.jupiter.api.BeforeEach

class LottoTicketsTest {
    lateinit var lottoTickets: LottoTickets

    @BeforeEach
    fun setup(){
        lottoTickets = LottoTickets()
    }

}