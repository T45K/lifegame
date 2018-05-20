package lifegame;
import java.util.ArrayList;

public class BoardModel {
	private int cols;
	private int rows;
	public int undoCounter;
	private boolean[][] cells,nextCells;
	private boolean[][][] undoCells;
	
	private ArrayList<BoardListener> listeners;

	BoardModel(int c,int r){
		cols=c;
		rows=r;
		cells = new boolean[c][r];
		nextCells = new boolean[c][r];
		undoCells = new boolean[32][c][r];
		listeners=new ArrayList<BoardListener>();
	}
	
	BoardModel(){
	}
	
	public void addListener(BoardListener listener){
		listeners.add(listener);
	}
	
	private void fireUpdate(){
		for(BoardListener listener:listeners){
			listener.updated(this);
		}
	}
	
	public int getCols(){
		return cols;
	}
	
	public int getRows(){
		return rows;
	}
	
	public void printForDebug(){
		for(int i=0;i<rows;i++){
			for(int j=0;j<cols;j++){
				if(cells[i][j]==false){
					System.out.print(".");
				}else{
					System.out.print("*");}
				if(j==cols-1)System.out.println();
			}
		}
	}
	
	public void changeCellState(int x,int y){
		
		undoCopy();
		
		if(cells[x][y]){
			cells[x][y]=false;
		}else{
			cells[x][y]=true;
			}
		
		fireUpdate();
		}
	
	public boolean isAlive(int x,int y){
		return(cells[x][y]);
	}
	
	
	public void next(){
		
		undoCopy();
		
		for(int p=0;p<cols;p++){
			for(int q=0;q<rows;q++){
				int s=0;
				if(p!=0){if(cells[p-1][q]==true)s++;}
				if(q!=0){if(cells[p][q-1]==true)s++;}
				if(p!=cols-1){if(cells[p+1][q]==true)s++;}
				if(q!=rows-1){if(cells[p][q+1]==true)s++;}
				if(p!=0&&q!=0){if(cells[p-1][q-1]==true)s++;}
				if(p!=0&&q!=rows-1){if(cells[p-1][q+1]==true)s++;}
				if(p!=cols-1&&q!=0){if(cells[p+1][q-1]==true)s++;}
				if(p!=cols-1&&q!=rows-1){if(cells[p+1][q+1]==true)s++;}
				if(cells[p][q]==true){if(s==2||s==3){nextCells[p][q]=true;}
													 else{nextCells[p][q]=false;}}
				if(cells[p][q]==false){if(s==3){nextCells[p][q]=true;}else{nextCells[p][q]=false;}
				}
			}
		}
		for(int p=0;p<cols;p++){
			for(int q=0;q<rows;q++){
				cells[p][q]=nextCells[p][q];
			}
		}
		fireUpdate();
	}
	
	public void undo(){

		for(int p=0;p<cols;p++){
			for(int q=0;q<rows;q++){
				cells[p][q] = undoCells[0][p][q];
			}
		}
		for(int uc = 0;uc < 31 ; uc ++){
			for(int p=0;p<cols;p++){
				for(int q=0;q<rows;q++){
					undoCells[uc][p][q]=undoCells[uc+1][p][q];
				}
			}
		}
		if(undoCounter>0)undoCounter--;
		fireUpdate();
	}
	
	public boolean isUndoable(){
		if(undoCounter>0)return true;
		else return false;
	}
	
	public void undoCopy(){

		for(int uc = 31;uc > 0 ; uc --){
			for(int p=0;p<cols;p++){
				for(int q=0;q<rows;q++){
					undoCells[uc][p][q]=undoCells[uc-1][p][q];
				}
			}
		}
		if(undoCounter!=32)undoCounter++;
		for(int p=0;p<cols;p++){
			for(int q=0;q<rows;q++){
				undoCells[0][p][q] = cells[p][q];
			}
		}
	}
}
	

