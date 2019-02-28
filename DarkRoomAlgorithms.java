/*
 * File: Breakout.java
 * -------------------
 * Name: Adam Kincer
 * Section Leader: Christian Davis
 * 
 * This program can perform a variety of functions on 
 * a given photo including rotating it left, rotating it 
 * right, flipping it horizontally,
 */

import java.awt.event.MouseEvent;

import acm.graphics.*;

public class DarkRoomAlgorithms implements DarkRoomAlgorithmsInterface {

	private int rows (int [][] source) {
		return source.length;
	}
	
	private int columns (int [][] source) {
		return source[0].length;
	}
	
	/*
	 * rotates an image 90 degrees counterclockwise
	 */
	
	public GImage rotateLeft(GImage source) {
		int [][] pixels = source.getPixelArray();
		int numberOfRows = rows(pixels);
		int numberOfColumns = columns(pixels);
		int [][] rotateLeft = new int[numberOfRows][numberOfColumns];
		for (int row = 0; row < numberOfRows; row++) {
			for (int column = 0; column < numberOfColumns; column++) {
				rotateLeft[row][numberOfColumns - 1 - column] = pixels[numberOfColumns][numberOfRows];			//flips and switches row and column
			}
		}	
		GImage rotateLeftImage = new GImage(rotateLeft);
		return rotateLeftImage;
	}

	/*
	 * rotates an image 90 degrees clockwise
	 */
	
	public GImage rotateRight(GImage source) {
		int [][] pixels = source.getPixelArray();
		int numberOfRows = rows(pixels);
		int numberOfColumns = columns(pixels);
		int [][] rotateRight = new int [numberOfColumns][numberOfRows];
		for (int row = 0; row < numberOfRows; row++) {
			for (int column = 0; column < numberOfColumns; column++) {
				rotateRight[column][numberOfRows - 1 - row] = pixels[row][column]; 								//switches row and column
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
	

	
	public GImage blur(GImage source) {								//received help in LAIR
		int [][] pixels = source.getPixelArray();
		int rows = rows(pixels);
		int columns = columns(pixels);
		int [][] blur = new int [rows][columns];
		for (int r = 0; r < rows; r++) {
			for (int c = 0; c < columns; c++) {
				int redTotal = 0;
				int greenTotal = 0;
				int blueTotal = 0;
				int avgPixels = 0;
				for (int i = r - 1; i<= r + 1; i++) {
					for (int j = c - 1; j <= c + 1; j++) {
						if (i > 0 && i < rows && j > 0 && j < columns) {	
							int red = GImage.getRed(pixels[i][j]);
							redTotal += red;
							int green = GImage.getGreen(pixels[i][j]);
							greenTotal += green;
							int blue = GImage.getBlue(pixels[i][j]);
							blueTotal += blue;
							avgPixels++;
						}
					}
				}		
		int avgRed = redTotal / avgPixels;
		int avgGreen = greenTotal / avgPixels;
		int avgBlue = blueTotal / avgPixels;
		blur[r][c] = GImage.createRGBPixel(avgRed, avgGreen, avgBlue);		
			}
		}
		GImage blurredImage = new GImage(blur);
		return blurredImage;
	}
	

	public GImage crop(GImage source, int cropX, int cropY, int cropWidth, int cropHeight) {
		int [][] pixels = source.getPixelArray();
		int [][] pixels1 = new int [cropHeight][cropWidth];
		for (int r = 0; r < cropHeight; r++) {
			for (int c = 0; c < cropWidth; c++) {
				int oldPIxel  = cropY + r;
				int oldPIxely  = cropX + c;
				pixels1 [r][c] = pixels [oldPIxel][oldPIxely];
			}
		}
		GImage cropImage = new GImage(pixels1);
		return cropImage;	
	}
	
	public GImage equalize(GImage source) {
		int[] equalize = luminosityHistogram(source);
		int[] cumulativeEqualize = cumulativeLuminosityHistogram(equalize);
		int[][] contrast = increaseContrast(cumulativeEqualize, source);
		GImage equalizeImage = new GImage(contrast);
		return equalizeImage;
	}	
	
	private int [] luminosityHistogram(GImage source) {
		int [][] pixels = source.getPixelArray();
		int rows = rows(pixels);
		int columns = columns(pixels);
		int [] equalize = new int [255];
		for (int r = 0; r < rows; r++) {
			for (int c = 0; c < columns; c++) {
				int color = pixels[r][c];
				int red = GImage.getRed(color); 
				int green = GImage.getGreen(color);
				int blue = GImage.getBlue(color);
				int luminosity = computeLuminosity(red, green, blue);
				equalize[luminosity]++;
			}
		}
		return equalize;
	}
	
	private int [] cumulativeLuminosityHistogram(int[] equalize) {
		int [] cumulativeEqualize = new int [255];
		for (int i = 0;i < cumulativeEqualize.length; i++) {
			cumulativeEqualize[i]++; 
		}
		return cumulativeEqualize;
	}
	
	private int[][] increaseContrast(int[] cumulativeEqualize, GImage source) {
		int [][] pixels = source.getPixelArray();
		int rows = rows(pixels);
		int columns = columns(pixels);
		int [][] contrast = new int [rows][columns];
		for (int r = 0; r < rows; r++) {
			for (int c = 0; c < columns; c++) {
				int color = pixels[r][c];
				int red = GImage.getRed(color); 
				int green = GImage.getGreen(color);
				int blue = GImage.getBlue(color);
				int luminosity = computeLuminosity(red, green, blue);
				int numPixelsLess = cumulativeEqualize[luminosity];
				int numPixelsTotal = rows * columns;
				int newRGB = 255 * (numPixelsLess/numPixelsTotal);
				contrast[r][c] = GImage.createRGBPixel(newRGB, newRGB, newRGB);
			}
		}	
		return contrast;
	}
}	

