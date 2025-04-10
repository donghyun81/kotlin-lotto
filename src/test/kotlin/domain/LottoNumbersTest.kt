package domain

import domain.exception.LottoException
import domain.model.LottoNumber
import domain.model.LottoNumbers
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.Test

class LottoNumbersTest {
    @Test
    fun `로또 번호가 6개일 때 정상 생성된다`() {
        val numbers = LottoNumbers(1, 2, 3, 4, 5, 6)
        assertThat(numbers.value).containsExactlyInAnyOrder(1, 2, 3, 4, 5, 6)
    }

    @Test
    fun `로또 번호가 중복 없이 6개가 아니면 예외를 던진다`() {
        assertThatThrownBy { LottoNumbers(1, 2, 3) }
            .isInstanceOf(LottoException.InvalidNumbersSizeException::class.java)
    }

    @Test
    fun `intersect는 겹치는 번호만 반환한다`() {
        val lotto = LottoNumbers(1, 2, 3, 4, 5, 6)
        val other = listOf(5, 6, 7, 8)
        assertThat(lotto.intersect(other)).containsExactlyInAnyOrder(5, 6)
    }

    @Test
    fun `contains는 지정한 LottoNumber가 포함되어 있는지 확인한다`() {
        val lotto = LottoNumbers(1, 2, 3, 4, 5, 6)
        assertThat(lotto.contains(LottoNumber(4))).isTrue
        assertThat(lotto.contains(LottoNumber(10))).isFalse
    }
}
