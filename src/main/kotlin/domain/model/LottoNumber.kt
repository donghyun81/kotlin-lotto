package domain.model

import domain.exception.LottoException

@JvmInline
value class LottoNumber(val value: Int) {
    init {
        if (value !in VALUE_RANGE) throw LottoException.InvalidNumberRangeException(value)
    }

    companion object {
        private val VALUE_RANGE = 1..45
    }
}
