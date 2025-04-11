package domain

import domain.fake.FakeLottoEvent
import domain.fake.FakeLottoMachine
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class LottoSessionTest {
    private lateinit var session: LottoSession

    @BeforeEach
    fun setUp() {
        session = LottoSession(FakeLottoEvent(5000))
    }

    @Test
    fun `로또 자동 구매 개수 계산`() {
        assertThat(session.autoPurchaseCount()).isEqualTo(5)
    }

    @Test
    fun `로또 구매 시 티켓이 저장된다`() {
        session.purchase(2, FakeLottoMachine(), FakeLottoEvent(2000))
        assertThat(session.lottoTickets()).hasSize(2)
    }

    @Test
    fun `당첨 번호 초기화 이후 준비 완료 상태가 된다`() {
        assertThat(session.isWinningReady()).isFalse()
        session.initWinning(FakeLottoEvent(5000))
        assertThat(session.isWinningReady()).isTrue()
    }

    @Test
    fun `당첨 번호가 초기화되지 않았을 경우 예외 발생`() {
        assertThatThrownBy { session.winningLotto() }
            .isInstanceOf(IllegalStateException::class.java)
            .hasMessageContaining("초기화되었는지 확인하고 사용")
    }

    @Test
    fun `당첨 번호가 초기화된 경우 정상 반환`() {
        session.initWinning(FakeLottoEvent(5000))
        val winning = session.winningLotto()
        assertThat(winning).isNotNull()
    }
}
