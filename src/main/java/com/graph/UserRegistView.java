package com.graph;

import com.entity.Person;
import com.entity.User;
import com.service.UserServ;
import com.service.impl.UserServImpl;
import javafx.application.Application;
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
 * @description: 用户账户注册界面
 * TODO
 * @date 2023/6/12 11:14
 */
public class UserRegistView extends Application {
    public ToggleGroup sex;
    public RadioButton manSex, womanSex;
    @FXML
    private Label topLabel;
    @FXML
    private TextField accountText, telText, nameText;
    @FXML
    private PasswordField passwordField, twicePasswordField;

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(UserRegistView.class.getResource("user-regist.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 800, 450);
        stage.setTitle("用户注册界面");
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    protected void returnButtonClick() throws IOException {
        Stage newstage = (Stage) topLabel.getScene().getWindow();
        newstage.hide();
        new HelloApplication().start(newstage);
    }

    @FXML
    protected void clearButtonClick() {
        accountText.setText("");
        passwordField.setText("");
        twicePasswordField.setText("");
    }

    @FXML
    protected void RegisterButtonClick() throws Exception {
        String personid = accountText.getText();
        String password1 = passwordField.getText();
        String password2 = twicePasswordField.getText();
        int gender = 0;
        long tel = 0;

        //检查信息填写是否完整
        if (Objects.equals(personid, "") || Objects.equals(password1, "") || Objects.equals(password2, "")) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("请填写完整信息");
            alert.setHeaderText(null);
            alert.setContentText("请填写完整账户信息！！！");
            alert.showAndWait();
            return;
        }

        //检查身份证号是否正确
        if (!checkID(personid)) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("身份证号输入错误");
            alert.setHeaderText(null);
            alert.setContentText("身份证号输入错误，请检查......");
            alert.showAndWait();
            return;
        }

        //检查电话号码是否正确
        if (!Objects.equals(telText.getText(), "")) {
            try {
                tel = Long.parseLong(telText.getText());
            } catch (NumberFormatException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("电话号码输入错误");
                alert.setHeaderText(null);
                alert.setContentText("电话号码输入错误，请检查......");
                alert.showAndWait();
                return;
            }
        }

        //判断两次输入密码是否一致
        if (password1.equals(password2)) {
            if (manSex.isSelected()) gender = 1;
            else gender = 2;

            Person newperson = new Person(nameText.getText(), personid, gender, tel);
            User user = new User(0, password1, personid);

            UserServ userServ = new UserServImpl();
            User nowuser = userServ.Register(user, newperson);

            if (nowuser == null) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("重复注册");
                alert.setHeaderText(null);
                alert.setContentText("该账户信息已存在，请尝试登录或重新注册......");
                alert.showAndWait();
                return;
            }

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("注册成功！！！");
            alert.setHeaderText(null);
            alert.setContentText("您已成功注册，您的用户账号为：" + nowuser.getId() + "\n已自动为您登录......");
            alert.showAndWait();

            Stage newstage = (Stage) accountText.getScene().getWindow();
            newstage.hide();
            new UserMenuView(nowuser).start(newstage);
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("两次密码不一致！！！");
            alert.setHeaderText(null);
            alert.setContentText("您输入的两次密码不一致, 请重新输入！！！");

            alert.showAndWait();
        }
    }

    private boolean checkID(String id) {
        if (id.length() != 18) return false;
        else {
            for (int i = 0; i < 18; i++) {
                char ch = id.charAt(i);
                if (ch < '0' || ch > '9') {
                    if (i == 17 && ch == 'X') continue;
                    else return false;
                }
            }
        }
        return true;
    }
}
