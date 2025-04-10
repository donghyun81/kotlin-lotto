package domain

import domain.model.Buyer
import domain.model.LottoTicket
import domain.model.Money
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.Test

class BuyerTest {
    @Test
    fun `로또를 구매하면 로또 리스트와 잔액이 갱신된 새로운 Buyer를 반환한다`() {
        // given
        val money = Money(5000)
        val buyer = Buyer(money)

        // when
        val updatedBuyer = buyer.purchase(price = 1000, listOf(LottoTicket(1, 2, 3, 4, 5, 6)))

        // then
        assertThat(updatedBuyer.money.value).isEqualTo(4000)
        assertThat(updatedBuyer.lottoTickets.size).isEqualTo(1)
        assertThat(updatedBuyer.lottoTickets[0].numbers.all { it in 1..6 }).isTrue()
    }

    @Test
    fun `돈이 부족하면 예외가 발생한다`() {
        val money = Money(500)
        val buyer = Buyer(money)

        assertThatThrownBy { buyer.purchase(price = 1000, listOf(LottoTicket(1, 2, 3, 4, 5, 6))) }.isInstanceOf(
            IllegalArgumentException::class.java,
        )
    }
}
