package domain.event

import domain.model.LottoNumber
import domain.model.LottoNumbers

interface WinningEvent {
    fun onWinningNumbers(): LottoNumbers

    fun onBonusNumber(lottoNumbers: LottoNumbers): LottoNumber
}
