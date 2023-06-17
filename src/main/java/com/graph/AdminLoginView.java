package com.graph;

import com.dao.AdminDao;
import com.entity.Admin;
import com.entity.User;
import com.service.AdminServ;
import com.service.UserServ;
import com.service.impl.AdminServImpl;
import com.service.impl.UserServImpl;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
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
        newstage.close();
        new HelloApplication().start(newstage);
    }

    @FXML
    protected void LoginButtonClick() throws IOException {
        String accountID = accountText.getText();
        String password1 = passwordField.getText();

        if (accountID.equals("") || password1.equals("")) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("信息不完整！");
            alert.setHeaderText(null);
            alert.setContentText("请填写完整登录信息！！！");
            alert.showAndWait();
            return;
        }

        AdminServ adminServ = new AdminServImpl();
        Admin admin = adminServ.Login(accountID, password1);

        if (admin == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("登录失败！");
            alert.setContentText("账号或密码错误！！！");
            alert.showAndWait();
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("登录成功");
            alert.setContentText("登录成功！！！");
            alert.showAndWait();

            Stage newstage = (Stage) accountText.getScene().getWindow();
            newstage.close();
            new AdminMenuView(admin).start(newstage);
        }
    }

    @FXML
    protected void clearButtonClick() {
        accountText.setText("");
        passwordField.setText("");
    }

}
