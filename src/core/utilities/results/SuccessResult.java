package core.utilities.results;

import javax.swing.*;

public class SuccessResult<T> {
    private T data;
    private String message;

    public SuccessResult(T data, String message) {
        this.data = data;
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public String getMessage() {
        return message;
    }

    public void showMessageDialog() {
        JOptionPane.showMessageDialog(null, message, "Success", JOptionPane.INFORMATION_MESSAGE);
    }
}