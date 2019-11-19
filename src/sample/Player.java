package sample;

import javafx.scene.image.Image;
import javafx.scene.layout.Pane;

public class Player extends SpriteBase {

    double playerMinX;
    double playerMaxX;
    double playerMinY;
    double playerMaxY;

    Input input;

    double speed;

    public Player(Pane layer, Image image, double x, double y, double dx, double dy, double speed, Input input) {

        super(layer, image, x, y,  dx, dy);

        this.speed = speed;
        this.input = input;

        init();
    }


    private void init() {

        // calculate movement bounds of the player ship
        // allow half of the ship to be outside of the screen
        playerMinX = 0 - image.getWidth() / 2.0;
        playerMaxX = Settings.SCENE_WIDTH - image.getWidth() / 2.0;
        playerMinY = 0 - image.getHeight() / 2.0;
        playerMaxY = Settings.SCENE_HEIGHT -image.getHeight() / 2.0;

    }

    public void processInput() {

        // ------------------------------------
        // movement
        // ------------------------------------

        // vertical direction

            if (input.isMoveUp())
            {
                dx = 0;
                dy = -speed;
            }
            else if (input.isMoveDown())
            {
                dx = 0;
                dy = speed;
            }

        // horizontal direction
            if (input.isMoveLeft())
            {
                dy = 0;
                dx = -speed;
            }
            else if (input.isMoveRight())
            {
                dy = 0;
                dx = speed;
            }
    }

    @Override
    public void move() {

        super.move();

        // ensure the ship can't move outside of the screen
        checkBounds();


    }

    private void checkBounds() {

        // vertical
        if( Double.compare( y, playerMinY) < 0) {
            y = playerMinY;
        } else if( Double.compare(y, playerMaxY) > 0) {
            y = playerMaxY;
        }

        // horizontal
        if( Double.compare( x, playerMinX) < 0) {
            x = playerMinX;
        } else if( Double.compare(x, playerMaxX) > 0) {
            x = playerMaxX;
        }

    }


    @Override
    public void checkRemovability() {
        // TODO Auto-generated method stub
    }

}