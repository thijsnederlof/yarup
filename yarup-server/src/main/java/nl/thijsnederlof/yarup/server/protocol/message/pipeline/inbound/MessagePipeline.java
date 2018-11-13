package nl.thijsnederlof.yarup.server.protocol.message.pipeline.inbound;

import nl.thijsnederlof.common.logger.AbstractYarupLogger;
import nl.thijsnederlof.yarup.server.protocol.connection.ClientManager;
import nl.thijsnederlof.yarup.server.protocol.handler.connect.YarupConnectHandler;
import nl.thijsnederlof.yarup.server.protocol.handler.disconnect.YarupDisconnectHandler;
import nl.thijsnederlof.yarup.server.protocol.handler.message.YarupMessageHandler;
import nl.thijsnederlof.yarup.server.protocol.message.Buffer;
import nl.thijsnederlof.yarup.server.protocol.message.MessageFactory;
import nl.thijsnederlof.yarup.server.protocol.message.pipeline.inbound.processor.MessageProcessorChain;
import nl.thijsnederlof.yarup.server.protocol.message.pipeline.inbound.processor.entry.*;
import nl.thijsnederlof.yarup.server.protocol.message.pipeline.inbound.processor.validator.CRC32Validator;

import static nl.thijsnederlof.yarup.server.protocol.message.type.InboundMessageType.*;

public class MessagePipeline {

    private final MessageProcessorChain processorChain;

    public MessagePipeline(final AbstractYarupLogger log,
                           final ClientManager clientManager,
                           final YarupConnectHandler connectHandler,
                           final MessageFactory messageFactory,
                           final YarupMessageHandler messageHandler,
                           final YarupDisconnectHandler disconnectHandler) {

        this.processorChain = MessageProcessorChain.builder()
                .addValidator(new CRC32Validator(log))
                .addProcessor(CONNECT_REQUEST, new Connect(log, connectHandler, messageFactory, clientManager))
                .addProcessor(DISCONNECT, new Disconnect(log, disconnectHandler, clientManager))
                .addProcessor(HEARTBEAT, new Heartbeat(log, clientManager))
                .addProcessor(MESSAGE, new Message(log, clientManager, messageHandler))
                .withFallback(new Unknown(log))
                .build();
    }

    public void processMessage(final String host, final int port, final Buffer buffer) {
        this.processorChain.processMessage(host, port, buffer);
    }
}
