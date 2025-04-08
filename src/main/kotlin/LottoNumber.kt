@JvmInline
value class LottoNumber(val number: Int) {
    init {
        require(number in 1..45)
    }
}
