package com.graph;

import com.dao.AccountDao;
import com.dao.PersonDao;
import com.dao.impl.AccountDaoImpl;
import com.dao.impl.PersonDaoImpl;
import com.entity.Account;
import com.entity.Person;
import com.entity.User;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

/**
 * @author mengtianluofan
 * @version 1.0
 * @description: 用户信息界面
 * TODO
 * @date 2023/6/17 12:23
 */
public class UserInfo implements Initializable {
    private static User user;
    List<Account> accountList;
    
    public Button changeInfoButton;
    public Label userIDLabel;
    public Label ownerIDLabel;
    public Label ownerNameLabel;
    public Label numLable;
    public Button viewButton;
    public Label genderLabel;
    public Label telLabel;

    public UserInfo(User user) {
        UserInfo.user = user;
    }
    
    public UserInfo(){}

    public void display(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(UserRegistView.class.getResource("user-info.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 800, 450);
        stage.setTitle("用户信息");
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        String sql = "select * from account where ownerID = ?";
        String[] param = {user.getOwnerID()};
        AccountDao accountDao = new AccountDaoImpl();
        accountList = accountDao.getAccount(sql, param);
        
        sql = "select * from person where personid = ?";
        PersonDao personDao = new PersonDaoImpl();
        Person person = personDao.getPerson(sql, param).get(0);
        
        userIDLabel.setText(String.valueOf(user.getId()));
        ownerIDLabel.setText(user.getOwnerID());
        ownerNameLabel.setText(person.getName());
        numLable.setText(accountList.size() + " 个");
        if(person.getGender() == 1)
            genderLabel.setText("男");
        else genderLabel.setText("女");
        telLabel.setText(String.valueOf(person.getTel()));
    }
    
    public void setViewButtonClick() throws IOException {
        Stage stage = (Stage) numLable.getScene().getWindow();
        stage.close();
        new OwnerAccountView(user).display(stage);
    }
    
    public void setChangeInfoButtonClick() throws IOException {
        Stage stage = (Stage) numLable.getScene().getWindow();
        stage.close();
        new UserChangeInfo(user).display(stage);
    }
}
