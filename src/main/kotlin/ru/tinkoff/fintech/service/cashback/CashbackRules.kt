package ru.tinkoff.fintech.service.cashback

import ru.tinkoff.fintech.model.TransactionInfo

interface CashbackRules {
    fun loyaltyBlack(transactionInfo: TransactionInfo): Double
    fun dividedByNumber(transactionInfo: TransactionInfo, number: Int=666): Double
    fun loyaltyAllMccSoftware(transactionInfo: TransactionInfo): Double
    fun loyaltyAndMccBeer(transactionInfo: TransactionInfo): Double
}