package com.graph;

import com.dao.AccountDao;
import com.dao.impl.AccountDaoImpl;
import com.entity.Account;
import com.entity.User;
import javafx.beans.property.LongProperty;
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
import java.util.List;
import java.util.ResourceBundle;

/**
 * @author mengtianluofan
 * @version 1.0
 * @description: 展示用户拥有的账户信息
 * TODO
 * @date 2023/6/17 19:14
 */
public class OwnerAccountView implements Initializable {
    private static User user;
    
    public Label typeLabel1;
    public TableView<Account> infoTabel1;
    public TableColumn<Account, Number> accountID1;
    public TableColumn<Account, String> accountType1;
    public TableColumn<Account, Number> amoutTabel1;
    public TableColumn<Account, String> ownerTabel1;
    
    public Label typeLabel2;
    public TableView<Account> infoTabel2;
    public TableColumn<Account, Number> accountID2;
    public TableColumn<Account, String> accountType2;
    public TableColumn<Account, Number> limitTabel;
    public TableColumn<Account, Number> amoutTabel2;
    public TableColumn<Account, String> ownerTabel2;

    private ObservableList<Account> accountData = FXCollections.observableArrayList();

    public OwnerAccountView(User user) {
        OwnerAccountView.user = user;
    }

    public OwnerAccountView() {
    }

    public void display(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(UserRegistView.class.getResource("owner-account-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 800, 450);
        stage.setTitle("用户所有账户");
        stage.setScene(scene);
        stage.show();
    }
    
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        infoTabel1.widthProperty().addListener((obs, oldVal, newVal) -> {
            double width = (double) newVal / infoTabel1.getColumns().size();
            for (TableColumn column : infoTabel1.getColumns()) {
                column.setPrefWidth(width);
            }
        });

        infoTabel2.widthProperty().addListener((obs, oldVal, newVal) -> {
            double width = (double) newVal / infoTabel2.getColumns().size();
            for (TableColumn column : infoTabel2.getColumns()) {
                column.setPrefWidth(width);
            }
        });
        
        accountID1.setCellValueFactory(cellData -> new SimpleLongProperty(cellData.getValue().getId()));
        accountID2.setCellValueFactory(cellData -> new SimpleLongProperty(cellData.getValue().getId()));
        accountType1.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getAccountTypeDesc()));
        accountType2.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getAccountTypeDesc()));
        amoutTabel1.setCellValueFactory(cellData -> new SimpleDoubleProperty(cellData.getValue().getAmount()));
        amoutTabel2.setCellValueFactory(cellData -> new SimpleDoubleProperty(cellData.getValue().getAmount()));
        limitTabel.setCellValueFactory(cellData -> new SimpleDoubleProperty(cellData.getValue().getLimit()));
        ownerTabel1.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getOwnerName()));
        ownerTabel2.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getOwnerName()));
        
        String sql = "select * from account where ownerID = ?";
        String[] param = {user.getOwnerID()};
        AccountDao accountDao = new AccountDaoImpl();
        List<Account> accountList= accountDao.getAccount(sql, param);
        
        int type1 = 0, type2 = 0;
        for(int i = 0; i < accountList.size(); i++){
            Account midAccount = accountList.get(i);
            if(midAccount.getAccountType() == 1){
                infoTabel1.getItems().add(midAccount);
                type1++;
            }
            else{
                infoTabel2.getItems().add(midAccount);
                type2++;
            }
        }
        
        typeLabel1.setText("储蓄卡：" + type1 + " 个");
        typeLabel2.setText("信用卡：" + type2 + " 个");
    }

    @FXML
    protected void returnButtonClick() throws Exception {
        Stage newstage = (Stage) typeLabel1.getScene().getWindow();
        newstage.close();
        new UserInfo().display(newstage);
    }

}
