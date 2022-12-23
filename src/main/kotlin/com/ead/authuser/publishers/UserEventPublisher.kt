package com.ead.authuser.publishers

import com.ead.authuser.dtos.UserEventDto
import com.ead.authuser.enums.ActionType
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component

@Component
class UserEventPublisher(val rabbitTemplate: RabbitTemplate) {

    @Value("\${ead.broker.exchange.userEvent}")
    lateinit var exchangeUserEvent: String

    fun publishUserEvent(userEventDto: UserEventDto, actionType: ActionType) {
        userEventDto.actionType = actionType.name
        rabbitTemplate.convertAndSend(exchangeUserEvent, "", userEventDto)
    }
}