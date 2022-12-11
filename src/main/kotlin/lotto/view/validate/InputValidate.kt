package lotto.view.validate

class InputValidate {

    fun purchaseAmount(input: String) {
        require(PURCHASE_AMOUNT_FORM.toRegex().matches(input))
        require(input.toInt() % 1000 == 0)
        require(input.toInt().div(1000) >= 1)
    }

    fun winningNumbersForm(input: String) {
        val winningNumbers = input.split(",")
        for (winningNumber in winningNumbers) {
            require(WINNING_NUMBER_FORM.toRegex().matches(winningNumber))
        }
        require(winningNumbers.size == 6)
        require(winningNumbers.distinct().size == 6)
    }

    fun bonusWinningNumberForm(input: String) {
        require(WINNING_NUMBER_FORM.toRegex().matches(input))
    }

    companion object {
        const val PURCHASE_AMOUNT_FORM = "^[0-9]*\$"
        const val WINNING_NUMBER_FORM = "^[1-45]\$"

    }

}