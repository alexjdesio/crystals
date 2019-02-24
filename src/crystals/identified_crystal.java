package crystals;
import java.util.Random;
public class identified_crystal {
	Random rand = new Random();
	public int crystalTier;
	public String crystalType;
	public String[] keywords;
	public int crystalPercent;
	public String[] t2keywords;
	int rating;
	
	
	identified_crystal(int tier){
		crystalTier = tier;
		crystalType = crystal_generate_type();
		keywords = crystal_generate_keywords(); 
		crystalPercent = crystal_generate_percent(crystalTier, keywords);
		t2keywords = crystal_t2keywords();
		rating = calculate_rating();
	}
	
	public int calculate_rating() {
		int rating = 0;
		
		int percentCalc = crystalPercent/crystalTier;
		percentCalc -= 3;
		rating += percentCalc;
		
		if(keywords[0] != "") {
			rating *= 100;
		}
		if(keywords[1] != "") {
			rating *= 10;
		}
		 
		
		return rating;
	}
	
	public String[] crystal_t2keywords() {
		String[] returnArray = new String[10];
		
		for(int i = 0;i<10;i++) {
			returnArray[i] = "";
		}
		//Order for this Array: Perfect (Prismatic/Illustrious)
		
		int maxPercent = this.crystalTier * 15;
		if(this.crystalPercent == maxPercent) {
			returnArray[1] = "Prismatic";
		}
		
		else if (this.crystalPercent>maxPercent) {
			returnArray[2] = "Illustrious";
			int perfectPercent = (maxPercent + maxPercent/10);
			if(this.crystalPercent == perfectPercent) {
				returnArray[0] = "Perfect";
			}
		}
		
		
		
		return returnArray;
	}
	
	
	public String display_keywords(String[] inputArray) {
		String output = "";
		for(int i = 0;i<inputArray.length;i++) {
			output += inputArray[i];
			if(inputArray[i] != "") {
				output += " ";
			}
			
		}
		
		return output;
	}
	
	public String crystal_generate_type() {
		String type = "";
		int typeValue = rand.nextInt(3);
		
		if (typeValue == 0) {
			type += "EXP Boost";
		}
		if(typeValue == 1) {
			type+= "Token Boost";
		}
		if(typeValue == 2) {
			type+= "Sell Boost";
		}
		
		
		// roll from 1 to 3
		
		return type;
	}
	
	public int crystal_generate_percent(int tier, String[] keywords) {
		int percent = 0;
		int maxPercent = tier*15; // equals tier * 15
		int minPercent = tier*3;
		// take the tier, multiply by 15, that's the max
		// take the tier, multiply by 5, that's the min
		// if it's ancient, add 10% to the final number
		percent = rand.nextInt(13) + 3;
		percent = percent * tier;
		
		if(keywords[1] != "") { // ancient
			percent += (percent/10);
		}
		// round down to the max
		if(percent > maxPercent) {
			percent = maxPercent;
		}
		// if it's primal, add 10% to the final number
		if(keywords[0] != "") { //primal
			percent += (percent/10);
		}
		// don't round down
		
		
		return percent;
	}
	
	
	public String[] crystal_generate_keywords() {
		//Options: normal, primal, ancient, primal ancient
		String[] keywords = new String[10];
		
		//roll from 1 to 100
			// if 1, primal
		for(int i = 0;i<10;i++) {
			keywords[i] = "";
		}
		
		
		int primalRoll = rand.nextInt(100);
		

		
		if(primalRoll == 1) {
			keywords[0] = "Primal";
		}
		
		//roll from 1 to 10
		// if 1, ancient
		int ancientRoll = rand.nextInt(10);
		
		if(ancientRoll == 1) {
			keywords[1] = "Ancient";
			
		}

		return keywords;
	}
	
}
