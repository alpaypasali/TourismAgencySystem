package core.utilities.results;

import java.util.List;

public class SuccessListResult<T> extends SuccessResult<List<T>> {
    public SuccessListResult(List<T> data, String message) {
        super(data, message);
    }
}
