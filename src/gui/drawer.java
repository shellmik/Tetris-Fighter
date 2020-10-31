package gui;

import java.awt.Color;
import java.awt.Graphics;

public class drawer {
	private int TILE_SIZE;
	private int SHADE_WIDTH;

	public drawer(int a, int b) {
		TILE_SIZE = a;
		SHADE_WIDTH = b;
	}

	// acdsjncv
	public void drawTile(Tile type, int x, int y, Graphics g) {
		g.setColor(type.getBaseColor());
		g.fillRect(x, y, TILE_SIZE, TILE_SIZE);

		g.setColor(type.getDarkColor());
		g.fillRect(x, y + TILE_SIZE - SHADE_WIDTH, TILE_SIZE, SHADE_WIDTH);
		g.fillRect(x + TILE_SIZE - SHADE_WIDTH, y, SHADE_WIDTH, TILE_SIZE);

		g.setColor(type.getLightColor());
		for (int i = 0; i < SHADE_WIDTH; i++) {
			g.drawLine(x, y + i, x + TILE_SIZE - i - 1, y + i);
			g.drawLine(x + i, y, x + i, y + TILE_SIZE - i - 1);
		}
	}

	public void drawTile(Color base, Color light, Color dark, int x, int y, Graphics g) {

		g.setColor(base);
		g.fillRect(x, y, TILE_SIZE, TILE_SIZE);

		g.setColor(dark);
		g.fillRect(x, y + TILE_SIZE - SHADE_WIDTH, TILE_SIZE, SHADE_WIDTH);
		g.fillRect(x + TILE_SIZE - SHADE_WIDTH, y, SHADE_WIDTH, TILE_SIZE);

		g.setColor(light);
		for (int i = 0; i < SHADE_WIDTH; i++) {
			g.drawLine(x, y + i, x + TILE_SIZE - i - 1, y + i);
			g.drawLine(x + i, y, x + i, y + TILE_SIZE - i - 1);
		}
	}
}
