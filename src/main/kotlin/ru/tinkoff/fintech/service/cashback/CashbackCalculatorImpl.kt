package ru.tinkoff.fintech.service.cashback

import ru.tinkoff.fintech.model.TransactionInfo

internal const val LOYALTY_PROGRAM_BLACK = "BLACK"
internal const val LOYALTY_PROGRAM_ALL = "ALL"
internal const val LOYALTY_PROGRAM_BEER = "BEER"
internal const val MAX_CASH_BACK = 3000.0
internal const val MCC_SOFTWARE = 5734
internal const val MCC_BEER = 5921

class CashbackCalculatorImpl : CashbackCalculator {

    private val cashbackRules: CashbackRulesImpl = CashbackRulesImpl()

    override fun calculateCashback(transactionInfo: TransactionInfo): Double {
        var cashbackAmount: Double = 0.0
        when (transactionInfo.loyaltyProgramName) {
            LOYALTY_PROGRAM_BLACK -> cashbackAmount = cashbackRules.loyaltyBlack(transactionInfo)
            LOYALTY_PROGRAM_ALL -> if (transactionInfo.mccCode == MCC_SOFTWARE) cashbackAmount =
                cashbackRules.loyaltyAllMccSoftware(transactionInfo)
            LOYALTY_PROGRAM_BEER -> if (transactionInfo.mccCode == MCC_BEER) cashbackAmount =
                cashbackRules.loyaltyAndMccBeer(transactionInfo)
        }
        cashbackAmount += cashbackRules.dividedByNumber(transactionInfo)
        return if (MAX_CASH_BACK < transactionInfo.cashbackTotalValue + cashbackAmount) {
            if (MAX_CASH_BACK <= transactionInfo.cashbackTotalValue) {
                0.0
            } else {
                MAX_CASH_BACK - transactionInfo.cashbackTotalValue
            }
        } else {
            Math.round(cashbackAmount * 100) / 100.toDouble()
        }
    }

}