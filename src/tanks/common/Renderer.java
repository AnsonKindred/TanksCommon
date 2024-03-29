package tanks.common;

import javax.media.opengl.GL2;

public interface Renderer 
{
	abstract void render(GL2 gl,int drawMode, Drawable drawable);
	abstract long getTimeSinceLastDraw();
}
