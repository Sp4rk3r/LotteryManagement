package be.thibaulthelsmoortel.lotterymanagement.web.views;

/**
 * Interface defining a view.
 *
 * @author Thibault Helsmoortel
 */
public interface LMView {

    enum ErrorType {
        CREATE, READ, UPDATE, DELETE
    }

    void showGeneralError(ErrorType type);

    void showError(String title, String description);

    void showError(String title);

    void showSuccessMessage(String title, String description);

    void showSuccessMessage(String title);
}
