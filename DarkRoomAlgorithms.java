/* 
 * Note: these methods are public in order for them to be used by other files
 * in this assignment; DO NOT change them to private.  You may add additional
 * private methods to implement required functionality if you would like.
 * 
 * You should remove the stub lines from each method and replace them with your
 * implementation that returns an updated image.
 */

// TODO: comment this file explaining its behavior

import acm.graphics.*;

public class DarkRoomAlgorithms implements DarkRoomAlgorithmsInterface {

	private int rows (int [][] source) {
		return source.length;
	}
	
	private int columns (int [][] source) {
		return source[0].length;
	}
	
	public GImage rotateLeft(GImage source) {
		int [][] pixels = source.getPixelArray();
		int rows = rows(pixels);
		int columns = columns(pixels);
		int [][] rotateLeft = new int [rows][columns];
		for (int r = 0; r < rows; r++) {
			for (int c = 0; c < columns; c++) {
				int rotateLeftRow = c;
				int rotateLeftColumn = rows - r;
				rotateLeft[rotateLeftRow][rotateLeftColumn] = pixels[r][c];
			}
		}	
		GImage rotateLeftImage = new GImage(rotateLeft);
		return rotateLeftImage;
	}

	public GImage rotateRight(GImage source) {
		// TODO
		return null;
	}

	public GImage flipHorizontal(GImage source) {
		// TODO
		return null;
	}

	public GImage negative(GImage source) {
		// TODO
		return null;
	}

	public GImage greenScreen(GImage source) {
		// TODO
		return null;
	}

	public GImage blur(GImage source) {
		// TODO
		return null;
	}

	public GImage crop(GImage source, int cropX, int cropY, int cropWidth, int cropHeight) {
		// TODO
		return null;
	}

	public GImage equalize(GImage source) {
		// TODO
		return null;
	}
}
