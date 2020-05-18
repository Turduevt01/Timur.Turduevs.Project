package sample;
import java.util.Random;
import java.util.ArrayList;
import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class Main extends Application {

    private Button[] buttons = new Button[9];
    private ArrayList<Integer> randomArray = NumbersGeneration();
    private int xFree = 0;
    private int yFree = 0;
    private int buttonFree = 0;
    private static int buttonSize = 60;



    private ArrayList<Integer> NumbersGeneration() {
        ArrayList<Integer> restart = new ArrayList<>();
        for (int i = 0; i < 9;i++) {
            Random random = new Random();
            int randoming = random.nextInt(9);
            if (!restart.contains(randoming)) {
                restart.add(randoming);
            }
            else {
                i--;
            }
        }
        return restart;
    }

    @Override
    public void start(Stage primaryStage) {
        ButtonsArray();
        Group group = new Group();
        group.getChildren().add(getButtons());
        Button StartButton = new Button("Restart the game");
        StartButton.setLayoutX(37);
        StartButton.setLayoutY(190);

        StartButton.setOnMouseClicked(new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent event) {
                randomArray = NumbersGeneration();
                for(int i = 0; i < buttons.length; i++) {
                    buttons[i].setText((randomArray.get(i) + 1) + "");
                    if (randomArray.get(i) == 8){
                        buttons[i].setText("");
                        xFree= i % 3;
                        yFree  = i / 3;
                        buttonFree = i;
                    }
                }
            }
        });

        group.getChildren().add(StartButton);
        Scene scene = new Scene(group);

        primaryStage.setTitle("8 Puzzle");
        primaryStage.setScene(scene);
        primaryStage.show();


    }



    private int ResultChecking() {
        int result = 1;
        for (int i=0;i<8;i++) {
            if (randomArray.get(i) !=i) {
                result = 0;
            }
        }
        return result;
    }

    private Pane getButtons() {
        int j = 0;
        GridPane gridpane = new GridPane();
        for(Button b : buttons) {
            int xNew = j % 3;
            int yNew = j / 3;
            gridpane.add(b, xNew*buttonSize, yNew*buttonSize);
            j++;
        }
        return gridpane;
    }

    private void ButtonsArray() {
        for(int i = 0; i < buttons.length; i++) {
            buttons[i] = new Button((randomArray.get(i) + 1)+"");
            buttons[i].setMaxWidth(buttonSize);
            buttons[i].setMaxHeight(buttonSize);
            buttons[i].setMinWidth(buttonSize);
            buttons[i].setMinHeight(buttonSize);
            if (randomArray.get(i)==8) {
                buttons[i].setText("");
                xFree = i %3;
                yFree  = i / 3;
                buttonFree  = i;
            }
            buttons[i].setId(i+"");

            buttons[i].setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent clicking) {
                    Button b = (Button)(clicking.getSource());
                    int buttonNew = Integer.parseInt(b.getId());
                    int xNew = buttonNew %3;
                    int yNew = buttonNew / 3;

                    if (Math.abs(xFree-xNew)+Math.abs(yFree -yNew)==1) {

                        buttons[buttonNew].setText("");
                        buttons[buttonFree ].setText((randomArray.get(buttonNew)+1)+"");


                        randomArray.set(buttonFree ,randomArray.get(buttonNew));
                        randomArray.set(buttonNew, randomArray.get(buttonFree ));

                        xFree=xNew;
                        yFree =yNew;
                        buttonFree =buttonNew;

                    }

                    if (ResultChecking()==1) {
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("8 Puzzle");

                        alert.setContentText("You won!");
                        alert.showAndWait();
                    }
                }
            });
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
