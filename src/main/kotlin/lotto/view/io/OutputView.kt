package lotto.view.io

class OutputView {

    fun guidePurchaseAmount() {
        println("구입금액을 입력해 주세요.")
    }

    fun purchaseCount(count: Int) {
        println("${count}개를 구매했습니다.")
    }

    fun purchaseLottoTickets(lottoTickets: List<List<Int>>) {
        for (lottoTicket in lottoTickets) {
            println(lottoTicket)
        }
    }

    fun guideWinNumbers() {
        println("당첨 번호를 입력해 주세요.")
    }

    fun guideBonusNumbers() {
        println("보너스 번호를 입력해 주세요.")
    }
}