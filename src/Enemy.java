import javafx.scene.image.ImageView;

public class Enemy extends ImageView
{
    // 1 is right, -1 is left, 2 is up, -2 is down
    int currentDirection;
    int xPos;
    int yPos;
    
    public Enemy()
    {
        this.currentDirection = 1;
        this.xPos = 0;
        this.yPos = 0;
    }
    
    public void walkRight() 
    {
        xPos += 5;
        currentDirection = 1;
    }

    public void walkLeft()
    {
        xPos -= 5;
        currentDirection = -1;
    }

    public void walkUp()
    {
        yPos += 5;
        currentDirection = 2;
    }
    
    public void walkDown()
    {
        yPos -= 5;
        currentDirection = -2;
    }
}