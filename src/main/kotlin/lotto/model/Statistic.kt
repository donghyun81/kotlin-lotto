package lotto.model

class Statistic(
    private val lottoTickets: List<Lotto>,
    private val winningNumbers: List<Int>,
    private val bonusWinningNumber: Int
) {

    fun winning(): MutableMap<Pair<Int, Boolean>, Int> {
        val lottoStatistic = mutableMapOf<Pair<Int, Boolean>, Int>()
        lottoTickets.map { lottoTicket ->
            val matchCount = lottoTicket.matches(Lotto(winningNumbers))
            val containsBonus = lottoTicket.numbers().contains(bonusWinningNumber)
            lottoStatistic[Pair(matchCount, containsBonus)] =
                lottoStatistic.getOrDefault(Pair(matchCount, containsBonus), 0).plus(1)
        }
        return lottoStatistic
    }


}