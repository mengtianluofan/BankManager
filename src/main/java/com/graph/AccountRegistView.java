package com.graph;

import com.entity.Account;
import com.entity.User;
import com.service.AccountServ;
import com.service.impl.AccountServImpl;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

/**
 * @author mengtianluofan
 * @version 1.0
 * @description: TODO
 * @date 2023/6/13 20:13
 */
public class AccountRegistView {

    public Label topLabel;
    public ComboBox<String> accountTypeBox;
    public Button registButton;
    @FXML
    protected PasswordField passwordField, twicePasswordField;
    private double limits;

    public void display() throws IOException {
        Stage stage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(UserRegistView.class.getResource("account-regist.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 800, 450);
        stage.setTitle("银行账户注册");
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    protected void returnButtonClick() throws IOException {
        Stage newstage = (Stage) topLabel.getScene().getWindow();
        newstage.close();
    }

    @FXML
    protected void clearButtonClick() {
        twicePasswordField.setText("");
        passwordField.setText("");
    }

    @FXML
    protected void setRegistButton() {
        String accountType = accountTypeBox.getValue();
        int type = judgeType(accountType);
        String password1 = passwordField.getText().trim();
        String password2 = twicePasswordField.getText().trim();

        if (type == -1 || Objects.equals(password1, "") || Objects.equals(password2, "")) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("请填写完整信息");
            alert.setContentText("请填写完整账户信息！！！");
            alert.showAndWait();
            return;
        }

        if (password1.equals(password2)) {
            User user = UserMenuView.getUser();

            Account newaccount = new Account(0, password1, 0, limits, type, user.getOwnerID(), "");
            AccountServ accountServ = new AccountServImpl();
            Account nowAccount = accountServ.Register(newaccount);

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("注册成功！！！");
            alert.setContentText("您已成功注册，您的银行账户为：" + nowAccount.getId());
            alert.showAndWait();

            Stage newstage = (Stage) topLabel.getScene().getWindow();
            newstage.close();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("两次密码不一致！！！");
            alert.setContentText("您输入的两次密码不一致, 请重新输入！！！");

            alert.showAndWait();
        }

    }

    private int judgeType(String accountType) {
        if (accountType == null) return -1;
        else if (accountType.equals("储蓄卡")) {
            limits = -1;
            return 1;
        } else if (accountType.equals("信用卡：限额2000")) {
            limits = 2000;
            return 2;
        } else if (accountType.equals("信用卡：限额10000")) {
            limits = 10000;
            return 2;
        }
        return -1;
    }
}
