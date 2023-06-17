package com.graph;

import com.entity.Account;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

import static java.lang.Math.abs;

/**
 * @author mengtianluofan
 * @version 1.0
 * @description: 账户信息
 * TODO
 * @date 2023/6/15 19:42
 */
public class AccountInfo implements Initializable {
    private static Account account;

    public FXMLLoader fxmlLoader;

    public Label cardID;
    public Label ownerIDLabel;
    public Label ownerNameLabel;
    public Label amountLable;
    public Label LimitLabel;

    public AccountInfo() {
    }

    public AccountInfo(Account account) {
        AccountInfo.account = account;
    }

    public void start(Stage stage) throws Exception {
        if (account.getAccountType() == 1)
            fxmlLoader = new FXMLLoader(UserRegistView.class.getResource("account-info1.fxml"));
        else fxmlLoader = new FXMLLoader(UserRegistView.class.getResource("account-info2.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 800, 450);
        stage.setTitle("银行账户信息");
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        cardID.setText(String.valueOf(account.getId()));
        ownerIDLabel.setText(account.getOwnerID());
        ownerNameLabel.setText(account.getOwnerName());
        amountLable.setText(String.valueOf(abs(account.getAmount())));
        LimitLabel.setText(String.valueOf(account.getLimit()));
    }
}
