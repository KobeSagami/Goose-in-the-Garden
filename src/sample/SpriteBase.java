package sample;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

import java.awt.image.BufferedImage;

public abstract class SpriteBase {

    Image idleUp;
    Image idleDown;
    Image idleLeft;
    Image moveLeft1;
    Image moveLeft2;
    Image idleRight;
    ImageView spriteAnimation;

    int frame;

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

    double w;
    double h;

    boolean canMove = true;

    private static BufferedImage spriteSheet;
    private static final int TILE_SIZE = 32;

    public SpriteBase(Pane layer, Image image, double x, double y, double dx, double dy) {

        this.layer = layer;
        idleUp = image;

        idleDown = new Image(getClass().getResource("Images/idleDown.png").toExternalForm());

        idleLeft = new Image(getClass().getResource("Images/idleLeft.png").toExternalForm());
        moveLeft1 = new Image(getClass().getResource("Images/chickenLeft1.png").toExternalForm());
        moveLeft2 = new Image(getClass().getResource("Images/chickenLeft2.png").toExternalForm());

        idleRight = new Image(getClass().getResource("Images/idleRight.png").toExternalForm());
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
        if (frame > 60)
            frame = 0;
        frame++;
        if (!canMove)
            return;

        x += dx;
        y += dy;

        if (dx < 0) {
            if (frame == 0)
                spriteAnimation.setImage(idleLeft);
            else if (frame == 30)
                spriteAnimation.setImage(moveLeft1);
            else if (frame == 60)
                spriteAnimation.setImage(moveLeft2);
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