package com.graph;

import com.entity.User;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * @author mengtianluofan
 * @version 1.0
 * @description: 用户登录后的第一个界面
 * TODO
 * @date 2023/6/13 10:06
 */
public class UserMenuView extends Application {

    public static User user;
    @FXML
    private Label topLabel;
    @FXML
    private Button seeInfo, changePassword, accountLogin, accountRegist;

    public UserMenuView(User user) {
        setUser(user);
    }

    public UserMenuView() {
    }

    public static User getUser() {
        return user;
    }

    public static void setUser(User user) {
        UserMenuView.user = user;
    }

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(UserRegistView.class.getResource("user-menu.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 800, 450);
        stage.setTitle("用户菜单界面");
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    protected void returnButtonClick() throws Exception {
        Stage newstage = (Stage) topLabel.getScene().getWindow();
        newstage.hide();
        new UserLoginView().start(newstage);
    }

    @FXML
    protected void setChangePassword() throws Exception {
        ChangePassword changePassword1 = new ChangePassword(user);
        changePassword1.display();
    }

    @FXML
    protected void setAccountLogin() throws IOException {
        Stage newstage = (Stage) topLabel.getScene().getWindow();
        newstage.hide();
        new AccountLogin().display(newstage);
    }

    @FXML
    protected void setAccountRegist() throws IOException {
        new AccountRegistView().display();
    }

}
