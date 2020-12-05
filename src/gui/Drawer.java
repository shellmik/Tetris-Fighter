package gui;

import java.awt.Color;
import java.awt.Graphics;

public class Drawer {
	private int tile_size;
	private int shade_width;

	public Drawer(int a, int b) {
		tile_size = a;
		shade_width = b;
	}

	public void drawTile(Tile type, int x, int y, Graphics g) {
		Color baseColor = type.getBaseColor();
		g.setColor(baseColor);
		g.fillRect(x, y, tile_size, tile_size);
		g.setColor(baseColor.darker());
		g.fillRect(x, y + tile_size - shade_width, tile_size, shade_width);
		g.fillRect(x + tile_size - shade_width, y, shade_width, tile_size);

		g.setColor(baseColor.brighter());
		for (int i = 0; i < shade_width; i++) {
			g.drawLine(x, y + i, x + tile_size - i - 1, y + i);
			g.drawLine(x + i, y, x + i, y + tile_size - i - 1);
		}
	}

}
