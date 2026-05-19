package sa.edu.kau.fcit.cpit252.project;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class LoginController {

    @FXML private TextField     usernameField;
    @FXML private PasswordField passwordField;
    @FXML private Label         errorLabel;

    @FXML
    private void handleLogin() {
        String username = usernameField.getText().trim();
        String password = passwordField.getText().trim();

        User user = databaseManager.getInstance().login(username, password);

        if (user == null) {
            errorLabel.setText("Invalid username or password.");
            return;
        }

        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/fxml/MainView.fxml")
            );
            Scene scene = new Scene(loader.load(), 980, 640);
            scene.getStylesheets().add(
                    getClass().getResource("/css/style.css").toExternalForm()
            );
            Stage stage = (Stage) usernameField.getScene().getWindow();
            stage.setTitle("KauBook — " +
                    (user.isAdmin() ? "Admin" : user.getUsername()));
            stage.setScene(scene);
            stage.setResizable(true);
            stage.show();
        } catch (Exception e) {
            errorLabel.setText("Failed to load main window.");
            e.printStackTrace();
        }
    }
}
