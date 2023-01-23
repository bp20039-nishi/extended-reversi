package all;

public class Board {
	
	private Stone[][] board;	//盤面上の石の配置
	private int[][] cellRate;	//盤面の得点
	
	/*初期処理*/
	Board(int[][] cellRates) {
		/*引数から盤面の点数を設定*/
		
		cellRate = new int[8][8];
		setAllCellRate(cellRates);
		
		/*最初の石の配置*/
		board = new Stone[8][8];
		for(int i=0; i<8; i++)
			for(int j=0; j<8; j++)
				board[i][j] = new Stone();
		setStone(Color.Black, 3, 4, 1);
		setStone(Color.Black, 4, 3, 1);
		setStone(Color.White, 3, 3, 1);
		setStone(Color.White, 4, 4, 1);	
	}
	
	/*指定した座標の盤面の点数を変更する*/
	public void setCellRate(int stonePlaceX, int stonePlaceY, int cellRate) {
		this.cellRate[stonePlaceX][stonePlaceY] = cellRate;
	}
	
	/*全ての盤面の点数を変更する*/
	public void setAllCellRate(int[][] cellRates) {
		this.cellRate = cellRates;
	}
	
	/*盤面の点数を返す*/
	public int[][] getCellRate() {
		return this.cellRate;
	}
	
	/*盤面の石の配置を返す*/
	public Stone[][] getBoard() {
		return this.board;
	}
	
	/*指定した座標に石を置く*/
	public void setStone(Color color, int stonePlaceX, int stonePlaceY, int stoneRate) {
		this.board[stonePlaceX][stonePlaceY].setColor(color);
		this.board[stonePlaceX][stonePlaceY].setScore(stoneRate);
	}
	
	/*指定した座標の石をひっくり返す*/
	public void turnOver(int stonePlaceX, int stonePlaceY) {
		Color color = this.board[stonePlaceX][stonePlaceY].getColor();
		
		if(color == Color.White)
			this.board[stonePlaceX][stonePlaceY].setColor(Color.Black);
		
		else if(color == Color.Black)
			this.board[stonePlaceX][stonePlaceY].setColor(Color.White);
		
	}
	
	/*指定した座標に石をおいて，挟んだ石をひっくり返す*/
	public void setAndTurnOver(Color color, int stonePlaceX,int stonePlaceY,int stoneRate) {
		
		this.board[stonePlaceX][stonePlaceY].setColor(color);
		this.board[stonePlaceX][stonePlaceY].setScore(stoneRate);
		check(color, stonePlaceX, stonePlaceY, true);
		
	}
	
	/*それぞれの色（[0]=white [1]=black）の合計得点を計算する*/
	public int[] countScore() {
		int[] score = new int[2];
		
		for(int i=0; i<8; i++)
			for(int j=0; j<8; j++) {
				if( this.board[i][j].getColor() == Color.White ) score[0] +=  this.cellRate[i][j] * this.board[i][j].getScore();
				if( this.board[i][j].getColor() == Color.Black ) score[1] +=  this.cellRate[i][j] * this.board[i][j].getScore();
			}
		return score;
	}
	
	/*石の置ける場所を判定する*/
	public boolean[][] judgeCandidate(Color color) {
		boolean[][] candidate = new boolean[8][8];
		
		for(int i=0; i<8; i++) {
			for(int j=0; j<8; j++) {
				
				/*既に石が置いてある場合*/
				if(this.board[i][j].getColor() != Color.None) {
					candidate[i][j] = false;
				}
				/*石が置かれていない場合*/
				else
					candidate[i][j] = check(color, i, j, false);
			}
		}
		
		return candidate;
	}
	
	/*置ける場所の判定とひっくり返す時に使う関数*/
	private boolean check(Color color, int x, int y, boolean reverse) {
		int k;
		
		/* x+ */
		for(k=1; x+k<8; ++k) {
			if(this.board[x+k][y].getColor() == color) {
				k -= 1;
				break;
			}
			else if(this.board[x+k][y].getColor() == Color.None) {
				k = 0;
				break;
			}
		}
		if(x+k > 6)k=0; 
		if(reverse)
			for(;k>0; k--) turnOver(x+k, y);
		else if(k!=0) {
			return true;
		}
		
		/* x- */
		for(k=1; x-k>=0; k++) {
			if(this.board[x-k][y].getColor() == color) {
				k -= 1;
				break;
			}
			else if(this.board[x-k][y].getColor() == Color.None) {
				k = 0;
				break;
			}
		}
		if(x-k < 1)k=0;
		if(reverse)
			for(;k>0; k--) turnOver(x-k, y);
		else if(k!=0) {
			return true;
		}
		
		/* y+ */
		for(k=1; y+k<8; k++) {
			if(this.board[x][y+k].getColor() == color) {
				k -= 1;
				break;
			}
			else if(this.board[x][y+k].getColor() == Color.None) {
				k = 0;
				break;
			}
		}
		if(y+k > 6)k=0;
		if(reverse)
			for(;k>0; k--) turnOver(x, y+k);
		else if(k!=0) {
			return true;
		}
		/* y- */
		for(k=1; y-k>=0; k++) {
			if(this.board[x][y-k].getColor() == color) {
				k -= 1;
				break;
			}
			else if(this.board[x][y-k].getColor() == Color.None) {
				k = 0;
				break;
			}
		}
		if(y-k < 1)k=0;
		if(reverse)
			for(;k>0; k--) turnOver(x, y-k);
		else if(k!=0) {
			return true;
		}
		
		/* x+ y+ */
		for(k=1; x+k<8 && y+k<8; k++) {
			if(this.board[x+k][y+k].getColor() == color) {
				k -= 1;
				break;
			}
			else if(this.board[x+k][y+k].getColor() == Color.None) {
				k = 0;
				break;
			}
		}
		if(x+k>6 || y+k>6)k=0;
		if(reverse)
			for(;k>0; k--) turnOver(x+k, y+k);
		else if(k!=0) {
			return true;
		}	
		/* x- y- */
		for(k=1; x-k>=0 && y-k>=0; k++) {
			if(this.board[x-k][y-k].getColor() == color) {
				k -= 1;
				break;
			}
			else if(this.board[x-k][y-k].getColor() == Color.None) {
				k = 0;
				break;
			}
		}
		if(x-k<1 || y-k<1)k=0;
		if(reverse)
			for(;k>0; k--) turnOver(x-k, y-k);
		else if(k!=0) {
			return true;
		}
		
		/* x+ y- */
		for(k=1; x+k<8 && y-k>=0; k++) {
			if(this.board[x+k][y-k].getColor() == color) {
				k -= 1;
				break;
			}
			else if(this.board[x+k][y-k].getColor() == Color.None) {
				k = 0;
				break;
			}
		}
		if(x+k>6 || y-k<1)k=0;
		if(reverse)
			for(;k>0; k--) turnOver(x+k, y-k);
		else if(k!=0) {
			return true;
		}
		
		/* x- y+ */
		for(k=1; x-k>=0 && y+k<8; k++) {
			if(this.board[x-k][y+k].getColor() == color) {
				k -= 1;
				break;
			}
			else if(this.board[x-k][y+k].getColor() == Color.None) {
				k = 0;
				break;
			}
		}
		if(x-k<1 || y+k>6)k=0;
		if(reverse)
			for(;k>0; k--) turnOver(x-k, y+k);
		else if(k!=0) {
			return true;
		}
		
		return false;
	}
	
	
}
