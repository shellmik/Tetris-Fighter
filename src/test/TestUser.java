package test;
import org.junit.Test;
import static org.junit.Assert.*;
import game.*;

public class TestUser {

	@Test
	public void testGetName_1() {
		
		User user = new User("Andi", 50, "2018-2-19  18:24");
		String result = user.getName();
		assertEquals("Andi", result);
	}
	
	@Test
	public void testGetName_2() {
		
		User user = new User("Tim", 50, "2018-2-19  18:24");
		String result = user.getName();
		assertEquals("Tim", result);
	}
	
	@Test
	public void testGetName_3() {
		
		User user = new User();
		user.setName( "andi" );
		String result = user.getName();
		assertEquals("andi", result);
	}
	
	@Test
	public void testGetName_4() {
		
		User user = new User();
		user.setName( "tim" );
		String result = user.getName();
		assertEquals("tim", result);
	}
	
	@Test
	public void testScore_1() {
		
		User user = new User("Andi", 50, "2018-2-19 18:31");
		int result = user.getScore();
		assertEquals(50, result);
	}
	
	@Test
	public void testScore_2() {
		
		User user = new User("Andi", 200, "2018-2-19 18:31");
		int result = user.getScore();
		assertEquals(200, result);
	}
	
	@Test
	public void testScore_3() {
		
		User user = new User();
		user.setScore(123);
		int result = user.getScore();
		assertEquals(123, result);
	}
	
	@Test
	public void testScore_4() {
		
		User user = new User();
		user.setScore(321);
		int result = user.getScore();
		assertEquals(321, result);
	}
	
	@Test
	public void testGetDate_1() {
		
		User user = new User("Andi", 50, "2018-2-19  18:24");
		String result = user.getDate();
		assertEquals("2018-2-19  18:24", result);
	}
	
	@Test
	public void testGetDate_2() {
		
		User user = new User("Andi", 50, "2019-3-19  15:24");
		String result = user.getDate();
		assertEquals("2019-3-19  15:24", result);
	}
	
	@Test
	public void testGetDate_3() {
		
		User user = new User();
		user.setDate("2019-2-19  18:24");
		String result = user.getDate();
		assertEquals("2019-2-19  18:24", result);
	}
	
	@Test
	public void testGetDate_4() {
		
		User user = new User();
		user.setDate("2016-2-19  3:24");
		String result = user.getDate();
		assertEquals("2016-2-19  3:24", result);
	}
	
	@Test
	public void testTo_String() {
		
		User user = new User("Andi", 50, "2018-2-19 18:31");
		String result = user.toString();
		assertEquals(String.format("%-10s%-6s%-20s", "Andi", 50, "2018-2-19 18:31"), result);
	}
	
	
}
