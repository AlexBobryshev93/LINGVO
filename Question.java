import java.util.*;

public class Question {
	private int orig, transl, right;
	private List<Integer> variants = new ArrayList<>();
	
	public Question(int num, Backend backend) {
		boolean fit = false;
		
		orig = (int) (Math.random() * 2);
		transl = 0;
		if (orig == 0) transl = 1; //the language of translation and the language of original should be different
		
		right = (int) (Math.random() * backend.getDifficulty());
		for (int i = 0; i < backend.getDifficulty(); i++) variants.add(-1); //so variants collection does not contain variants before the selection
		variants.set(right, num);

		for (int i = 0; i < backend.getDifficulty(); i++) {
			if (variants.get(i) == num) continue;
			do {
				do {
					variants.set(i, (int) (Math.random() * backend.getRange()));
				} while (variants.get(i) == num); // check if the next generated variant is the same with the right
				if (i != 0) {  // in order to avoid repetitions
					for (int j = 0; j < i; j++) {
						if (variants.get(i) == variants.get(j)) break;
						if ((i - 1) == j) fit = true;
					}
				}
				else fit = true;
			} while (!fit);
			fit = false;
		}
	}
	
	public int getOrig() {
		return orig;
	}
	
	public int getTransl() {
		return transl;
	}
	
	public int getRight() {
		return right;
	}

	public int getVariant(int i) {
		return variants.get(i);
	}
}