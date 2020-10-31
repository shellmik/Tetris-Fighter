package gui;

import java.awt.Color;

public class Tile {

	public static final int COLOR_MIN = 35;
	public static final int COLOR_MAX = 255 - COLOR_MIN;

	private Color baseColor;

	private int spawnX;

	private int spawnY;

	private int dimension;

	private int rows;

	private int cols;

	private boolean[][] tiles;

	public Tile(Color color, int dimension, int cols, int rows, boolean[][] tiles) {
		this.baseColor = color;
		this.dimension = dimension;
		this.tiles = tiles;
		this.cols = cols;
		this.rows = rows;

		this.spawnX = 5 - (dimension >> 1);
		this.spawnY = getTopInset(0);
	}

	public Color getBaseColor() {
		return baseColor;
	}

	public int getDimension() {
		return dimension;
	}

	public int getSpawnColumn() {
		return spawnX;
	}

	public int getSpawnRow() {
		return spawnY;
	}

	public int getRows() {
		return rows;
	}

	public int getCols() {
		return cols;
	}

	public boolean isTile(int x, int y, int rotation) {
		return tiles[rotation][y * dimension + x];
	}

	public int getLeftInset(int rotation) {

		for (int x = 0; x < dimension; x++) {
			for (int y = 0; y < dimension; y++) {
				if (isTile(x, y, rotation)) {
					return x;
				}
			}
		}
		return -1;
	}

	public int getRightInset(int rotation) {

		for (int x = dimension - 1; x >= 0; x--) {
			for (int y = 0; y < dimension; y++) {
				if (isTile(x, y, rotation)) {
					return dimension - x;
				}
			}
		}
		return -1;
	}

	public int getTopInset(int rotation) {

		for (int y = 0; y < dimension; y++) {
			for (int x = 0; x < dimension; x++) {
				if (isTile(x, y, rotation)) {
					return y;
				}
			}
		}
		return -1;
	}

	public int getBottomInset(int rotation) {

		for (int y = dimension - 1; y >= 0; y--) {
			for (int x = 0; x < dimension; x++) {
				if (isTile(x, y, rotation)) {
					return dimension - y;
				}
			}
		}
		return -1;
	}

}
