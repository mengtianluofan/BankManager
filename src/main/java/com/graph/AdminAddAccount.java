package com.graph;

import com.dao.AccountDao;
import com.dao.PersonDao;
import com.dao.impl.AccountDaoImpl;
import com.dao.impl.PersonDaoImpl;
import com.entity.Account;
import com.entity.Person;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.util.List;

/**
 * @author mengtianluofan
 * @version 1.0
 * @description: 管理员添加账户的Controller
 * TODO
 * @date 2023/6/18 14:05
 */
public class AdminAddAccount {
    @FXML
    protected Button confirmButton;
    @FXML
    protected TextField owneridTextField;
    @FXML
    protected ComboBox<Double> LimitBox;
    
    private Account newaccount = null;
    
    public Account getNewAccount(){return newaccount;}
    
    public void confirm1(){
        String ownerID = owneridTextField.getText().trim();
        
        if(ownerID.isEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR, "所有字段都不能为空，请重新输入!");
            alert.showAndWait();
            return;
        }

        String ownername = searchPerson(ownerID);
        if(ownername == null) return;
        
        AccountDao accountDao = new AccountDaoImpl();
        accountDao.insertAccount(new Account(0, "123456", 0, -1, 1, ownerID, ownername));
        String sql = "select * from account where ownerid = ?";
        String[] param = {ownerID};
        List<Account> accountList = accountDao.getAccount(sql, param);
        
        newaccount = accountList.get(accountList.size() - 1);
        ((Stage) confirmButton.getScene().getWindow()).close();
    }
    
    public void confirm2(){
        String ownerID = owneridTextField.getText().trim();
        Double limit = LimitBox.getValue();

        if(ownerID.isEmpty() || limit == null){
            Alert alert = new Alert(Alert.AlertType.ERROR, "所有字段都不能为空，请重新输入!");
            alert.showAndWait();
            return;
        }

        String ownername = searchPerson(ownerID);
        if(ownername == null) return;

        AccountDao accountDao = new AccountDaoImpl();
        accountDao.insertAccount(new Account(0, "123456", 0, limit, 2, ownerID, ownername));
        String sql = "select * from account where ownerid = ?";
        String[] param = {ownerID};
        List<Account> accountList = accountDao.getAccount(sql, param);

        newaccount = accountList.get(accountList.size() - 1);
        ((Stage) confirmButton.getScene().getWindow()).close();
    }
    
    public String searchPerson(String id){
        String sql = "select * from person where personid = ?";
        String[] param = {id};
        PersonDao personDao = new PersonDaoImpl();
        List<Person> personList = personDao.getPerson(sql,param);
        if(personList.size() == 0) return null;
        return personList.get(0).getName();
    }
}
