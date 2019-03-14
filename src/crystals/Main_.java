package crystals;
import java.util.Scanner;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Random;
import javax.swing.*;

import java.awt.*;



public class Main_ {
	//variables
	int create_modifier = 1; // sets the base tier for what crystal is made
	boolean automerge = false; // merges every time new crystals are added
	boolean autoidentify = false; // identifies every time merge occurs
	boolean autodust = false; // dusts every time crystal replaced
	boolean autosell = false;
	boolean showCollection = true;
	int maxInvSize = 36;
	int maxCrystalTier = 30; // can be upgraded
	int[] dustQuantity = new int[maxCrystalTier];
	int[] crystalQuantity = new int[maxCrystalTier]; // 20 is the current max tier supported
	identified_crystal[] identifiedArray = new identified_crystal[maxInvSize];
	int autoIdentifyTier = 10;
	int money = 0;
	
	Random rand = new Random();
	
	
	/**
	 * Enables all of the automodes for playtesting. By default, these modes are set to false.
	 */
	public void admin_enable_auto() {
		automerge = true; // merges every time new crystals are added
		autoidentify = true; // identifies every time merge occurs
		autodust = true; 
		autosell = false;// cannot have autosell and autodust enabled
	}
	
	
	/**
	 * Checks if there are two or more unidentified crystals of the same tier. If there are, it merges them together.
	 * This process repeats for every tier, and then repeats for every tier of dust.
	 */
	public void merge() {
		for(int i = 1;i<crystalQuantity.length;i++) {
			if (crystalQuantity[i] >= 2) { 
				crystalQuantity[i] -= 2; // remove two of the current tier
				crystalQuantity[i+1] += 1; // add one of the new tier
			}
		}
		for(int i = 1;i<dustQuantity.length;i++) {
			if (dustQuantity[i] >= 2) { 
				dustQuantity[i] -= 2; // remove two of the current tier
				crystalQuantity[i+1] += 1; // add one of the new tier
			}
		}
		
		
		if(!automerge) {
			collection();
		}
		
	}
	
	public void autosell(){
		if(autosell) {
			if(autodust) {
				autodust = false;
			}
		}
	}
	
	
	/**
	 * Comparator for the sort_crystals method. Does not work if there are null values in identified_array.
	 * 
	 *
	 */
	class SortByRating implements Comparator<identified_crystal> 
	{ 
	    // Used for sorting in ascending order of 
	    // roll number 
	    public int compare(identified_crystal a, identified_crystal b) 
	    { 

	    	return a.rating - b.rating; 
	    } 
	} 
	
	/*
	 * Sorts crystals in descending order based on rating. Must be done before a corrupt_all method call.
	 */
	public void sort_crystals() {
		Arrays.sort(identifiedArray, new SortByRating()); 
	}
	
	
	
	/**
	 * 
	 * @param index of crystal to be removed
	 */
	public void sell(int index) {
		identified_crystal sellcrystal = identifiedArray[index];
		int value = 0;
		
		value = (int) (Math.pow(2,sellcrystal.crystalTier) * sellcrystal.rating);

		value = value/100;
		
		value = (int) Math.max(Math.pow(2,sellcrystal.crystalTier),value);
		
		money += value;
		
		identifiedArray[index] = null;
	}
	
	
	/*
	 * Sells all crystals in the inventory.
	 */
	public void sell_all() {
		for(int i = 0; i<identifiedArray.length;i++) {
			if(identifiedArray[i]!=null) {
				sell(i);
			}
		}
	}
	
	/*
	 * Adjusts maximum inventory size and also clears the inventory.
	 */
	public void modify_maxInv(int arg1) {
		maxInvSize = arg1;
		identifiedArray = new identified_crystal[maxInvSize]; // wipes the inv
	}
	
	
	/**
	 * Intended to be used in conjunction with the automerge method
	 * Creates a new crystal and adds it to the array. If the array is full,
	 * replaces the lowest rating crystal in the inventory if the rating of the new crystal
	 * is higher than the lowest rated crystal in the inventory.
	 * 
	 * Only works if there is at least one unidentified crystal of the specified tier and will consume it.
	 * @param tier
	 * @return
	 */
	public void auto_identify_crystal(int tier) {
		if(crystalQuantity[tier]>0) {
			crystalQuantity[tier]-=1;
			identified_crystal new_crystal = new identified_crystal(tier);
			boolean added = false;
			
			for(int i = 0; i<maxInvSize;i++) {
				if(identifiedArray[i]==null) {
					identifiedArray[i] = new_crystal;
					added = true;
					break;
				}
			}
			int minRating = minimum_rating();
			
			if(added == false) {
				for(int i = 0;i<identifiedArray.length;i++) {
					if(identifiedArray[i] != null) {
						if(identifiedArray[i].rating == minRating) {
							if(new_crystal.rating>minRating) {
								if(autosell) {
									autodust = false;
									sell(i); // takes index, removes crystal, credits user money
								}
								else if(autodust) {
									autosell = false;
									identifiedArray[i] = null;
									dustQuantity[i] += 1;
								}
								else {
									identifiedArray[i] = null;
								}
								//identifiedArray[i] = null;
								identifiedArray[i] = new_crystal;
								added = true;
								
								
								break;
							}
							
						}
					}
					
				}
			}
		}
		
		
	}
	
	/*
	 * Merges all applicable tiers and autoidentifies at the specified tier.
	 */
	public void automerge() {
		if (automerge) {
			for(int i = 0; i<create_modifier+1;i++) {
				merge();
			}
		if(autoidentify) {
			auto_identify_crystal(autoIdentifyTier); // replace this eventually
		}
			//collection();
		}
	}
	
	/*
	 * Displays all unidentified crystals and their tiers.
	 */
	public void collection() {
		if(showCollection) {
			clear_console();
			for(int i = 1;i<crystalQuantity.length;i++) {
				if(crystalQuantity[i]>0) {
					System.out.println("T" + i + ": " + crystalQuantity[i]);
				}
			}
		}
		
	}
	
	/*
	 * Corrupts all crystals in the inventory.
	 */
	public void corrupt_all() {
		for(int i = 0; i<identifiedArray.length;i++) {
			if(identifiedArray[i]!=null) {
				corrupt(i);
			}
		}
	}
	
	/*
	 * Sets the boolean keyword of the crystal to true.
	 * Rolls from 0 to 12 with the following results
	 * 0-2: "Blessed", Tier +=1
	 * 3-5: "Cursed", Tier-=1;
	 * 6-8: "Destroyed", crystal = null
	 * 9-11: "+", rating *= 4;
	 * 
	 * Can only occur once on a crystal.
	 */
	public void corrupt(int indexOfCrystal) {
		if(identifiedArray[indexOfCrystal] != null) {
			if(identifiedArray[indexOfCrystal].corrupted == false) {
				identifiedArray[indexOfCrystal].corrupted = true;
				int rollCorrupt = rand.nextInt(12);
				if(rollCorrupt == 0) { // 1,2,3 upgrade %
					identifiedArray[indexOfCrystal].crystalType = "Blessed Token Boost";
					corrupt_tier_up(indexOfCrystal);
				}
				if(rollCorrupt == 1) {
					identifiedArray[indexOfCrystal].crystalType = "Blessed Sell Boost";
					corrupt_tier_up(indexOfCrystal);
				}
				if(rollCorrupt == 2) {
					identifiedArray[indexOfCrystal].crystalType = "Blessed EXP Boost";
					corrupt_tier_up(indexOfCrystal);
				}
				if((rollCorrupt>2)&&(rollCorrupt<6)){// 3,4,5 destroy
					identifiedArray[indexOfCrystal] = null;
				}
				if(rollCorrupt == 6) { // 1,2,3 upgrade %
					identifiedArray[indexOfCrystal].crystalType = "Cursed Token Boost";
					corrupt_tier_down(indexOfCrystal);
				}
				if(rollCorrupt == 7) {
					identifiedArray[indexOfCrystal].crystalType = "Cursed Sell Boost";
					corrupt_tier_down(indexOfCrystal);
				}
				if(rollCorrupt == 8) {
					identifiedArray[indexOfCrystal].crystalType = "Cursed EXP Boost";
					corrupt_tier_down(indexOfCrystal);
				}
				if((rollCorrupt>8)&&(rollCorrupt<12)) { // 9,10,11
					String[] array1 = identifiedArray[indexOfCrystal].t2keywords;
					String[] array2 = identifiedArray[indexOfCrystal].keywords;
					boolean plus = false;
					for(int i = 0;i<array1.length;i++) {
						if(array1[i]!="") {
							array1[i] += "+";
							plus = true;
							identifiedArray[indexOfCrystal].rating *= 4;
							break;
						}
					}
					if(plus == false) {
						for(int i = 0;i<array2.length;i++) {
							if(array2[i]!="") {
								array2[i] += "+";
								plus = true;
								identifiedArray[indexOfCrystal].rating *= 4;
								break;
							}
						}
					}
					
					identifiedArray[indexOfCrystal].t2keywords= array1;
					identifiedArray[indexOfCrystal].keywords= array2;
					
				}
			}
			
		}
	}
	
	
	/*
	 * Handles the "Blessed" keyword for the corrupt method.
	 */
	public void corrupt_tier_up(int indexOfCrystal) {
		int newPercent = identifiedArray[indexOfCrystal].crystalPercent;
		newPercent = newPercent / identifiedArray[indexOfCrystal].crystalTier;
		identifiedArray[indexOfCrystal].crystalTier +=1;
		newPercent = newPercent * identifiedArray[indexOfCrystal].crystalTier;
		identifiedArray[indexOfCrystal].crystalPercent = newPercent;
		identifiedArray[indexOfCrystal].rating = identifiedArray[indexOfCrystal].calculate_rating();
	}
	
	/*
	 * Handles the "Cursed" keyword for the corrupt method.
	 */
	public void corrupt_tier_down(int indexOfCrystal) {
		if(identifiedArray[indexOfCrystal].crystalTier>1) {
			int newPercent = identifiedArray[indexOfCrystal].crystalPercent;
			newPercent = newPercent / identifiedArray[indexOfCrystal].crystalTier;
			identifiedArray[indexOfCrystal].crystalTier -=1;
			newPercent = newPercent * identifiedArray[indexOfCrystal].crystalTier;
			identifiedArray[indexOfCrystal].crystalPercent = newPercent;
			identifiedArray[indexOfCrystal].rating = identifiedArray[indexOfCrystal].calculate_rating();
		}
		else {
			identifiedArray[indexOfCrystal] = null;
		}
		
		
	}
	
	/*
	 * Creates (1+create_modifier) T1 unidentified crystals and adds them to the array.
	 * Also checks for automerge, and consequently autodust and autoidentify.
	 */
	public void create_crystal() {
		crystalQuantity[1] += (1+create_modifier);
		automerge();
		collection();
	}
	
	/*
	 * Floods the console with print statements for readability.
	 */
	public void clear_console() {
		for(int i = 0;i<50;i++) {
			System.out.println("");
		}
	}
	
	

	/*
	 * Handles the print statements for the menu.
	 */
	public static void menu() {
		System.out.println("Menu:");
		System.out.println("1: Create");
		System.out.println("2: Merge");
		System.out.println("3: Collection");
		System.out.println("4: Upgrades");
		System.out.println("5: Sell");
		System.out.println("6: Identify Crystal");
		System.out.println("");
	}
	
	/*
	 * Used in the display-inventory method. Displays all key characteristics of the specified crystal.
	 */
	public void crystal_description(identified_crystal arg1) {
		System.out.print(arg1.display_keywords(arg1.t2keywords) + arg1.display_keywords(arg1.keywords));
		System.out.print("T" + arg1.crystalTier + " " + arg1.crystalType);
		System.out.print("(" + arg1.rating + ")");
		System.out.println(" " + arg1.crystalPercent + "%");
		
	}
	
	/*
	 * Handles manual identification of crystals.
	 */
	public void menu_identify_crystal(Scanner scnr) {
		System.out.println("What tier would you like to identify?");
		collection();
		int tier = scnr.nextInt();
		auto_identify_crystal(tier);
		
	}
	
	/*
	 * Displays all identified crystals in the inventory.
	 */
	public void display_inventory() {
		// displays all identified crystals
		for(int i = 0;i<maxInvSize;i++) {
			if(identifiedArray[i] != null) {
				crystal_description(identifiedArray[i]);
			}
		}
		
	}
	

	/*
	 * Calculates the minimum rated crystal in the inventory.
	 */
	public int minimum_rating() {
		int minimumRating = 0;
		for(int i = 0; i<identifiedArray.length;i++) {
			if(identifiedArray[i] != null) {
				minimumRating = identifiedArray[i].rating;
				break;
			}
		}
		for(int i = 0; i<identifiedArray.length;i++) {
			if(identifiedArray[i] != null) {
				if (minimumRating>identifiedArray[i].rating) {
					minimumRating = identifiedArray[i].rating;
				}
			}
			
		}
		return minimumRating;
	}
	
	public static void main(String[] args) {
		boolean gameOver = false;
		Scanner scnr = new Scanner(System.in);
		Main_ new_game = new Main_();
		
		
		JPanel contentPane = new JPanel(new BorderLayout());
		contentPane.setBorder(BorderFactory.createLineBorder(Color.black));
		
		Component someComponent = new JButton(); // needs text, etc 
		
		JLabel label1 = new JLabel("Image and Text", JLabel.CENTER);
		label1.setOpaque(true);
		label1.setVerticalTextPosition(JLabel.BOTTOM);
		label1.setHorizontalTextPosition(JLabel.CENTER);
		
		//unEditable JEditorPane
		//ProgressBar.java in ListProj
		
		contentPane.add(someComponent, BorderLayout.CENTER);
		
		
		
		while(gameOver) {
			menu();
		int selection = scnr.nextInt();
			if(selection == 0) {
				break; // Exit
			}
			if (selection == 1) {
				new_game.create_crystal();
			}
			if (selection == 2) {
				new_game.merge();
			}
			if (selection == 3) {
				new_game.collection();
			}
			
			if (selection == 4) {
				
			}
			
			if (selection == 6) {
				new_game.menu_identify_crystal(scnr);

			}
			
			if (selection == 7) {
				new_game.display_inventory(); 
			}
			
			if (selection == 8) {
				new_game.automerge = true; // enables automerge
			}
			
		}
		
		
	}
	
}
