/*
 * File: DarkRoomAlgorithms.java
 * -------------------
 * Name: Adam Kincer
 * Section Leader: Christian Davis
 * 
 * This program can perform a variety of functions on 
 * a given photo including rotating it left, rotating it 
 * right, flipping it horizontally, making the image negative,
 * producing a green screen, blurring the image, or equalizing the image.
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
		int [][] rotateLeft = new int[columns(pixels)][rows(pixels)];
		for (int row = 0; row < rows(pixels); row++) {
			for (int column = 0; column < columns(pixels); column++) {
				rotateLeft[columns(pixels) - 1 - column][row] = pixels[row][column];			//flips and switches pixel row and column
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
		int [][] rotateRight = new int [columns(pixels)][rows(pixels)];
		for (int row = 0; row < rows(pixels); row++) {
			for (int column = 0; column < columns(pixels); column++) {
				rotateRight[column][rows(pixels) - 1 - row] = pixels[row][column]; 								//switches pixel row and column
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
		int [][] flipHorizontal = new int [rows(pixels)][columns(pixels)];
		for (int row= 0; row < rows(pixels); row++) {
			for (int column = 0; column < columns(pixels); column++) {
				flipHorizontal[row][columns(pixels) - 1 - column] = pixels[row][column];			//reverses the order of pixels of each row			
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
		for (int row = 0; row < rows(pixels); row++) {
			for (int column = 0; column < columns(pixels); column++) {
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
		for (int row = 0; row < rows(pixels); row++) {
			for (int column = 0; column < columns(pixels); column++) {
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
		int [][] blur = new int [rows(pixels)][columns(pixels)];
		for (int row = 0; row < rows(pixels); row++) {
			for (int column = 0; column < columns(pixels); column++) {
				int redTotal = 0;
				int greenTotal = 0;
				int blueTotal = 0;
				int avgPixels = 0;
				for (int surroundingRow = row - 1; surroundingRow <= row + 1; surroundingRow++) {
					for (int surroundingColumn = column - 1; surroundingColumn <= column + 1; surroundingColumn++) {
						if (surroundingRow > 0 && surroundingRow < rows(pixels) && surroundingColumn > 0 && surroundingColumn < columns(pixels)) {	
							int blurPixels = pixels [surroundingRow][surroundingColumn];
							int red = GImage.getRed(blurPixels);
							redTotal += red;
							int green = GImage.getGreen(blurPixels);
							greenTotal += green;
							int blue = GImage.getBlue(blurPixels);
							blueTotal += blue;
							pixels [surroundingRow][surroundingColumn] = GImage.createRGBPixel(red, green, blue);
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
	 * crops an image
	 */

	public GImage crop(GImage source, int cropX, int cropY, int cropWidth, int cropHeight) {
		int [][] pixels = source.getPixelArray();
		int [][] cropPixels = new int [cropHeight][cropWidth];
		for (int r = 0; r < cropHeight; r++) {
			for (int c = 0; c < cropWidth; c++) {
				int oldPixelX  = cropY + r;
				int oldPixelY  = cropX + c;
				cropPixels [r][c] = pixels [oldPixelX][oldPixelY];
			}
		}
		GImage cropImage = new GImage(cropPixels);
		return cropImage;	
	}
	
	/*
	 * grayscales the image and spreads the luminosity of its pixels 
	 * to sharpen image by increasing contrast
	 */
	
	public GImage equalize(GImage source) {
		int[] equalize = luminosityHistogram(source);								
		int[] cumulativeEqualize = cumulativeLuminosityHistogram(equalize);			
		int[][] contrast = increaseContrast(cumulativeEqualize, source);
		GImage equalizeImage = new GImage(contrast);
		return equalizeImage;
	}	
	
	/*
	 * creates array for luminosity histogram (number of pixels in image with given luminosity)
	 */
	
	private int[] luminosityHistogram(GImage source) {
		int [][] pixels = source.getPixelArray();
		int [] equalize = new int [256];
		for (int row = 0; row < rows(pixels); row++) {
			for (int column = 0; column < columns(pixels); column++) {
				int equalizePixel = pixels[row][column];
				int red = GImage.getRed(equalizePixel); 
				int green = GImage.getGreen(equalizePixel);
				int blue = GImage.getBlue(equalizePixel);
				int luminosity = computeLuminosity(red, green, blue);
				equalize[luminosity] = equalize[luminosity] + 1;
			}
		}
		return equalize;
	}
	
	/*
	 * creates array for cumulative luminosity histogram 
	 * (number of pixels in image with given luminosity value or less)
	 */
	
	private int [] cumulativeLuminosityHistogram(int[] equalize) {
		int [] cumulativeEqualize = new int [256];
		for (int i = 0; i < 255; i++) {
			cumulativeEqualize[i + 1] = cumulativeEqualize[i] + equalize[i + 1];
		}
		return cumulativeEqualize;
	}
	
	/*
	 * spreads luminosity values of pixels as far across possible luminosity
	 * range as possible, increasing contrast
	 */
	
	private int[][] increaseContrast(int[] cumulativeEqualize, GImage source) {
		int [][] pixels = source.getPixelArray();
		int [][] contrast = new int [rows(pixels)][columns(pixels)];
		for (int row = 0; row < rows(pixels); row++) {
			for (int column = 0; column < columns(pixels); column++) {
				int color = pixels[row][column];
				int red = GImage.getRed(color); 
				int green = GImage.getGreen(color);
				int blue = GImage.getBlue(color);
				int luminosity = computeLuminosity(red, green, blue);
				int numPixelsLess = cumulativeEqualize[luminosity];
				int contrastPixel = 255 * numPixelsLess/(rows(pixels) * columns(pixels));
				contrast[row][column] = GImage.createRGBPixel(contrastPixel, contrastPixel, contrastPixel);
			}
		}	
		return contrast;
	}
	
	/*
	 * puts pink filter over image
	 */
	
	public GImage filter(GImage source) {
		int [][] pixels = source.getPixelArray();
		for (int row = 0; row < rows(pixels); row++) {
			for (int column = 0; column < columns(pixels); column++) {
				int filterPixels = pixels[row][column];							
				int red = GImage.getRed(filterPixels); 						
				int blue = GImage.getBlue(filterPixels);
				pixels[row][column] = GImage.createRGBPixel(red, 0, blue);
			}
		}	
		GImage filterImage = new GImage(pixels);
		return filterImage;	
	}
}	

