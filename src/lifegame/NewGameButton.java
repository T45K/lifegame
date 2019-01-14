package lifegame;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.*;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class NewGameButton implements ActionListener{
	
	public void actionPerformed(ActionEvent e){

		BoardModel model = new BoardModel(20,20);
		JFrame frame = new JFrame("Lifegame");
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		JPanel base = new JPanel();
		frame.setContentPane(base);
		base.setPreferredSize(new Dimension(400,300));
		frame.setMaximumSize(new Dimension(300,200));

		JButton ub = new JButton("Undo(remaining"+model.undoCounter+")");
		
		base.setLayout(new BorderLayout());
		BoardView view = new BoardView(20,10,model,base,ub);
		base.add(view,BorderLayout.CENTER);
		
		frame.pack();
		frame.setVisible(true);
		
		JPanel buttonPanel=new JPanel();
		base.add(buttonPanel,BorderLayout.SOUTH);
		buttonPanel.setLayout(new FlowLayout());
		
		JButton nb = new JButton("Next");
		nb.addActionListener(new NextButton(model,base,ub));
		ub.addActionListener(new UndoButton(model,base,ub));
		ub.setEnabled(false);
		JButton ngb = new JButton("New Game");
		ngb.addActionListener(new NewGameButton());
		
		buttonPanel.add(nb);
		buttonPanel.add(ub);
		buttonPanel.add(ngb);
	}

}
