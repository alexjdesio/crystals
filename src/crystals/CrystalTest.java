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
	/**
	@Test
	public void identify_36_crystals_T10() {
		int testingVal = 20;
		int testingVal2 = 500000;
		new_game.admin_enable_auto();
		new_game.modify_maxInv(testingVal);
		new_game.showCollection = false;
		
		for(int i = 0;i<testingVal2;i++) {
			new_game.create_crystal();
		}

		//new_game.display_inventory();
		new_game.corrupt_all();
		new_game.display_inventory();
		
		assertEquals(true,true);
	}
	**/
	@Test
	public void sellAllTest() {
		new_game.admin_enable_auto(); // sets all 'auto' booleans to true for testing
		new_game.autosell = true;
		new_game.autoIdentifyTier = 5;
		new_game.showCollection = false;
		
		for(int i = 0;i<40000;i++) {
			new_game.create_crystal();
		}
		
		new_game.sort_crystals();
		//new_game.corrupt_all();
		new_game.display_inventory();
		System.out.println(new_game.money);
		new_game.sell_all();
		System.out.println(new_game.money);
		
	}
	
}
