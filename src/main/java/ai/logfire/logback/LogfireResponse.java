package ai.logfire.logback;

/**
 * Holder for Logfire error message.
 * 
 * @author tomas@logfire.ai
 */
public class LogfireResponse {

    private String error;
    private int status;

    public LogfireResponse(String error, int status) {
        this.error = error;
        this.status = status;
    }

    public String getError() { return error; }
    public int getStatus() { return status; }

}
