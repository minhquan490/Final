package fa.training.exception;

public class EntityExistException extends RuntimeException {
    public EntityExistException(String msg) {
        super(msg);
    }
}
