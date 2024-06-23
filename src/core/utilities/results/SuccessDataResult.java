package core.utilities.results;

public class SuccessDataResult<T> extends SuccessResult<T> {
    public SuccessDataResult(T data, String message) {
        super(data, message);
    }
}
