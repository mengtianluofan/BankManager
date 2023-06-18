package com.graph;

import com.dao.PersonDao;
import com.dao.UserDao;
import com.dao.impl.PersonDaoImpl;
import com.dao.impl.UserDaoImpl;
import com.entity.Person;
import com.entity.User;
import com.service.UserServ;
import com.service.impl.UserServImpl;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.util.List;

/**
 * @author mengtianluofan
 * @version 1.0
 * @description: TODO
 * @date 2023/6/18 11:10
 */
public class AddUserDialog {
    @FXML
    private TextField owneridTextField;
    @FXML
    private TextField nameTextField;
    @FXML
    private ComboBox genderComboBox;
    @FXML
    private TextField telTextField;
    @FXML
    private Button confirmButton;
    private UserAndPerson newUser = null;

    public UserAndPerson getNewUser() {
        return newUser;
    }

    @FXML
    void confirm1() {
        // 获取表单中用户输入的数据
        String ownerid = owneridTextField.getText().trim();
        String name = nameTextField.getText().trim();
        String gender = genderComboBox.getValue().toString();
        String tel = telTextField.getText().trim();

        // 如果输入数据为空，则弹出错误提示
        if (ownerid.isEmpty() || name.isEmpty() || gender.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "所有字段都不能为空，请重新输入!");
            alert.showAndWait();
            return;
        }

        // 如果电话号码不是十一位，则弹出错误提示
        if (!tel.isEmpty() && !tel.matches("\\d{11}")) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "电话号码必须是11位数字，请重新输入!");
            alert.showAndWait();
            return;
        }
        int genderInt = gender.equals("男") ? 1 : 2;

        Person newperson = new Person(name, ownerid, genderInt, Long.parseLong(tel));
        User user = new User(0, "123456", ownerid);
        UserServ userServ = new UserServImpl();
        UserDao userDao = new UserDaoImpl();
        String sql1 = "select * from user where ownerid = ?";
        String[] param = {ownerid};
        User nowuser = userServ.Register(user, newperson);
        
        if (nowuser == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "已存在该身份信息！请尝试用已有信息注册！");
            alert.showAndWait();
            return;
            
        }else {
            userDao.insertUser(user);
            List<User> userList = userDao.getUser(sql1, param);
            nowuser = userList.get(userList.size() - 1);
        }
        
        newUser = new UserAndPerson(nowuser);
        // 关闭窗口
        ((Stage) confirmButton.getScene().getWindow()).close();
    }
    
    @FXML
    void  confirm2(){
        String ownerid = owneridTextField.getText().trim();

        if (ownerid.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "身份证号不能为空，请输入!");
            alert.showAndWait();
            return;
        }
        
        PersonDao personDao = new PersonDaoImpl();
        String sql = "select * from person where personid = ?";
        String[] param = {ownerid};
        List<Person> oldpersonList = personDao.getPerson(sql, param);
        User newuser = null;
        if(oldpersonList.size() == 0){
            Alert alert = new Alert(Alert.AlertType.ERROR, "不存在该身份证号所对应的身份信息");
            alert.showAndWait();
            return;
        }
        else {
            UserDao userDao = new UserDaoImpl();
            String sql1 = "select * from user where ownerid = ?";
            List<User> userList = userDao.getUser(sql1, param);
            if(userList.size() != 0){
                Alert alert = new Alert(Alert.AlertType.ERROR, "该身份信息已存在用户！无法注册！");
                alert.showAndWait();
                return;
            }
            else{
                newuser = new User(0, "123456", oldpersonList.get(0).getPersonID());
                userDao.insertUser(newuser);
                userList = userDao.getUser(sql1, param);
                newuser = userList.get(userList.size() - 1);
            }
        }

        newUser = new UserAndPerson(newuser);
        // 关闭窗口
        ((Stage) confirmButton.getScene().getWindow()).close();
    }

}
