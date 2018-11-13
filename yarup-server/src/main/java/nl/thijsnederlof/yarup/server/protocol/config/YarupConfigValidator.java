package nl.thijsnederlof.yarup.server.protocol.config;

import lombok.NoArgsConstructor;
import nl.thijsnederlof.common.logger.stndOutLogger;
import nl.thijsnederlof.yarup.server.protocol.handler.connect.AlwaysAcceptHandler;
import nl.thijsnederlof.yarup.server.protocol.handler.disconnect.NoOpDisconnectHandler;
import nl.thijsnederlof.yarup.server.protocol.handler.timeout.NoOpTimeoutHandler;

import static java.lang.Long.MAX_VALUE;
import static java.lang.String.format;
import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
class YarupConfigValidator {

    static void validateConfig(final YarupServerProtocolConfig.YarupServerProtocolConfigBuilder configBuilder) {
        if(configBuilder.getLogger() == null) {
            configBuilder.setLogger(new stndOutLogger());
            configBuilder.getLogger().info("No custom logger specified: using default logger");
        }

        if(configBuilder.getConnectHandler() == null) {
            configBuilder.getLogger().info("No connect constraint handler specified: always accepting new connections.");
            configBuilder.setConnectHandler(new AlwaysAcceptHandler());
        }

        if(configBuilder.getDisconnectHandler() == null) {
            configBuilder.setDisconnectHandler(new NoOpDisconnectHandler());
        }

        if(configBuilder.getTimeoutHandler() == null) {
            configBuilder.setTimeoutHandler(new NoOpTimeoutHandler());
        }

        if(configBuilder.getMaxConnectionAmount() == null) {
            configBuilder.getLogger().info(format("No maximum connections specified: setting limit to %d", MAX_VALUE));
            configBuilder.setMaxConnectionAmount(MAX_VALUE);
        }

        if(configBuilder.getProtocolId().length() > 10) {
            configBuilder.getLogger().warn("Using a protocol id larger than 10 bytes causes large overhead. Consider making it no longer than 4.");
        }

        if(configBuilder.getTimeoutThreshold() == null) {
            configBuilder.setTimeoutThreshold(10000L);
            configBuilder.getLogger().info(format("No timeout threshold specified: setting to %d", configBuilder.getTimeoutThreshold()));
        }

        if(configBuilder.getHeartbeatThreshold() == null) {
            configBuilder.setHeartbeatThreshold(2000L);
            configBuilder.getLogger().info(format("No heartbeat threshold specified: setting to %d", configBuilder.getHeartbeatThreshold()));
        } else if(configBuilder.getHeartbeatThreshold() > configBuilder.getTimeoutThreshold()) {
            configBuilder.getLogger().warn("Heartbeat threshold is greater than timeout threshold. No connection keep alive will be preformed");
        }

        if(configBuilder.getSendHandler() == null) {
            configBuilder.getLogger().error("Sendhandler is a mandatory field!");
        }

        if(configBuilder.getMessageHandler() == null) {
            configBuilder.getLogger().error("Messagehandler is a mandatory field!");
        }
    }
}
