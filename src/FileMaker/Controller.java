package FileMaker;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;
import java.util.List;
import java.util.Scanner;

public class Controller extends Application {


    //UI Hooks
    @FXML
    GridPane gridPane;
    @FXML
    TextArea reportLog;
    @FXML
    TextField openPath;
    @FXML
    TextField savePath;
    @FXML
    TextField expression;
    @FXML
    TextField HTMLTagExp;


    @Override
    public void start(Stage primaryStage) throws Exception {

    }


    //opens files
    public void openFile() throws FileNotFoundException {

        final String homedir = "I:\\Toby_BCIT";
        StringBuffer resultslog = new StringBuffer();

        try {
            //opens up the file chooser window
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Open text file");
            fileChooser.setInitialDirectory(new File(homedir));
            File input = fileChooser.showOpenDialog(gridPane.getScene().getWindow());
            //wipes input and output text space
            openPath.setText("");
            savePath.setText("");
            //if invalid file selected throws invlaid file error at path bar, if not then sets path to selected file
            if (input != null) {
                openPath.setText(String.valueOf(input));
            } else {
                openPath.setText("Invalid File Selected");
            }
            //outputs file contents onto the input text space
            Scanner inputScan = new Scanner(input);
            while (inputScan.hasNext()) {
                resultslog.append(inputScan.nextLine());
                resultslog.append("\n");
            }
            //shows the content of the file to the results text area
            reportLog.setText(resultslog.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    //file save procedure. Stores output into text field
    public void saveFile() throws FileNotFoundException {

        final String homedir = "I:\\Toby_BCIT";
        StringBuffer resultslog = new StringBuffer();

        try {
            //Save output file directory choose
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Save file");
            fileChooser.setInitialDirectory(new File(homedir));
            File outputPath = fileChooser.showSaveDialog(gridPane.getScene().getWindow());
            //saves the save path to the text field allows user to type if wished
            savePath.setText(outputPath.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    //extracts keywords through use of expression
    public void definitionExtract() throws IOException {
        //gets user input fields
        String expr = expression.getText();
        String fileOpen = openPath.getText();
        String fileSave = savePath.getText();
        //opens the text file via user input field path. Makes new buffer
        Scanner scanner = new Scanner(new File(fileOpen));
        StringBuffer buffer = new StringBuffer();
        //while there are still lines in the text file...
        while (scanner.hasNext()) {
            //reads the next line and splits the line into a string array seperated via space
            String def = scanner.nextLine();
            //allows each word to have its own array element
            String[] list = def.split("\\s+");
            //for loop that goes through each word in the array
            for (int i = 0; i < list.length; i++) {
                // in the word i nthe array matches the user expression then the definition will trigger
                if (list[i].equals(expr)) {
                    //this for loop will print all the words that are before the expression into the string buffer
                    for (int j = 0; j < i; j++) {
                        buffer.append(list[j]);
                        buffer.append(" ");
                    }
                    //adds a newline to the buffer indicating next term. once found the expression

                    buffer.append("\n");
                    //break the loop and move on to nextline as there can only be one seperator. if unsure about the totals, remove to append all that triggers the expression
                    break;
                }
            }
        }
        //writes the file to waht the user indicated
        BufferedWriter writer = new BufferedWriter(new FileWriter(new File(fileSave)));
        writer.write(buffer.toString());
        //clean up threads and report to log.
        writer.flush();
        writer.close();
        reportLog.appendText("\n\n\nExtraction Complete");

    }

    public void HTMLTagAdder() {
        //gets the user inputs
        String tagExpr = HTMLTagExp.getText();
        String term = "l";
        String definition="2";
        String fileOpen = openPath.getText();
        String fileSave = savePath.getText();
        //stores the HTML tags
        String tagsAdded = "<"+tagExpr+" class=\"tooltip\">"+term+"<"+tagExpr+" class=\"tooltippopup\"><"+tagExpr+" class=\"tooltiptitle\">"+term+
                "</"+tagExpr+"><"+tagExpr+" class=\"tooltiptext\">"+definition+"</"+tagExpr+">"+"</"+tagExpr+">"+"</"+tagExpr+">";


    }
}
