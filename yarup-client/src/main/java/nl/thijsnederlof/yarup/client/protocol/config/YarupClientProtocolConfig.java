package nl.thijsnederlof.yarup.client.protocol.config;

import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import nl.thijsnederlof.common.handler.send.YarupSendHandler;
import nl.thijsnederlof.common.logger.AbstractYarupLogger;
import nl.thijsnederlof.yarup.client.protocol.handler.*;

@Builder
@Getter
@RequiredArgsConstructor
public class YarupClientProtocolConfig {

    @NonNull
    private final YarupConnectionAcceptHandler connectionAcceptHandler;

    @NonNull
    private final YarupConnectionRejectHandler connectionRejectHandler;

    @NonNull
    private final YarupDisconnectHandler disconnectHandler;

    @NonNull
    private final YarupMessageHandler messageHandler;

    @NonNull
    private final YarupTimeoutHandler timeoutHandler;

    @NonNull
    private final long timeoutThreshold;

    @NonNull
    private final String protocolId;

    @NonNull
    private final AbstractYarupLogger logger;

    @NonNull
    private final YarupSendHandler sendHandler;

//    public static YarupServerProtocolConfigBuilder builder() {
//        return new YarupServerProtocolConfigBuilder();
//    }

//    public static class YarupServerProtocolConfigBuilder {
//
//        private YarupConnectionAcceptHandler connectionAcceptHandler;
//
//        private YarupConnectionRejectHandler connectionRejectHandler;
//
//        private YarupDisconnectHandler disconnectHandler;
//
//        private YarupMessageHandler messageHandler;
//
//        private YarupTimeoutHandler timeoutHandler;
//
//        private long timeoutThreshold;
//
//        private String protocolId;
//
//        private AbstractYarupLogger logger;
//
//        public YarupServerProtocolConfigBuilder connectHandler(final YarupConnectHandler handler) {
//            this.connectHandler = handler;
//            return this;
//        }
//
//        public YarupServerProtocolConfigBuilder disconnectHandler(final YarupDisconnectHandler handler) {
//            this.disconnectHandler = handler;
//            return this;
//        }
//
//        public YarupServerProtocolConfigBuilder timeoutHandler(final YarupTimeoutHandler handler) {
//            this.timeoutHandler = handler;
//            return this;
//        }
//
//        public YarupServerProtocolConfigBuilder logger(final AbstractYarupLogger logger) {
//            this.logger = logger;
//            return this;
//        }
//
//        public YarupServerProtocolConfigBuilder maxConnections(final long maxConnections) {
//            this.maxConnectionAmount = maxConnections;
//            return this;
//        }
//
//        public YarupServerProtocolConfigBuilder protocolId(final String protocolId) {
//            this.protocolId = protocolId;
//            return this;
//        }
//
//        public YarupServerProtocolConfigBuilder timeoutThreshold(final long timeoutThreshold) {
//            this.timeoutThreshold = timeoutThreshold;
//            return this;
//        }
//
//        public YarupServerProtocolConfigBuilder heartbeatThreshold(final long timeoutThreshold) {
//            this.timeoutThreshold = timeoutThreshold;
//            return this;
//        }
//
//        public YarupServerProtocolConfig build() {
//            return new YarupServerProtocolConfig(
//                    this.connectHandler,
//                    this.disconnectHandler,
//                    this.timeoutHandler,
//                    this.maxConnectionAmount,
//                    this.protocolId,
//                    this.timeoutThreshold,
//                    this.heartbeatThreshold,
//                    this.logger
//            );
//        }
//    }
}
