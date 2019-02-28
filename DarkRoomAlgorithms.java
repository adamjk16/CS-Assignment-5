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
		int [][] rotateLeft = new int[numberOfColumns][numberOfRows];
		for (int row = 0; row < numberOfRows; row++) {
			for (int column = 0; column < numberOfColumns; column++) {
				rotateLeft[numberOfColumns - 1 - column][row] = pixels[row][column];			//flips and switches pixel row and column
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
				rotateRight[column][numberOfRows - 1 - row] = pixels[row][column]; 								//switches pixel row and column
			}
		}	
		GImage rotateRightImage = new GImage(rotateRight);
		return rotateRightImage;
	}
	
	/*
	 * rotates an image 180 degrees
	 */
	public GImage flipHorizontal(GImage source) {
		int [][] pixels = source.getPixelArray();
		int numberOfRows = rows(pixels);
		int numberOfColumns = columns(pixels);
		int [][] flipHorizontal = new int [numberOfRows][numberOfColumns];
		for (int row= 0; row < numberOfRows; row++) {
			for (int column = 0; column < numberOfColumns; column++) {
				flipHorizontal[row][numberOfColumns - 1 - column] = pixels[row][column];			//reverses the order of pixels of each row			
			}
		}	
		GImage flippedImage = new GImage(flipHorizontal);
		return flippedImage;
	}
	
	/*
	 * inverts each color of the picture
	 */
	
	public GImage negative(GImage source) {
		int [][] pixels = source.getPixelArray();
		int numberOfRows = rows(pixels);
		int numberOfColumns = columns(pixels);
		for (int row = 0; row < numberOfRows; row++) {
			for (int column = 0; column < numberOfColumns; column++) {
				int negativePixels = pixels[row][column];							
				int red = 255 - GImage.getRed(negativePixels); 						
				int green = 255 - GImage.getGreen(negativePixels);
				int blue = 255 - GImage.getBlue(negativePixels);
				pixels[row][column] = GImage.createRGBPixel(red, blue, green);
			}
		}	
		GImage negativeImage = new GImage(pixels);
		return negativeImage;	
	}
	/*
	 * takes an image in front of a green screen and overlays it onto a background
	 */
	
	public GImage greenScreen(GImage source) {
		int [][] pixels = source.getPixelArray();
		int numberOfRows = rows(pixels);
		int numberOfColumns = columns(pixels);
		for (int row = 0; row < numberOfRows; row++) {
			for (int column = 0; column < numberOfColumns; column++) {
				int greenScreenPixel = pixels[row][column];
				int red = GImage.getRed(greenScreenPixel); 
				int green = GImage.getGreen(greenScreenPixel);
				int blue = GImage.getBlue(greenScreenPixel);
				if (green >= 2 * Math.max(red, blue)) {
					pixels [row][column] = GImage.createRGBPixel (red, green, blue, 0);
				} else {
					pixels [row][column] = GImage.createRGBPixel (red, green, blue);
				}
			}
		}	
		GImage greenScreenImage = new GImage(pixels);
		return greenScreenImage;
	}	
	
	/*
	 * blurs an image
	 */
	
	public GImage blur(GImage source) {								//received help in LAIR
		int [][] pixels = source.getPixelArray();
		int numberOfRows = rows(pixels);
		int numberOfColumns = columns(pixels);
		int [][] blur = new int [numberOfRows][numberOfColumns];
		for (int row = 0; row < numberOfRows; row++) {
			for (int column = 0; column < numberOfColumns; column++) {
				int redTotal = 0;
				int greenTotal = 0;
				int blueTotal = 0;
				int avgPixels = 0;
				for (int surroundingRows = row - 1; surroundingRows <= row + 1; surroundingRows++) {
					for (int surroundingColumns = column - 1; surroundingColumns <= column + 1; surroundingColumns++) {
						if (surroundingRows > 0 && surroundingRows < numberOfRows && surroundingColumns > 0 && surroundingColumns < numberOfColumns) {	
							int red = GImage.getRed(pixels[surroundingColumns][surroundingRows]);
							redTotal += red;
							int green = GImage.getGreen(pixels[surroundingColumns][surroundingRows]);
							greenTotal += green;
							int blue = GImage.getBlue(pixels[surroundingColumns][surroundingRows]);
							blueTotal += blue;
							avgPixels++;
						}
					}
				}		
		int avgRed = redTotal / avgPixels;
		int avgGreen = greenTotal / avgPixels;
		int avgBlue = blueTotal / avgPixels;
		blur[row][column] = GImage.createRGBPixel(avgRed, avgGreen, avgBlue);		
			}
		}
		GImage blurredImage = new GImage(blur);
		return blurredImage;
	}
	
	/*
	 * resizes an image based on user specification
	 */
	
	public GImage crop(GImage source, int cropX, int cropY, int cropWidth, int cropHeight) {
		int [][] pixels = source.getPixelArray();
		int [][] cropPixels = new int [cropHeight][cropWidth];
		for (int row = 0; row < cropHeight; row++) {
			for (int column = 0; column < cropWidth; column++) {
				int oldPixelX  = cropY + row;
				int oldPixelY  = cropX + column;
				cropPixels [row][column] = pixels [oldPixelX][oldPixelY];
			}
		}
		GImage cropImage = new GImage(cropPixels);
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

