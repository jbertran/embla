package embla.view.glUtils;

<<<<<<< HEAD
import java.awt.color.ColorSpace;
import java.awt.image.ComponentColorModel;
import java.awt.image.DataBuffer;
import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.HashMap;

import de.matthiasmann.twl.utils.PNGDecoder;
import de.matthiasmann.twl.utils.PNGDecoder.Format;
=======
import java.awt.Color;
import java.awt.Graphics;
import java.awt.color.ColorSpace;
import java.awt.image.BufferedImage;
import java.awt.image.ComponentColorModel;
import java.awt.image.DataBuffer;
import java.awt.image.DataBufferByte;
import java.awt.image.Raster;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.util.HashMap;
import java.util.Hashtable;

import javax.imageio.ImageIO;

import org.lwjgl.BufferUtils;

>>>>>>> e1f7bac4b3a009f807ccdee591f26ae24a6dd090
import embla.view.glShapes.GLTexture;

public class TextureLoader {
	
	private HashMap<String, GLTexture> textures;
	static final ComponentColorModel alphaCM = new ComponentColorModel(
			ColorSpace.getInstance(ColorSpace.CS_sRGB),
			new int[] {8, 8, 8, 8},
			true, false, ComponentColorModel.TRANSLUCENT, DataBuffer.TYPE_BYTE);
	static final ComponentColorModel stdCM = new ComponentColorModel(
			ColorSpace.getInstance(ColorSpace.CS_sRGB),
			new int[] {8, 8, 8, 0},
			false, false, ComponentColorModel.OPAQUE, DataBuffer.TYPE_BYTE);
	
	public TextureLoader() {
		this.textures  = new HashMap<>();
	}
	
	public GLTexture loadTexture(String path) {
<<<<<<< HEAD
		InputStream in = new FileInputStream("white_pixel.png");
		PNGDecoder decoder = new PNGDecoder(in);
		 
		System.out.println("width="+decoder.getWidth());
		System.out.println("height="+decoder.getHeight());
		 
		ByteBuffer buf = ByteBuffer.allocateDirect(4*decoder.getWidth()*decoder.getHeight());
		decoder.decode(buf, decoder.getWidth()*4, Format.RGBA);
		buf.flip();
		
		return new GLTexture(decoder.getWidth(), decoder.getHeight(), path, buf);
=======
		
		if (textures.get(path) != null)
			return textures.get(path);
		
		BufferedImage image = null; 
		try {
			image = ImageIO.read(new File(path));
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(-1);
		}
		WritableRaster raster;
		BufferedImage resImg;
		FloatBuffer buffer;

		int texWidth = 2;
		int texHeight = 2;
		while(texWidth < image.getWidth())
			texWidth *= 2;
		while(texHeight < image.getHeight())
			texHeight *= 2;
		
		boolean hasAlpha = image.getColorModel().hasAlpha(); 
		if (hasAlpha) {
			raster = Raster.createInterleavedRaster(DataBuffer.TYPE_BYTE, texWidth, texHeight, 4, null);
			resImg = new BufferedImage(alphaCM, raster, false, new Hashtable());
		} else {			
			raster = Raster.createInterleavedRaster(DataBuffer.TYPE_BYTE, texWidth, texHeight, 3, null);
			resImg = new BufferedImage(stdCM, raster, false, new Hashtable());
		}

		Graphics g = resImg.getGraphics();
		g.setColor(new Color(0f, 0f, 0f, 0f));
		g.fillRect(0, 0, texWidth, texHeight);
		g.drawImage(image, 0, 0, null);

		byte[] rawdata = ((DataBufferByte) resImg.getRaster().getDataBuffer()).getData();
		buffer = BufferUtils.createFloatBuffer(rawdata.length);
		buffer.flip();
		
		return new GLTexture(texWidth, texHeight, rawdata.length, hasAlpha, buffer);
>>>>>>> e1f7bac4b3a009f807ccdee591f26ae24a6dd090
	}
}
