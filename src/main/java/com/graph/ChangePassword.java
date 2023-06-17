package com.graph;

import com.dao.AccountDao;
import com.dao.AdminDao;
import com.dao.UserDao;
import com.dao.impl.AccountDaoImpl;
import com.dao.impl.AdminDaoImpl;
import com.dao.impl.UserDaoImpl;
import com.entity.Account;
import com.entity.Admin;
import com.entity.User;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

/**
 * @author mengtianluofan
 * @version 1.0
 * @description: 更改密码的类，支持三种类型更改
 * TODO
 * @date 2023/6/13 17:44
 */
public class ChangePassword {
    private static User user;
    private static Admin admin;
    private static Account account;

    @FXML
    private PasswordField oldPasswordField, passwordField, twicePasswordField;

    public ChangePassword(Object obj) {
        user = null;
        account = null;
        admin = null;
        if (obj instanceof User) user = (User) obj;
        else if (obj instanceof Account) account = (Account) obj;
        else if (obj instanceof Admin) admin = (Admin) obj;
    }

    public ChangePassword() {
    }

    public void display() throws Exception {
        Stage stage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(UserRegistView.class.getResource("change-password.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 800, 450);
        stage.setTitle("修改密码");
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    protected void clearButtonClick() {
        oldPasswordField.setText("");
        passwordField.setText("");
        twicePasswordField.setText("");
    }

    @FXML
    protected void changeButtonClick() throws IOException {
        String oldPassword = oldPasswordField.getText();
        String newPassword = passwordField.getText();
        String twicePassword = twicePasswordField.getText();

        if (oldPassword.equals("") || newPassword.equals("") || twicePassword.equals("")) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("请输入完整信息");
            alert.setContentText("有信息未输入，请输入完整信息！！！");
            alert.showAndWait();
            return;
        }

        if (user != null) {
            if (!Objects.equals(user.getPassword(), oldPassword)) {
                oldPasswordError();
                return;
            }
            if (!newPassword.equals(twicePassword)) {
                newPasswordError();
                return;
            }

            if (userChange(user, newPassword)) {
                user.setPassword(newPassword);
                UserMenuView.setUser(user);
                changeVec();
            }
        } else if (admin != null) {
            if (!Objects.equals(admin.getPassword(), oldPassword)) {
                oldPasswordError();
                return;
            }
            if (!newPassword.equals(twicePassword)) {
                newPasswordError();
                return;
            }

            if (adminChange(admin, newPassword)) {
                //修改admin
                changeVec();
            }
        } else if (account != null) {
            if (!Objects.equals(account.getPassword(), oldPassword)) {
                oldPasswordError();
                return;
            }
            if (!newPassword.equals(twicePassword)) {
                newPasswordError();
                return;
            }
            if (accountChange(account, newPassword)) {
                account.setPassword(newPassword);
                AccountMenu.setAccount(account);
                changeVec();
            }
        }
    }

    private void oldPasswordError() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("密码错误");
        alert.setContentText("原密码与账户密码不符，请重试！！！");
        alert.showAndWait();
    }

    private void newPasswordError() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("密码错误");
        alert.setContentText("新密码两次输入不一致，请重试！！！");
        alert.showAndWait();
    }

    private boolean userChange(User user, String password) {
        String sql = "update user set password = ? where id = ?";
        String[] param = {password, String.valueOf(user.getId())};

        UserDao userDao = new UserDaoImpl();
        return userDao.updateUser(sql, param) != 0;
    }

    private boolean adminChange(Admin admin, String password) {
        String sql = "update admin set password = ? where id = ?";
        String[] param = {password, String.valueOf(admin.getId())};

        AdminDao adminDao = new AdminDaoImpl();
        return adminDao.updateAdmin(sql, param) != 0;
    }

    private boolean accountChange(Account account, String password) {
        String sql = "update account set password = ? where id = ?";
        String[] param = {password, String.valueOf(account.getId())};

        AccountDao accountDao = new AccountDaoImpl();
        return accountDao.updateAccount(sql, param) != 0;
    }

    private void changeVec() throws IOException {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("修改成功");
        alert.setContentText("您的密码已修改成功！");
        alert.showAndWait();

        Stage oldstage = (Stage) passwordField.getScene().getWindow();
        oldstage.close();
    }
}
