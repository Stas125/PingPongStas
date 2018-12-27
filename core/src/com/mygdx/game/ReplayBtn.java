package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class ReplayBtn {
    Texture texture;
    int x, y;
    public ReplayBtn() {
        texture = new Texture("replay_btn.png");
        x = Gdx.graphics.getWidth() - texture.getWidth();
    }

    void draw(SpriteBatch batch){
        batch.draw(texture, x, y);
    }

    boolean isClicked(){
        if(Gdx.input.justTouched()){
            int touchX = Gdx.input.getX();
            int touchY = Gdx.graphics.getHeight() - Gdx.input.getY();
            if(touchX >= x && touchX < x + texture.getWidth() && touchY >= y && touchY < y + texture.getHeight()){
                return true;
            }
        }
        return false;
    }

    void dispose(){
        texture.dispose();
    }
}
