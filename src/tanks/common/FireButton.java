package tanks.common;

import javax.media.opengl.GL2;
import javax.media.opengl.GLAutoDrawable;

public class FireButton extends Drawable 
{
	static final float WIDTH = .2f;
	static final float HEIGHT = .2f;
	
	//private Drawable text = new Drawable();

	float baseGeometry[] = new float[12];
	
	public void buildGeometry(float viewWidth, float viewHeight)
	{
		baseGeometry[0]=0; baseGeometry[1]=0; baseGeometry[2]=0;
		baseGeometry[3]=0; baseGeometry[4]=HEIGHT*viewHeight; baseGeometry[5]=0;
		baseGeometry[6]=WIDTH*viewWidth; baseGeometry[7]=0; baseGeometry[8]=0;
		baseGeometry[9]=WIDTH*viewWidth; baseGeometry[10]=HEIGHT*viewHeight; baseGeometry[11]=0;
		this.drawMode = GL2.GL_TRIANGLE_STRIP;
		this.x = -viewWidth/2;
		this.y = -viewHeight/2;
		this.width = WIDTH*viewWidth;
		this.height = HEIGHT*viewHeight;
		super.baseGeometry = this.baseGeometry;
		
		/*text.baseGeometry = this.baseGeometry;
		text.drawMode = GL2.GL_TRIANGLE_STRIP;
		text.texture_id = TextUtil.generateTextTexture("Fire", 42, 29, 19);
		text.width = this.width = WIDTH*viewWidth;
		text.height = this.height = HEIGHT*viewHeight;
		text.x = this.x = -viewWidth/2;
		text.y = this.y = -viewHeight/2;*/
	}
	
	public void display(GLAutoDrawable d, Renderer renderer)
	{
		super.display(d, renderer);
		//text.display(d, renderer);
	}
	
	public void init(GLAutoDrawable d, int viewWidth, int viewHeight)
	{
		super.init(d, viewWidth, viewHeight);
		//text.init(d, viewWidth, viewHeight);
	}
	
	public void reshape(GLAutoDrawable d, int x, int y, int width, int height)
	{
		super.reshape(d, x, y, width, height);
		//text.reshape(d, x, y, width, height);
	}

	public void click() 
	{
		this.fireGLClick(new GLClickEvent(this));
	}
	
	public void depress()
	{
		
	}
}
