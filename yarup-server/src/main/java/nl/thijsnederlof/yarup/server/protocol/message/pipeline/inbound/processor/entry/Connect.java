package nl.thijsnederlof.yarup.server.protocol.message.pipeline.inbound.processor.entry;

import nl.thijsnederlof.common.logger.AbstractYarupLogger;
import nl.thijsnederlof.yarup.server.protocol.connection.ClientManager;
import nl.thijsnederlof.yarup.server.protocol.connection.model.AcceptConnectionResult;
import nl.thijsnederlof.yarup.server.protocol.handler.connect.YarupConnectHandler;
import nl.thijsnederlof.yarup.server.protocol.message.Buffer;
import nl.thijsnederlof.yarup.server.protocol.message.MessageFactory;
import nl.thijsnederlof.yarup.server.protocol.message.pipeline.inbound.processor.common.AbstractProcessor;

import static java.lang.String.format;

public class Connect extends AbstractProcessor {

    private final YarupConnectHandler connectHandler;

    private final MessageFactory messageFactory;

    private final ClientManager clientManager;

    public Connect(final AbstractYarupLogger log, final YarupConnectHandler connectHandler, final MessageFactory messageFactory, final ClientManager clientManager) {
        super(log);
        this.connectHandler = connectHandler;
        this.messageFactory = messageFactory;
        this.clientManager = clientManager;
    }

    @Override
    protected void doProcess(final String host, final int port, final Buffer buffer) {
        if(clientManager.isConnected(host, port)) {
            log.debug(format("Received connection attempt from %s:%d but user was already connected", host, port));
        }

        final AcceptConnectionResult result = connectHandler.acceptConnection(host, port, buffer.getPayload());

        if (!result.isAccepted()) {
            messageFactory.rejectConnectionCustom(host, port, result.getMessage());
            log.info(format("Connection %s:%d rejected by custom rule", host, port));
            return;
        }

        if (!clientManager.addNewConnection(host, port)) {
            messageFactory.rejectConnectionFull(host, port);
            log.info(format("Connection %s:%d rejected: server full", host, port));
            return;
        }

        log.info(format("Connection %s:%d accepted", host, port));
    }
}
