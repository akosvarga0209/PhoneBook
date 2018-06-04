
package phonebook;

import com.itextpdf.text.Document;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.FileOutputStream;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.util.Callback;


public class ViewController implements Initializable {
    
    DB db = new DB();

    
    @FXML
    TableView table;
    @FXML
    TextField inputLastname;
    @FXML
    TextField inputFirstname;
    @FXML
    TextField inputEmail;
    @FXML
    Button addNewContentButton;
    @FXML
    Pane menuPane;
    @FXML
    Pane contactPane;
    @FXML
    Pane exportPane;
    @FXML
    Button exportButton;
    @FXML
    AnchorPane anchor;
    @FXML
    SplitPane mainSplit;     
    @FXML
    TextField inputExport;
    
    private final String MENU_CONTACTS = "Kontaktok";
    private final String MENU_LIST = "Lista";
    private final String MENU_EXPORT = "Exportálás";
    private final String MENU_EXIT = "Kilpés";
    
    
    
    private final ObservableList<Person> data = FXCollections.observableArrayList();
    
    
    
 
    public void setTableData(){
        TableColumn lastNameCo1 = new TableColumn("Vezetéknév");
        lastNameCo1.setMinWidth(130);
        lastNameCo1.setCellFactory(TextFieldTableCell.forTableColumn());
        lastNameCo1.setCellValueFactory(new PropertyValueFactory<Person, String>("lastName"));
        
        lastNameCo1.setOnEditCommit(
                new EventHandler<TableColumn.CellEditEvent<Person,String>>(){
                    
                    @Override
                    public void handle(TableColumn.CellEditEvent<Person,String> t){
                       Person actualPerson = (Person)t.getTableView().getItems().get(t.getTablePosition().getRow());
                       actualPerson.setLastName(t.getNewValue());
                       db.updateContact(actualPerson);
                    }
                    
                }
        );
        
        
    
        TableColumn firstNameCol = new TableColumn("Keresztnév");
        firstNameCol.setMinWidth(130);
        firstNameCol.setCellFactory(TextFieldTableCell.forTableColumn());
        firstNameCol.setCellValueFactory(new PropertyValueFactory<Person, String>("firstName"));      
        firstNameCol.setOnEditCommit(
         new EventHandler<TableColumn.CellEditEvent<Person,String>>(){
            @Override
            
            public void handle(TableColumn.CellEditEvent<Person, String> t) {
              Person actualPerson = (Person)t.getTableView().getItems().get(t.getTablePosition().getRow());
                       actualPerson.setFirstName(t.getNewValue());
                       db.updateContact(actualPerson);
            }        
         }
        );
        
        TableColumn emailCo1 = new TableColumn("email");
        emailCo1.setMinWidth(100);
        emailCo1.setCellFactory(TextFieldTableCell.forTableColumn());
        emailCo1.setCellValueFactory(new PropertyValueFactory<Person, String>("email"));
        
        emailCo1.setOnEditCommit(
          new EventHandler<TableColumn.CellEditEvent<Person,String>>(){
            @Override
            public void handle(TableColumn.CellEditEvent<Person, String> t) {
              Person actualPerson = (Person)t.getTableView().getItems().get(t.getTablePosition().getRow());
                       actualPerson.setEmail(t.getNewValue());
                       db.updateContact(actualPerson);
            }   
          } 
        );   
        
        TableColumn removeCol = new TableColumn( "Törlés" );
        

        Callback<TableColumn<Person, String>, TableCell<Person, String>> cellFactory = 
                new Callback<TableColumn<Person, String>, TableCell<Person, String>>()
                {
                    @Override
                    public TableCell call( final TableColumn<Person, String> param )
                    {
                        final TableCell<Person, String> cell = new TableCell<Person, String>()
                        {   
                            final Button btn = new Button( "Törlés" );

                            @Override
                            public void updateItem( String item, boolean empty )
                            {
                                super.updateItem( item, empty );
                                if ( empty )
                                {
                                    setGraphic( null );
                                    setText( null );
                                }
                                else
                                {
                                    btn.setOnAction( ( ActionEvent event ) ->
                                            {
                                                Person person = getTableView().getItems().get( getIndex() );
                                                data.remove(person);
                                                db.removeContacts(person);
                                       } );
                                    setGraphic( btn );
                                    setText( null );
                                }
                            }
                        };
                        return cell;
                    }
                };

        removeCol.setCellFactory( cellFactory );
    
        
        table.getColumns().addAll(lastNameCo1,firstNameCol,emailCo1,removeCol);
        
        data.addAll(db.getAllContacts());
        table.setItems(data);
    }  
    
    private void setMenuData() {
       
        TreeItem<String> treeItemRoot1 = new TreeItem<>("Menü");
        TreeView<String> treeView = new TreeView<>(treeItemRoot1);  //Fa létrehozása, ehhez kell 1 elem-ez lesz a root
        treeView.setShowRoot(false);
        
        TreeItem<String> nodeItemA = new TreeItem<>(MENU_CONTACTS);
        TreeItem<String> nodeItemB = new TreeItem<>(MENU_EXIT);
        
//        nodeItemA.setExpanded(true);
        
        Node contactsNode = new ImageView(new Image(getClass().getResourceAsStream("/contacts.png")));
        Node exportNode = new ImageView(new Image(getClass().getResourceAsStream("/export.png")));
        TreeItem<String> nodeItemA1 = new TreeItem<>(MENU_LIST,contactsNode);
        TreeItem<String> nodeItemA2 = new TreeItem<>(MENU_EXPORT,exportNode);
        
        nodeItemA.getChildren().addAll(nodeItemA1,nodeItemA2);
        
        treeItemRoot1.getChildren().addAll(nodeItemA,nodeItemB);
        
        menuPane.getChildren().add(treeView);
        
       treeView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener(){
        public void changed(ObservableValue observable, Object oldValue, Object newValue){
            TreeItem<String> selectedItem = (TreeItem<String>) newValue;
            String selectedMenu;
            selectedMenu=selectedItem.getValue();
            selectedItem.setExpanded(true);
            if (null!=selectedMenu) {
                switch (selectedMenu){
                    case MENU_CONTACTS:
                        try {
                            selectedItem.setExpanded(true);
                        }catch(Exception e) { 
                        }
                        break;
                        
                     case MENU_LIST:
                        try {
                            contactPane.setVisible(true);
                            exportPane.setVisible(false);
                        }catch(Exception e) { 
                        }
                        break;
                        
                     case MENU_EXPORT:
                        try {
                            contactPane.setVisible(false);
                            exportPane.setVisible(true);
                        }catch(Exception e) { 
                        }
                        break;
                    
                        
                    case MENU_EXIT:
                        System.exit(0);
                        break;
                }
            }
        } 
    });
    
 }
    
    @FXML
    private void addContact(ActionEvent event){
        String email = inputEmail.getText();
        if (email.length()>3 && email.contains("@") && email.contains(".")) {
        System.out.println("Button clicked");
        Person newPerson = new Person(inputLastname.getText(),inputFirstname.getText(),email);
        data.add(newPerson);
        db.addContact(newPerson);
        
        inputLastname.clear();
        inputFirstname.clear();
        inputEmail.clear();
      } else {
            alert("Adj meg 1 valódi email-címet!");
        }
    }
    
    @FXML
    private void exPortPDf(ActionEvent event) {
        
         String fileNev = inputExport.getText();
         fileNev = fileNev.replaceAll("\\s", "");
        if (fileNev != null && !fileNev.equals("")) {
            PDfGeneration pdfCreator = new PDfGeneration();
            pdfCreator.pdfGeneration(fileNev, data);
        } else {
            alert("Adj meg egy file-nevet!");
        }
   }
        
    private void alert(String text){
        mainSplit.setDisable(true);
        mainSplit.setOpacity(0.4);
        
        Label label = new Label (text);
        Button alertButton = new Button("OK");
        VBox vbox = new VBox(label,alertButton);
        vbox.setAlignment(Pos.CENTER);
        
        
        alertButton.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event) {
                mainSplit.setDisable(false);
                mainSplit.setOpacity(1);
                vbox.setVisible(false);
            }
        
        
        
        
        
    });
        
        anchor.getChildren().add(vbox);
        anchor.setTopAnchor(vbox, 300.0);
        anchor.setLeftAnchor(vbox,300.0);
        
        
    }
    
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {

         setTableData();
         setMenuData();
 
         
    
    }


}
    

