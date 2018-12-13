package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Ball {
    int x, y;
    Texture texture;
    int velocityX = 6, velocityY = 6;
    int ballStartFrameCounter, flyFrameCounter;
    final int FRAMES_TO_WAIT_BEFORE_BALL_START = 60;

    void loadTexture(){
        texture = new Texture("ball_small.png");
    }
    void draw(SpriteBatch batch){
        batch.draw(texture, x, y);
    }

    void dispose(){
        texture.dispose();
    }

    void move(Paddle paddle){
        if(ballStartFrameCounter > FRAMES_TO_WAIT_BEFORE_BALL_START){
            y += velocityY;
            x += velocityX;
            flyFrameCounter++;

            if(flyFrameCounter > 100){
                if(velocityX > 0){
                    velocityX += 1;
                }else{
                    velocityX -= 1;
                }
                if(velocityY > 0){
                    velocityY += 1;
                }else{
                    velocityY -= 1;
                }
                flyFrameCounter = 0;
            }
        }

        if (ballStartFrameCounter < FRAMES_TO_WAIT_BEFORE_BALL_START){
            x = paddle.x + paddle.texture.getWidth() / 2 - texture.getWidth() / 2;
        }
    }

    void reset(Paddle paddle){
        y = paddle.y + paddle.texture.getHeight();
        x = paddle.x + paddle.texture.getWidth() / 2 - texture.getWidth() / 2;
        ballStartFrameCounter = 0;
        velocityY = Math.abs(velocityY);
    }
}
