package core.utilities.handlers;

import core.utilities.exceptions.BusinessException;
import core.utilities.exceptions.DatabaseException;
import core.utilities.exceptions.ValidationException;

import javax.swing.*;

public class ErrorHandler {
    public static void handleException(Exception e) {

        if (e instanceof ValidationException) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.WARNING_MESSAGE);

        }else if (e instanceof BusinessException) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "İnformation", JOptionPane.WARNING_MESSAGE);
        }else if (e instanceof DatabaseException) {
            JOptionPane.showMessageDialog(null, "Veritabanı hatası oluştu. Lütfen tekrar deneyin.", "Veritabanı Hatası", JOptionPane.ERROR_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(null, "Bilinmeyen bir hata oluştu. Lütfen destek ile iletişime geçin.", "Uygulama Hatası", JOptionPane.ERROR_MESSAGE);
        }
    }
}
