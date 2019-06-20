package thundermail;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class FXMLDocumentController {
    
    // REGISTER & MAIN VARIABLES
    
    @FXML
    private PasswordField txtP;
    @FXML
    private TextField txtName;
    @FXML
    private TextField txtEmail;
    @FXML
    private Button btnComplete;
    public static LinkedList<User> users = new LinkedList<>();
    private double xOffset = 0;
    private double yOffset = 0;
    @FXML
    private PasswordField txtPass;
    @FXML
    private Button btnLogin;
    @FXML
    private Button btnRegister;
    @FXML
    private TextField txtUser;
    @FXML
    private Label lblWarn;
    @FXML
    private Label lblStatus;
    @FXML
    private Button btnClose;
    @FXML
    private Button btnExit;
    @FXML
    private AnchorPane paneAcnhor;
    @FXML
    private CheckBox checkAdmin;
    
    // GLOBAL VARIABLES
    
    public static User actualUser;
    public static boolean usersLoaded;
    
    // FEED VARIABLES
    
    @FXML
    private Label lblWelcome;
    @FXML
    private Button btnLogout;
    @FXML
    private PasswordField txtSettingsPass;
    @FXML
    private Label lblSettingsUsername;
    @FXML
    private Label lblSettingsShowPass;
    @FXML
    private Label lblSettingsStatus;
    @FXML
    private Button btnSettingsSave;
    @FXML
    private TextField txtSettingsEmail;
    @FXML
    private TextField txtSettingsName;
    @FXML
    private Hyperlink linkSettingsShow;
    @FXML
    private Hyperlink linkSettings;
    @FXML
    private AnchorPane anchorSettings;
    @FXML
    private Label lblSettingsAdmin;
    @FXML
    private Label lblUsersWarn;
    @FXML
    private AnchorPane anchorUsers;
    @FXML
    private TableView<User> tableUsers;
    @FXML
    private TableColumn<TableView, String> tcolumnEmail;
    @FXML
    private TableColumn<TableView, String> tcolumnPass;
    @FXML
    private TableColumn<TableView, String> tcolumnUsername;
    @FXML
    private Button btnRefresh;
    @FXML
    private Button btnSettingsDeleteSelected;
    
    @FXML
    private void login(ActionEvent event) throws IOException {
        
        // Getting text fields
        String n = txtUser.getText();
        String p = txtPass.getText();
        
        int index = 0;
        
        // Default is always empty
        if(users.isEmpty()){
            lblStatus.setTextFill(Color.web("#aa0000"));
            lblStatus.setText("ERROR");
        }else{
            for(int x = 1; x <= users.size(); x++){
                if(n.equals(users.get(index).getUsername()) && p.equals(users.get(index).getPassword())){
                    lblStatus.setTextFill(Color.web("#008206"));
                    lblStatus.setText("Login in...");
                    actualUser = users.get(index);
                    exit(event);
                    feed(event, actualUser.getUsername());
                    usersLoaded = false;
                }else{
                    lblStatus.setTextFill(Color.web("#aa0000"));
                    lblStatus.setText("Wrong credentials");
                }
                index++;
            }
        }
    }
    
    @FXML
    public void complete(ActionEvent event){
        
        String u = txtName.getText();
        String p = txtPass.getText();
        String e = txtEmail.getText();
        Stage stage = (Stage) btnComplete.getScene().getWindow();

        // If you write a point on wherever position
        // it lets you continue (bug) it is no order
        // restricted.
        if(!e.contains("@") || !e.contains(".")){
            lblWarn.setTextFill(Color.web("#aa0000"));
            lblWarn.setText("Enter a valid email");
        }else if(u.equals("") || p.equals("") || e.equals("")){
            lblWarn.setTextFill(Color.web("#aa0000"));
            lblWarn.setText("Fill all the text fields");
        }else{
            if(checkAdmin.isSelected()){
                User usr = new User(u, p, e, true);
                users.add(usr);
            }else{
                User usr = new User(u, p, e, false);
                users.add(usr);
            }
            stage.close();
        }
    }
    
    @FXML
    private void register(ActionEvent event) throws IOException{
        
        // It should be able to close or disable main
        // screen when opening register window.
        // Still a task to do or to be added.
        Stage stage = new Stage();
        
        stage.getIcons().add(new Image("/resources/icon.jpg"));
        stage.setTitle("Register now");
        stage.setResizable(false);
        
        stage.initStyle(StageStyle.UNDECORATED);
        Parent root = FXMLLoader.load(getClass().getResource("FXMLRegister.fxml"));

        // Let you drag the window pressing on any space.
        // This code is setted on diferent window-display methods.
        root.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                xOffset = event.getSceneX();
                yOffset = event.getSceneY();
            }
        });
        root.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                stage.setX(event.getScreenX() - xOffset);
                stage.setY(event.getScreenY() - yOffset);
            }
        });

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    
    private void feed(ActionEvent event, String username) throws IOException{
        
        Stage stage = new Stage();
        stage.getIcons().add(new Image("/resources/icon.jpg"));
        stage.setTitle(username + " feed");
        stage.setResizable(false);
        
        stage.initStyle(StageStyle.UNDECORATED);
        Parent root = FXMLLoader.load(getClass().getResource("FXMLFeed.fxml"));

        root.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                xOffset = event.getSceneX();
                yOffset = event.getSceneY();
            }
        });
        root.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                stage.setX(event.getScreenX() - xOffset);
                stage.setY(event.getScreenY() - yOffset);
            }
        });

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();  
    }
    
    @FXML
    private void exit(ActionEvent event) {
        Stage stage = (Stage) btnClose.getScene().getWindow();
        stage.close();
    }
    
    @FXML
    private void logout(ActionEvent event) throws IOException{
        actualUser = null;
        Stage cStage = new Stage();
        Stage stage = new Stage();
        stage.getIcons().add(new Image("/resources/icon.jpg"));
        cStage = (Stage) btnLogout.getScene().getWindow();
        cStage.close();
        stage.setTitle("Thundermail");
        stage.setResizable(false);
        
        stage.initStyle(StageStyle.UNDECORATED);
        Parent root = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));

        root.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                xOffset = event.getSceneX();
                yOffset = event.getSceneY();
            }
        });
        root.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                stage.setX(event.getScreenX() - xOffset);
                stage.setY(event.getScreenY() - yOffset);
            }
        });

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    
    @FXML
    private void showSettings(ActionEvent event){
        
        anchorSettings.setVisible(true);
        anchorUsers.setVisible(false);
        
        txtSettingsPass.setText(actualUser.getPassword());
        txtSettingsName.setText(actualUser.getUsername());
        txtSettingsEmail.setText(actualUser.getEmail());
        
        if(actualUser.isAdmin()){
            lblSettingsAdmin.setText("YES");
        } else {
            lblSettingsAdmin.setText("NO");
        }
    }
    
    @FXML
    private void saveSettings(ActionEvent event){
        
        String plength = txtSettingsPass.getText();
        if(!txtSettingsEmail.getText().contains("@") && !txtSettingsEmail.getText().contains(".com") && !txtSettingsEmail.getText().contains(".es") && !txtSettingsEmail.getText().contains(".net") && !txtSettingsEmail.getText().contains(".")){
            lblSettingsStatus.setTextFill(Color.web("#aa0000"));
            lblSettingsStatus.setText("Enter a valid email");
        }else if(plength.length() > 10){
            lblSettingsStatus.setTextFill(Color.web("#aa0000"));
            lblSettingsStatus.setText("Password max. is 8 characters.");
        }else{
            Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle("Save settings");
            alert.setHeaderText("Are you ok with this?");
            alert.setContentText("All your credentials will change.");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK){
                // ... user choose OK
                actualUser.setPassword(txtSettingsPass.getText());
                actualUser.setEmail(txtSettingsEmail.getText());
                actualUser.setUsername(txtSettingsName.getText());

                lblSettingsStatus.setText("All changes have been saved");
                lblSettingsStatus.setTextFill(Color.web("#008206"));
            } else {
                // ... user choose CANCEL or closed the dialog
            }
            
        }
    }
    
    @FXML
    private void showSettingsPassword(ActionEvent event){
        
        lblSettingsShowPass.setText(txtSettingsPass.getText());
        if(txtSettingsPass.getText().length() > 10){
            lblSettingsShowPass.setText("Too long to display.");
        } else {
            if(!lblSettingsShowPass.isVisible()){
                lblSettingsShowPass.setVisible(true);
            } else {
                lblSettingsShowPass.setVisible(false);
            }
        }
    }
    
    @FXML
    private void showUsers(ActionEvent event){
        
        chargeUsers();
        anchorSettings.setVisible(false);
        anchorUsers.setVisible(true);
        
        if(!actualUser.isAdmin()){
            anchorUsers.setDisable(true);
        }
        
    }
    
    private void chargeUsers(){

        if(!usersLoaded){
            int index = 0;
            tcolumnUsername.setCellValueFactory(new PropertyValueFactory<>("username"));
            tcolumnEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
            tcolumnPass.setCellValueFactory(new PropertyValueFactory<>("password"));
            for(int x = 1; x <= users.size(); x++){
                tableUsers.getItems().add(users.get(index));
                index++;
            }
        }

        usersLoaded = true;
        
    }
    
    @FXML
    private void refreshTable(ActionEvent event){
        
        tableUsers.refresh();
    }
    
    @FXML
    private void deleteUser(ActionEvent event) throws IOException{
        
        int index = 0;
        LinkedList<User> ls = users;
        
        for(int x = 1; x <= ls.size(); x++){
            if(tableUsers.getSelectionModel().getSelectedItem().getUsername().equals(ls.get(index).getUsername()) && !tableUsers.getSelectionModel().getSelectedItem().getUsername().equals(actualUser.getUsername())){
                ls.remove(index);
                tableUsers.getItems().removeAll(tableUsers.getSelectionModel().getSelectedItem());
                lblUsersWarn.setTextFill(Color.web("#008206"));
                lblUsersWarn.setText("User have been deleted");
            } else if (tableUsers.getSelectionModel().getSelectedItem().getUsername().equals(actualUser.getUsername())){
                Alert alert = new Alert(AlertType.CONFIRMATION);
                alert.setTitle("Delete this user");
                alert.setHeaderText("Are you ok with this?");
                alert.setContentText("If you delete this user you'll be "
                        + "logged out.");

                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK){
                    // ... user chose OK
                    ls.remove(index);
                    logout(event);
                } else {
                    // ... user chose CANCEL or closed the dialog
                }
                
            }
            index++;
        }
    }
    
}
