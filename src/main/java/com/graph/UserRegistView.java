package com.graph;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * @author mengtianluofan
 * @version 1.0
 * @description: 用户账户注册界面
 * TODO
 * @date 2023/6/12 11:14
 */
public class UserRegistView extends Application {
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(UserRegistView.class.getResource("user-regist.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 800, 450);
        stage.setTitle("用户注册界面");
        stage.setScene(scene);
        stage.show();
    }
}
