package com.graph.controller;

import com.graph.HelloApplication;
import com.graph.UserRegistView;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * @author mengtianluofan
 * @version 1.0
 * @description: 用户登录界面
 * TODO
 * @date 2023/6/8 17:08
 */
public class UserLoginController {
    @FXML
    private TextField accountText;
    @FXML
    private PasswordField passwordField;

    @FXML
    protected void returnButtonClick() throws IOException {
        Stage newstage = (Stage) accountText.getScene().getWindow();
        newstage.hide();
        new HelloApplication().start(newstage);
    }

    @FXML
    protected void registerButtonClick() throws IOException {
        Stage newstage = (Stage) accountText.getScene().getWindow();
        newstage.hide();
        new UserRegistView().start(newstage);
    }

    @FXML
    protected void LoginButtonClick(){

    }

    @FXML
    protected void clearButtonClick(){
        accountText.setText("");
        passwordField.setText("");
    }

}
