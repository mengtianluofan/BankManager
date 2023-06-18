package com.graph;

import com.dao.AccountDao;
import com.dao.InfoDao;
import com.dao.impl.AccountDaoImpl;
import com.dao.impl.InfoDaoImpl;
import com.entity.Account;
import com.entity.Information;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static java.lang.Math.abs;

/**
 * @author mengtianluofan
 * @version 1.0
 * @description: 转账
 * TODO
 * @date 2023/6/18 18:01
 */
public class TransferView {
    private static Account account;

    @FXML
    private PasswordField passwordField;

    @FXML
    private TextField toText;

    @FXML
    private TextField amoutText;

    public TransferView(Account account) {
        TransferView.account = account;
    }

    public TransferView() {
    }

    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(UserRegistView.class.getResource("transfer-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 800, 450);
        stage.setTitle("转账页面");
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void confirm() {
        String password = passwordField.getText().trim();
        String toaccountid = toText.getText().trim();
        String amountStr = amoutText.getText().trim();
        double amount;

        if (password.isEmpty() || toaccountid.isEmpty() || amountStr.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "请填写所有信息！");
            alert.showAndWait();
            return;
        }

        try {
            amount = Double.parseDouble(amountStr);
            if (amount < 0) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "金额必须是正数！");
                alert.showAndWait();
                return;
            }
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "金额必须是正数！");
            alert.showAndWait();
            return;
        }

        if (!password.equals(account.getPassword())) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "密码输入错误!");
            alert.showAndWait();
            return;
        }

        String sql1 = "select * from account where id = ?";
        String[] param1 = {toaccountid};
        AccountDao accountDao = new AccountDaoImpl();
        List<Account> accountList = accountDao.getAccount(sql1, param1);
        if (accountList.size() == 0) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "不存在该转出账户，请查看输入是否正确！");
            alert.showAndWait();
            return;
        }

        Account toAccount = accountList.get(0);
        if (!account.withdraw(amount)) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            if (account.getAccountType() == 1) alert.setContentText("账户余额不足！");
            else alert.setContentText("账户可贷款余额不足！");
            alert.showAndWait();
            return;
        } else {
            if (!toAccount.saving(amount)) {
                account.saving(amount);
                Alert alert = new Alert(Alert.AlertType.ERROR, "超出转入账户还款额，无法还款！");
                alert.showAndWait();
                return;
            } else {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setContentText("将从 " + account.getId() + " 转账 " + amount + "元 给账户 " + toAccount.getId() + "， 是否确认转账？");
                Optional<ButtonType> result = alert.showAndWait();
                if (result.isPresent() && result.get() == ButtonType.OK){
                    accountDao.updateAccountAmount(account);
                    accountDao.updateAccountAmount(toAccount);
                    Date date = new Date();
                    Information information = new Information(0, String.valueOf(account.getId()), date, "转账", amount, String.valueOf(toAccount.getId()));
                    new InfoDaoImpl().insertInfo(information);

                    Alert alert1;
                    if(account.getAccountType() == 1)
                        alert1 = new Alert(Alert.AlertType.INFORMATION, "转账成功！当前账户余额：" + account.getAmount());
                    else
                        alert1 = new Alert(Alert.AlertType.INFORMATION, "转账成功！当前账户欠款：" + abs(account.getAmount()));
                    alert1.showAndWait();
                    
                    cancel();
                }
            }
        }
    }

    @FXML
    private void cancel() {
        Stage stage = (Stage) amoutText.getScene().getWindow();
        stage.close();
    }
}
