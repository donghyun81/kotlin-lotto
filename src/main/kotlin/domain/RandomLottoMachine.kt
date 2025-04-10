package domain

import domain.model.LottoNumbers
import domain.model.LottoTicket

class RandomLottoMachine(private val count: Int) : LottoMachine {
    override fun create(): List<LottoTicket> {
        return List(count) { lotto() }
    }

    private fun lotto(): LottoTicket {
        val lottoNumbers = randomNumbers().take(6)
        return LottoTicket(LottoNumbers(lottoNumbers))
    }

    private fun randomNumbers() = LOTTO_RANGE.shuffled()

    companion object {
        private val LOTTO_RANGE = 1..45
    }
}
