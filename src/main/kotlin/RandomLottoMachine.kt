class RandomLottoMachine(private val count: Int) : LottoMachine {
    override fun create(): List<Lotto> {
        return List(count) { lotto() }
    }

    private fun lotto(): Lotto {
        val numbers = LOTTO_RANGE.toList()
        val lottoNumbers = randomNumbers(numbers).take(6).map { LottoNumber(it) }
        return Lotto(lottoNumbers.toSet())
    }

    private fun randomNumbers(numbers: List<Int>) = LOTTO_RANGE.shuffled()

    companion object {
        private val LOTTO_RANGE = 1..45
    }
}
