package com.graph;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    @FXML
    private Label welcomeText;

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 800, 450);
        stage.setTitle("银行账户管理系统");
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    protected void onAdminButtonClick() throws Exception {
        Stage newstage = (Stage) welcomeText.getScene().getWindow();
        newstage.hide();
        new AdminLoginView().start(newstage);
    }

    @FXML
    protected void onUserButtonClick() throws Exception {
        Stage newstage = (Stage) welcomeText.getScene().getWindow();
        newstage.hide();
        new UserLoginView().start(newstage);
    }
}