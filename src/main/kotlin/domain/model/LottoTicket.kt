package domain.model

class LottoTicket(private val _numbers: LottoNumbers) {
    val numbers get() = _numbers.value

    constructor(vararg number: Int) : this(LottoNumbers(number.toList()))

    fun contains(lottoNumber: LottoNumber) = numbers.contains(lottoNumber.value)
}
