package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
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
	Texture gameOverLogoTexture;
	boolean isGameOver;
	Button closeBtn, replayBtn;

	@Override
	public void create () {
		batch = new SpriteBatch();
		font = new BitmapFont();
		font.getData().setScale(3);
		soundManager = new SoundManager();
		soundManager.loadSounds();
		ball = new Ball();
		ball.loadTexture();
		paddle = new Paddle();
		paddle.loadTexture();
		paddle.x = (Gdx.graphics.getWidth() - paddle.texture.getWidth()) / 2;
		ball.reset(paddle);
		gameOverLogoTexture = new Texture("game_over_logo.jpg");
		closeBtn = new Button("close_btn.png");
		closeBtn.x = Gdx.graphics.getWidth() - closeBtn.texture.getWidth();
		replayBtn = new Button("replay_btn.png");
	}

	@Override
	public void render () {
		if(isGameOver && closeBtn.isClicked()){
			System.exit(0);
		}
		if(isGameOver && replayBtn.isClicked()){
			System.exit(0);
		}

		if(!isGameOver) {
			paddle.move();
			ball.ballStartFrameCounter++;
			ball.move(paddle);
		}
		//теряем мяч
		if(ball.y < -ball.texture.getHeight()){
            soundManager.loseBallSound.play();
			ball.reset(paddle);
			livesCount--;
			if (livesCount == 0){
				isGameOver = true;
			}
		}

		collideBall();

		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		ball.draw(batch);
		paddle.draw(batch);
		if(isGameOver) {
			batch.draw(gameOverLogoTexture, (Gdx.graphics.getWidth() - gameOverLogoTexture.getWidth()) / 2,
					(Gdx.graphics.getHeight() - gameOverLogoTexture.getHeight()) / 2);
			closeBtn.draw(batch);
			replayBtn.draw(batch);
		}
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
		gameOverLogoTexture.dispose();
		closeBtn.dispose();
		replayBtn.dispose();
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