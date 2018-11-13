package nl.thijsnederlof.yarup.client.protocol.handler;

import nl.thijsnederlof.yarup.client.protocol.message.RejectReason;

public interface YarupConnectionRejectHandler {

    void handleConnectionReject(final RejectReason reason, final byte[] message);
}
