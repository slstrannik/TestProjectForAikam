package exception;

public class ResultException extends Exception {


    @Override
    public String getMessage() {
        return super.getMessage();
    }

    public ResultException() {
    }

    public ResultException(String message) {
        super(message);
    }
}
