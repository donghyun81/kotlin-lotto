package domain.exception

sealed class MoneyException : IllegalArgumentException() {
    class InvalidPurchaseException(val price: Int, val money: Int) : LottoException()

    class InvalidInitMoneyException(val minValue: Int) : LottoException()
}
