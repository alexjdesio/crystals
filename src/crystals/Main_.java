package crystals;
import java.util.Scanner;
import java.util.Random;
public class Main_ {
	//variables
	int[] crystalQuantity = new int[20]; // 20 is the current max tier supported
	int create_modifier = 0; // sets the base tier for what crystal is made
	boolean automerge = false; // merges every time new crystals are added
	
	
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
	
	
	public static void main(String[] args) {
		boolean gameOver = false;
		Scanner scnr = new Scanner(System.in);
		Main_ new_game = new Main_();
		
		identified_crystal[] identifiedArray = new identified_crystal[36];
		int identifiedCount = 0;
		
		
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
			if (selection == 6) {
				System.out.println("What tier would you like to identify?");
				new_game.show_available_tiers();
				int tier = scnr.nextInt();
					identified_crystal new_crystal = new identified_crystal(tier);
					identifiedArray[identifiedCount] = new_crystal;
					identifiedCount++;
					// display the crystal stats
					// add the crystal to the object array for crystals
			}
			
		}
		
		
	}
	
}
