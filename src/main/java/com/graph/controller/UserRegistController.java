package com.graph.controller;

import com.dao.impl.PersonDaoImpl;
import com.entity.Person;
import com.entity.User;
import com.graph.HelloApplication;
import com.service.UserServ;
import com.service.impl.UserServImpl;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * @author mengtianluofan
 * @version 1.0
 * @description: 用户注册界面控制器
 * TODO
 * @date 2023/6/12 11:15
 */
public class UserRegistController {
    public ToggleGroup sex;
    public RadioButton manSex, womanSex;
    @FXML
    private Label topLabel;
    @FXML
    private TextField accountText, telText, nameText;
    @FXML
    private PasswordField passwordField, twicePasswordField;
    
    
    @FXML
    protected void returnButtonClick() throws IOException {
        Stage newstage = (Stage) topLabel.getScene().getWindow();
        newstage.hide();
        new HelloApplication().start(newstage);
    }

    @FXML
    protected void clearButtonClick(){
        accountText.setText("");
        passwordField.setText("");
        twicePasswordField.setText("");
    }
    
    @FXML
    protected void oldRegisterButtonClick(){
        long personid =  Long.parseLong(accountText.getText());
        String password1 = passwordField.getText();
        String password2 = twicePasswordField.getText();
        int gender = 0;
        long tel = Long.parseLong(telText.getText());
        if(password1.equals(password2)){
            if(manSex.isSelected()) gender = 1;
            else gender = 2;
            
            Person newperson = new Person(nameText.getText(), personid, gender, tel);
            User user = new User(0, password1, personid);
            
            UserServ userServ = new UserServImpl();
            User nowuser = userServ.Register(user, newperson);
            
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("注册成功！！！");
            alert.setHeaderText(null);
            alert.setContentText("您已成功注册，您的用户账号为：" + nowuser.getId());

            alert.showAndWait();
        }
        else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("两次密码不一致！！！");
            alert.setHeaderText(null);
            alert.setContentText("您输入的两次密码不一致, 请重新输入。。。");

            alert.showAndWait();
        }
    }
}
