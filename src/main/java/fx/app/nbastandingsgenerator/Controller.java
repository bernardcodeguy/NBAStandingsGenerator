package fx.app.nbastandingsgenerator;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    @FXML
    private ComboBox<String> cbxConference;
    @FXML
    private ProgressIndicator progressIn;
    @FXML
    private Text txt;

    @FXML
    private TableView<Standings> tableView;

    @FXML
    private TableColumn<Standings, String> tcAway;

    @FXML
    private TableColumn<Standings, String> tcConf;

    @FXML
    private TableColumn<Standings, Double> tcGB;

    @FXML
    private TableColumn<Standings, Double> tcHome;

    @FXML
    private TableColumn<Standings, Integer> tcL;

    @FXML
    private TableColumn<Standings, String> tcL10;

    @FXML
    private TableColumn<Standings, String> tcPCT;

    @FXML
    private TableColumn<Standings, Integer> tcRank;

    @FXML
    private TableColumn<Standings, String> tcStrk;

    @FXML
    private TableColumn<Standings, String> tcTeamName;

    @FXML
    private TableColumn<Standings, Integer> tcW;

    @FXML
    private TextField tfSeason;

    @FXML
    private Button btnGenerate;

    private final String [] conferences = {"Western Conference","Eastern Conference"};

    private final ObservableList<String> CONFERENCES = FXCollections.observableArrayList(conferences);
    private final ObservableList<Standings> list = FXCollections.observableArrayList();;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        progressIn.setVisible(false);
        cbxConference.setItems(CONFERENCES);

        tfSeason.setTextFormatter(new TextFormatter<>(change -> {
            if (!change.getControlNewText().matches("\\d*")) {
                return null;
            }
            return change;
        }));

        btnGenerate.setOnAction( e ->{

            list.clear();
            if(tfSeason.getText().isEmpty() || cbxConference.getSelectionModel().getSelectedItem() == null){
                txt.setFill(Color.RED);
                txt.setText("Empty Field(s) detected");
                return;
            }



            StandingsRequestTask standingsRequestTask = new StandingsRequestTask(Integer.parseInt(tfSeason.getText()),cbxConference.getSelectionModel().getSelectedItem());


            // Set up UI elements
            txt.setFill(Color.BLACK);
            txt.setText("Generating table...Please Wait....It gonna take a while");
            progressIn.progressProperty().bind(standingsRequestTask.progressProperty());


            standingsRequestTask.setOnRunning(ex ->{
                progressIn.setVisible(true);
            });

            standingsRequestTask.setOnSucceeded(ex ->{
                progressIn.setVisible(false);
                List<Standings> standingsList = standingsRequestTask.getValue();

                if(standingsList.size() > 0){
                    for (Standings standings : standingsList) {
                        list.add(standings);
                    }

                    tcRank.setCellValueFactory(new PropertyValueFactory<>("rank"));
                    tcTeamName.setCellValueFactory(new PropertyValueFactory<>("teamName"));
                    tcW.setCellValueFactory(new PropertyValueFactory<>("w"));
                    tcL.setCellValueFactory(new PropertyValueFactory<>("l"));
                    tcPCT.setCellValueFactory(new PropertyValueFactory<>("pct"));
                    tcGB.setCellValueFactory(new PropertyValueFactory<>("gb"));
                    tcConf.setCellValueFactory(new PropertyValueFactory<>("conf"));
                    tcHome.setCellValueFactory(new PropertyValueFactory<>("home"));
                    tcAway.setCellValueFactory(new PropertyValueFactory<>("away"));
                    tcL10.setCellValueFactory(new PropertyValueFactory<>("l10"));
                    tcStrk.setCellValueFactory(new PropertyValueFactory<>("strk"));



                    tableView.setItems(list);
                    txt.setFill(Color.GREEN);
                    txt.setText("Success");


                }else{
                    txt.setFill(Color.RED);
                    txt.setText("Season data might not be available or Network/Server Error");
                }


            });


            standingsRequestTask.setOnFailed((event) -> {
                progressIn.setVisible(false);
                txt.setFill(Color.RED);
                txt.setText("Server error or Season data might not be available");
                
            });

            Thread taskThread = new Thread(standingsRequestTask);
            taskThread.setDaemon(true);
            taskThread.start();

        });



    }
}
