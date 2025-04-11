package domain.model

class Buyer(
    val money: Money,
    private val _lottoTickets: List<LottoTicket> = listOf(),
) {
    val lottoTickets = _lottoTickets.toList()

    fun purchase(
        price: Int,
        lottoTickets: List<LottoTicket>,
    ): Buyer {
        return Buyer(money.purchase(price), _lottoTickets + lottoTickets)
    }

    fun purchasableCount(price: Int) = money.purchasableCount(price)

    fun purchasable(totalPrice: Int): Boolean = money.purchasable(totalPrice)
}
