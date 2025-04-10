package domain.exception

sealed class LottoException : IllegalArgumentException() {
    class InvalidNumbersSizeException(val numbers: List<Int>) : LottoException()

    class InvalidNumberRangeException(val number: Int) : LottoException()

    class DuplicateNumberException(val numbers: List<Int>) : LottoException()

    class BonusOverlapException(val winningNumbers: List<Int>, val bonusNumber: Int) : LottoException()
}
