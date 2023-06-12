package com.graph;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * @author mengtianluofan
 * @version 1.0
 * @description: 用户登录界面
 * TODO
 * @date 2023/6/8 17:05
 */
public class UserLoginView extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(UserLoginView.class.getResource("user-login.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 800, 450);
        stage.setTitle("用户登录界面");
        stage.setScene(scene);
        stage.show();
    }
}
