class Lotto(val numbers: Set<LottoNumber>) {
    constructor(vararg number: Int) : this(number.map { LottoNumber(it) }.toSet())

    init {
        require(numbers.size == 6)
    }

    fun contains(lottoNumber: LottoNumber) = numbers.contains(lottoNumber)
}
