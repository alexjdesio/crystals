package crystals;
import java.util.Scanner;
import java.util.Random;
public class Main_ {
	//variables
	int create_modifier = 0; // sets the base tier for what crystal is made
	boolean automerge = false; // merges every time new crystals are added
	int maxInvSize = 36;
	final int maxCrystalTier = 20;
	int[] crystalQuantity = new int[20]; // 20 is the current max tier supported
	identified_crystal[] identifiedArray = new identified_crystal[maxInvSize];
	int identifiedCount = 0;
	
	public void merge() {
		for(int i = 1;i<crystalQuantity.length;i++) {
			if (crystalQuantity[i] >= 2) { 
				crystalQuantity[i] -= 2; // remove two of the current tier
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
	
	public void automerge() {
		if (automerge) {
			for(int i = 0; i<create_modifier+1;i++) {
				merge();
			}
			collection();
		}
	}
	
	public void collection() {
		clear_console();
		for(int i = 1;i<crystalQuantity.length;i++) {
			System.out.println("T" + i + ": " + crystalQuantity[i]);
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
	
	public void dust(identified_crystal arg1) {
		//remove arg1 from the identified crystal array of objects
		// add 1 to the dust array at the appropriate tier
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
		else {
			System.out.println("Not enough unidentified T" + tier + " crystals.");
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
