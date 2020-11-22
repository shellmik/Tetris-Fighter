package test;
import org.junit.Test;
import static org.junit.Assert.*;
import game.*;
import gui.TileJ;
import gui.TileL;

public class TestPieceGenerator {

	@Test
	public void testGetType_1() {
		
		PieceGenerator pg = PieceGenerator.getInstance();
		assertEquals( TileJ.getInstance(), pg.getType(2) );
	}
	
	@Test
	public void testGetType_2() {
		
		PieceGenerator pg = PieceGenerator.getInstance();
		assertEquals( TileL.getInstance(), pg.getType(3) );
	}
}
