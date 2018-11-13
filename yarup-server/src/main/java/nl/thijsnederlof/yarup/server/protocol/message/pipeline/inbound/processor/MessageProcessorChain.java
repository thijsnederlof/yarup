package nl.thijsnederlof.yarup.server.protocol.message.pipeline.inbound.processor;

import lombok.RequiredArgsConstructor;
import nl.thijsnederlof.yarup.server.protocol.message.Buffer;
import nl.thijsnederlof.yarup.server.protocol.message.pipeline.inbound.processor.common.AbstractProcessor;
import nl.thijsnederlof.yarup.server.protocol.message.pipeline.inbound.processor.common.MessageValidator;
import nl.thijsnederlof.yarup.server.protocol.message.type.InboundMessageType;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import static lombok.AccessLevel.PRIVATE;

@RequiredArgsConstructor(access = PRIVATE)
public class MessageProcessorChain {

    private final Set<MessageValidator> validatorChain;

    private final HashMap<InboundMessageType, AbstractProcessor> processorChain;

    private final AbstractProcessor fallback;

    public void processMessage(final String host, final int port, final Buffer buffer) {
        for (final MessageValidator validator : validatorChain) {
            if (!validator.validate(host, port, buffer)) {
                return;
            }
        }

        processorChain.getOrDefault(buffer.getMessageType(), fallback).processMessage(host, port, buffer);
    }

    public static MessageProcessorChainbuilder builder() {
        return new MessageProcessorChainbuilder();
    }

    public static class MessageProcessorChainbuilder {

        private final Set<MessageValidator> validatorChain = new HashSet<>();

        private final HashMap<InboundMessageType, AbstractProcessor> processorChain = new HashMap<>();

        private AbstractProcessor fallback;

        public MessageProcessorChainbuilder addValidator(final MessageValidator validator) {
            validatorChain.add(validator);
            return this;
        }

        public MessageProcessorChainbuilder addProcessor(final InboundMessageType messageType, final AbstractProcessor processor) {
            processorChain.put(messageType, processor);
            return this;
        }

        public MessageProcessorChainbuilder withFallback(final AbstractProcessor fallback) {
            this.fallback = fallback;
            return this;
        }

        public MessageProcessorChain build() {
            return new MessageProcessorChain(this.validatorChain, this.processorChain, this.fallback);
        }
    }
}
