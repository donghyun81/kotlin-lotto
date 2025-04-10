package domain.model

import domain.exception.LottoException

class LottoNumbers(private val _value: Set<LottoNumber>) {
    val value get() = _value.map { it.value }

    constructor(numbers: List<Int>) : this(numbers.map { LottoNumber(it) }.toSet())

    constructor(vararg number: Int) : this(number.map { LottoNumber(it) }.toSet())

    init {
        if (_value.size != 6) throw LottoException.InvalidNumbersSizeException(_value.map { it.value })
    }

    fun intersect(lottoNumbers: List<Int>): Set<Int> = lottoNumbers.intersect(value.toSet())

    fun contains(lottoNumber: LottoNumber): Boolean = _value.contains(lottoNumber)
}
