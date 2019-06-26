import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.concurrent.atomic.*;

public class Lingvo implements ActionListener {
	private Backend backend = Backend.getInstance();
	private Selection selection = new Selection(backend);
	private int qCounter = 0;
	private AtomicBoolean needUpdate = new AtomicBoolean(false);
	Thread thrd;
	
	JLabel sc, numb, ques, res;
	JButton[] variants = new JButton[backend.getDifficulty()];
	
	public Lingvo() {
		JFrame jfrm = new JFrame("LINGVO Training (by Alex Rock-n-Roller)");
		jfrm.setLayout(new FlowLayout());
		jfrm.setSize(1024, 768);
		jfrm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		sc = new JLabel("Score: " + backend.getScore(), SwingConstants.CENTER);
		sc.setPreferredSize(new Dimension(400,20));
		sc.setFont(new Font("Arial", Font.PLAIN, 18));
		numb = new JLabel("Question " + (qCounter + 1) + "/" + selection.getLength(), SwingConstants.CENTER);
		numb.setPreferredSize(new Dimension(400,20));
		numb.setFont(new Font("Arial", Font.PLAIN, 18));
		ques = new JLabel("The word " + "'" + backend.getWord(selection.getQuestion(qCounter).getOrig(), selection.getQuestion(qCounter).getVariant(selection.getQuestion(qCounter).getRight())) + "'" + " means:", SwingConstants.CENTER);
		ques.setPreferredSize(new Dimension(400,200));
		ques.setFont(new Font("Arial", Font.PLAIN, 18));
		res = new JLabel(backend.getResult(), SwingConstants.CENTER);
		res.setPreferredSize(new Dimension(400,200));
		res.setFont(new Font("Arial", Font.PLAIN, 18));
		
		for (int i = 0; i < variants.length; i++) {
			variants[i] = new JButton(backend.getWord(selection.getQuestion(qCounter).getTransl(), selection.getQuestion(qCounter).getVariant(i)));
			variants[i].setPreferredSize(new Dimension(600,50));
			variants[i].setFont(new Font("Arial", Font.PLAIN, 18));
			variants[i].addActionListener(this);
		}
		
		jfrm.add(sc);
		jfrm.add(numb);
		jfrm.add(ques);
		for (int i = 0; i < variants.length; i++) jfrm.add(variants[i]);
		jfrm.add(res);
		jfrm.setResizable(false);
		jfrm.setVisible(true);
		
		Runnable myThread = new Runnable() {
			public void run() {
				try {
					while(qCounter != selection.getLength()) {
						if (needUpdate.get()) {
							needUpdate.set(false);
							Thread.sleep(2000);
							SwingUtilities.invokeLater(new Runnable() {
								public void run() {
									updateGUI();
								}
							});
						} else Thread.sleep(100);
					}
				} catch (InterruptedException exc) {
					System.out.println("Call to sleep was interrupted.");
					System.exit(1);
				}
			}
		};
	
		thrd = new Thread(myThread);
		thrd.start();
	}
	
	public void actionPerformed(ActionEvent ae) {
		String btn = ae.getActionCommand();
		for (int i = 0; i < variants.length; i++) {
			if (btn == backend.getWord(selection.getQuestion(qCounter).getTransl(), selection.getQuestion(qCounter).getVariant(i))) {
				backend.setAnswer(i);
				break;
			}
		}
		
		if (backend.getAnswer() == selection.getQuestion(qCounter).getRight()) {
			variants[backend.getAnswer()].setBackground(Color.green);
			backend.setScore(backend.getScore() + 1);
			backend.setResult("You're right.");
		}
		else {
			variants[backend.getAnswer()].setBackground(Color.red);
			backend.setResult("Wrong. The right answer is " + "'" + backend.getWord(selection.getQuestion(qCounter).getTransl(), selection.getQuestion(qCounter).getVariant(selection.getQuestion(qCounter).getRight())) + "'.");
		}

		res.setText(backend.getResult());
		sc.setText("Score: " + backend.getScore());
		qCounter++;
		
		//Finishing
		if (qCounter == selection.getLength()) {
			for (int i = 0; i < variants.length; i++) variants[i].setEnabled(false);
			for (int i = 0; i < variants.length; i++) variants[i].removeActionListener(this);
			res.setText("The test is over. Your score: " + backend.getScore());
			return;
		}

		for (int i = 0; i < variants.length; i++) variants[i].setEnabled(false);
		needUpdate.set(true);
	}

	public void updateGUI() {
		variants[backend.getAnswer()].setBackground(null);
		numb.setText("Question " + (qCounter + 1) + "/" + selection.getLength());
		ques.setText("The word " + "'" + backend.getWord(selection.getQuestion(qCounter).getOrig(), selection.getQuestion(qCounter).getVariant(selection.getQuestion(qCounter).getRight())) + "'" + " means:");
		res.setText("");
		for (int i = 0; i < variants.length; i++) variants[i].setText(backend.getWord(selection.getQuestion(qCounter).getTransl(), selection.getQuestion(qCounter).getVariant(i)));
		for (int i = 0; i < variants.length; i++) variants[i].setEnabled(true);
	}
	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				new Lingvo();
			}
		});
	}
}

