package nl.thijsnederlof.yarup.server.protocol.connection.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class AcceptConnectionResult {

    private final boolean accepted;

    private final byte[] message;
}
