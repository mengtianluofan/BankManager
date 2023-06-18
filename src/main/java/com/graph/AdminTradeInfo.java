package com.graph;

import com.dao.AccountDao;
import com.dao.InfoDao;
import com.dao.impl.AccountDaoImpl;
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
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

/**
 * @author mengtianluofan
 * @version 1.0
 * @description: 管理员查看交易信息的界面
 * TODO
 * @date 2023/6/17 21:58
 */
public class AdminTradeInfo implements Initializable {
    public TextField searchText;
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
    
    private ObservableList<Information> informationObservableList = FXCollections.observableArrayList();

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
        
        tableView.setItems(informationObservableList);
        numCol.setCellValueFactory(cell -> new SimpleLongProperty(cell.getValue().getId()));
        operatCol.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getOperate()));
        amountCol.setCellValueFactory(cell -> new SimpleDoubleProperty(cell.getValue().getAmount()));
        fromCol.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getFromaccount()));
        toCol.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getToaccount()));
        timeCol.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().setDateAsString()));
        
        viewAll();
    }

    @FXML
    protected void setSearchButtonClick() {
        String id = searchText.getText().trim();
        if (id.isEmpty()) {
            informationObservableList.clear();
            numLabel.setText("总条数：" + 0);
            return;
        }
        String sql;
        String[] param;
        InfoDao infoDao = new InfoDaoImpl();
        List<Information> informationList = new ArrayList<>();

        if (id.length() == 18) {
            sql = "select * from account where ownerID = ?";
            param = new String[]{id};
            
            AccountDao accountDao = new AccountDaoImpl();
            List<Account> accountList = accountDao.getAccount(sql, param);
            if(accountList.size() == 0){
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "未查询到相关信息！");
                alert.showAndWait();
                return;
            }
            for(Account account : accountList){
                String sql1 = "select * from information where fromaccount = ? or toaccount = ?";
                String[] param1 = {String.valueOf(account.getId()), String.valueOf(account.getId())};
                informationList.addAll(infoDao.getInfo(sql1, param1));
            }
        } else {
            sql = "select * from information where fromaccount = ? or toaccount = ?";
            param = new String[]{id, id};
            informationList = infoDao.getInfo(sql, param);
        }
        if (informationList.size() == 0) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "未查询到相关信息！");
            alert.showAndWait();
            return;
        }

        informationObservableList.clear();
        informationObservableList.addAll(informationList);
        numLabel.setText("总条数：" + informationObservableList.size());
    }
    
    @FXML
    protected void viewAll(){
        informationObservableList.clear();
        
        String sql = "select * from information";
        String[] param = {};
        InfoDao infoDao = new InfoDaoImpl();
        informationObservableList.addAll(infoDao.getInfo(sql, param));
        numLabel.setText("总条数：" + informationObservableList.size());
    }
}
