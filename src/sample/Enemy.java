package sample;

import javafx.scene.image.Image;
import javafx.scene.layout.Pane;

public class Enemy extends SpriteBase {

    public Enemy(Pane layer, Image image, double x, double y, double dx, double dy , String mapName) {
        super(layer, image, x, y,  dx, dy,mapName);

    }

    @Override
    public void checkRemovability() {

        if( Double.compare( getY(), Settings.SCENE_HEIGHT) > 0) {
            setRemovable(true);
        }


    }

        public void AI(boolean canmoveleft, boolean canmoveright, boolean canmoveup, boolean canmovedown, double Playerx, double Playery){
            double toworkonx = Playerx;
            double toworkony = Playery;
            boolean currentmovement = false;

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

            if(!currentmovement){
                dy = 0;
                dx = 0;
                if(toworkony < y && canmoveup){
                    dy = -5;
                }
                else if(toworkony > y && canmovedown){
                    dy = 5;
                }
                else if (toworkonx < x && canmoveleft){
                    dx = -5;
                }
                else if(toworkonx > x && canmoveright){
                    dx = 5;
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


    }
}