package crystals;
import java.util.Scanner;
import java.util.Random;
public class Main_ {
	//variables
	int create_modifier = 1; // sets the base tier for what crystal is made
	boolean automerge = false; // merges every time new crystals are added
	boolean autoidentify = false; // identifies every time merge occurs
	boolean autodust = false; // dusts every time crystal replaced
	boolean showCollection = true;
	int maxInvSize = 36;
	int maxCrystalTier = 30; // can be upgraded
	int[] dustQuantity = new int[maxCrystalTier];
	int[] crystalQuantity = new int[maxCrystalTier]; // 20 is the current max tier supported
	identified_crystal[] identifiedArray = new identified_crystal[maxInvSize];
	int identifiedCount = 0;
	int autoIdentifyTier = 10;
	
	Random rand = new Random();
	
	public void admin_enable_auto() {
		automerge = true; // merges every time new crystals are added
		autoidentify = true; // identifies every time merge occurs
		autodust = true; 
	}
	
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
	
	public void modify_maxInv(int arg1) {
		maxInvSize = arg1;
		identifiedArray = new identified_crystal[maxInvSize]; // wipes the inv
	}
	
	public identified_crystal auto_identify_crystal(int tier) {
		identified_crystal new_crystal = new identified_crystal(tier);
		for(int i = 0; i<maxInvSize;i++) {
			if(identifiedArray[i]==null) {
				identifiedArray[i] = new_crystal;
				break;
			}
		}
		return new_crystal;
	}
	
	
	public void automerge() {
		if (automerge) {
			for(int i = 0; i<create_modifier+1;i++) {
				merge();
			}
		if(autoidentify) {
			crystal_replace(auto_identify_crystal(autoIdentifyTier)); // replace this eventually
		}
			//collection();
		}
	}
	
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
	
	public void corrupt_all() {
		for(int i = 0; i<identifiedArray.length;i++) {
			if(identifiedArray[i]!=null) {
				corrupt(i);
			}
		}
	}
	
	
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
	
	public void corrupt_tier_up(int indexOfCrystal) {
		int newPercent = identifiedArray[indexOfCrystal].crystalPercent;
		newPercent = newPercent / identifiedArray[indexOfCrystal].crystalTier;
		identifiedArray[indexOfCrystal].crystalTier +=1;
		newPercent = newPercent * identifiedArray[indexOfCrystal].crystalTier;
		identifiedArray[indexOfCrystal].crystalPercent = newPercent;
		identifiedArray[indexOfCrystal].rating = identifiedArray[indexOfCrystal].calculate_rating();
	}
	
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
	
	
	public void create_crystal() {
		crystalQuantity[1] += (1+create_modifier);
		automerge();
		collection();
	}
	
	public void clear_console() {
		for(int i = 0;i<50;i++) {
			System.out.println("");
		}
	}
	
	public void identify(int crystalTier) {
		if(crystalQuantity[crystalTier]>0) {
			crystalQuantity[crystalTier] -= 1;
			// create a new crystal object
		}
	}
	
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
	
	public void show_available_tiers() {
		for(int i = 0;i<crystalQuantity.length;i++) {
			if(crystalQuantity[i]>0) {
				System.out.println("T" + i + ": " + crystalQuantity[i]);
			}
		}
	}
	
	
	public void identify_crystal(int tier) {
		identified_crystal new_crystal = new identified_crystal(tier);
		crystal_description(new_crystal);
		identifiedArray[identifiedCount] = new_crystal;
		identifiedCount++;
	}
	
	
	
	
	public void crystal_description(identified_crystal arg1) {
		System.out.print(arg1.display_keywords(arg1.t2keywords) + arg1.display_keywords(arg1.keywords));
		System.out.print("T" + arg1.crystalTier + " " + arg1.crystalType);
		System.out.print("(" + arg1.rating + ")");
		System.out.println(" " + arg1.crystalPercent + "%");
		
	}
	
	public void menu_identify_crystal(Scanner scnr) {
		System.out.println("What tier would you like to identify?");
		show_available_tiers();
		int tier = scnr.nextInt();
		if((crystalQuantity[tier]>0) && (identifiedCount<maxInvSize)) {
			identify_crystal(tier);
		}
		else {
			System.out.println("Not enough unidentified T" + tier + " crystals.");
		}
		
	}
	
	public void menu_identify_crystal(int tier) {
		if((crystalQuantity[tier]>0) && (identifiedCount<maxInvSize)) {
			identify_crystal(tier);
		}
		
	}
	
	public void admin_identify_crystal(int tier) {
		identified_crystal new_crystal = new identified_crystal(tier);
		crystal_description(new_crystal);
		identifiedArray[identifiedCount] = new_crystal;
		identifiedCount++;
	}
	
	public void display_inventory() {
		// displays all identified crystals
		for(int i = 0;i<maxInvSize;i++) {
			if(identifiedArray[i] != null) {
				crystal_description(identifiedArray[i]);
			}
		}
		
	}
	
	public void crystal_replace(identified_crystal arg1) {
		int minRating = minimum_rating();
		
		for(int i = 0;i<identifiedArray.length;i++) {
			if(identifiedArray[i] != null) {
				if(identifiedArray[i].rating == minRating) {
					if(arg1.rating>minRating) {
						identifiedArray[i] = null;
						identifiedArray[i] = arg1;
						identifiedCount--;
						if(autodust) {
							//dustQuantity[i] += 1;
						}
						break;
					}
					
				}
			}
			
		}
		
	}
	
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
		
		while(!gameOver) {
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
