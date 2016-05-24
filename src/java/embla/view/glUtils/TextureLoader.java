package embla.view.glUtils;

import java.awt.color.ColorSpace;
import java.awt.image.ComponentColorModel;
import java.awt.image.DataBuffer;
import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.HashMap;

import de.matthiasmann.twl.utils.PNGDecoder;
import de.matthiasmann.twl.utils.PNGDecoder.Format;
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
		InputStream in = new FileInputStream("white_pixel.png");
		PNGDecoder decoder = new PNGDecoder(in);

		System.out.println("width="+decoder.getWidth());
		System.out.println("height="+decoder.getHeight());

		ByteBuffer buf = ByteBuffer.allocateDirect(4*decoder.getWidth()*decoder.getHeight());
		decoder.decode(buf, decoder.getWidth()*4, Format.RGBA);
		buf.flip();

		return new GLTexture(decoder.getWidth(), decoder.getHeight(), path, buf);
	}
}
