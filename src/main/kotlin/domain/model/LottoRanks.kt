package domain.model

import common.LottoRank

class LottoRanks(private val _value: Map<LottoRank, Int>) {
    val value get() = _value.toMap()

    fun totalPrize() = _value.entries.sumOf { (rank, count) -> rank.prize.toBigDecimal() * count.toBigDecimal() }
}
