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
		int [][] rotateLeft = new int[columns][rows];
		for (int r = 0; r < rows; r++) {
			for (int c = 0; c < columns; c++) {
				int rotateLeftRow = (columns - 1)  - c;
				int rotateLeftColumn = r;
				rotateLeft[rotateLeftRow][rotateLeftColumn] = pixels[r][c];
			}
		}	
		GImage rotateLeftImage = new GImage(rotateLeft);
		return rotateLeftImage;
	}

	public GImage rotateRight(GImage source) {
		int [][] pixels = source.getPixelArray();
		int rows = rows(pixels);
		int columns = columns(pixels);
		int [][] rotateRight = new int [columns][rows];
		for (int r = 0; r < rows; r++) {
			for (int c = 0; c < columns; c++) {
				int rotateLeftRow = c;
				int rotateLeftColumn = (rows - 1)  - r;
				rotateRight[rotateLeftRow][rotateLeftColumn] = pixels[r][c];
			}
		}	
		GImage rotateRightImage = new GImage(rotateRight);
		return rotateRightImage;
	}

	public GImage flipHorizontal(GImage source) {
		int [][] pixels = source.getPixelArray();
		int rows = rows(pixels);
		int columns = columns(pixels);
		int [][] flipHorizontal = new int [rows][columns];
		for (int r = 0; r < rows; r++) {
			for (int c = 0; c < columns; c++) {
				int flipRow = r;
				int flipColumn = (columns - 1)  - c;
				flipHorizontal[flipRow][flipColumn] = pixels[r][c];
			}
		}	
		GImage flippedImage = new GImage(flipHorizontal);
		return flippedImage;
	}

	public GImage negative(GImage source) {
		int [][] pixels = source.getPixelArray();
		int rows = rows(pixels);
		int columns = columns(pixels);
		int [][] negative = new int [rows][columns];
		for (int r = 0; r < rows; r++) {
			for (int c = 0; c < columns; c++) {
				int negativePixel = pixels[r][c];
				int red = 255 - GImage.getRed(negativePixel); 
				int green = 255 - GImage.getGreen(negativePixel);
				int blue = 255 - GImage.getBlue(negativePixel);
				negative[r][c] = GImage.createRGBPixel(red, blue, green);
			}
		}	
		GImage negativeImage = new GImage(negative);
		return negativeImage;	
	}

	public GImage greenScreen(GImage source) {
		int [][] pixels = source.getPixelArray();
		int rows = rows(pixels);
		int columns = columns(pixels);
		for (int r = 0; r < rows; r++) {
			for (int c = 0; c < columns; c++) {
				int greenScreenPixel = pixels[r][c];
				int red = GImage.getRed(greenScreenPixel); 
				int green = GImage.getGreen(greenScreenPixel);
				int blue = GImage.getBlue(greenScreenPixel);
				int max = Math.max(red, blue);
				if (green >= 2 * max) {
					pixels [r][c] = GImage.createRGBPixel (red, green, blue, 0);
				} else {
					pixels [r][c] = GImage.createRGBPixel (red, green, blue);
				}
			}
		}	
		GImage greenScreenImage = new GImage(pixels);
		return greenScreenImage;
	}	

	public GImage blur(GImage source) {
		int [][] pixels = source.getPixelArray();
		int [][] blur = new int [rows][columns];
		
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
