package domain.event

import domain.model.LottoNumber
import domain.model.LottoNumbers
import domain.model.LottoTicket
import domain.model.Money

interface LottoEvent {
    fun onInitMoney(minMoney: Int): Money

    fun onLottoInit(lottoTickets: List<LottoTicket>)

    fun onWinningNumbers(): LottoNumbers

    fun onBonusNumber(lottoNumbers: LottoNumbers): LottoNumber
}
