package com.example.bankingSystem;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;

import java.io.*;
import java.net.URL;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.ResourceBundle;
public class HelloController implements Initializable {
    @FXML
    private TableView<Account> accountTable=new TableView<>();

    @FXML
    private TableColumn<Account, String> idcol=new TableColumn<>();

    @FXML
    private TableColumn<Account, String>namecol=new TableColumn<>();

    @FXML
    private TableColumn<Account, String> passcol=new TableColumn<>();

    @FXML
    private TableColumn<Account, String> rolecol=new TableColumn<>();

    @FXML
    private TableColumn<Account, String> usernamecol=new TableColumn<>();

    @FXML
    Label userNameerr;
    @FXML
    Label label;
    @FXML
    Label passError;
    @FXML
    private Label nameerror;
    @FXML
    private Label idError;
    @FXML
    private TextField txtID;
    @FXML
    private TextField txtsearch;
    @FXML
    private TextField txtbalance;
    @FXML
    private TextField txtaccnumber;

    @FXML
    private TextField txtname;

    @FXML
    private TextField txtUsername;

    @FXML
    private TextField txtpassword;
    @FXML
    private TextField txtdeposit=new TextField();

    @FXML
    private ComboBox<String> role=new ComboBox<>();
    @FXML
    private ComboBox<String> roleempcu=new ComboBox<>();

    String rolee="superadmin";
    String rolead="employee";
    ArrayList<String> drop=new ArrayList<>();
    ArrayList<String> dropad=new ArrayList<>();
    @FXML
    private Button txtregister;
    @FXML
     AnchorPane diplay;
    ObservableList<Account> data = FXCollections.observableArrayList();
    DataBaseConnection connection=new DataBaseConnection();
    Connection con=connection.getConnection();
    private boolean t=false;
    private String user="";
    private String userId;
    @FXML
    private Label superlabel=new Label();
    @FXML
    private TextField txtaddress;
    private String logedPerson;
    @FXML
    private TextField txtamount=new TextField();
    @FXML
    private TextField txtto;
    @FXML
    private TextField adderror;

    public HelloController() throws SQLException, ClassNotFoundException {
    }




    @FXML
 void Back(ActionEvent event) throws IOException {
     FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("LoginPage.fxml"));
     Scene scene = new Scene(fxmlLoader.load());
     HelloApplication.stage.setTitle("Login Page!");
     HelloApplication.stage.setScene(scene);
 }
    @FXML
    void register(ActionEvent event) throws SQLException, ClassNotFoundException, IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Create.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        HelloApplication.stage.setTitle("Create Account!");
        HelloApplication.stage.setScene(scene);
        drop.add("admin");
        drop.add("customer");
        drop.add("superadmin");
        drop.add("employee");
        dropad.add("customer");
        dropad.add("employee");

        role.getItems().addAll(drop);
        roleempcu.getItems().addAll(dropad);

        System.out.println(role.getValue());

    }
    public  void  CreateAccount(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("CreateAccount.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        HelloApplication.stage.setTitle("Hello!");
        HelloApplication.stage.setScene(scene);    }
    public  void  CreateAccountempcu(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("CreateAccountForEmployeAndUser.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        HelloApplication.stage.setTitle("Hello!");
        HelloApplication.stage.setScene(scene);    }
    public  void  backToSuper(ActionEvent event) throws IOException, SQLException {
        String sql2 = "select role from admin where id =?";
        String c2 = readLogedAccount();
        c2=c2.trim();
        PreparedStatement st2 = con.prepareStatement(sql2);
        st2.setString(1, c2);
        ResultSet rs2 = st2.executeQuery();
        while (rs2.next()) {
            System.out.println("employee");

            if (rs2.getString("role").equals("superadmin")) {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("SuperAdmin.fxml"));
                Scene scene = new Scene(fxmlLoader.load());
                HelloApplication.stage.setTitle("Login Successfully!");
                HelloApplication.stage.setScene(scene);
            } else if (rs2.getString("role").equals("admin")) {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Admin.fxml"));
                Scene scene = new Scene(fxmlLoader.load());
                HelloApplication.stage.setTitle("Hello!");
                HelloApplication.stage.setScene(scene);
            }
        }

        //.....................................
        }
    public  void  backToAdmin(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Admin.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        HelloApplication.stage.setTitle("Hello!");
        HelloApplication.stage.setScene(scene);    }

    public  void updateAccount(ActionEvent event) throws SQLException {
        String sql="UPDATE admin SET password=?,name=?,address=? where id='"+ txtsearch.getText()+"'";
        PreparedStatement prep = con.prepareStatement(sql);
        prep.setString(1, txtpassword.getText());
        prep.setString(2, txtname.getText());
        prep.setString(3, txtaddress.getText());
        prep.executeUpdate();
        String sql1="UPDATE employee SET password=?,name=?,address=? where id='"+ txtsearch.getText()+"'";
        PreparedStatement prep2 = con.prepareStatement(sql1);
        prep2.setString(1, txtpassword.getText());
        prep2.setString(2, txtname.getText());
        prep2.setString(3, txtaddress.getText());
        prep2.executeUpdate();
        txtpassword.clear();
        txtname.clear();
        txtaddress.clear();
        txtID.clear();
    }
    public  void deleteAccount(ActionEvent event) throws SQLException {


        String sql="delete from super_admin where id=?";

        PreparedStatement prep = con.prepareStatement(sql);
        prep.setString(1, txtsearch.getText());
        prep.executeUpdate();

        String sql2="delete from admin where id=?";

        PreparedStatement prep2 = con.prepareStatement(sql2);
        prep2.setString(1, txtsearch.getText());
        prep2.executeUpdate();
        String sql3="delete from employee where id=?";

        PreparedStatement prep3= con.prepareStatement(sql3);
        prep3.setString(1, txtsearch.getText());
        prep3.executeUpdate();
        txtID.setText("");
        txtpassword.setText("");
        txtaddress.setText("");
        txtname.setText("");
    }

    public  void transfer(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Transfer.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        HelloApplication.stage.setTitle("Hello!");
        HelloApplication.stage.setScene(scene);

    }

    public  void search(ActionEvent event) throws SQLException {

        String id = "",name = null,address = null,password = null,rolew = null;
        String sql="select *from super_admin where id=?";
        PreparedStatement stmt=con.prepareStatement(sql);
        stmt.setString(1,txtsearch.getText());
        ResultSet re=stmt.executeQuery();
        while (re.next()){
                  id =re.getString("id");
                  name=re.getString("name");
                   address=re.getString("address");
                  password=re.getString("password");
                  rolew=re.getString("role");

             }

        String sql2="select *from admin where id=?";
        PreparedStatement stmt2=con.prepareStatement(sql2);
        stmt2.setString(1,txtsearch.getText());
        ResultSet re2=stmt2.executeQuery();
        while (re2.next()){
             id =re2.getString("id");
             name=re2.getString("name");
              address=re2.getString("address");
             password=re2.getString("password");
            rolew=re2.getString("role");

        }  String sql3="select *from employee where id=?";
        PreparedStatement stmt3=con.prepareStatement(sql3);
        stmt3.setString(1,txtsearch.getText());
        ResultSet re3=stmt.executeQuery();
        while (re3.next()){
             id =re3.getString("id");
             name=re3.getString("name");
              address=re3.getString("address");
             password=re3.getString("password");
            rolew=re3.getString("role");

        }

                 txtID.setText(id);
                 txtpassword.setText(password);
                 txtaddress.setText(address);
                 txtname.setText(name);

    }

    @FXML
    void  createAccount() throws SQLException, ClassNotFoundException, IOException {
        boolean n,d,p,e,a;
        e=n=p=d=a=true;


         if (txtpassword.getText().isBlank()){
             p=false;
             passError.setText("Password is Required");

         }

         if (!txtpassword.getText().isBlank() && txtpassword.getText().length()<8){
             p=false;
             passError.setText("password should at least 8 words");

         }

        if(txtname.getText().isBlank()){
            nameerror.setText("Name is required");
            n=false;
        }


        if (txtID.getText().isBlank()){
            d=false;
            idError.setText("ID is Required");

        }


        if(!txtname.getText().isBlank()){
            nameerror.setText("");
            n=true;
        }
        if(!txtID.getText().isBlank()){
            idError.setText("");
            d=true;
        }

        if (!txtpassword.getText().isBlank() && txtpassword.getText().length()>8){
            p=true;
            passError.setText("");

        }

        String sql2="select *from admin where role=?";
        PreparedStatement pst=con.prepareStatement(sql2);
        pst.setString(1,"superadmin");
        ResultSet re1=pst.executeQuery();
        boolean superadmin=false;
        while (re1.next()){
            if (re1.getString("role").equals("superadmin")){
                superadmin=true;
                System.out.println(re1.getString("role").equals("superadmin"));
            }
        }

        if (superadmin) {
            if (e && n && d && p && rolee != "superadmin") {
                if (rolee.equals("customer")) {
                    String sql3 ="select count(*) from customer where id=?";
                    PreparedStatement statement=con.prepareStatement(sql3);
                     statement.setString(1,txtID.getText());
                     ResultSet resultSet=statement.executeQuery();
                     while (resultSet.next()){
                         if (resultSet.getInt(1)<1) {
                             String sql = "INSERT INTO customer  (id,name,address,accountNumber,deposit,password,role) VALUES(?,?,?,?,?,?,?)";
                             PreparedStatement prep = con.prepareStatement(sql);
                             prep.setString(1, txtID.getText());
                             prep.setString(2, txtname.getText());
                             prep.setString(3, txtaddress.getText());
                             prep.setString(4, txtaccnumber.getText());
                             prep.setDouble(5, Double.parseDouble(txtdeposit.getText()));
                             prep.setString(6, txtpassword.getText());
                             prep.setString(7, rolee);
                             prep.executeUpdate();
                             Alert alert = new Alert(Alert.AlertType.INFORMATION);
                             alert.setTitle("Information Dialog");
                             alert.setHeaderText("Login Page");
                             alert.setContentText("You successfully registered !!!");
                             alert.showAndWait();
                             System.out.println(user == "superadmin");
                         }
                         else {
                             Alert alert = new Alert(Alert.AlertType.WARNING);
                             alert.setTitle("Already exist");
                             alert.setHeaderText("Double Registration is not allowed");
                             alert.setContentText("The user is already exist!!!");
                             alert.showAndWait();
                             System.out.println(user == "superadmin");
                         }
                }}
                else{
                    String sql3 ="select count(*) from admin where id=?";
                    PreparedStatement statement=con.prepareStatement(sql3);
                    statement.setString(1,txtID.getText());
                    ResultSet resultSet=statement.executeQuery();
                    while (resultSet.next()){
                        if (resultSet.getInt(1)<1) {
                    String sql = "INSERT INTO admin (id,name,address,password,role) VALUES(?,?,?,?,?)";
                    PreparedStatement prep = con.prepareStatement(sql);
                    prep.setString(1, txtID.getText());
                    prep.setString(2, txtname.getText());
                    prep.setString(3, txtaddress.getText());
                    prep.setString(4, txtpassword.getText());
                    prep.setString(5, rolee);
                    prep.executeUpdate();
                            Alert alert = new Alert(Alert.AlertType.INFORMATION);
                            alert.setTitle("Information Dialog");
                            alert.setHeaderText("Login Page");
                            alert.setContentText("You successfully registered !!!");
                            alert.showAndWait();
                            System.out.println(user == "superadmin");
                }
                 else {
                            Alert alert = new Alert(Alert.AlertType.WARNING);
                            alert.setTitle("Already exist");
                            alert.setHeaderText("Double Registration is not allowed");
                            alert.setContentText("The user is already exist!!!");
                            alert.showAndWait();
                            System.out.println(user == "superadmin");
                 }
                    }}
//                if (rolee.equals("employee")) {
//                    String sql = "INSERT INTO employee  (id,name,address,password,role) VALUES(?,?,?,?,?)";
//                    PreparedStatement prep = con.prepareStatement(sql);
//                    prep.setString(1, txtID.getText());
//                    prep.setString(2, txtname.getText());
//                    prep.setString(3, txtaddress.getText());
//                    prep.setString(4, txtpassword.getText());
//                    prep.setString(5, rolee);
//                    prep.executeUpdate();
//                }


//                String sql11 = "INSERT INTO user (name,id) VALUES(?,?)";
//                PreparedStatement prep11 = con.prepareStatement(sql11);
//                prep11.setString(1, txtname.getText());
//                prep11.setString(2, txtID.getText());
//                prep11.executeUpdate();
                Date now = new Date(System.currentTimeMillis());
                // Format the date and time using a SimpleDateFormat
                SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
                String formattedDate = formatter.format(now);
                String sqltr = "INSERT INTO transaction (user_id,devent,tevent) VALUES(?,?,?)";
                PreparedStatement preptr = con.prepareStatement(sqltr);
                preptr.setString(1, txtID.getText());
                preptr.setString(2, formattedDate);
                preptr.setString(3, "create account");
                preptr.executeUpdate();



                write_read();
                txtname.clear();
                txtID.clear();
                txtpassword.clear();



            }
        }
        else {
            String sql = "INSERT INTO admin  (id,name,address,password,role) VALUES(?,?,?,?,?)";
            PreparedStatement prep = con.prepareStatement(sql);
            prep.setString(1, txtID.getText());
            prep.setString(2, txtname.getText());
            prep.setString(3, txtaddress.getText());
            prep.setString(4, txtpassword.getText());
            prep.setString(5, rolee);
            prep.executeUpdate();

            Date now = new Date(System.currentTimeMillis());

            // Print the current date and time
            System.out.println(now);
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
            String formattedDate = formatter.format(now);
            String sqltr = "INSERT INTO transaction (user_id,devent,tevent) VALUES(?,?,?)";
            PreparedStatement preptr = con.prepareStatement(sqltr);
            preptr.setString(1, txtID.getText());
            preptr.setString(2, formattedDate);
            preptr.setString(3, "create account");
            preptr.executeUpdate();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information Dialog");
            alert.setHeaderText("Login Page");
            alert.setContentText("You successfully registered !!!");
            alert.showAndWait();
            write_read();
            txtname.clear();
            txtID.clear();
            txtpassword.clear();
            txtaddress.clear();


        }

 }

    @FXML
    void login(ActionEvent event) throws SQLException, ClassNotFoundException, IOException {

        boolean n,d,p,e;
        e=n=p=d=true;

        if(txtUsername.getText().isBlank()){
            userNameerr.setText("userName is required");
            e=false;
        }


        if (txtpassword.getText().isBlank()){
            p=false;
            passError.setText("Password is Required");

        }
        if (!txtpassword.getText().isBlank() && txtpassword.getText().length()<8){
            p=false;
            passError.setText("Password should contian at least 8 words");

        }
        if(!txtUsername.getText().isBlank()){
            userNameerr.setText("");
            e=true;

        }


        if(!txtUsername.getText().isBlank()){
            userNameerr.setText("");
            e=true;
        }



        if (!txtpassword.getText().isBlank() && txtpassword.getText().length()>8){
            p=true;
            passError.setText("");

        }

//        String sql = "SELECT COUNT(1) FROM FXsql where password =? and userName=?";
//        PreparedStatement statement=con.prepareStatement(sql);
//        statement.setString(1,txtpassword.getText());
//        statement.setString(2,txtUsername.getText());
//        ResultSet res = statement.executeQuery();
        String sql2="select *from admin where role=?";
        PreparedStatement pst=con.prepareStatement(sql2);
        pst.setString(1,"superadmin");
        ResultSet re1=pst.executeQuery();
        boolean superadmin=false;
        while (re1.next()){
            if (re1.getString("role").equals("superadmin")){
                superadmin=true;
                System.out.println(re1.getString("role").equals("superadmin"));
            }
        }
       if (superadmin){
//           String sql3="select *from super_admin where password =? and name=?";
//           PreparedStatement statement3=con.prepareStatement(sql3);
//           statement3.setString(1,txtpassword.getText());
//           statement3.setString(2,txtUsername.getText());
//           ResultSet re3=statement3.executeQuery();
//           while (re3.next()) {
//               if (!re3.getString("role").isBlank())
//                   logedPerson = re3.getString("role");
//               userId=re3.getString("id");
//           }

           String sql4="select *from admin where password =? and name=?";
           PreparedStatement statement4=con.prepareStatement(sql4);
           statement4.setString(1,txtpassword.getText());
           statement4.setString(2,txtUsername.getText());
           ResultSet re4=statement4.executeQuery();
           while (re4.next()) {
               if (!re4.getString("role").isBlank())
                   logedPerson = re4.getString("role");
                   userId=re4.getString("id");

           }
           String sql5="select *from customer where password =? and name=?";
           PreparedStatement statement5=con.prepareStatement(sql5);
           statement5.setString(1,txtpassword.getText());
           statement5.setString(2,txtUsername.getText());
           ResultSet re5=statement5.executeQuery();
           while (re5.next()) {
               if (!re5.getString("role").isBlank())
                   logedPerson = re5.getString("role");
               userId=re5.getString("id");

           }

//           while (res.next()) {
//            if (res.getInt(1) ==1) {
//                String sql1 = "SELECT *FROM FXsql where password =? and userName=?";
//                PreparedStatement statement1=con.prepareStatement(sql1);
//                statement1.setString(1,txtpassword.getText());
//                statement1.setString(2,txtUsername.getText());
//                ResultSet res1 = statement1.executeQuery();
//
//                while (res1.next()) {
//                    userId=res1.getString("account_id");
                    if (logedPerson.equals("admin")) {
                        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Admin.fxml"));
                        Scene scene = new Scene(fxmlLoader.load());
                        HelloApplication.stage.setTitle("Login Successfully!");
                        HelloApplication.stage.setScene(scene);
                        user=logedPerson;
                        System.out.println("user l"+user);
                        scene.getRoot().layout();
                    } else if (logedPerson.equals("superadmin")) {
                        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("SuperAdmin.fxml"));
                        Scene scene = new Scene(fxmlLoader.load());
                        HelloApplication.stage.setTitle("Login Successfully!");
                        HelloApplication.stage.setScene(scene);
                        user=logedPerson;
                        System.out.println("user l"+user);
                        scene.getRoot().layout();

                    }
                    else if (logedPerson.equals("employee")) {
                        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Empoyee.fxml"));
                        Scene scene = new Scene(fxmlLoader.load());
                        HelloApplication.stage.setTitle("Login Successfully!");
                        HelloApplication.stage.setScene(scene);
                        user=logedPerson;
                        scene.getRoot().layout();
                    }
                    else if (logedPerson.equals("customer")) {
                        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Customer.fxml"));
                        Scene scene = new Scene(fxmlLoader.load());
                        HelloApplication.stage.setTitle("Login Successfully!");
                        HelloApplication.stage.setScene(scene);
                        user=logedPerson;
                        scene.getRoot().layout();
                    }
                    BufferedWriter writer=new BufferedWriter(new FileWriter("account.txt"));
                    writer.write(userId);
                    writer.close();
           Date now = new Date(System.currentTimeMillis());

           SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
           String formattedDate = formatter.format(now);
           String sqltr= "INSERT INTO transaction (user_id,devent,tevent) VALUES(?,?,?)";
           PreparedStatement preptr = con.prepareStatement(sqltr);
           preptr.setString(1, userId);
           preptr.setString(2, formattedDate);
           preptr.setString(3, "log in");
           preptr.executeUpdate();

                }









        else {
           Alert alert = new Alert(Alert.AlertType.INFORMATION);
           alert.setTitle("Information Dialog");
           alert.setHeaderText("You don't have a super Admin ");
           alert.setContentText("please create super admin first !!!");
           alert.showAndWait();
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("CreateSuperAdmin.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            HelloApplication.stage.setTitle("Create Account!");
            HelloApplication.stage.setScene(scene);
        }
        write_read();
    }
