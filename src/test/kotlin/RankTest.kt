import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource

class RankTest {
    @ParameterizedTest
    @CsvSource(
        "6, false, FIRST",
        "5, true, SECOND",
        "5, false, THIRD",
        "4, false, FOURTH",
        "3, false, FIFTH",
        "2, false, NONE",
        "0, false, NONE",
    )
    fun `등수 계산 테스트`(
        matchCount: Int,
        matchBonus: Boolean,
        expected: Rank,
    ) {
        val result = Rank.of(matchCount, matchBonus)
        assertThat(result).isEqualTo(expected)
    }
}
