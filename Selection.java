import java.util.*;

public class Selection {
	private List<Question> q = new ArrayList<>();
		
	public Selection(Backend back) {
		if ((back.getRange() < back.getQuantity()) || (back.getRange() < back.getDifficulty())) return; // ERROR
		
		int[] nums = new int[back.getQuantity()];
		boolean fit = false;
		
		nums[0] = (int) (Math.random() * back.getRange());
		
		for (int i = 1; i < back.getQuantity(); i++) {
			do {
				nums[i] = (int) (Math.random() * back.getRange());
				for (int j = 0; j < i; j++) { // in order to avoid repetitions
					if (nums[i] == nums[j]) break;
					if ((i - 1) == j) fit = true;
				}
			} while (!fit);
			fit = false;
		}
		
		for (int i = 0; i < back.getQuantity(); i++) q.add(new Question(nums[i], back));
	}
	
	public Question getQuestion(int i) {
		return q.get(i);
	}
	
	public int getLength() {
		return q.size();
	}
}