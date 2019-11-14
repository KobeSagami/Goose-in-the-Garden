
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;

import javax.swing.Timer;

import javafx.animation.Animation;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

// This doesn't necessarily take user input it's all RNG based and I think it's more fun that way.
// Something something players like RNG.
// Couldn't figure out how to track the time either.

public class Driver extends Application
{
    Button nameButton;
    Button numberButton;
    Button emailButton;;
    
    TextField textField;
    
    String playerName;
    int playerNumber;
    String playerEmail;
    
    Text playerNameText;
    Text playerNumberText;
    Text playerEmailText;
    
    Pane myPane;
    Pane racePane;
    
    Group myGroup;
    Group raceGroup;
    
    Scene myScene;
    Scene raceScene;
    
    Stage stage;
    
    public static void main(String[] args)
    {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception
    {
        Text loadingText = new Text();
        loadingText.setText("Goose in the Garden");
        loadingText.relocate(190, 220);
        
        // Loading screen first
        Image spriteMap = new Image("SpriteMap.png");
        ImageView walkingGoose = new ImageView();
        walkingGoose.setImage(spriteMap);
        walkingGoose.setViewport(new Rectangle2D(0, 35, 25, 30));
        walkingGoose.setScaleX(1.25);
        walkingGoose.setScaleY(1.25);
        walkingGoose.relocate(240, 250);
        
        ImageView runningGoose = new ImageView();
        runningGoose.setImage(spriteMap);
        runningGoose.setViewport(new Rectangle2D(8, 65, 34, 25));
        runningGoose.setScaleX(1.25);
        runningGoose.setScaleY(1.25);
        runningGoose.relocate(240, 250);

        Animation walkingAnimation = new SpriteAnimation(
                walkingGoose,
                Duration.millis(750),
                4, 4,
                0, 35,
                25, 30
        );
        
        Animation runningAnimation = new SpriteAnimation(
                runningGoose,
                Duration.millis(5000),
                4, 4,
                8, 65,
                34, 25
        );
        
        walkingAnimation.setCycleCount(Animation.INDEFINITE);
        walkingAnimation.play();
        
        runningAnimation.setCycleCount(Animation.INDEFINITE);
        runningAnimation.play();
        
        myPane = new Pane();
        
        nameButton = new Button("Enter your name:");
        nameButton.relocate(300, 200);
        nameButton.setOnAction(this::nameButtonClick);
        
        textField = new TextField();
        textField.relocate(100, 200);
        
        playerNameText = new Text();
        playerNameText.relocate(5, 5);
        
        playerNumberText = new Text();
        playerNumberText.relocate(5, 20);
        
        playerEmailText = new Text();
        playerEmailText.relocate(5, 35);
        
        myPane.getChildren().addAll(runningGoose, loadingText);
        Group myGroup = new Group(myPane);
        myScene = new Scene(myGroup, 500, 500);
        myScene.setFill(Color.GRAY);

        // Staging
        this.stage = primaryStage;

        primaryStage.setTitle("Goose in the Garden");
        primaryStage.setScene(myScene);
        primaryStage.show();        
    }
    
    public void nameButtonClick(ActionEvent args)
    {
        playerName = textField.getText();
        playerNameText.setText("Name: " + playerName);
        nameButton.setVisible(false);
        textField.clear();
        
        numberButton = new Button("Enter your race number:");
        numberButton.relocate(300, 200);
        numberButton.setOnAction(this::numberButtonClick);
        myPane.getChildren().addAll(numberButton);
    }
    
    public void numberButtonClick(ActionEvent args)
    {
        try
        {
            playerNumber = Integer.parseInt(textField.getText());
            playerNumberText.setText("Number: " + playerNumber);
            
            numberButton.setVisible(false);
            
            emailButton = new Button("Enter your email:");
            emailButton.relocate(300, 200);
            emailButton.setOnAction(this::emailButtonClick);
            myPane.getChildren().addAll(emailButton);
        }
        catch(Exception NumberFormatException)
        {
            numberButton.setText("Please enter a valid number:");
        }
        finally
        {
            textField.clear();
        }
    }
    
    public void emailButtonClick(ActionEvent args)
    {       
        playerEmail = textField.getText().trim().replaceAll(" ", "");
        String email = "";
        String emailWebsite = "";
        boolean hasATsymbol = false;
        for (int i = 0; i < playerEmail.length(); i++)
        {
            if (playerEmail.charAt(i) == '@')
            {
                email = playerEmail.substring(0, i);
                emailWebsite = playerEmail.substring(i + 1);
                hasATsymbol = true;
                break;
            }
        }
        
        if (!hasATsymbol)
        {
            emailButton.setText("Please enter a valid email:");
        }
        else
        {
            playerEmail = email + "@" + emailWebsite;
            playerEmailText.setText("Email: " + playerEmail);
            emailButton.setVisible(false);
            textField.setVisible(false);
        }
        
        textField.clear();
        
        racePane = new Pane();
        racePane.getChildren().addAll(playerNameText, playerNumberText, playerEmailText);
                
                
        Group raceGroup = new Group(racePane);
        raceScene = new Scene(raceGroup, 500, 500);
        stage.setScene(raceScene);
    }
}