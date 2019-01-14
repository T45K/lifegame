package lifegame;

import java.awt.Graphics;
import java.awt.event.*;
import javax.swing.JPanel;
import java.awt.Point;
import javax.swing.JButton;

public class BoardView extends JPanel implements BoardListener,MouseListener,MouseMotionListener{
	public int cols;
	public int rows;
	public int w,h,d,iDragg,jDragg;
	BoardModel model;
	JPanel panel;
	JButton button;

	public BoardView(int c,int r,BoardModel m,JPanel p,JButton b){
		cols=c;
		rows=r;
		model = m;
		panel = p;
		button = b;
		this.addMouseListener(this);
		this.addMouseMotionListener(this);
	}
	
	public BoardView(){}
	
	public void updated(BoardModel m){}
	
	public void mouseClicked(MouseEvent e){}
	
	public void mouseEntered(MouseEvent e){}
	public void mouseExited(MouseEvent e){}
	public void mousePressed(MouseEvent e){
		w=this.getWidth();
		h=this.getHeight();
		Point point = e.getPoint();
		int i=0,j=0;
		int wr=w/rows,hc=h/cols;
		int modW=w%rows,modH=h%cols;
		
		if(wr>hc){
			d=w-rows*hc;
			while(i*hc+modH/2<point.y) i++;
			while(d/2+j*hc<point.x) j++;
			
			if(j>0&&j<rows+1)model.changeCellState(i-1, j-1);
			
		}
		else{
			d=h-cols*wr;
			while(d/2+i*wr<point.y) i++;
			while(j*w/rows+modW/2<point.x) j++;
			
			if(i>0&&i<cols+1)model.changeCellState(i-1, j-1);
			
		}
		
		panel.repaint();
		button.setEnabled(model.isUndoable());
		button.setLabel("Undo(remaining"+model.undoCounter+")");
		
		jDragg=j;
		iDragg=i;
	}
	public void mouseReleased(MouseEvent e){}
	
	public void mouseDragged(MouseEvent e){
		w=this.getWidth();
		h=this.getHeight();
		Point point = e.getPoint();
		int i=0,j=0;
		int wr=w/rows,hc=h/cols;
		int modW=w%rows,modH=h%cols;
		
		if(wr>hc){
			d=w-rows*hc;
			while(i*hc+modH/2<point.y) i++;
			while(d/2+j*hc<point.x) j++;
			
			if(j>0&&j<rows+1&&i>0&&i<cols+1){
				if(i!=iDragg||j!=jDragg){
					model.changeCellState(i-1, j-1);
					iDragg=i;
					jDragg=j;
				}
			}else{iDragg=-1;jDragg=-1;}
			
		}
		else{
			d=h-cols*wr;
			while(d/2+i*wr<point.y) i++;
			while(j*wr+modW/2<point.x) j++;
			
			if(i>0&&i<cols+1&&j>0&&j<rows+1){
				if(i!=iDragg||j!=jDragg){
					model.changeCellState(i-1, j-1);
					iDragg=i;
					jDragg=j;
				}
			}else{iDragg=-1;jDragg=-1;}
			
		}
		
		panel.repaint();
		button.setEnabled(model.isUndoable());
		button.setLabel("Undo(remaining"+model.undoCounter+")");
		
	}
	
	public void mouseMoved(MouseEvent e){}

	@Override
	public void paint(Graphics g){
		
		w=this.getWidth();
		h=this.getHeight();
		int wr=w/rows,hc=h/cols;
		int modW=w%rows,modH=h%cols;
		
		if(wr>hc){
			d=w-rows*hc;
			g.drawLine(d/2,h-1-modH/2,w-1-d/2,h-1-modH/2);
			g.drawLine(w-1-d/2,modH/2,w-1-d/2,h-1-modH/2);
			
			for(int i=0;i<cols;i++){
				g.drawLine(d/2,i*hc+modH/2,w-1-d/2,i*hc+modH/2);
			}
			for(int i=0;i<rows;i++){
				g.drawLine(i*hc+d/2,modH/2,i*hc+d/2,h-1-modH/2);
			}
			for(int c=0;c<cols;c++){
				for(int r=0;r<rows;r++){
					if(model.isAlive(c,r)==true)
						g.fillRect(r*hc+d/2,c*hc+modH/2,hc+1,hc+1);
				}
			}			
		}
		else{
			d=h-cols*wr;
			g.drawLine(modW/2,h-1-d/2,w-1-modW/2,h-1-d/2);
			g.drawLine(w-1-modW/2,d/2,w-1-modW/2,h-1-d/2);
			
			for(int i=0;i<cols;i++){
				g.drawLine(modW/2,i*wr+d/2,w-1-modW/2,i*wr+d/2);
			}
			for(int i=0;i<rows;i++){
				g.drawLine(i*wr+modW/2,d/2,i*wr+modW/2,h-1-d/2);
			}
			for(int c=0;c<cols;c++){
				for(int r=0;r<rows;r++){
					if(model.isAlive(c,r)==true)
						g.fillRect(r*wr+modW/2,c*wr+d/2,wr+1,wr+1);
				}
			}
		}		
	}

}
