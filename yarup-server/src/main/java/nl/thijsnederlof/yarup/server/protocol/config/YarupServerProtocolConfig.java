package nl.thijsnederlof.yarup.server.protocol.config;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import nl.thijsnederlof.common.handler.send.YarupSendHandler;
import nl.thijsnederlof.common.logger.AbstractYarupLogger;
import nl.thijsnederlof.yarup.server.protocol.handler.connect.YarupConnectHandler;
import nl.thijsnederlof.yarup.server.protocol.handler.disconnect.YarupDisconnectHandler;
import nl.thijsnederlof.yarup.server.protocol.handler.message.YarupMessageHandler;
import nl.thijsnederlof.yarup.server.protocol.handler.timeout.YarupTimeoutHandler;

import static lombok.AccessLevel.PACKAGE;
import static nl.thijsnederlof.yarup.server.protocol.config.YarupConfigValidator.validateConfig;

@Getter
@RequiredArgsConstructor
public class YarupServerProtocolConfig {

    @NonNull
    private final AbstractYarupLogger logger;

    @NonNull
    private final YarupConnectHandler connectHandler;

    @NonNull
    private final YarupDisconnectHandler disconnectHandler;

    @NonNull
    private final YarupTimeoutHandler timeoutHandler;

    @NonNull
    private final YarupSendHandler sendHandler;

    @NonNull
    private final long maxConnectionAmount;

    @NonNull
    private final String protocolId;

    @NonNull
    private final long timeoutThreshold;

    @NonNull
    private final long heartbeatThreshold;

    @NonNull
    private final YarupMessageHandler messageHandler;

    public static YarupServerProtocolConfigBuilder builder() {
        return new YarupServerProtocolConfigBuilder();
    }

    @Getter(value = PACKAGE)
    @Setter(value = PACKAGE)
    public static class YarupServerProtocolConfigBuilder {

        private AbstractYarupLogger logger;

        private YarupConnectHandler connectHandler;

        private YarupDisconnectHandler disconnectHandler;

        private YarupTimeoutHandler timeoutHandler;

        private YarupSendHandler sendHandler;

        private Long maxConnectionAmount;

        private String protocolId;

        private Long timeoutThreshold;

        private Long heartbeatThreshold;

        private YarupMessageHandler messageHandler;

        public YarupServerProtocolConfigBuilder optionalLogger(final AbstractYarupLogger logger) {
            this.logger = logger;
            return this;
        }

        public YarupServerProtocolConfigBuilder optionalConnectHandler(final YarupConnectHandler handler) {
            this.connectHandler = handler;
            return this;
        }

        public YarupServerProtocolConfigBuilder optionalDisconnectHandler(final YarupDisconnectHandler handler) {
            this.disconnectHandler = handler;
            return this;
        }

        public YarupServerProtocolConfigBuilder optionalTimeoutHandler(final YarupTimeoutHandler handler) {
            this.timeoutHandler = handler;
            return this;
        }

        public YarupServerProtocolConfigBuilder optionalMaxConnections(final long maxConnections) {
            this.maxConnectionAmount = maxConnections;
            return this;
        }

        public YarupServerProtocolConfigBuilder protocolId(final String protocolId) {
            this.protocolId = protocolId;
            return this;
        }

        public YarupServerProtocolConfigBuilder optionalTimeoutThreshold(final long timeoutThreshold) {
            this.timeoutThreshold = timeoutThreshold;
            return this;
        }

        public YarupServerProtocolConfigBuilder optionalHeartbeatThreshold(final long timeoutThreshold) {
            this.heartbeatThreshold = timeoutThreshold;
            return this;
        }

        public YarupServerProtocolConfigBuilder sendHandler(final YarupSendHandler sendHandler) {
            this.sendHandler = sendHandler;
            return this;
        }

        public YarupServerProtocolConfigBuilder messageHandler(final YarupMessageHandler messageHandler) {
            this.messageHandler = messageHandler;
            return this;
        }

        public YarupServerProtocolConfig build() {
            validateConfig(this);

            return new YarupServerProtocolConfig(
                    this.logger,
                    this.connectHandler,
                    this.disconnectHandler,
                    this.timeoutHandler,
                    this.sendHandler,
                    this.maxConnectionAmount,
                    this.protocolId,
                    this.timeoutThreshold,
                    this.heartbeatThreshold,
                    this.messageHandler
            );
        }
    }
}



    