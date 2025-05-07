package domain.model

import common.LottoRank

class LottosRanking {
    fun ranks(
        lottoTickets: List<LottoTicket>,
        winningLotto: WinningLotto,
    ): LottoRanks {
        return LottoRanks(
            lottoTickets
                .map { rank(it, winningLotto) }
                .groupingBy { it }
                .eachCount(),
        )
    }

    private fun rank(
        lottoTicket: LottoTicket,
        winningLotto: WinningLotto,
    ) = LottoRank.of(winningLotto.matchCount(lottoTicket), winningLotto.hasBonus(lottoTicket))
}
