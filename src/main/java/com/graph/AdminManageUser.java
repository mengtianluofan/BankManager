package com.graph;

import com.dao.PersonDao;
import com.dao.impl.PersonDaoImpl;
import com.dao.impl.UserDaoImpl;
import com.entity.Person;
import com.entity.User;
import com.service.AdminServ;
import com.service.impl.AdminServImpl;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * @author mengtianluofan
 * @version 1.0
 * @description: 管理员用户管理界面
 * TODO
 * @date 2023/6/18 00:32
 */
public class AdminManageUser implements Initializable {
    @FXML
    protected Button deleteButton;
    @FXML
    protected Label numLabel;
    @FXML
    protected Button addButton;
    @FXML
    protected TableView<UserAndPerson> tableView;
    @FXML
    protected TableColumn<UserAndPerson, Number> idCol;
    @FXML
    protected TableColumn<UserAndPerson, String> ownernameCol;
    @FXML
    protected TableColumn<UserAndPerson, String> owneridCol;
    @FXML
    protected TableColumn<UserAndPerson, String> genderCol;
    @FXML
    protected TableColumn<UserAndPerson, Number> telCol;

    private ObservableList<UserAndPerson> userAndPeople = FXCollections.observableArrayList();

    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(UserRegistView.class.getResource("admin-manage-user.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 800, 450);
        stage.setTitle("用户管理");
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        tableView.widthProperty().addListener((obs, oldVal, newVal) -> {
            double width = (double) newVal / tableView.getColumns().size();
            for (TableColumn column : tableView.getColumns()) {
                column.setPrefWidth(width);
            }
        });

        tableView.setItems(userAndPeople);
        idCol.setCellValueFactory(cell -> new SimpleLongProperty(cell.getValue().getId()));
        ownernameCol.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getOwner()));
        owneridCol.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getOwnerid()));
        genderCol.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().genderToString()));
        telCol.setCellValueFactory(cell -> new SimpleLongProperty(cell.getValue().getTel()));

        String sql = "select * from user";
        String[] param = {};
        List<User> userList = new UserDaoImpl().getUser(sql, param);
        for (User user : userList) {
            userAndPeople.add(new UserAndPerson(user));
        }
        numLabel.setText("用户数： " + userList.size());
    }

    @FXML
    //删除用户
    protected void setDeleteButtonClick() {
        //获取选定的行
        UserAndPerson selectedUser = tableView.getSelectionModel().getSelectedItem();
        if (selectedUser == null) {
            // 如果没有被选中的行，则弹出错误提示
            Alert alert = new Alert(Alert.AlertType.ERROR, "请选择要删除的行!");
            alert.showAndWait();
        } else {
            // 如果有选中的行，则弹出确认对话框
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "确认删除此用户吗?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                AdminServ adminServ = new AdminServImpl();
                Alert alert1 = new Alert(Alert.AlertType.CONFIRMATION, "是否删除其名下账户?");
                Optional<ButtonType> result1 = alert.showAndWait();
                if (result1.isPresent() && result1.get() == ButtonType.OK) {
                    adminServ.deleteAccountByOwnerID(selectedUser.getOwnerid());
                }
                
                if (adminServ.deleteUser(selectedUser.getId())) {
                    userAndPeople.remove(selectedUser);
                    numLabel.setText("用户数： " + userAndPeople.size());
                    // 弹出成功提示
                    Alert successAlert = new Alert(Alert.AlertType.INFORMATION, "删除成功!");
                    successAlert.showAndWait();
                } else {
                    // 如果删除失败，则弹出错误提示
                    Alert errorAlert = new Alert(Alert.AlertType.ERROR, "删除失败，请稍后再试!");
                    errorAlert.showAndWait();
                }
                
            }
        }
    }

    @FXML
    //添加用户
    protected void setAddButtonClick() {

    }
}

class UserAndPerson {
    private long id;
    private String ownerid;
    private String owner;
    private int gender;
    private long tel;

    public UserAndPerson(User user) {
        id = user.getId();
        ownerid = user.getOwnerID();
        getPersonInfo();
    }

    public UserAndPerson(Long id, String ownerid, String owner, int gender, Long tel) {
        this.id = id;
        this.ownerid = ownerid;
        this.owner = owner;
        this.gender = gender;
        this.tel = tel;
    }

    public UserAndPerson() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getOwnerid() {
        return ownerid;
    }

    public void setOwnerid(String ownerid) {
        this.ownerid = ownerid;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public long getTel() {
        return tel;
    }

    public void setTel(long tel) {
        this.tel = tel;
    }

    private void getPersonInfo() {
        String sql = "select * from person where personid = ?";
        String[] param = {ownerid};
        PersonDao personDao = new PersonDaoImpl();
        Person person = personDao.getPerson(sql, param).get(0);
        owner = person.getName();
        gender = person.getGender();
        tel = person.getTel();
    }

    public String genderToString() {
        if (gender == 1) return "男";
        else return "女";
    }
}
