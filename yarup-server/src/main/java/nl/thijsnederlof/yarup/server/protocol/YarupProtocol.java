package nl.thijsnederlof.yarup.server.protocol;

import nl.thijsnederlof.yarup.server.protocol.connection.ClientManager;
import nl.thijsnederlof.yarup.server.protocol.connection.model.AcceptConnectionResult;
import nl.thijsnederlof.yarup.server.protocol.handler.*;
import nl.thijsnederlof.yarup.server.protocol.message.MessageFactory;

import static nl.thijsnederlof.common.util.CRC32.validate;
import static nl.thijsnederlof.common.util.Connectionkey.parseConnectionkey;

public class YarupProtocol {

    private final MessageFactory messageFactory;

    private final ConnectHandler connectHandler;

    private final ClientManager clientManager;

    private final MessageHandler messageHandler;

    private final int protocolId;

    public YarupProtocol(final ConnectHandler connectHandler, final DisconnectHandler disconnectHandler, final TimeoutHandler timeoutHandler,
                         final SendHandler sender, final MessageHandler messageHandler, final int maxConnectionAmount, final int protocolId) {
        this.protocolId = protocolId;
        this.connectHandler = connectHandler;
        this.messageHandler = messageHandler;
        this.messageFactory = new MessageFactory(sender);
        this.clientManager = new ClientManager(maxConnectionAmount, disconnectHandler, messageFactory, timeoutHandler);
    }

    public void receiveMessage(final String host, final int port, final byte[] message) {
        if (validate(protocolId, message)) {
            return;
        }

        if(!clientManager.isConnected(host, port)) {
            final AcceptConnectionResult acceptConnectionResult = connectHandler.acceptConnection(host, port, message);

            if(!acceptConnectionResult.isAccepted()) {
                messageFactory.rejectConnectionCustom(host, port, acceptConnectionResult.getMessage());
                return;
            }

            if(!clientManager.addNewConnection(host, port)) {
                messageFactory.rejectConnectionFull(host, port);
                return;
            }
        }

        if(new String(message).equals("DISCONNECT")) {
            clientManager.disconnectByConnectionkey(parseConnectionkey(host, port));
        }

        this.messageHandler.handleMessage(clientManager.getClientByConnectionkey(parseConnectionkey(host, port)), message);
    }
}
