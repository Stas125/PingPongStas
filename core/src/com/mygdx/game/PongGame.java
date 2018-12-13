package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class PongGame extends ApplicationAdapter {
	SpriteBatch batch;
	SoundManager soundManager;
	Ball ball;
	int score;
    int livesCount = 3;
	BitmapFont font;
	Paddle paddle;
	int catchBallBonus = 100;

	@Override
	public void create () {
		batch = new SpriteBatch();
		font = new BitmapFont();
		font.getData().setScale(5);
		soundManager = new SoundManager();
		soundManager.loadSounds();
		ball = new Ball();
		ball.loadTexture();
		paddle = new Paddle();
		paddle.loadTexture();
		paddle.x = (Gdx.graphics.getWidth() - paddle.texture.getWidth()) / 2;
		ball.reset(paddle);
	}

	@Override
	public void render () {
		paddle.move();
		ball.ballStartFrameCounter++;
		ball.move(paddle);
		//теряем мяч
		if(ball.y < -ball.texture.getHeight()){
            soundManager.loseBallSound.play();
			ball.reset(paddle);
			livesCount--;
		}

		collideBall();

		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		ball.draw(batch);
		paddle.draw(batch);
		font.draw(batch, "Score: " + score + "  Lives: " + livesCount, 0, Gdx.graphics.getHeight());
		batch.end();
	}

	@Override
	public void dispose () {
		batch.dispose();
		font.dispose();
		ball.dispose();
		paddle.dispose();
		soundManager.dispose();
	}

	void collideBall(){
//		мяч отскакивает от правой стенки
		if(ball.x >= Gdx.graphics.getWidth() - ball.texture.getWidth()){
			ball.velocityX = -ball.velocityX;
			soundManager.playRandomBounceSound();
		}
//		мяч отскакивает от верхней стенки
		if( ball.y >= Gdx.graphics.getHeight() - ball.texture.getHeight()) {
			ball.velocityY = -ball.velocityY;
			soundManager.playRandomBounceSound();
		}
//		мяч отскакивает от левай стенки
		if(ball.x <= 0) {
			ball.velocityX = -ball.velocityX;
			soundManager.playRandomBounceSound();
		}
		 //мяч отскакивает от верхнего края битки
		if(ball.x > paddle.x - ball.texture.getWidth() / 2 &&
				ball.x < paddle.x + paddle.texture.getWidth() - ball.texture.getWidth() +  ball.texture.getWidth() / 2){
			if (ball.y < paddle.texture.getHeight()  + paddle.y && ball.y > paddle.y){
				ball.velocityY = -ball.velocityY;
				soundManager.playRandomBounceSound();
				score += catchBallBonus;
			}
		}
		//мяч отскакивает от левого края битки
		if(ball.x > paddle.x - ball.texture.getWidth() && ball.x < paddle.x - ball.texture.getWidth() / 2 + 1){
			if(ball.y < paddle.y + paddle.texture.getHeight()){
				if(ball.velocityX > 0) {
					ball.velocityX = -ball.velocityX;
					soundManager.playRandomBounceSound();
				}
			}
		}
		//мяч отскакивает от правого края битки
		if(ball.x > paddle.x + paddle.texture.getWidth() - ball.texture.getWidth() / 2 - 1
				&& ball.x < paddle.x + paddle.texture.getWidth()){
			if(ball.y < paddle.y + paddle.texture.getHeight()){
				if(ball.velocityX < 0) {
					ball.velocityX = -ball.velocityX;
					soundManager.playRandomBounceSound();
				}
			}
		}
	}
}