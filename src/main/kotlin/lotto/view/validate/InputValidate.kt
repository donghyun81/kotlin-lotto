package lotto.view.validate

class InputValidate {

    fun winningNumbersForm(input: String) {
        val winningNumbers = input.split(",")
        for (winningNumber in winningNumbers) {
            require(WINNING_NUMBER_FORM.toRegex().matches(winningNumber))
        }
        require(winningNumbers.size == 6)
        require(winningNumbers.distinct().size == 6)
    }

    companion object {
        const val WINNING_NUMBER_FORM = "^[1-45]\$"
    }

}