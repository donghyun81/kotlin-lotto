package lotto.model

import camp.nextstep.edu.missionutils.Randoms

class LottoTickets {

    fun count(amount: Int) = amount.div(1000)

    fun getLottoTickets(count: Int): List<Lotto> {
        val lottoTickets = mutableListOf<Lotto>()
        repeat(count) {
            lottoTickets.add(Lotto(Randoms.pickUniqueNumbersInRange(1,45,6)))
        }
        return lottoTickets
    }

}