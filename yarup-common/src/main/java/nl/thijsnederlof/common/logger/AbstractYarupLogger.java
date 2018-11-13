package nl.thijsnederlof.common.logger;

public abstract class AbstractYarupLogger {

    public abstract void debug(final String debug);

    public abstract void info(final String info);

    public abstract void warn(final String warn);

    public abstract void error(final String error);

    public abstract void trace(final String trace);
}
