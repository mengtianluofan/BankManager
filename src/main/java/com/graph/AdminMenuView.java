package com.graph;

import com.entity.Admin;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * @author mengtianluofan
 * @version 1.0
 * @description: 管理员菜单界面
 * TODO
 * @date 2023/6/17 21:50
 */
public class AdminMenuView {
    static Admin admin;
    
    public Label topLabel;
    public Button userManage;
    public Button accountManage;
    public Button tradeInfo;
    public Button changePassword;

    public AdminMenuView(Admin admin) {
        AdminMenuView.admin = admin;
    }

    public AdminMenuView() {
    }
    
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(UserRegistView.class.getResource("admin-menu.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 800, 450);
        stage.setTitle("管理员菜单界面");
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    protected void returnButtonClick() throws Exception {
        Stage newstage = (Stage) topLabel.getScene().getWindow();
        newstage.close();
        new UserLoginView().start(newstage);
    }


    @FXML
    protected void setChangePassword() throws Exception {
        ChangePassword changePassword1 = new ChangePassword(admin);
        changePassword1.display();
    }
    
    @FXML
    protected void setTradeInfo() throws IOException {
        Stage stage = new Stage();
        new AdminTradeInfo().start(stage);
    }
    
    @FXML
    protected void setAccountManage() throws IOException {
        Stage stage = new Stage();
        new AdminManageAccount().start(stage);
    }
    
    @FXML
    protected void setUserManage() throws IOException {
        Stage stage = new Stage();
        new AdminManageUser().start(stage);
    }

}
