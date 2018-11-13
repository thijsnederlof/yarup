package nl.thijsnederlof.yarup.server.protocol.message.pipeline.inbound.processor.validator;

import lombok.RequiredArgsConstructor;
import nl.thijsnederlof.common.logger.AbstractYarupLogger;
import nl.thijsnederlof.yarup.server.protocol.message.Buffer;
import nl.thijsnederlof.yarup.server.protocol.message.pipeline.inbound.processor.common.MessageValidator;

import static java.lang.String.format;

@RequiredArgsConstructor
public class CRC32Validator implements MessageValidator {

    private final AbstractYarupLogger log;

    @Override
    public boolean validate(final String host, final int port, final Buffer buffer) {
        if (!buffer.validateCRC32Checksum()) {
            log.warn(format("Received message with invalid CRC32 check from %s:%d", host, port));
            return false;
        }

        return true;
    }
}
