package ui.mapper

import domain.exception.LottoException
import domain.exception.MoneyException

class ConsoleErrorMessageMapper : ErrorMessageMapper {
    override fun koreanErrorMessage(e: Throwable): String =
        when (e) {
            is LottoException.InvalidNumbersSizeException -> "입력하신 번호는${e.numbers} 입니다. 로또 번호는 6개입니다."
            is LottoException.InvalidNumberRangeException -> "${e.number}는 1~45 사이여야 해요."
            is LottoException.DuplicateNumberException -> "중복된 번호가 있어요: ${e.numbers}"
            is LottoException.BonusOverlapException -> "당첨 번호${e.winningNumbers}와 보너스 번호(${e.bonusNumber})가 중복됐어요"
            is MoneyException.InvalidInitMoneyException -> "시작 금액은 최소 ${e.minValue}원 이상이어야 해요."
            is MoneyException.InvalidPurchaseException -> "보유하신 ${e.money}로 총 구매가격 ${e.price}원을 구매할 수 없습니다."
            else -> "알 수 없는 오류가 발생했어요."
        }
}
