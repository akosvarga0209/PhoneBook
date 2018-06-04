
package phonebook;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class DB {
    
    final String URL ="jdbc:derby:sampleDB;create=true";;
    final String USERNAME ="";;
    final String PASSWORD ="";;
    
    Connection conn=null;
    Statement createStatement = null;
    DatabaseMetaData dbmd = null;
    ResultSet rs1 = null;
    
    
    public DB() {
        
        
        try {
            conn = DriverManager.getConnection(URL);
            System.out.println("A hid létrejött");
        } catch (SQLException ex) {
             System.out.println("Valami baj van a kapcsolat lérehosánál");
            System.out.println(""+ex);
  
        }
        
        if (conn!=null){
            try {
               createStatement = conn.createStatement();
            } catch (SQLException ex) {
                 System.out.println("Valami baj van a createStatement létrehozásnál");
            System.out.println(""+ex);
            }
        }
        
        
        try {
            dbmd = conn.getMetaData();
        } catch (SQLException ex) {
            System.out.println("Valami baj van a Meteadat létrehozásánál");
            System.out.println(""+ex);
        }
         
        try {
            rs1 = dbmd.getTables(null, "APP", "CONTACTS", null);
            if(!rs1.next()){
              createStatement.execute("create table contacts(id INT not null primary key GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),lastname varchar(20), firstname varchar(20), email varchar(30))");  
        }
            
        } catch (SQLException ex) {
             System.out.println("Valami baj van az adatbázis létrehozásakor");
            System.out.println(""+ex);
        }

    }
     public ArrayList<Person> getAllContacts(){
          String sql = "select * from contacts";
           ArrayList<Person> users = null;
        try {
            ResultSet rs2 = createStatement.executeQuery(sql);
            users = new ArrayList<>();
            
            while(rs2.next()){
               
                Person person = new Person(rs2.getInt("id"),rs2.getString("lastname"),rs2.getString("firstname"),rs2.getString("email"));
                users.add(person);
                
            }
        } catch (SQLException ex) {
            System.out.println("Valami baj van az userek lekérésnél(arraylist)");
            System.out.println(""+ex);
        }
         
         return users;
     }
     
     public void addContact (Person person){
         
         try {
              String sql = "insert into contacts(lastname,firstname,email) values (?,?,?)";
              PreparedStatement preparedStatement = conn.prepareStatement(sql);
              preparedStatement.setString(1, person.getLastName());
              preparedStatement.setString(2, person.getFirstName());
              preparedStatement.setString(3, person.getEmail());
              preparedStatement.execute();
        } catch (SQLException ex) {
            System.out.println("Valami baj van a contact hozzáadasakkor(user objektum)");
            System.out.println(""+ex);
        }
     }
     
     public void updateContact (Person person){
         
         try {
              String sql = "update contacts set lastname = ?, firstname = ?, email = ? where id = ?";
              PreparedStatement preparedStatement = conn.prepareStatement(sql);
              preparedStatement.setString(1, person.getLastName());
              preparedStatement.setString(2, person.getFirstName());
              preparedStatement.setString(3, person.getEmail());
              preparedStatement.setInt(4, Integer.parseInt(person.getId()));
              preparedStatement.execute();
        } catch (SQLException ex) {
            System.out.println("Valami baj van a contact hozzáadasakkor(user objektum)");
            System.out.println(""+ex);
        }
     }
    
     
     public void removeContacts (Person person){
         
         try {
              String sql = "delete from contacts where id = ?";
              PreparedStatement preparedStatement = conn.prepareStatement(sql);;
              preparedStatement.setInt(1, Integer.parseInt(person.getId()));
              preparedStatement.execute();
        } catch (SQLException ex) {
            System.out.println("Valami baj van a contact törlésekor");
            System.out.println(""+ex);
        }
     }
     
     
     
    
}
