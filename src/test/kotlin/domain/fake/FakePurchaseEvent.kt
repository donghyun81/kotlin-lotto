package domain.fake

import domain.LottoMachine
import domain.ManualLottoMachine
import domain.RandomLottoMachine
import domain.event.PurchaseEvent
import domain.model.LottoNumbers
import domain.model.Money

class FakePurchaseEvent(
    private val amount: Int = 5000,
    private val lottoNumbers: LottoNumbers = LottoNumbers(1, 2, 3, 4, 5, 6),
) : PurchaseEvent {
    override fun onInitMoney(minMoney: Int): Money {
        return Money(amount)
    }

    override fun onManualLottoMachine(count: Int): LottoMachine {
        return ManualLottoMachine(listOf(lottoNumbers))
    }

    override fun onRandomLottoMachine(count: Int): LottoMachine {
        return RandomLottoMachine(count)
    }
}
