package santaJam.entities.player;

import java.awt.Color;
import java.awt.Rectangle;

import santaJam.audio.MusicManager;
import santaJam.graphics.particles.movers.Straight;
import santaJam.graphics.particles.shapes.OvalParticle;
import santaJam.graphics.particles.shapes.colourers.Timed;
import santaJam.graphics.particles.spawners.RectangleSpawn;
import santaJam.inputs.Inputs;
import santaJam.states.StateManager;

public class SlideJump extends PlayerState{
	public static final double JUMPSTRENGTH=5, STOPSTRENGTH=0.75;
	boolean firstFrame=true;

	
	@Override
	public void start(PlayerState prevState) {
		width=slideWidth;
		height=slideHeight;
	}

	@Override
	public PlayerState update(Player player) {		
		if(!StateManager.getGameState().getSave().hasSlide()) {
			normalGravity(player);
			normalMoveLeftRight(player);
			return new Falling();
		}
		
		
		super.update(player);
		player.setAnim(Player.sliding);
		slideGravity(player);
		if(Math.abs(player.getVelX())<=TOPWALKSPEED) {
			return new Falling();
			
		}
		if(firstFrame) {
			MusicManager.playSound(MusicManager.jump);
			player.setVelY(-JUMPSTRENGTH);
			Rectangle pBounds = player.getBounds();
			new RectangleSpawn(5, pBounds.x-3, pBounds.y+pBounds.height-3, pBounds.width+10,5,new Straight(0, 0, -90,60,0.5),
					new OvalParticle(2, new Timed(Color.white,10,5)) , true);
			firstFrame=false;
		}
		
		
		if(Inputs.up().getHoldLength()<BUFFERLENGTH&&Inputs.up().getHoldLength()>0&&!Inputs.up().isInputUsed()) {
			Inputs.up().useInput();
			return new UpBoost(this,player);
		}
		if(Inputs.grapple().getHoldLength()<BUFFERLENGTH&&Inputs.grapple().getHoldLength()>0&&!Inputs.grapple().isInputUsed()) {
			Inputs.grapple().useInput();
			return new SlideGrapple(this,player);
		}
		
		if(player.getVelY()<=0) {
			if(!Inputs.jump().isHeld()) {
				player.addVelY(STOPSTRENGTH);
			}	
		}else {
			return new SlideFalling();
		}
		
		return null;
		
		
	}

	@Override
	public void end() {}
}
