package nl.thijsnederlof.common.logger;

public class stndOutLogger extends AbstractYarupLogger {

    @Override
    public void debug(final String debug) {
        System.out.println("DEBUG: " + debug);
    }

    @Override
    public void info(final String info) {
        System.out.println("INFO: " + info);
    }

    @Override
    public void warn(final String warn) {
        System.out.println("WARN: " + warn);
    }

    @Override
    public void error(final String error) {
        System.out.println("ERROR: " + error);
    }

    @Override
    public void trace(final String trace) {
        System.out.println("TRACE: " + trace);
    }
}
