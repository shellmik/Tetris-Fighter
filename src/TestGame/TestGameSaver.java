package TestGame;

import org.junit.Test;
import static org.junit.Assert.*;
import game.*;

public class TestGameSaver {
	@Test
	public void testDisplay() {
		GameSaver gs = GameSaver.getInstance();
		gs.display();
	}
	
	@Test
	public void testSaveRankingAndSave() {
		GameSaver gs = GameSaver.getInstance();
		User user = new User("Andi", 50, "2018-2-19  18:24");
		gs.saveRankingList(user);
		gs.display();
		gs.save("andi", 50, "2018-2-19  18:24");
		gs.display();
	}
	
	@Test
	public void testClean() {
		
		GameSaver gs = GameSaver.getInstance();
		User user = new User("Andi", 50, "2018-2-19  18:24");
		gs.saveRankingList(user);
		gs.display();
		gs.clean();
		gs.display();
	}
	
	@Test
	public void testOpenRankingList() {
		
		GameSaver gs = GameSaver.getInstance();
		gs.openRankingList();
	}
	
	
}
