package com.graph;

import com.dao.InfoDao;
import com.dao.impl.InfoDaoImpl;
import com.entity.Account;
import com.entity.Information;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

/**
 * @author mengtianluofan
 * @version 1.0
 * @description: 账户交易信息
 * TODO
 * @date 2023/6/16 19:17
 */
public class AccountTradeInfo implements Initializable {
    public TableView<Information> InfoTable;
    public TableColumn<Information, Long> numCol;
    public TableColumn<Information, Long> userCol;
    public TableColumn<Information, String> operateCol;
    public TableColumn<Information, Double> amountCol;
    public TableColumn<Information, String> toCol;
    public TableColumn<Information, Date> timeCol;

    public TableView<Information> InfoTable1;
    
    private static Account account;
    public TableColumn<Information, Long> numCol1;
    public TableColumn<Information, String> fromCol;
    public TableColumn<Information, String> operateCol1;
    public TableColumn<Information, Double> amountCol1;
    public TableColumn<Information, Date> timeCol1;


    private ObservableList<Account> data;
    private List<Information> informationList;

    public AccountTradeInfo(Account account) {
        AccountTradeInfo.account = account;
    }

    public AccountTradeInfo() {
    }

    public void display(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(UserRegistView.class.getResource("account-trade-info.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 800, 450);
        stage.setTitle("账户菜单界面");
        stage.setScene(scene);
        stage.show();
    }
    
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        informationList = new ArrayList<>();
        String sql = "select * from information where fromaccount = ?";
        String[] param = {String.valueOf(account.getId())};
        InfoDao infoDao = new InfoDaoImpl();
        informationList = infoDao.getInfo(sql, param);
        
        numCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        operateCol.setCellValueFactory(new PropertyValueFactory<>("operate"));
        amountCol.setCellValueFactory(new PropertyValueFactory<>("amount"));
        toCol.setCellValueFactory(new PropertyValueFactory<>("toaccount"));
        timeCol.setCellValueFactory(new PropertyValueFactory<>("date"));
        
        for(int i = 0; i < informationList.size(); i++){
            Information midInfo = informationList.get(i);
            midInfo.setId(i + 1);
            InfoTable.getItems().add(midInfo);
        }
        
        sql = "select * from information where toaccount = ?";
        informationList = infoDao.getInfo(sql, param);

        numCol1.setCellValueFactory(new PropertyValueFactory<>("id"));
        fromCol.setCellValueFactory(new PropertyValueFactory<>("fromaccount"));
        operateCol1.setCellValueFactory(new PropertyValueFactory<>("operate"));
        amountCol1.setCellValueFactory(new PropertyValueFactory<>("amount"));
        timeCol1.setCellValueFactory(new PropertyValueFactory<>("date"));

        for(int i = 0; i < informationList.size(); i++){
            Information midInfo = informationList.get(i);
            midInfo.setId(i + 1);
            InfoTable1.getItems().add(midInfo);
        }
    }
}
