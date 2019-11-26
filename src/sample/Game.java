package sample;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextBoundsType;
import javafx.stage.Stage;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class Game extends Application {

    Random rnd = new Random();

    Button startButton;

    Pane playfieldLayer;
    Pane scoreLayer;

    String mapName = "Map1.csv";

    Image playerImage;
    Image enemyImage;

    List<Player> players = new ArrayList<>();
    List<Enemy> enemies = new ArrayList<>();

    Text collisionText = new Text();
    boolean collision = false;

    Scene scene;

    Group root;

    ImageView backgroundView;

    public Game() throws URISyntaxException {
    }

    @Override
    public void start(Stage primaryStage) {
        Image background = new Image("sample/Map.jpg");
        backgroundView = new ImageView();
        backgroundView.setImage(background);

        root = new Group();

        // create layers
        playfieldLayer = new Pane();
        scoreLayer = new Pane();

        startButton = new Button("Start Game");
        startButton.relocate(350, 400);
        startButton.setOnAction(this::startButtonClickEvent);

        root.getChildren().add(startButton);



        scene = new Scene( root, Settings.SCENE_WIDTH, Settings.SCENE_HEIGHT);

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public void startButtonClickEvent(ActionEvent args)
    {
        startButton.setVisible(false);

        root.getChildren().add(backgroundView);
        root.getChildren().add(playfieldLayer);
        root.getChildren().add(scoreLayer);

        loadGame();

        createScoreLayer();
        createPlayer();

        AnimationTimer gameLoop = new AnimationTimer() {

            @Override
            public void handle(long now) {

                // player input
                players.forEach(sprite -> sprite.processInput());

                // add random enemies
                spawnEnemies( true);

                // movement
                players.forEach(sprite -> sprite.move());
                enemies.forEach(sprite -> sprite.move());

                // check collisions
                checkCollisions();

                // update sprites in scene
                players.forEach(sprite -> sprite.updateUI());
                enemies.forEach(sprite -> sprite.updateUI());

                // check if sprite can be removed
                enemies.forEach(sprite -> sprite.checkRemovability());

                // remove removables from list, layer, etc
                removeSprites( enemies);

                // update score, health, etc
                updateScore();
            }

        };
        gameLoop.start();
    }

    private void loadGame() {
        playerImage = new Image( getClass().getResource("Images/chickens/whiteChickenDown1.png").toExternalForm());
        enemyImage = new Image( getClass().getResource("enemy.png").toExternalForm());
    }

    private void createScoreLayer() {


        collisionText.setFont( Font.font( null, FontWeight.BOLD, 64));
        collisionText.setStroke(Color.BLACK);
        collisionText.setFill(Color.RED);

        scoreLayer.getChildren().add( collisionText);

        // TODO: quick-hack to ensure the text is centered; usually you don't have that; instead you have a health bar on top
        collisionText.setText("Collision");
        double x = (Settings.SCENE_WIDTH - collisionText.getBoundsInLocal().getWidth()) / 2;
        double y = (Settings.SCENE_HEIGHT - collisionText.getBoundsInLocal().getHeight()) / 2;
        collisionText.relocate(x, y);
        collisionText.setText("");

        collisionText.setBoundsType(TextBoundsType.VISUAL);


    }
    private void createPlayer() {

        // player input
        Input input = new Input( scene);

        // register input listeners
        input.addListeners(); // TODO: remove listeners on game over

        Image image = playerImage;

        // center horizontally, position at 70% vertically
        double x = 400;
        double y = 350;

        // create player
        Player player = new Player(playfieldLayer, image, x, y,  0, 0 , Settings.PLAYER_SPEED, input,mapName);

        // register player
        players.add( player);

    }

    private void spawnEnemies( boolean random) {

        if( random && rnd.nextInt(Settings.ENEMY_SPAWN_RANDOMNESS) != 0) {
            return;
        }

        // image
        Image image = enemyImage;

        // random speed
        double speed = rnd.nextDouble() * 1.0 + 2.0;

        // x position range: enemy is always fully inside the screen, no part of it is outside
        // y position: right on top of the view, so that it becomes visible with the next game iteration
        double x = rnd.nextDouble() * (Settings.SCENE_WIDTH - image.getWidth());
        double y = -image.getHeight();

        // create a sprite
        Enemy enemy = new Enemy( playfieldLayer, image, x, y, 0,  speed,mapName);

        // manage sprite
        enemies.add( enemy);

    }

    private void removeSprites(  List<? extends SpriteBase> spriteList) {
        Iterator<? extends SpriteBase> iter = spriteList.iterator();
        while( iter.hasNext()) {
            SpriteBase sprite = iter.next();

            if( sprite.isRemovable()) {

                // remove from layer
                sprite.removeFromLayer();

                // remove from list
                iter.remove();
            }
        }
    }

    private void checkCollisions() {

        collision = false;

        for( Player player: players) {
            for( Enemy enemy: enemies) {
                if( player.collidesWith(enemy)) {
                    collision = true;
                }
            }
        }
    }

    private void updateScore() {
        if( collision) {
            collisionText.setText("Collision");
        } else {
            collisionText.setText("");
        }
    }

    public static void main(String[] args) {
        launch(args);
    }


}