package domain.fake

import domain.event.WinningEvent
import domain.model.LottoNumber
import domain.model.LottoNumbers

class FakeWinningEvent(
    private val fixedWinningNumbers: LottoNumbers = LottoNumbers(1, 2, 3, 4, 5, 6),
    private val fixedBonusNumber: LottoNumber = LottoNumber(7),
) : WinningEvent {
    override fun onWinningNumbers(): LottoNumbers {
        return fixedWinningNumbers
    }

    override fun onBonusNumber(lottoNumbers: LottoNumbers): LottoNumber {
        return fixedBonusNumber
    }
}
