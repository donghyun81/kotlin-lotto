package domain.event.factory

import domain.event.LottoEvent

interface LottoEventFactory {
    fun create(): LottoEvent
}
