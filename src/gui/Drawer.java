package gui;

import java.awt.Color;
import java.awt.Graphics;

public class Drawer {
	private int TILE_SIZE;
	private int SHADE_WIDTH;

	public Drawer(int a, int b) {
		TILE_SIZE = a;
		SHADE_WIDTH = b;
	}

	public void drawTile(Tile type, int x, int y, Graphics g) {
		Color baseColor = type.getBaseColor();
		g.setColor(baseColor);
		g.fillRect(x, y, TILE_SIZE, TILE_SIZE);

		g.setColor(baseColor.darker());
		g.fillRect(x, y + TILE_SIZE - SHADE_WIDTH, TILE_SIZE, SHADE_WIDTH);
		g.fillRect(x + TILE_SIZE - SHADE_WIDTH, y, SHADE_WIDTH, TILE_SIZE);

		g.setColor(baseColor.brighter());
		for (int i = 0; i < SHADE_WIDTH; i++) {
			g.drawLine(x, y + i, x + TILE_SIZE - i - 1, y + i);
			g.drawLine(x + i, y, x + i, y + TILE_SIZE - i - 1);
		}
	}

}
