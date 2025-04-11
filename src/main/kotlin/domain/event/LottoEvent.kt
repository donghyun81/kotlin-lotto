package domain.event

import domain.model.LottoNumber
import domain.model.LottoNumbers
import domain.model.Money

interface LottoEvent {
    fun onInitMoney(minMoney: Int): Money

    fun onWinningNumbers(): LottoNumbers

    fun onBonusNumber(lottoNumbers: LottoNumbers): LottoNumber
}
