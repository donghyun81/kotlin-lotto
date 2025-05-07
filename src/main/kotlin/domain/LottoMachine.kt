package domain

import domain.model.LottoTicket

interface LottoMachine {
    fun create(): List<LottoTicket>
}
