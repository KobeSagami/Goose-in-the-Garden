package sample;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

import java.awt.image.BufferedImage;
import java.net.URISyntaxException;

public abstract class SpriteBase {

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

    double health;
    double damage;

    boolean removable = false;

    CheckMap mapLevel;

    double w;
    double h;

    boolean canMove = true;

    public SpriteBase(Pane layer, Image image, double x, double y, double dx, double dy,String mapName) {

        try {
            mapLevel = new CheckMap(mapName);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        this.layer = layer;
        this.color = "white";

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
        movement = mapLevel.movement(x,y,dx,dy);
        x =movement[0];
        y =movement[1];

        if (dx < 0)
        {
            switch(frame)
            {
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
        if (dx > 0)
        {
            switch(frame)
            {
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
        if (dy > 0)
        {
            switch(frame)
            {
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
        if (dy < 0)
        {
            switch(frame)
            {
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

}