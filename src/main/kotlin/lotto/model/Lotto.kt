package lotto.model

class Lotto(private val numbers: List<Int>) {
    init {
        require(numbers.size == 6)
    }

    fun numbers() = numbers
    fun matches(win: Lotto) = numbers.intersect(win.numbers.toSet()).count()

}
