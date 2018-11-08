package nl.thijsnederlof.common.util;

import lombok.NoArgsConstructor;

import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import static java.util.regex.Pattern.compile;
import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class Connectionkey {

    private static Pattern validIpv4Pattern;
    private static Pattern validIpv6Pattern;
    private static final String IPV4_PATTERN = "(([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.){3}([01]?\\d\\d?|2[0-4]\\d|25[0-5])";
    private static final String IPV6_PATTERN = "([0-9a-f]{1,4}:){7}([0-9a-f]){1,4}";

    static {
        try {
            validIpv4Pattern = compile(IPV4_PATTERN, Pattern.CASE_INSENSITIVE);
            validIpv6Pattern = compile(IPV6_PATTERN, Pattern.CASE_INSENSITIVE);
        } catch (PatternSyntaxException e) {
            throw new IllegalStateException("Could not compile connection regex patterns!");
        }
    }

    public static String parseConnectionkey(final String host, final int port) {
        if(!Connectionkey.validIpv4Pattern.matcher(host).matches() || !Connectionkey.validIpv6Pattern.matcher(host).matches()) {
            throw new IllegalArgumentException("Supplied host is not a valid ipv4 or ipv6 address");
        }

        if(port < 0 || port > 65535) {
            throw new IllegalArgumentException("Supplied port is not in range for a valid ip address");
        }

        return host + ":" + port;
    }
}
