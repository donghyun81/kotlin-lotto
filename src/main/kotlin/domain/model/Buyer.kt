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
        return Buyer(money.purchase(price), lottoTickets + _lottoTickets)
    }

    fun purchasableCount(price: Int) = money.purchasableCount(price)
}
