package com.graph;

import com.dao.AccountDao;
import com.dao.impl.AccountDaoImpl;
import com.entity.Account;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Optional;

/**
 * @author mengtianluofan
 * @version 1.0
 * @description: 账户界面
 * TODO
 * @date 2023/6/14 18:55
 */
public class AccountMenu {
    public static Account account;
    public Label topLabel;
    public Button seeInfo;
    public Button changePassword;
    public Button operate;
    public Button transfer;
    public Button histroy;
    public Button deleteButton;

    public AccountMenu() {
    }

    public AccountMenu(Account account) {
        setAccount(account);
    }

    public static Account getAccount() {
        return account;
    }

    public static void setAccount(Account account) {
        AccountMenu.account = account;
    }
    
    public void start(Stage stage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(UserRegistView.class.getResource("account-menu.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 800, 450);
        stage.setTitle("账户菜单界面");
        stage.setScene(scene);
        stage.show();
    }


    public void returnButtonClick() throws Exception {
        Stage newstage = (Stage) topLabel.getScene().getWindow();
        newstage.hide();
        new UserMenuView().start(newstage);
    }

    @FXML
    protected void setChangePassword() throws Exception {
        ChangePassword changePassword1 = new ChangePassword(account);
        changePassword1.display();
    }
    
    public void setDeleteButton() throws Exception {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("注销当前账户");
        alert.setHeaderText("是否确认注销账户？（账户无法找回）");
        alert.setContentText("注销请点击 “OK” \n" + "取消请关闭窗口 或 点击 “Cancel” ");
        
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            if(account.getAmount() != 0){
                Alert alert1 = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("账户无法注销");
                alert.setContentText("您的账户中有余额或尚未还款，无法注销！");
                alert1.showAndWait();
                
                return;
            }

            AccountDao accountDao = new AccountDaoImpl();
            String sql = "delete from account where id = ?";
            String[] param = {String.valueOf(account.getId())};
            
            accountDao.updateAccount(sql, param);
            account = null;
            
            returnButtonClick();
        } else {
            alert.close();
        }

    }
    
    public void setOperate() throws Exception {
        Stage newstage = new Stage();
        new AccountOperateView(account).start(newstage);
    }
    
    public void setSeeInfo() throws Exception {
        Stage newstage = new Stage();
        new AccountInfo(account).start(newstage);
    }
    
    public void setTransfer(){
        
    }
    
    public void setHistroy() throws IOException {
        Stage newstage = new Stage();
        new AccountTradeInfo(account).display(newstage);
    }
}
