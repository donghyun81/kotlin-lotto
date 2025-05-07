package domain.exception

sealed class MoneyException : IllegalArgumentException() {
    class InvalidPurchaseException(val price: Int, val money: Int) : MoneyException()

    class InvalidInitMoneyException(val minValue: Int) : MoneyException()
}
