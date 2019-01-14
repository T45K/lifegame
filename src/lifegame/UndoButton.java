package lifegame;

import java.awt.event.*;

import javax.swing.JPanel;
import javax.swing.JButton;
 
public class UndoButton implements ActionListener {
	
	BoardModel model;
	JPanel panel;
	JButton button1;
	
	public UndoButton(BoardModel model1,JPanel panel1,JButton button){
	
	model=model1;
	panel=panel1;
	button1=button;
	}
	
	public void actionPerformed(ActionEvent e){
		model.undo();
		panel.repaint();
		button1.setEnabled(model.isUndoable());
		button1.setLabel("Undo(remaining"+model.undoCounter+")");
	}

}
