package domain.event

import domain.LottoMachine
import domain.model.Money

interface PurchaseEvent {
    fun onInitMoney(minMoney: Int): Money

    fun onManualLottoMachine(count: Int): LottoMachine

    fun onRandomLottoMachine(count: Int): LottoMachine
}
