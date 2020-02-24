package ru.tinkoff.fintech.service.notification

import ru.tinkoff.fintech.model.NotificationMessageInfo
import java.time.format.DateTimeFormatter

class NotificationMessageGeneratorImpl(
    private val cardNumberMasker: CardNumberMasker
) : NotificationMessageGenerator {

    override fun generateMessage(notificationMessageInfo: NotificationMessageInfo): String {
        return "Уважаемый, " + notificationMessageInfo.name + "!\n" +
                "Спешим Вам сообщить, что на карту " +
                cardNumberMasker.mask(notificationMessageInfo.cardNumber, '#', 0, 12) +
                " начислен cashback в размере " + notificationMessageInfo.cashback.toString() +
                " за категорию " + notificationMessageInfo.category + ".\nСпасибо за покупку " + notificationMessageInfo.transactionDate.format( DateTimeFormatter.ofPattern("yyyy-MM-ddEHH:mm"))

    }
}