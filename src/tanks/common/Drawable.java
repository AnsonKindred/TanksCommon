package tanks.common;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.util.ArrayList;
import javax.media.opengl.GL2;
import javax.media.opengl.GLAutoDrawable;
import javax.swing.event.EventListenerList;

public class Drawable
{
	private ArrayList<Drawable> drawables = new ArrayList<Drawable>();
	
	protected EventListenerList listenerList = new EventListenerList();
	
	float x = 0, y = 0;
	
	float rotation = 0;
	float slope    = 0;
	float width    = 0;
	float height   = 0;
	
	FloatBuffer geometryBuffer = null;
	FloatBuffer textureBuffer  = null;
	FloatBuffer colorBuffer    = null;
	
	float[] color = {.3f, .4f, .5f, 1};
	
	float[] vertexColors = null;
	float[] textureCoords = null;
	float[] baseGeometry = null;
	int texture_id = 0;	
	
	int drawMode = GL2.GL_TRIANGLES;
	
	public boolean didInit = false;
	
	public void buildGeometry(float viewWidth, float viewHeight)
	{
		
	}
	
	public void finalizeGeometry()
	{
		if(baseGeometry == null) return;
        vertexColors = new float[baseGeometry.length/3 * 4];
        for(int c = 0; c < baseGeometry.length/3; c++)
        {
        	int i = c * 4;
        	vertexColors[i] = color[0];
        	vertexColors[i+1] = color[1];
        	vertexColors[i+2] = color[2];
        	vertexColors[i+3] = color[3];
        }
        
        textureCoords = new float[baseGeometry.length/3 * 2];
        float factor = height;
        if(width > height) factor = width;
        for(int c = 0; c < baseGeometry.length/3; c++)
        {
        	textureCoords[c*2] = baseGeometry[c*3]/factor;
        	textureCoords[c*2+1] = 1-baseGeometry[c*3+1]/factor;
        }
        
        // initialize color Buffer  
        ByteBuffer vbb = ByteBuffer.allocateDirect(
                // (# of coordinate values * 4 bytes per float)
                vertexColors.length * 4
            ).order(ByteOrder.nativeOrder());
        colorBuffer = vbb.asFloatBuffer();
        colorBuffer.put(vertexColors);    // add the coordinates to the FloatBuffer
        colorBuffer.position(0);          // set the buffer to read the first coordinate
        
        // initialize vertex Buffer  
        vbb = ByteBuffer.allocateDirect(
                // (# of coordinate values * 4 bytes per float)
                baseGeometry.length * 4
            ).order(ByteOrder.nativeOrder());
        geometryBuffer = vbb.asFloatBuffer();
        geometryBuffer.put(baseGeometry);    // add the coordinates to the FloatBuffer
        geometryBuffer.position(0);          // set the buffer to read the first coordinate
        
        // initialize texture Buffer  
        vbb = ByteBuffer.allocateDirect(
                // (# of coordinate values * 4 bytes per float)
                textureCoords.length * 4
            ).order(ByteOrder.nativeOrder());
        textureBuffer = vbb.asFloatBuffer();
        textureBuffer.put(textureCoords);    // add the coordinates to the FloatBuffer
        textureBuffer.position(0);          // set the buffer to read the first coordinate
	}
	
	public void display(GLAutoDrawable d, Renderer r)
	{
		GL2 gl = (GL2)d.getGL();
		
		gl.glPushMatrix();
		gl.glTranslatef(x, y, 0);
		if(rotation != 0) {
			gl.glRotatef(this.rotation, 0, 0, 1);
		}

		if(baseGeometry != null) {
			r.render(gl, drawMode, this);
		}
		
		for(int i=0; i < drawables.size(); i++) {
			drawables.get(i).display(d, r);
		}
		
		gl.glPopMatrix();
	}
	
	public float getRotation()
	{
		return rotation;
	}
	
	public void setRotation(float x, float y)
	{
		float theta = (float) Math.atan(y/x);
		rotation = (float) Math.toDegrees(theta)-90;
		slope = y/x;
		if(x < 0) rotation += 180;
	}
	
	public void setRotation(float deg)
	{
		rotation = deg;
		slope = (float) Math.tan(Math.toRadians(deg+90));
	}

	public float getSlope()
	{
		return slope;
	}

	public void init(GLAutoDrawable d, float viewWidth, float viewHeight) 
	{
		buildGeometry(viewWidth, viewHeight);
		finalizeGeometry();
		for(int i=0; i < drawables.size(); i++) {
			if(!drawables.get(i).didInit) {
				drawables.get(i).init(d, viewWidth, viewHeight);
			}
		}
		didInit = true;
	}
	
	public void dispose(GLAutoDrawable d)
	{
		for(int i=0; i < drawables.size(); i++) drawables.get(i).dispose(d);
	}
	
	public void reshape(GLAutoDrawable d, int x, int y, int width, int height) 
	{
		buildGeometry(width, height);
		finalizeGeometry();
		for(int i=0; i < drawables.size(); i++) drawables.get(i).reshape(d, x, y, width, height);
	}

	public FloatBuffer getGeometry() 
	{
		return geometryBuffer;	
	}

	public int getNumPoints() 
	{
		return baseGeometry.length/3;
	}
	
	public void registerDrawable(Drawable d)
    {
    	drawables.add(d);
    }
	
    public void addGLClickListener(GLClickListener listener) 
    {
        listenerList.add(GLClickListener.class, listener);
    }

    public void removeGLClickListener(GLClickListener listener) 
    {
        listenerList.remove(GLClickListener.class, listener);
    }

    void fireGLClick(GLClickEvent evt) 
    {
        Object[] listeners = listenerList.getListenerList();
        // Each listener occupies two elements - the first is the listener class
        // and the second is the listener instance
        for (int i=0; i<listeners.length; i+=2) {
            if (listeners[i]==GLClickListener.class) {
                ((GLClickListener)listeners[i+1]).glClicked(evt);
            }
        }
    }
}
