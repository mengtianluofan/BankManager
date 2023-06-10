package com.graph.controller;

import com.graph.AdminLoginView;
import com.graph.UserLoginView;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class HelloController {
    @FXML
    private Label welcomeText;

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