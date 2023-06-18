package com.graph;

import com.dao.AccountDao;
import com.dao.impl.AccountDaoImpl;
import com.entity.Account;
import com.service.AdminServ;
import com.service.impl.AdminServImpl;
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
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * @author mengtianluofan
 * @version 1.0
 * @description: 账户管理界面
 * TODO
 * @date 2023/6/18 13:39
 */
public class AdminManageAccount implements Initializable {
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
    public Button addCredit;
    public Button addDeposit;
    public Button deleteButton;
    public TextField searchText;
    public Button searchButton;

    private ObservableList<Account> accountData1 = FXCollections.observableArrayList();
    private ObservableList<Account> accountData2 = FXCollections.observableArrayList();

    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(UserRegistView.class.getResource("admin-manage-account.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 800, 450);
        stage.setTitle("账户管理");
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

        infoTabel1.setItems(accountData1);
        infoTabel2.setItems(accountData2);
        accountID1.setCellValueFactory(cellData -> new SimpleLongProperty(cellData.getValue().getId()));
        accountID2.setCellValueFactory(cellData -> new SimpleLongProperty(cellData.getValue().getId()));
        accountType1.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getAccountTypeDesc()));
        accountType2.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getAccountTypeDesc()));
        amoutTabel1.setCellValueFactory(cellData -> new SimpleDoubleProperty(cellData.getValue().getAmount()));
        amoutTabel2.setCellValueFactory(cellData -> new SimpleDoubleProperty(cellData.getValue().getAmount()));
        limitTabel.setCellValueFactory(cellData -> new SimpleDoubleProperty(cellData.getValue().getLimit()));
        ownerTabel1.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getOwnerName()));
        ownerTabel2.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getOwnerName()));

        viewAll();
    }

    //添加储蓄账户
    public void setAddDeposit() throws IOException {
        Stage stage = new Stage();
        FXMLLoader loader = new FXMLLoader(AddUserDialog.class.getResource("admin-add-deposit.fxml"));
        Scene scene = new Scene(loader.load(), 800, 450);
        stage.setScene(scene);
        stage.setTitle("添加储蓄卡");
        stage.showAndWait();
        Account account = loader.<AdminAddAccount>getController().getNewAccount();
        if (account != null) {
            addAccount(account);
        } else {
            // 如果添加失败，则弹出错误提示
            Alert errorAlert = new Alert(Alert.AlertType.ERROR, "添加失败，请稍后再试!");
            errorAlert.showAndWait();
        }
    }

    //添加信用账户
    public void setAddCredit() throws IOException {
        Stage stage = new Stage();
        FXMLLoader loader = new FXMLLoader(AddUserDialog.class.getResource("admin-add-credit.fxml"));
        Scene scene = new Scene(loader.load(), 800, 450);
        stage.setScene(scene);
        stage.setTitle("添加信用卡");
        stage.showAndWait();
        Account account = loader.<AdminAddAccount>getController().getNewAccount();
        if (account != null) {
            addAccount(account);
        } else {
            // 如果添加失败，则弹出错误提示
            Alert errorAlert = new Alert(Alert.AlertType.ERROR, "添加失败，请稍后再试!");
            errorAlert.showAndWait();
        }
    }

    private void addAccount(Account account) {
        if (account.getAccountType() == 1) {
            accountData1.add(account);
            typeLabel1.setText("储蓄卡：" + accountData1.size() + " 个");
        } else {
            accountData2.add(account);
            typeLabel2.setText("信用卡：" + accountData2.size() + " 个");
        }
    }

    private void deleteAccount(Account account) {
        if (account.getAccountType() == 1) {
            accountData1.remove(account);
            typeLabel1.setText("储蓄卡：" + accountData1.size() + " 个");
        } else {
            accountData2.remove(account);
            typeLabel2.setText("信用卡：" + accountData2.size() + " 个");
        }
    }

    @FXML
    protected void setDeleteButtonClick() {
        //获取选定的行
        Account selectedAccount = infoTabel1.getSelectionModel().getSelectedItem();
        if (selectedAccount == null) selectedAccount = infoTabel2.getSelectionModel().getSelectedItem();
        if (selectedAccount == null) {
            // 如果没有被选中的行，则弹出错误提示
            Alert alert = new Alert(Alert.AlertType.ERROR, "请选择要删除的行!");
            alert.showAndWait();
        } else {
            // 如果有选中的行，则弹出确认对话框
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "确认删除此账户吗?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                AdminServ adminServ = new AdminServImpl();
                if (adminServ.deleteAccountByID(selectedAccount.getId())) {
                    deleteAccount(selectedAccount);
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
    protected void setSearchButtonClick() {
        String id = searchText.getText().trim();
        if (id.isEmpty()) {
            accountData1.clear();
            accountData2.clear();
            typeLabel1.setText("储蓄卡：" + accountData1.size() + " 个");
            typeLabel2.setText("信用卡：" + accountData2.size() + " 个");
            return;
        }
        String sql = "select * from account where ";
        String[] param = {id};
        AccountDao accountDao = new AccountDaoImpl();
        List<Account> accountList;

        if (id.length() == 18) {
            sql += "ownerID = ?";
        } else {
            sql += "id = ?";
        }
        accountList = accountDao.getAccount(sql, param);
        if (accountList.size() == 0) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "未查询到相关信息！");
            alert.showAndWait();
            return;
        }

        accountData1.clear();
        accountData2.clear();
        for (Account account : accountList) {
            addAccount(account);
        }
    }

    @FXML
    protected void viewAll() {
        accountData1.clear();
        accountData2.clear();

        String sql = "select * from account";
        String[] param = {};
        AccountDao accountDao = new AccountDaoImpl();
        List<Account> accountList = accountDao.getAccount(sql, param);

        for (Account account : accountList) {
            if (account.getAccountType() == 1) accountData1.add(account);
            else accountData2.add(account);
        }

        typeLabel1.setText("储蓄卡：" + accountData1.size() + " 个");
        typeLabel2.setText("信用卡：" + accountData2.size() + " 个");
    }
}
