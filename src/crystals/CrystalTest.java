package crystals;

import org.junit.*;
import static org.junit.Assert.*;
/**


import org.junit.Before;
import org.junit.Test;

**/
public class CrystalTest {
	Main_ new_game;
	
	@Before
	public void before() {
		new_game = new Main_();
	}
	/**
	@Test
	public void identify_36_crystals_T1() {
		for(int i = 0;i<40;i++) {
			new_game.create_crystal();
		}
		for(int i = 0;i<36;i++) {
			new_game.menu_identify_crystal(1);
		}
		
		new_game.display_inventory();
		
		assertEquals(true,true);
	}
	**/
	@Test
	public void identify_36_crystals_T10() {
		int testingVal = 500;
		
		new_game.modify_maxInv(testingVal);
		
		for(int i = 0;i<testingVal;i++) {
			new_game.create_crystal();
		}
		for(int i = 0;i<testingVal;i++) {
			new_game.admin_identify_crystal(10);
		}
		
		new_game.display_inventory();
		
		assertEquals(true,true);
	}
}
