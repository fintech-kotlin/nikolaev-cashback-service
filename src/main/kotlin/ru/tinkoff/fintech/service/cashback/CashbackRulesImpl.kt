package ru.tinkoff.fintech.service.cashback

import ru.tinkoff.fintech.model.TransactionInfo
import java.security.KeyStore
import java.time.LocalDate

class CashbackRulesImpl : CashbackRules {
    override fun loyaltyBlack(transactionInfo: TransactionInfo): Double {
        return transactionInfo.transactionSum * 0.01
    }

    override fun dividedByNumber(transactionInfo: TransactionInfo, number: Int): Double {
        return if (transactionInfo.transactionSum % number == 0.00) {
            number / 100.00
        } else {
            0.0
        }
    }

    override fun loyaltyAllMccSoftware(transactionInfo: TransactionInfo): Double {
        var allowedReplace = 1
        var palindrome: String = (transactionInfo.transactionSum * 100).toInt().toString()
        for (i in 0 until palindrome.length / 2) {
            if (palindrome[i] != palindrome[palindrome.length - 1 - i]) allowedReplace--
        }
        return if (allowedReplace >= 0) {
            transactionInfo.transactionSum * nok(
                transactionInfo.firstName.length,
                transactionInfo.lastName.length
            ) / 1000 / 100
        } else {
            0.00
        }
    }


    override fun loyaltyAndMccBeer(transactionInfo: TransactionInfo): Double {
        val monthFirstLetterVector = "#яфмамииасонд"
        val date = LocalDate.now()
        return if (transactionInfo.firstName.toLowerCase() == "олег") {
            if (transactionInfo.lastName.toLowerCase() == "олегов") transactionInfo.transactionSum * (10.0 / 100) else transactionInfo.transactionSum * (7.0 / 100)
        } else if (monthFirstLetterVector[date.monthValue] == transactionInfo.firstName[0]) {
            transactionInfo.transactionSum * (5.0 / 100)
        } else if (monthFirstLetterVector[date.minusMonths(1).monthValue] == transactionInfo.firstName[0] ||
            monthFirstLetterVector[date.plusMonths(1).monthValue] == transactionInfo.firstName[0]
        ) {
            transactionInfo.transactionSum * (3.0 / 100)
        } else transactionInfo.transactionSum * (2.0 / 100)
    }

    private fun nok(x: Int, y: Int): Int {
        return x * y / nod(x, y)
    }

    private fun nod(x: Int, y: Int): Int {
        val temp: Int = x % y
        return if (temp == 0) y else nod(y, temp)
    }


}