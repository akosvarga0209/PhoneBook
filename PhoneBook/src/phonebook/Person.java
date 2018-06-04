
package phonebook;

import javafx.beans.property.SimpleStringProperty;


public class Person {


    private final SimpleStringProperty firstName;
    private final SimpleStringProperty lastName;
    private final SimpleStringProperty email;
    private final SimpleStringProperty id;

    
    
    public Person(){
        this.firstName = new SimpleStringProperty("");
        this.lastName = new SimpleStringProperty("");
        this.email = new SimpleStringProperty("");
        this.id = new SimpleStringProperty("");
         
    }
    
    public Person(String lname, String fname, String email){
        this.firstName = new SimpleStringProperty(fname);
        this.lastName = new SimpleStringProperty(lname);
        this.email = new SimpleStringProperty(email);
        this.id = new SimpleStringProperty("");
         
    }
    public Person(Integer id, String lname, String fname, String email){
        this.firstName = new SimpleStringProperty(fname);
        this.lastName = new SimpleStringProperty(lname);
        this.email = new SimpleStringProperty(email);
        this.id = new SimpleStringProperty(String.valueOf(id));
         
    }
    
    public String getFirstName() {
        return firstName.get();
    }
    
    public void setFirstName (String fName) {
        this.firstName.set(fName);
    }
    

    public String getLastName() {
        return lastName.get();
    }
    
    public void setLastName (String lName) {
    this.lastName.set(lName);
    }
    

    public String getEmail() {
        return email.get();
    }
    
    public void setEmail (String email) {
    this.email.set(email);
    }
    
    public String getId() {
        return id.get();
    }
    
    public void setId(String fId){
        
        id.set(fId);
    }
    
    
}
