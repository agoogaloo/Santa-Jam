package santaJam.entities.wallEntities;

import java.awt.Color;
import java.awt.Graphics2D;

import santaJam.states.Camera;

public class BreakableWall extends WallEntity{

	public BreakableWall(int x, int y) {
		super(x,y);
	}	
	@Override
	public void update() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void render(Graphics2D g, Camera camera) {
		g.setColor(new Color(50,175,200));
		g.fillRect(bounds.x-camera.getxOffset(),bounds.y-camera.getyOffset(),bounds.width,bounds.height);
	}
	
	public void smash() {
		killed=true;
	}
	

}