package domain.model

import domain.exception.LottoException

class WinningLotto(private val lottoNumbers: LottoNumbers, private val bonusNumber: LottoNumber) {
    init {
        if (lottoNumbers.contains(bonusNumber)) throw LottoException.BonusOverlapException(lottoNumbers.value, bonusNumber.value)
    }

    fun matchCount(lottoTicket: LottoTicket): Int = lottoNumbers.intersect(lottoTicket.numbers).size

    fun hasBonus(lottoTicket: LottoTicket): Boolean = lottoTicket.contains(bonusNumber)
}
