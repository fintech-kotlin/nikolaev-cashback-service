package ru.tinkoff.fintech.service.notification

import java.lang.Integer.min

class CardNumberMaskerImpl : CardNumberMasker {

    override fun mask(cardNumber: String, maskChar: Char, start: Int, end: Int): String {

        if (start > end) {
        }

        if (cardNumber.equals("")) {
            return ""
        } else {
            return cardNumber.replaceRange(
                start,
                min(end, cardNumber.length),
                maskChar.toString().repeat(min(end, cardNumber.length) - start)
            )
        }
    }
}