import java.awt.Dimension;
import java.awt.EventQueue;

import javax.swing.*;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;
import java.util.Arrays; 

public class PuzzleGame {

	public JButton[] button;
	private JFrame frame;
	public int WherIsZero = 0; // pozycja liczby 0
	int[] IntNumbers = {0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15};
	int ileRuchowDoUlorzenia = 0; // licznik ruchów uk³adania
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					PuzzleGame window = new PuzzleGame();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public PuzzleGame() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 350, 315);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.X_AXIS));
		
		// miesznie tablicy
		MixBoard();
		
		// wyswietlanie
		drowButtons();		
	}
	

	public void drowButtons() { // wyswietlanie
		JPanel panel = new JPanel();
		frame.getContentPane().add(panel);
		button = new JButton[16];
		ListenForButtons lForBut = new ListenForButtons();
		for(int i=0; i<16; i++) {		
			button[i] = new JButton();
			button[i].setText(Integer.toString(IntNumbers[i]));
			button[i].setMargin(new Insets(0, 0, 0, 0));
			button[i].setPreferredSize(new Dimension(63, 63));
			button[i].addActionListener(lForBut);
			panel.add(button[i]);
		}
	}
	
	public void MixBoard() { // miesznie tablicy
		Random rand = new Random();
		for(int i=0, k=15; i<k; i++) {
			int n = rand.nextInt(k)+1;
			int p = IntNumbers[n];
			IntNumbers[n] = IntNumbers[k];
			IntNumbers[k] = p;
		}
	}
	
	public boolean CorrectNumbers(int[] Num) {
		int[] IntCorrectNumbers = {0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15};
		
		if (Arrays.equals(Num, IntCorrectNumbers)) {
			MixBoard();
			for(int i=0, k=16; i<k; i++)
				button[i].setText(Integer.toString(IntNumbers[i]));
			return true;
		} else return false;
		
	}
	
	public void swapNumbers(int i) { // zamiana liczb
		int p = IntNumbers[i];
		IntNumbers[i] = 0;
		IntNumbers[WherIsZero] = p;
		
		button[WherIsZero].setText(button[i].getText());
		WherIsZero = i;
		button[i].setText("0");
	}
	
	public class CongratulationPanel {
		public void score(int ileRuchowDoUlorzenia) {
			JPanel thePanel = new JPanel();
			JLabel theLabel = new JLabel("Score: " + Integer.toString(ileRuchowDoUlorzenia));
			thePanel.add(theLabel);
			JOptionPane.showConfirmDialog(null, thePanel, "Congratulation", JOptionPane.OK_CANCEL_OPTION);			
		}
	}

	public class ListenForButtons implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			ileRuchowDoUlorzenia++;
			for(int i=0; i<16; i++)
				if(e.getSource() == button[i]) {
					
					if( (WherIsZero==i+1 && i%4!=3) || (WherIsZero==i-1 && i%4!=0) || 
						WherIsZero==i+4 || WherIsZero==i-4) // sprawdzenie czy jest obok 0
						swapNumbers(i); // zamiana liczb
				}
			if(CorrectNumbers(IntNumbers)) { 
				CongratulationPanel aaa = new CongratulationPanel();
				aaa.score(ileRuchowDoUlorzenia);
				ileRuchowDoUlorzenia = 0;
			}
		}
	}
	
}
