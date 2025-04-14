package ui.mapper.error

import domain.exception.LottoException
import domain.exception.MoneyException

class KoreanErrorMessage : LanguageErrorMessage {
    override fun create(e: Throwable): String = mapToKoreanMessage(e)

    private fun mapToKoreanMessage(e: Throwable) =
        when (e) {
            is LottoException -> koreanLottoErrorMessage(e)
            is MoneyException -> koreanMoneyErrorMessage(e)
            else -> "알 수 없는 오류가 발생했습니다. 문제가 계속되면 고객센터로 문의해주세요."
        }

    private fun koreanLottoErrorMessage(exception: LottoException): String =
        when (exception) {
            is LottoException.InvalidNumbersSizeException -> "입력하신 번호는${exception.numbers} 입니다. 로또 번호는 6개입니다."
            is LottoException.InvalidNumberRangeException -> "입력하신 로또 번호는${exception.number}는 1~45 사이여야 합니다."
            is LottoException.DuplicateNumberException -> "중복된 번호가 있습니다: ${exception.numbers}"
            is LottoException.BonusOverlapException -> "당첨 번호${exception.winningNumbers}와 보너스 번호(${exception.bonusNumber})가 중복됐어요"
        }

    private fun koreanMoneyErrorMessage(exception: MoneyException): String =
        when (exception) {
            is MoneyException.InvalidInitMoneyException -> "시작 금액은 최소 ${exception.minValue}원 이상이어야 해요."
            is MoneyException.InvalidPurchaseException -> "보유하신 ${exception.money}로 총 구매가격 ${exception.price}원을 구매할 수 없습니다."
        }
}
