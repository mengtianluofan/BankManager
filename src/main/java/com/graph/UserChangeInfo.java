package com.graph;

import com.dao.PersonDao;
import com.dao.impl.PersonDaoImpl;
import com.entity.Person;
import com.entity.User;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * @author mengtianluofan
 * @version 1.0
 * @description: 更改用户信息（好像只有电话号码能改）
 * TODO
 * @date 2023/6/17 13:13
 */
public class UserChangeInfo implements Initializable {
    static User user;
    public Button modifyButton;
    public Label idLabel;
    public Label nameLabel;
    public Label genderLabel;
    public TextField telText;
    Person person;

    public UserChangeInfo(User user) {
        UserChangeInfo.user = user;
    }

    public UserChangeInfo() {
    }

    public void display(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(UserRegistView.class.getResource("user-change-info.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 800, 450);
        stage.setTitle("更改用户信息");
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        String sql = "select * from person where personid = ?";
        String[] param = {user.getOwnerID()};
        PersonDao personDao = new PersonDaoImpl();
        person = personDao.getPerson(sql, param).get(0);

        idLabel.setText(person.getPersonID());
        nameLabel.setText(person.getName());
        if (person.getGender() == 1)
            genderLabel.setText("男");
        else genderLabel.setText("女");
        telText.setText(String.valueOf(person.getTel()));
    }

    @FXML
    protected void returnButtonClick() throws Exception {
        Stage newstage = (Stage) modifyButton.getScene().getWindow();
        newstage.close();
        new UserInfo().display(newstage);
    }

    @FXML
    protected void clearButtonClick() {
        telText.setText("");
    }

    @FXML
    protected void setModifyButtonClick() throws Exception {
        long tel = -1;

        if (telText.getText().equals("")) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("电话号码输入错误");
            alert.setContentText("电话号码输入错误，请检查......");
            alert.showAndWait();
            return;
        }

        try {
            tel = Long.parseLong(telText.getText());
        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("电话号码输入错误");
            alert.setContentText("电话号码输入错误，请检查......");
            alert.showAndWait();
            return;
        }

        person.setTel(tel);

        String sql = "update person set tel = ? where personid = ?";
        String[] param = {String.valueOf(person.getTel()), person.getPersonID()};
        PersonDao personDao = new PersonDaoImpl();
        personDao.updatePerson(sql, param);

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("修改完成");
        alert.setContentText("修改成功！");
        alert.showAndWait();
        
        returnButtonClick();
    }
}
