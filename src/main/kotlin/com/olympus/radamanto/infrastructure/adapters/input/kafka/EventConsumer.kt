package com.olympus.radamanto.infrastructure.adapters.input.kafka

import com.olympus.radamanto.domain.events.DomainEvent
import com.olympus.radamanto.domain.ports.output.EventStore
import org.apache.kafka.clients.consumer.ConsumerRecord
import org.slf4j.LoggerFactory
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.kafka.support.Acknowledgment
import org.springframework.stereotype.Component

@Component
class EventConsumer(
    private val eventStore: EventStore
) {

    private val logger = LoggerFactory.getLogger(EventConsumer::class.java)


    @KafkaListener(topics = ["olympus.radamanto.user_created.json"])
    fun listen(record: ConsumerRecord<String, DomainEvent>, ack: Acknowledgment) {
        val event = record.value()
        logger.info("Received event: $event")

        runCatching {
            eventStore.saveEvents(event.aggregateId, listOf(event), event.version - 1)
        }.fold(onSuccess = {
            ack.acknowledge()
        }, onFailure = {
            logger.error("Failed to save event to EventStore", it)
        })
    }
}