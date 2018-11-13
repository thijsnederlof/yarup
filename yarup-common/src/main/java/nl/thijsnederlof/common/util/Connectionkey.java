package nl.thijsnederlof.common.util;

import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class Connectionkey {

    public static String parse(final String host, final int port) {
        return host + ":" + port;
    }
}
