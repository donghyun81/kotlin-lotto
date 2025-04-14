package domain

import domain.model.LottoNumbers
import domain.model.LottoTicket

class RandomLottoMachine(private val count: Int) : LottoMachine {
    override fun create(): List<LottoTicket> {
        return generateTickets()
    }

    private fun generateTickets() = List(count) { generateTicket() }

    private fun generateTicket(): LottoTicket {
        val lottoNumbers = randomNumbers().take(LOTTO_SIZE)
        return LottoTicket(LottoNumbers(lottoNumbers))
    }

    private fun randomNumbers() = LOTTO_RANGE.shuffled()

    companion object {
        private val LOTTO_RANGE = 1..45
        private const val LOTTO_SIZE = 6
    }
}
