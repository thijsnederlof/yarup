package nl.thijsnederlof.yarup.server.protocol.connection.model;

import lombok.Getter;

import static java.lang.System.currentTimeMillis;
import static nl.thijsnederlof.common.util.Connectionkey.parseConnectionkey;

public class ConnectionDetails {

    @Getter
    private final String host;

    @Getter
    private final int port;

    @Getter
    private final long lastMessageReceivedTimestamp;

    public ConnectionDetails(final String host, int port) {
        this.host = host;
        this.port = port;

        this.lastMessageReceivedTimestamp = currentTimeMillis();
    }

    public String getConnectionKey() {
        return parseConnectionkey(host, port);
    }
}
