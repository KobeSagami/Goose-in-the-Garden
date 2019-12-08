package sample;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

import java.awt.image.BufferedImage;
import java.net.URISyntaxException;

public abstract class SpriteBase {
    boolean horizontalorVertical = false;
    Image idleUp;
    Image idleDown;
    Image idleLeft;
    Image idleRight;
    Image moveLeft1;
    Image moveLeft2;
    Image moveRight1;
    Image moveRight2;
    Image moveDown1;
    Image moveDown2;
    Image moveUp1;
    Image moveUp2;

    ImageView spriteAnimation;

    int frame;

    String color;

    Pane layer;

    double x;
    double y;
    double r;

    double dx;
    double dy;
    double dr;

    double playerSpottedX = -10000;
    double playerSpottedY = -10000;

    double health;
    double damage;

    boolean removable = false;

    CheckMap mapLevel;

    double w;
    double h;

    boolean canMove = true;

    public SpriteBase(Pane layer, Image image, double x, double y, double dx, double dy, String mapName, String color) {

        try {
            mapLevel = new CheckMap(mapName);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        this.layer = layer;
        this.color = color;

        idleDown = new Image(getClass().getResource("Images/chickens/" + color + "ChickenDown2.png").toExternalForm());
        idleLeft = new Image(getClass().getResource("Images/chickens/" + color + "ChickenLeft2.png").toExternalForm());
        idleRight = new Image(getClass().getResource("Images/chickens/" + color + "ChickenRight2.png").toExternalForm());
        idleUp = new Image(getClass().getResource("Images/chickens/" + color + "ChickenUp2.png").toExternalForm());

        moveLeft1 = new Image(getClass().getResource("Images/chickens/" + color + "ChickenLeft1.png").toExternalForm());
        moveLeft2 = new Image(getClass().getResource("Images/chickens/" + color + "ChickenLeft3.png").toExternalForm());
        moveRight1 = new Image(getClass().getResource("Images/chickens/" + color + "ChickenRight1.png").toExternalForm());
        moveRight2 = new Image(getClass().getResource("Images/chickens/" + color + "ChickenRight3.png").toExternalForm());
        moveDown1 = new Image(getClass().getResource("Images/chickens/" + color + "ChickenDown1.png").toExternalForm());
        moveDown2 = new Image(getClass().getResource("Images/chickens/" + color + "ChickenDown3.png").toExternalForm());
        moveUp1 = new Image(getClass().getResource("Images/chickens/" + color + "ChickenUp1.png").toExternalForm());
        moveUp2 = new Image(getClass().getResource("Images/chickens/" + color + "ChickenUp3.png").toExternalForm());

        this.x = x;
        this.y = y;
        this.dx = dx;
        this.dy = dy;

        this.spriteAnimation = new ImageView(idleUp);
        this.spriteAnimation.relocate(x, y);
        this.spriteAnimation.setRotate(r);

        this.w = image.getWidth(); // imageView.getBoundsInParent().getWidth();
        this.h = image.getHeight(); // imageView.getBoundsInParent().getHeight();

        frame = 0;

        addToLayer();

    }


    public void addToLayer() {
        this.layer.getChildren().add(this.spriteAnimation);
    }

    public void removeFromLayer() {
        this.layer.getChildren().remove(this.spriteAnimation);
    }

    public Pane getLayer() {
        return layer;
    }

    public void setLayer(Pane layer) {
        this.layer = layer;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getR() {
        return r;
    }

    public void setR(double r) {
        this.r = r;
    }

    public double getDx() {
        return dx;
    }

    public void setDx(double dx) {
        this.dx = dx;
    }

    public double getDy() {
        return dy;
    }

    public void setDy(double dy) {
        this.dy = dy;
    }

    public boolean isRemovable() {
        return removable;
    }

    public void setRemovable(boolean removable) {
        this.removable = removable;
    }

    public void move() {
        frame = ++frame % 60;
        if (!canMove)
            return;

        double[] movement;
        movement = mapLevel.movement(x, y, dx, dy);
        x = movement[0];
        y = movement[1];

        if (dx < 0) {
            switch (frame) {
                case 0:
                case 30:
                    spriteAnimation.setImage(idleLeft);
                    break;
                case 15:
                    spriteAnimation.setImage(moveLeft1);
                    break;
                case 45:
                    spriteAnimation.setImage(moveLeft2);
                    break;
                default:
                    break;
            }
        }
        if (dx > 0) {
            switch (frame) {
                case 0:
                case 30:
                    spriteAnimation.setImage(idleRight);
                    break;
                case 15:
                    spriteAnimation.setImage(moveRight1);
                    break;
                case 45:
                    spriteAnimation.setImage(moveRight2);
                    break;
                default:
                    break;
            }
        }
        if (dy > 0) {
            switch (frame) {
                case 0:
                case 30:
                    spriteAnimation.setImage(idleDown);
                    break;
                case 15:
                    spriteAnimation.setImage(moveDown1);
                    break;
                case 45:
                    spriteAnimation.setImage(moveDown2);
                    break;
                default:
                    break;
            }
        }
        if (dy < 0) {
            switch (frame) {
                case 0:
                case 30:
                    spriteAnimation.setImage(idleUp);
                    break;
                case 15:
                    spriteAnimation.setImage(moveUp1);
                    break;
                case 45:
                    spriteAnimation.setImage(moveUp2);
                    break;
                default:
                    break;
            }
        }
    }

    public ImageView getView() {
        return spriteAnimation;
    }

    public void updateUI() {
        spriteAnimation.relocate(x, y);
    }

    public double getWidth() {
        return w;
    }

    public double getHeight() {
        return h;
    }

    public double getCenterX() {
        return x + w * 0.5;
    }

    public double getCenterY() {
        return y + h * 0.5;
    }

    // TODO: per-pixel-collision and map collisions
    public boolean collidesWith(SpriteBase otherSprite) {
        return (otherSprite.x + otherSprite.w >= x && otherSprite.y + otherSprite.h >= y && otherSprite.x <= x + w && otherSprite.y <= y + h);
    }

    /**
     * Set flag that the sprite can be removed from the UI.
     */
    public void remove() {
        setRemovable(true);
    }

    /**
     * Set flag that the sprite can't move anymore.
     */
    public void stopMovement() {
        this.canMove = false;
    }

    public abstract void checkRemovability();

    public void AI(double Playerx, double Playery) {

        double toworkonx = Playerx;
        double toworkony = Playery;

        if (frame % 6 == 0) {
            if (Math.abs(Playerx - x) < Math.abs(Playery - y)) {
                if (Playerx > x && mapLevel.canMoveRight(x, y)) {
                    dy = 0;
                    dx = 5;
                } else if (Playerx < x && mapLevel.canMoveLeft(x, y)) {
                    dy = 0;
                    dx = -5;
                } else if (Playery > y && mapLevel.canMoveDown(x, y)) {
                    dy = 5;
                    dx = 0;
                } else {
                    dy = -5;
                    dx = 0;
                }

            } else {
                if (Playery > y && mapLevel.canMoveDown(x, y)) {
                    dy = 5;
                    dx = 0;
                } else if (Playery < y && mapLevel.canMoveUp(x, y)) {
                    dy = -5;
                    dx = 0;
                } else if (Playerx > x && mapLevel.canMoveRight(x, y)) {
                    dy = 0;
                    dx = 5;
                } else {
                    dy = 0;
                    dx = -5;
                }
            }


        }

      /*  boolean currentmovement = false;
        boolean canmovedown = mapLevel.canMoveDown(x, y);
        boolean canmoveup = mapLevel.canMoveUp(x, y);
        boolean canmoveleft = mapLevel.canMoveLeft(x, y);
        boolean canmoveright = mapLevel.canMoveRight(x,y);

        if(dy > 0){
            currentmovement = canmovedown;
        }
        if(dy < 0){
            currentmovement = canmoveup;
        }
        if(dx < 0){
            currentmovement = canmoveleft;
        }
        if(dx > 0){
            currentmovement = canmoveright;
        }

        if(toworkony < y && canmoveup && frame ==0){
            dx = 0;
            dy = -5;
        }

        else if(toworkony > y && canmovedown && frame ==0){
            dx = 0;
            dy = 5;
        }

        else if(toworkonx < x && canmoveright && frame == 0){
            dx = 5;
            dy = 0;
        }

        else if(toworkonx > x && canmoveleft && frame ==0){
            dx = -5;
            dy = 0;
        }

        if(!currentmovement){
            dy = 0;
            dx = 0;
            if(toworkony < y && canmoveup && !horizontalorVertical){
                dy = -5;
                horizontalorVertical = !horizontalorVertical;
            }
            else if(toworkony > y && canmovedown && !horizontalorVertical){
                dy = 5;
                horizontalorVertical = !horizontalorVertical;
            }
            else if (toworkonx < x && canmoveleft && horizontalorVertical){
                dx = -5;
                horizontalorVertical = !horizontalorVertical;
            }
            else if(toworkonx > x && canmoveright && horizontalorVertical){
                dx = 5;
                horizontalorVertical = !horizontalorVertical;
            }
            else{
                if(canmoveup){
                    dy = -5;
                }
                else if(canmovedown){
                    dy = 5;
                }
                else if (canmoveright){
                    dx = 5;
                }
                else if (canmoveleft){
                    dx = -5;
                }
            }

        }
       */
    }

    public void LOS(double Playerx, double Playery){
        double px = Playerx;
        double py = Playery;
        if (frame % 6 == 0) {
            //Detect player
            if (Math.abs(px - x) < 20){
                if (py < y && mapLevel.canMoveUp(x,y)){
                    playerSpottedX = px;
                    playerSpottedY = py;
                }
                else if (py > y && mapLevel.canMoveDown(x,y)){
                    playerSpottedX = px;
                    playerSpottedY = py;
                }
            }
            else if (Math.abs(py - y) < 20){
                if (px < x && mapLevel.canMoveLeft(x,y)){
                    playerSpottedX = px;
                    playerSpottedY = py;
                }
                else if (px > x && mapLevel.canMoveRight(x,y)){
                    playerSpottedX = px;
                    playerSpottedY = py;
                }
            }

            //Move to last detected position
            if (playerSpottedX != -10000){
                if (Math.abs(playerSpottedX - x) < 20){
                 if (playerSpottedY < y && mapLevel.canMoveUp(x,y)){
                     dy = -5;
                     dx = 0;
                 }
                else if (playerSpottedY > y && mapLevel.canMoveDown(x,y)){
                     dy = 5;
                     dx = 0;
                }
            }
            else if (Math.abs(playerSpottedY - y) < 20){
                if (playerSpottedX < x && mapLevel.canMoveLeft(x,y)){
                    dy = 0;
                    dx = -5;
                }
                else if (playerSpottedX > x && mapLevel.canMoveRight(x,y)){
                    dy = 0;
                    dx = 5;
                }
                else{
                    dy = 0;
                    dx = 0;
                }
            }
        }
            else{
                dy = 0;
                dx = 0;
            }
    }
//        if (frame % 6 == 0) {
//            if (Math.abs(px - x) < 20){
//                 if (py < y && mapLevel.canMoveUp(x,y)){
//                     dy = -5;
//                     dx = 0;
//                 }
//                else if (py > y && mapLevel.canMoveDown(x,y)){
//                     dy = 5;
//                     dx = 0;
//                }
//            }
//            else if (Math.abs(py - y) < 20){
//                if (px < x && mapLevel.canMoveLeft(x,y)){
//                    dy = 0;
//                    dx = -5;
//                }
//                else if (px > x && mapLevel.canMoveRight(x,y)){
//                    dy = 0;
//                    dx = 5;
//                }
//            }
//            else {
//                dy = 0;
//                dx = 0;
//            }
//        }
    }

    public void menuAI() {
        int dir;
        if (frame % 6 == 0) {
            dir = (int)Math.round(Math.random()* 25);

            switch(dir){
                case 1:
                    if(mapLevel.canMoveUp(x, y)){
                        dy = -4;
                        dx = 0;
                    }
                break;

                case 2:
                    if(mapLevel.canMoveDown(x, y)){
                        dy = 4;
                        dx = 0;
                    }
                    break;

                case 3:
                    if(mapLevel.canMoveLeft(x, y)){
                        dy = 0;
                        dx = -4;
                    }
                    break;

                case 4:
                    if(mapLevel.canMoveRight(x, y)){
                        dy = 0;
                        dx = 4;
                    }
                    break;
                default:
                    if (dx > 0){
                        dx = dx - 1;
                    }
                    if (dx < 0){
                        dx = dx + 1;
                    }

                    if (dy > 0){
                        dy = dy - 1;
                    }
                    if (dy < 0){
                        dy = dy + 1;
                    }
            }
        }
    }
}
