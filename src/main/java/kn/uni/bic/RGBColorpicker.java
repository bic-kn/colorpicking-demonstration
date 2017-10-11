package kn.uni.bic;

import java.util.Arrays;

import org.scijava.command.Command;
import org.scijava.command.DynamicCommand;
import org.scijava.plugin.Parameter;
import org.scijava.plugin.Plugin;

import ij.ImagePlus;
import ij.process.ByteProcessor;
import ij.process.LUT;

@Plugin(type=Command.class, menuPath="Plugins>BIC>RGB Colorpicker Demonstration")
public class RGBColorpicker extends DynamicCommand {

	@Parameter(label="Red", min="0", max="255", style="slider", callback="colorChanged", persist=false)
	private int red = 217;
	
	@Parameter(label="Green", min="0", max="255", style="slider", callback="colorChanged", persist=false)
	private int green = 118;

	@Parameter(label="Blue", min="0", max="255", style="slider", callback="colorChanged", persist=false)
	private int blue = 33;

	private ImagePlus colorPreviewImp;

	@Override
	public void preview() {
		if (colorPreviewImp == null) {
			ByteProcessor byteProcessor = new ByteProcessor(400, 400);
			colorPreviewImp = new ImagePlus("Color Preview", byteProcessor);
			byte[][] lut = lut(red, green, blue);
			colorPreviewImp.setLut(new LUT(lut[0], lut[1], lut[2]));
			colorPreviewImp.show();
		}
	}

	@Override
	public void cancel(String reason) {
		colorPreviewImp.close();
	}

	public void colorChanged() {
		byte[][] lut = lut(red, green, blue);
		colorPreviewImp.setLut(new LUT(lut[0], lut[1], lut[2]));
		colorPreviewImp.updateAndDraw();
	}

	// -- Helper methods --

	/** Creates a lut array with 3 components and 256 values (only one value). */
	private static byte[][] lut(final int r, final int g, final int b)
	{
		final byte[][] gray = new byte[ 3 ][ 256 ];
		byte[] reds = new byte[256];
		Arrays.fill(reds, (byte) r);
		gray[0] = reds;
		byte[] greens = new byte[256];
		Arrays.fill(greens, (byte) g);
		gray[1] = greens;
		byte[] blues = new byte[256];
		Arrays.fill(blues, (byte) b);
		gray[2] = blues;
		return gray;
	}

}
