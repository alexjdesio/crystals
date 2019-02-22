package crystals;
import java.util.Random;
public class identified_crystal {
	Random rand = new Random();
	
	identified_crystal(int tier){
		int crystalTier = tier;
		String crystalType = crystal_generate_type();
		String[] keywords = crystal_generate_keywords(); 
		int crystalPercent = crystal_generate_percent(crystalTier, keywords);
	}
	
	public String display_keywords(String[] inputArray) {
		String output = "";
		for(int i = 0;i<inputArray.length;i++) {
			output += inputArray[i];
			output += " ";
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
		String[] keywords = new String[2];
		
		//roll from 1 to 100
			// if 1, primal
		if(true) {
			keywords[0] = "primal";
		}
		
		//roll from 1 to 10
		// if 1, ancient
		if(true) {
			keywords[1] = "ancient";
			
		}

		return keywords;
	}
	
}
