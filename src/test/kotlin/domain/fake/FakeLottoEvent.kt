package domain.fake

import domain.event.LottoEvent
import domain.model.LottoNumber
import domain.model.LottoNumbers
import domain.model.LottoTicket
import domain.model.Money

class FakeLottoEvent(
    private val amount: Int = 5000,
    private val fixedWinningNumbers: LottoNumbers = LottoNumbers(1, 2, 3, 4, 5, 6),
    private val fixedBonusNumber: LottoNumber = LottoNumber(7),
) : LottoEvent {
    override fun onInitMoney(minMoney: Int): Money {
        return Money(amount)
    }

    override fun onLottoInit(lottoTickets: List<LottoTicket>) {
        // 출력 테스트 x
    }

    override fun onWinningNumbers(): LottoNumbers {
        return fixedWinningNumbers
    }

    override fun onBonusNumber(lottoNumbers: LottoNumbers): LottoNumber {
        return fixedBonusNumber
    }
}
