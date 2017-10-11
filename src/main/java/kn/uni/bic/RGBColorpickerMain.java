package kn.uni.bic;

import net.imagej.ImageJ;

public class RGBColorpickerMain {

	public static void main(String[] args) {
		ImageJ ij = new ImageJ();
		ij.ui().showUI();

		ij.command().run(RGBColorpicker.class, true);
	}

}
