package domain

import domain.model.LottoNumbers
import domain.model.LottoTicket

class ManualLottoMachine(private val lottosNumbers: List<LottoNumbers>) : LottoMachine {
    override fun create(): List<LottoTicket> {
        return generateTickets()
    }

    private fun generateTickets() = lottosNumbers.map { LottoTicket(it) }
}
