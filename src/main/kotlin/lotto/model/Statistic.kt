package lotto.model

class Statistic(
    private val lottoTickets: List<Lotto>,
    private val winningNumbers: List<Int>,
    private val bonusWinningNumber: Int
) {

    fun getRank(lotto:Lotto) : Pair<Int,Boolean> {
        return Pair(lotto.matches(Lotto(winningNumbers)),lotto.numbers().contains(bonusWinningNumber))
    }

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

    fun getRevenue(rank: Pair<Int, Boolean>): Int {
        var revenue = 0
        when (rank.first) {
            6 -> revenue += 2_000_000_000
            5 -> revenue += if (rank.second) 30_000_000 else 1_500_000
            4 -> revenue += 50_000
            3 -> revenue += 5_000
        }
        return revenue
    }
    fun rateOfTotalRevenue(amount:Int) :String {
        var rateOfRevenue = 0F
        winning().mapKeys { rank ->
            rateOfRevenue += getRevenue(rank.key) * rank.value
        }
        return String.format("%.1f%%", rateOfRevenue / amount.toFloat() * 100)
    }

}