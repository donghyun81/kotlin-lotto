package domain

import domain.event.LottoEvent
import domain.fake.FakePurchaseEvent
import domain.fake.FakeWinningEvent
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class LottoSessionTest {
    private lateinit var session: LottoSession

    @BeforeEach
    fun setUp() {
        session = LottoSession(LottoEvent(FakePurchaseEvent(), FakeWinningEvent()))
    }

    @Test
    fun `로또 자동 구매 개수 계산`() {
        assertThat(session.autoPurchaseCount()).isEqualTo(5)
    }

    @Test
    fun `로또 구매 시 티켓이 저장된다`() {
        session = session.purchaseRandom(1)
        assertThat(session.lottoTickets).hasSize(1)
    }

    @Test
    fun `당첨 번호 초기화 이후 준비 완료 상태가 된다`() {
        assertThat(session.isWinningReady()).isFalse()
        session = session.initWinning()
        assertThat(session.isWinningReady()).isTrue()
    }

    @Test
    fun `당첨 번호가 초기화되지 않았을 경우 예외 발생`() {
        assertThatThrownBy { session.winningLotto }
            .isInstanceOf(IllegalStateException::class.java)
            .hasMessageContaining("당첨 번호가 초기화 되지 않았습니다.")
    }

    @Test
    fun `당첨 번호가 초기화된 경우 정상 반환`() {
        session = session.initWinning()
        val winning = session.winningLotto
        assertThat(winning).isNotNull()
    }
}
