package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Paddle {
    Texture texture;
    int x, y = 100;

    void loadTexture(){
        texture = new Texture("paddle.bmp");
    }

    void draw(SpriteBatch batch){
        batch.draw(texture, x, y);
    }

    void dispose(){
        texture.dispose();
    }
    void move(){
        if(Gdx.input.isTouched()) {
            x = Gdx.input.getX() - texture.getWidth() / 2;
            //недайом битке заходить за левую стенку екрана
            if (x < 0) {
                x = 0;
            }
            //недайом битке заходить за правую стенку екрана
            if (x > Gdx.graphics.getWidth() - texture.getWidth()) {
                x = Gdx.graphics.getWidth() - texture.getWidth();
            }
        }
    }
}
