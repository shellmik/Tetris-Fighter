package test;

import static org.junit.Assert.*;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JFrame;
import javax.swing.JPanel;

import org.junit.Test;

import gui.BoardPanel;
import gui.Drawer;
import gui.Panel;
import gui.Tile;
import gui.TileO;

public class TestDrawer {

	@Test
	public void testDrawer() {
		Drawer drawer = new Drawer(4, 1);
	}
	
}
