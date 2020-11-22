package test;

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
	public void testClean() {
		
		GameSaver gs = GameSaver.getInstance();
		User user = new User("Andi", 50, "2018-2-19  18:24");
		gs.saveRankingList(user);
		gs.display();
		gs.clean();
		gs.display();
	}
	
	@Test
	public void testOpenRankingList_1() {
		
		GameSaver gs = GameSaver.getInstance();
		gs.openRankingList();
	}
	
	@Test
	public void testOpenRankingList_2() {
		
		GameSaver gs = GameSaver.getInstance();
		gs.clean();
		gs.openRankingList();
	}
	
	@Test
	 public void testSaveRanking() {
	  GameSaver gs = GameSaver.getInstance();
	  User user = new User("Andi", 100, "2018-2-19  18:24");
	  gs.saveRankingList(user);
	  User user2 = new User("Andi", 50, "2018-2-19  18:24");
	  gs.saveRankingList(user2);
	 }
	 
	 @Test
	 public void testSave() {
	  GameSaver gs = GameSaver.getInstance();
	  gs.save("Andi", 100, "2018-2-19  18:24");
	  gs.save("andi", 50, "2018-2-19  18:24");
	  gs.display();
	 }
	 
	 @Test
	 public void testSaveRankingAndSave() {
	  GameSaver gs = GameSaver.getInstance();
	  User user = new User("Andi", 100, "2018-2-19  18:24");
	  gs.saveRankingList(user);
	  User user2 = new User("Andi", 50, "2018-2-19  18:24");
	  gs.saveRankingList(user2);
	  gs.display();
	  gs.save("andi", 50, "2018-2-19  18:24");
	  gs.display();
	 }
	
}
