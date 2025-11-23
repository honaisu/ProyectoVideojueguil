package logica;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class AnimationHandler {
	private final Animation<TextureRegion> animation;
	private Sprite sprite;
	private float stateTime;
	
	public AnimationHandler(Animation<TextureRegion> animation, Sprite sprite) {
		this.animation = animation;
		this.sprite = sprite;
	}
	
	private void handle(SpriteBatch batch, TextureRegion frame) {
		batch.draw(frame,
	    		sprite.getX(), sprite.getY(),
	    		sprite.getOriginX(), sprite.getOriginY(),
	    		sprite.getWidth(), sprite.getHeight(),
	    		sprite.getScaleX(), sprite.getScaleY(),
	    		sprite.getRotation());
	}
	
	public void handle(SpriteBatch batch, boolean looping) {
		TextureRegion currentFrame = animation.getKeyFrame(stateTime, looping);
		handle(batch, currentFrame);
	}
	
	public void handle(SpriteBatch batch, boolean condition, boolean looping) {
		TextureRegion currentFrame = animation.getKeyFrame(stateTime);
		if (condition) {
 	        currentFrame = animation.getKeyFrame(stateTime, looping);
 	    } else {
 	        currentFrame = animation.getKeyFrame(0, looping);
 	    }
		handle(batch, currentFrame);
	}
	
	public void updateSprite(Sprite sprite) { 
		this.sprite = sprite;
	}
	
	public void updateStateTime(float delta) {
		stateTime += delta;
	}
}
