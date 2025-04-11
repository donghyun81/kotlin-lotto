package domain

import domain.model.LottoNumbers
import domain.model.LottoTicket

class ManualLottoMachine(private val lottosNumbers: List<List<Int>>) : LottoMachine {
    override fun create(): List<LottoTicket> {
        return lottosNumbers.map { LottoTicket(LottoNumbers(it)) }
    }
}
