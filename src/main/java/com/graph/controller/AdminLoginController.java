package com.graph.controller;

import com.graph.HelloApplication;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * @author mengtianluofan
 * @version 1.0
 * @description: 管理员登录
 * TODO
 * @date 2023/6/8 13:26
 */
public class AdminLoginController {
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
    protected void LoginButtonClick(){
        
    }
    
    @FXML
    protected void clearButtonClick(){
        accountText.setText("");
        passwordField.setText("");
    }
}
