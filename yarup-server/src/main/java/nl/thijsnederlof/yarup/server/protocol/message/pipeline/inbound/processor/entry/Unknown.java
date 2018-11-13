package nl.thijsnederlof.yarup.server.protocol.message.pipeline.inbound.processor.entry;

import nl.thijsnederlof.common.logger.AbstractYarupLogger;
import nl.thijsnederlof.yarup.server.protocol.message.Buffer;
import nl.thijsnederlof.yarup.server.protocol.message.pipeline.inbound.processor.common.AbstractProcessor;

import static java.lang.String.format;
import static nl.thijsnederlof.common.util.Connectionkey.parse;

public class Unknown extends AbstractProcessor {

    public Unknown(final AbstractYarupLogger log) {
        super(log);
    }

    @Override
    public void doProcess(final String host, final int port, final Buffer buffer) {
        log.warn(format("Received unknown message type from %s", parse(host, port)));
    }
}
