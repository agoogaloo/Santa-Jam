package santaJam.states.menus;

import java.awt.Graphics;
import java.awt.Rectangle;

import santaJam.inputs.Inputs;

public class Menu extends MenuSelection{
	
	
	
	private MenuObject[] menuObjects;
	private int selection=-1;
	private int hovered=0;
	private boolean inSubMenu=false;
	
	public Menu(Rectangle bounds,String name,  MenuObject[] menuObjects) {
		super(bounds,name);
		this.menuObjects=menuObjects;
	}
	public Menu(Rectangle bounds,  MenuObject[] menuObjects) {
		this(bounds, "", menuObjects);
	}


	public void update() {
		
		if(inSubMenu) {
			((Menu) menuObjects[selection]).update();
			if(!menuObjects[selection].selected) {
				inSubMenu=false;
			}
			return;
		}
		
		
		if(Inputs.Down().isPressed()) {
			
			hovered++;
			if(hovered>menuObjects.length-1) {
				hovered=0;
			}
		}
		if(Inputs.up().isPressed()) {
			hovered--;
			if(hovered<0) {
				hovered=menuObjects.length-1;
			}
		}
		menuObjects[hovered].hover();
		
		
		
		if(Inputs.jump().isPressed()) {
			if(selection!=-1) {
				menuObjects[selection].deselect();
			}
			menuObjects[hovered].select();
			selection=hovered;
			if(menuObjects[selection] instanceof Menu) {
				inSubMenu=true;
			}
		}

		
		
	}
		
	@Override
	public void render(Graphics g) {
		if(inSubMenu) {
			menuObjects[selection].render(g);
			return;
		}
		if(selected) {
			renderOptions(g);
		}else {
			super.render(g);
		}
		
		
	}
	
	private void renderOptions(Graphics g) {
		if(inSubMenu||!selected) {
			return;
		}
		for(MenuObject i: menuObjects) {
			i.render(g);
			
		}
	}
	
	public void closeSubMenu() {
		if(inSubMenu) {
			((Menu) menuObjects[selection]).closeSubMenu();
			inSubMenu=false;
		}else {
			selected=false;
		}
	}
}
