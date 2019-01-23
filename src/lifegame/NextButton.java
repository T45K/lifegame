package lifegame;

import java.awt.event.*;


import javax.swing.JPanel;
import javax.swing.JButton;

public class NextButton implements ActionListener{
	
	BoardModel model;
	JPanel panel ;
	JButton button1;
	
	public NextButton(BoardModel model1,JPanel panel1,JButton button){
		model = model1;
		panel = panel1;
		button1=button;
	}
	
	@SuppressWarnings("deprecation")
	public void actionPerformed(ActionEvent e){
		model.next();
		panel.repaint();
		button1.setEnabled(model.isUndoable());
		button1.setLabel("Undo(remaining"+model.undoCounter+")");
	}

}
