package lotto.model

class Lotto(private val numbers: List<Int>) {
    init {
        require(numbers.size == 6)
    }

    fun numbers() = numbers

    fun matches(winning: Lotto) = numbers.intersect(winning.numbers.toSet()).count()

}
