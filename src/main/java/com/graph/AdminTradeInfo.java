package com.graph;

import com.dao.InfoDao;
import com.dao.impl.InfoDaoImpl;
import com.entity.Account;
import com.entity.Information;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;

/**
 * @author mengtianluofan
 * @version 1.0
 * @description: 管理员查看交易信息的界面
 * TODO
 * @date 2023/6/17 21:58
 */
public class AdminTradeInfo implements Initializable {
    @FXML
    protected Label numLabel;
    @FXML
    protected TableView<Information> tableView;
    @FXML
    protected TableColumn<Information, Number> numCol;
    @FXML
    protected TableColumn<Information, String> operatCol;
    @FXML
    protected TableColumn<Information, Number> amountCol;
    @FXML
    protected TableColumn<Information, String> fromCol;
    @FXML
    protected TableColumn<Information, String> toCol;
    @FXML
    protected TableColumn<Information, String> timeCol;
    
    private ObservableList<Information> accountObservableList = FXCollections.observableArrayList();

    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(UserRegistView.class.getResource("admin-trade-info.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 800, 450);
        stage.setTitle("交易信息");
        stage.setScene(scene);
        stage.show();
    }
    
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        tableView.widthProperty().addListener((obs, oldVal, newVal) -> {
            double width = (double) newVal / tableView.getColumns().size();
            for(TableColumn column : tableView.getColumns()) {
                column.setPrefWidth(width);
            }
        });
        
        tableView.setItems(accountObservableList);
        numCol.setCellValueFactory(cell -> new SimpleLongProperty(cell.getValue().getId()));
        operatCol.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getOperate()));
        amountCol.setCellValueFactory(cell -> new SimpleDoubleProperty(cell.getValue().getAmount()));
        fromCol.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getFromaccount()));
        toCol.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getToaccount()));
        timeCol.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().setDateAsString()));
        
        String sql = "select * from information";
        String[] param = {};
        InfoDao infoDao = new InfoDaoImpl();
        accountObservableList.addAll(infoDao.getInfo(sql, param));
        numLabel.setText(numLabel.getText() + accountObservableList.size());
    }
}
