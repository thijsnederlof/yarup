package nl.thijsnederlof.yarup.server.protocol.message.pipeline.inbound.processor.common;

import lombok.RequiredArgsConstructor;
import nl.thijsnederlof.common.logger.AbstractYarupLogger;
import nl.thijsnederlof.yarup.server.protocol.message.Buffer;

import static java.lang.String.format;

@RequiredArgsConstructor
public abstract class AbstractProcessor {

    protected final AbstractYarupLogger log;

    public void processMessage(final String host, final int port, final Buffer buffer) {
        log.trace(format("Received message from: %s:%d", host, port));
        this.doProcess(host, port, buffer);
    }

    protected abstract void doProcess(final String host, final int port, final Buffer buffer);
}
