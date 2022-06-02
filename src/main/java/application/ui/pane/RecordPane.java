package application.ui.pane;

import application.Main;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import utils.buttons.Buttons;
import utils.buttons.ImageButton;
import utils.files.CreateJson;
import utils.files.RecordVoice;

import java.io.File;
import java.util.Objects;

public class RecordPane extends BorderPane {

    boolean runningAudio = false;
    boolean runningRecord = false;
    boolean recordDone = false;

    Button playStopAudioFile;
    Button record;

    Label errorStatusAudioLabel;
    Label errorAudioFileNameLabel;
    TextField audioFileNameText;
    ImageView statusImageView;

    HBox hbox;
    RecordVoice recordVoice;
    CreateJson createJson;

    String pathFolder;
    String generatedNameFile;

    public RecordPane(Main main, Stage primaryStage, String pathFolder){
        super();

        recordVoice = new RecordVoice(pathFolder);
        createJson = new CreateJson(main, pathFolder);
        this.pathFolder = pathFolder;
        this.generatedNameFile = this.generateNameFile(main);

        playStopAudioFile = createPlayStopAudioFileButton();
        record = createRecordButton();
        Button validate = createValidateRecordButton(main, primaryStage);
        Button returnBack = createReturnBackButton(main, primaryStage);
        Button reset = createResetAudioButton();

        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        {
            Label statusAudioFile = new Label("Enregistrement audio fait ?");
            Image statusImage = new Image("images/error.png");
            statusImageView = new ImageView();
            statusImageView.setFitWidth(30);
            statusImageView.setFitHeight(30);
            statusImageView.setImage(statusImage);
            errorStatusAudioLabel = new Label();
            gridPane.add(statusAudioFile, 1, 0);
            gridPane.add(statusImageView, 2, 0);
            gridPane.add(errorStatusAudioLabel, 3, 0);
            statusAudioFile.getStyleClass().add("textLabel");
            errorStatusAudioLabel.getStyleClass().add("errorLabel");

            Label audioFileNameLabel = new Label("Nom du fichier audio enregistrer : ");
            audioFileNameText = new TextField(this.generatedNameFile);
            errorAudioFileNameLabel = new Label();
            gridPane.add(audioFileNameLabel, 1, 1);
            gridPane.add(audioFileNameText, 2, 1);
            gridPane.add(errorAudioFileNameLabel, 3, 1);
            audioFileNameLabel.getStyleClass().add("textLabel");
            audioFileNameText.getStyleClass().add("textField");
            errorAudioFileNameLabel.getStyleClass().add("errorLabel");

            Label emptyLabel = new Label("");
            gridPane.add(emptyLabel, 0, 2);

            gridPane.add(returnBack, 0, 3);
            gridPane.add(record, 1, 3);
            gridPane.add(playStopAudioFile, 2, 3);
            gridPane.add(reset, 3, 3);
            gridPane.add(validate, 4, 3);
        }

        hbox = new HBox(gridPane);
        hbox.setSpacing(5);
        hbox.setAlignment(Pos.CENTER);
        BorderPane.setAlignment(hbox, Pos.CENTER);
        gridPane.setAlignment(Pos.CENTER);
        BorderPane.setAlignment(gridPane, Pos.CENTER);
        this.setCenter(hbox);

        this.setStyle("-fx-background-color: #535e65");
    }

    public Button createPlayStopAudioFileButton() {
        Button playStopAudioFile = new Buttons();
        playStopAudioFile.setGraphic(ImageButton.createButtonImageView("images/play.png"));
        playStopAudioFile.getStyleClass().add("blue");
        playStopAudioFile.setContentDisplay(ContentDisplay.TOP);
        playStopAudioFile.setPrefHeight(50);
        playStopAudioFile.setPrefWidth(300);
        playStopAudioFile.setOnAction((e) -> {
            if (runningAudio) {
                runningAudio = false;
                recordVoice.playStopMediaPlayer("pause");
                ((ImageView) playStopAudioFile.getGraphic()).setImage(new Image("images/play.png"));
            } else {
                runningAudio = true;
                recordVoice.playStopMediaPlayer("play");
                ((ImageView) playStopAudioFile.getGraphic()).setImage(new Image("images/pause.png"));
            }
        });
        return playStopAudioFile;
    }

    public Button createResetAudioButton(){
        Button resetAudio = new Buttons();
        resetAudio.setGraphic(ImageButton.createButtonImageView("images/reset.png"));
        resetAudio.getStyleClass().add("blue");
        resetAudio.setContentDisplay(ContentDisplay.TOP);
        resetAudio.setPrefHeight(50);
        resetAudio.setPrefWidth(300);
        resetAudio.setOnAction((e) -> {
            recordVoice.playStopMediaPlayer("stop");
            if (runningAudio){
                playStopAudioFile.fire();
            }
        });
        return resetAudio;
    }

    public Button createRecordButton(){
        Button record = new Buttons();
        record.setGraphic(ImageButton.createButtonImageView("images/record.png"));
        record.getStyleClass().add("blue");
        record.setContentDisplay(ContentDisplay.TOP);
        record.setPrefHeight(50);
        record.setPrefWidth(300);
        record.setOnAction((e) -> {
            if (runningRecord) {
                runningRecord = false;
                recordVoice.stopRecording();
                recordDone = true;
                this.setStatusImageView(true);
                ((ImageView) record.getGraphic()).setImage(new Image("images/record.png"));
            } else {
                runningRecord = true;
                recordDone = true;
                this.setStatusImageView(false);
                recordVoice.startRecording();
                ((ImageView) record.getGraphic()).setImage(new Image("images/stopRecord.png"));
            }
        });
        return record;
    }

    public Button createReturnBackButton(Main main, Stage primaryStage){
        Button returnBack = new Buttons();
        returnBack.setGraphic(ImageButton.createButtonImageView("images/back.png"));
        returnBack.getStyleClass().add("blue");
        returnBack.setContentDisplay(ContentDisplay.TOP);
        returnBack.setPrefHeight(50);
        returnBack.setPrefWidth(300);
        returnBack.setOnAction((e) -> {
            this.resetValue();
            this.resetButton();
            main.goToHome(primaryStage);
        });
        return  returnBack;
    }

    public Button createValidateRecordButton(Main main, Stage primaryStage){
        Button validateRecord = new Button();
        validateRecord.setGraphic(ImageButton.createButtonImageView("images/validate.png"));
        validateRecord.getStyleClass().add("blue");
        validateRecord.setContentDisplay(ContentDisplay.TOP);
        validateRecord.setPrefHeight(50);
        validateRecord.setPrefWidth(300);
        validateRecord.setOnAction((e) -> {
            if (!recordDone){
                this.errorStatusAudioMessage("Un enregistrement est nécessaire !");
            }else if (Objects.equals(audioFileNameText.getText(), "")){
                this.errorAudioMessage("Un nom est nécessaire pour le fichier audio !");
            }else {
                if (!this.checkNameAlreadyUse(audioFileNameText.getText())){
                    recordVoice.renameTempAudioFile(audioFileNameText.getText());
                    createJson.createJson(audioFileNameText.getText());
                    this.resetValue();
                    this.resetButton();
                    main.goToHome(primaryStage);
                }
            }
        });
        return validateRecord;
    }

    public Boolean checkNameAlreadyUse(String recordFileName){
        File recordFile = new File(pathFolder + "//RecordFiles//" + recordFileName + ".wav");

        if (recordFile.exists()){
            this.errorAudioMessage("Ce nom est déjà utilisé !");
            return true;
        }else {
            return false;
        }
    }

    public void errorStatusAudioMessage(String error){
        this.errorStatusAudioLabel.setText(error);
    }

    public void errorAudioMessage(String error){
        this.errorStatusAudioLabel.setText("");
        this.errorAudioFileNameLabel.setText(error);
    }

    public void setStatusImageView(boolean status){
        Image statusImage;

        if (status){
            statusImage = new Image("images/validate.png");
        }else {
            statusImage = new Image("images/error.png");
        }
        this.statusImageView.setImage(statusImage);
    }

    public void resetValue(){
        recordDone = false;

        Image statusImage = new Image("images/error.png");
        this.statusImageView.setImage(statusImage);

        this.errorStatusAudioLabel.setText("");
        this.errorAudioFileNameLabel.setText("");

        this.audioFileNameText.setText("");
    }

    public void resetButton(){
        if (runningAudio){
            playStopAudioFile.fire();
        }
        if (runningRecord){
            record.fire();
        }
    }

    public String generateNameFile(Main main){
        boolean findNewName = false;
        String nameFile = main.getWavePane().getWaveService().audioFileName;
        int number = 1;
        String newNameFile = "";
        File newNameFileGenerated;

        while (!findNewName){
            newNameFile = nameFile + "_Translate" + number;
            newNameFileGenerated = new File(pathFolder + "//RecordFiles//" + newNameFile + ".wav");
            if (newNameFileGenerated.exists()){
                number += 1;
            }else {
                findNewName = true;
            }
        }

        return newNameFile;
    }
}
