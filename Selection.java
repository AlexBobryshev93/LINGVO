import java.util.*;

public class Selection {
	private List<Question> q = new ArrayList<>();
		
	public Selection(Backend backend) {
		if ((backend.getRange() < backend.getQuantity()) || (backend.getRange() < backend.getDifficulty())) return; // ERROR
		
		int[] nums = new int[backend.getQuantity()];
		boolean fit = false;
		
		nums[0] = (int) (Math.random() * backend.getRange());
		
		for (int i = 1; i < backend.getQuantity(); i++) {
			do {
				nums[i] = (int) (Math.random() * backend.getRange());
				for (int j = 0; j < i; j++) { // in order to avoid repetitions
					if (nums[i] == nums[j]) break;
					if ((i - 1) == j) fit = true;
				}
			} while (!fit);
			fit = false;
		}
		
		for (int i = 0; i < backend.getQuantity(); i++) q.add(new Question(nums[i], backend));
	}
	
	public Question getQuestion(int i) {
		return q.get(i);
	}
	
	public int getLength() {
		return q.size();
	}
}