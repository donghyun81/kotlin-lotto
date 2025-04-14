package domain.event

class LottoEvent(
    private val purchaseEvent: PurchaseEvent,
    private val winningEvent: WinningEvent,
) : PurchaseEvent by purchaseEvent, WinningEvent by winningEvent
