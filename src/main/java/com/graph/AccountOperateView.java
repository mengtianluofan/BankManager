package com.graph;

import com.entity.Account;
import com.service.AccountServ;
import com.service.impl.AccountServImpl;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import java.io.IOException;

import static java.lang.Math.abs;

/**
 * @author mengtianluofan
 * @version 1.0
 * @description: 存取（借还）款界面
 * TODO
 * @date 2023/6/14 20:01
 */
public class AccountOperateView {
    private static Account account;

    public Label topLabel;
    public Button actionButton;
    public TextField amountTextField;
    public Label amountLabel;
    public PasswordField passwordField;
    public ComboBox<String> operateTypeBox;
    public Label passwordLabel;
    public Label moneyLable;
    public Button viewButton;
    private double money;

    public AccountOperateView() {
    }

    public AccountOperateView(Account account) {
        this.account = account;
    }

    public void start(Stage stage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(UserRegistView.class.getResource("account-operate.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 800, 450);
        stage.setTitle("银行账户操作");
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
        amountTextField.setText("");
        passwordField.setText("");
    }

    public void setActionButton() throws IOException {
        passwordLabel.setText("");
        amountLabel.setText("");
        
        String operateType = operateTypeBox.getValue();
        int type = judgeType(operateType);
        String password = passwordField.getText();
        String moneyText = amountTextField.getText();

        if (type == -1 || password.equals("") || moneyText.equals("")) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("请填写完整信息");
            alert.setContentText("请填写完整信息！！！");
            alert.showAndWait();
            return;
        }

        if (!password.equals(account.getPassword())) {
            passwordLabel.setText("密码输入错误，请重试！");
            return;
        } else {
            try {
                money = Double.parseDouble(moneyText);
            } catch (NumberFormatException e) {
                amountLabel.setText("金额输入应为正数！");
                return;
            }

            if (money <= 0) {
                amountLabel.setText("金额输入应为正数！");
                return;
            }

            AccountServ accountServ = new AccountServImpl();
            if (type == 1) {
                Account nowaccount = accountServ.saving(money, account);
                if (nowaccount == null) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("还款超出上限");
                    alert.setContentText("还款超出上限，最大还款：" + abs(account.getAmount()) + " 元");
                    alert.showAndWait();
                    return;
                } else {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    if (nowaccount.getAccountType() == 1) {
                        alert.setTitle("存款成功");
                        alert.setContentText("当前账户金额：" + nowaccount.getAmount() + " 元");
                    } else {
                        alert.setTitle("还款成功");
                        alert.setContentText("仍所需还款：" + abs(nowaccount.getAmount()) + " 元");
                    }
                    alert.showAndWait();
                    returnButtonClick();
                    return;
                }
            } else if (type == 2) {
                Account nowaccount = accountServ.withdraw(money, account);
                if (nowaccount == null) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("操作失败！");
                    if (account.getAccountType() == 1) alert.setContentText("账户余额不足！");
                    else alert.setContentText("超出信用卡额度！");
                    alert.showAndWait();
                    return;
                } else {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    if (nowaccount.getAccountType() == 1) {
                        alert.setTitle("取款成功");
                        alert.setContentText("当前账户金额：" + nowaccount.getAmount() + " 元");
                    } else {
                        alert.setTitle("借款成功");
                        alert.setContentText("您的账户需还款：" + abs(nowaccount.getAmount()) + " 元");
                    }
                    alert.showAndWait();
                    returnButtonClick();
                    return;
                }
            }
        }
    }

    public void setViewButton() {
        moneyLable.setText(account.getAmount() + " 元");
    }

    private int judgeType(String accountType) {
        if (accountType == null) return -1;
        else if (accountType.equals("存款/还款")) {
            return 1;
        } else if (accountType.equals("取款/借款")) {
            return 2;
        }
        return -1;
    }
}
