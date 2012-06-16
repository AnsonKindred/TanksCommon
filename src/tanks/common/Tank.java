package tanks.common;

import javax.media.opengl.GL2;
import javax.media.opengl.GLAutoDrawable;

public class Tank extends Drawable
{
	static final float TURRENT_LENGTH = .045f; // ratio of screen height
	static final float WIDTH = .05f; // ratio of screen height
	static final float HEIGHT = .07f; // ratio of screen height

	float baseGeometry[];
	
	public Drawable turret = new Drawable();
	float turretLength = 5;
	
	float xRatio, yRatio;
	
	public void buildGeometry(float viewWidth, float viewHeight)
	{
		width = WIDTH*viewWidth;
		height = HEIGHT*viewHeight;
		float[] baseGeometry = {
		            // X, Y
					0, 0, 0,
					width, 0f,0,
					width/2, height,0
		        };
		super.baseGeometry = baseGeometry;
		
		x = viewWidth*xRatio;
		y = viewHeight*yRatio;
		
		turretLength = TURRENT_LENGTH*viewHeight;
		float turretGeometry[] = {
				0, 0, 0,
				0, turretLength, 0
			};
		turret.drawMode = GL2.GL_LINES;
		turret.baseGeometry = turretGeometry;
		turret.x = x+width/2;
		turret.y = y+height;
	}
	
	public void init(GLAutoDrawable d, float viewWidth, float viewHeight)
	{
		x = (float) (Math.random()*viewWidth)-viewWidth/2;
		y = (float) (Math.random()*viewHeight)-viewHeight/2;
		xRatio = x/viewWidth;
		yRatio = y/viewHeight;
		super.init(d, viewWidth, viewHeight);
		turret.init(d, viewWidth, viewHeight);
	}
	
	public void reshape(GLAutoDrawable d, int x, int y, int width, int height)
	{
		super.reshape(d, x, y, width, height);
		turret.reshape(d, x, y, width, height);
	}
	
	public void display(GLAutoDrawable d, Renderer r)
	{
		super.display(d, r);
		turret.display(d, r);
	}
	
}
