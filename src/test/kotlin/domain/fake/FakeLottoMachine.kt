package domain.fake

import domain.LottoMachine
import domain.model.LottoTicket

class FakeLottoMachine : LottoMachine {
    override fun create(): List<LottoTicket> = listOf(LottoTicket(1, 2, 3, 4, 5, 6), LottoTicket(1, 2, 3, 4, 5, 6))
}
