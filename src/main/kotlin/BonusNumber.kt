class BonusNumber(val value: LottoNumber, lotto: Lotto) {
    init {
        require(lotto.contains(value).not())
    }
}
