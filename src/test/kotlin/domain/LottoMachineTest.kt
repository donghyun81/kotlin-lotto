package domain

import domain.model.LottoTicket
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class LottoMachineTest {
    private lateinit var lottoMachine: LottoMachine

    @Test
    fun `로또를 생성하는 기능 추가`() {
        lottoMachine = FakeLottoMachine()
        assertThat(lottoMachine.create().map { it.numbers }).isEqualTo(
            listOf(
                LottoTicket(1, 2, 3, 4, 5, 6),
                LottoTicket(1, 2, 3, 4, 5, 6),
            ).map { it.numbers },
        )
    }

    class FakeLottoMachine : LottoMachine {
        override fun create(): List<LottoTicket> = listOf(LottoTicket(1, 2, 3, 4, 5, 6), LottoTicket(1, 2, 3, 4, 5, 6))
    }
}
