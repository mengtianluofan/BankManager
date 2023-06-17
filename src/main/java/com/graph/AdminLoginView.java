package com.graph;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * @author mengtianluofan
 * @version 1.0
 * @description: 管理员登录
 * TODO
 * @date 2023/6/8 13:19
 */
public class AdminLoginView extends Application {

    @FXML
    private TextField accountText;
    @FXML
    private PasswordField passwordField;

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(AdminLoginView.class.getResource("admin-login.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 800, 450);
        stage.setTitle("管理员登录界面");
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    protected void returnButtonClick() throws IOException {
        Stage newstage = (Stage) accountText.getScene().getWindow();
        newstage.hide();
        new HelloApplication().start(newstage);
    }

    @FXML
    protected void LoginButtonClick() {

    }

    @FXML
    protected void clearButtonClick() {
        accountText.setText("");
        passwordField.setText("");
    }

}
