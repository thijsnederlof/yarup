package nl.thijsnederlof.common.connection;

import lombok.Getter;

import static java.lang.System.currentTimeMillis;
import static nl.thijsnederlof.common.util.Connectionkey.parse;

public class ConnectionDetails {

    @Getter
    private final String host;

    @Getter
    private final int port;

    @Getter
    private long lastMessageReceivedTimestamp;

    public ConnectionDetails(final String host, final int port) {
        this.host = host;
        this.port = port;

        this.lastMessageReceivedTimestamp = currentTimeMillis();
    }

    public String getConnectionKey() {
        return parse(host, port);
    }

    public void updatelastMessageReceivedTimestamp() {
        this.lastMessageReceivedTimestamp = currentTimeMillis();
    }
}
