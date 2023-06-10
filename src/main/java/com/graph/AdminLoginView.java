package com.graph;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * @author mengtianluofan
 * @version 1.0
 * @description: 管理员登录
 * TODO
 * @date 2023/6/8 13:19
 */
public class AdminLoginView extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(AdminLoginView.class.getResource("admin-login.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 800, 450);
        stage.setTitle("管理员登录界面");
        stage.setScene(scene);
        stage.show();
    }
    
}
