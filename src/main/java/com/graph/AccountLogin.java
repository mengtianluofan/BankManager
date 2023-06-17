package com.graph;

import com.entity.Account;
import com.entity.User;
import com.service.AccountServ;
import com.service.UserServ;
import com.service.impl.AccountServImpl;
import com.service.impl.UserServImpl;
import javafx.event.ActionEvent;
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
 * @description: 银行账户登录
 * TODO
 * @date 2023/6/14 18:37
 */
public class AccountLogin {

    public PasswordField passwordField;
    public TextField accountText;

    public void display(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(UserLoginView.class.getResource("account-login.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 800, 450);
        stage.setTitle("用户登录界面");
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    protected void returnButtonClick() throws Exception {
        Stage newstage = (Stage) accountText.getScene().getWindow();
        newstage.hide();
        new UserMenuView().start(newstage);
    }

    @FXML
    protected void clearButtonClick(){
        accountText.setText("");
        passwordField.setText("");
    }

    @FXML
    protected void LoginButtonClick() throws Exception {
        String accountID = accountText.getText();
        String password1 = passwordField.getText();

        if(accountID.equals("") || password1.equals("")){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("信息不完整！");
            alert.setHeaderText(null);
            alert.setContentText("请填写完整登录信息！！！");
            alert.showAndWait();
            return;
        }

        AccountServ accountServ = new AccountServImpl();
        Account account = accountServ.Login(accountID, password1);

        if(account == null){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("登录失败！");
            alert.setContentText("账号或密码错误！！！");
            alert.showAndWait();
        }
        else{
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("登录成功");
            alert.setContentText("登录成功！！！");
            alert.showAndWait();

            Stage newstage = (Stage) accountText.getScene().getWindow();
            newstage.hide();
            new AccountMenu(account).start(newstage);
        }
    }
    
}
