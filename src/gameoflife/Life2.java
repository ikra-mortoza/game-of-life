package gameoflife;
import java.util.Random;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class Life2 {

	
	public static int numRows(boolean[][]cells) {
		return cells.length;
	}
	
	public static int numCols(boolean[][]cells) {
		if(Life2.numRows(cells) > 0) {
			return cells[0].length;
		}
		else {
			return 0;
		}
	}
	
	public static boolean isValid(boolean[][]cells, int row, int col) {
		if(row < 0 || row >= Life2.numRows(cells)) {
			return false;
		}
		if(col < 0 || col >= Life2.numCols(cells)) {
			return false;
		}
		else {
			return true;
		}
	}
	
	public static boolean[][] clone(boolean[][]cells) {
		int rows = Life2.numRows(cells);
		int cols = Life2.numCols(cells);
		boolean copy[][] = new boolean[rows][cols];
		for(int i = 0; i < rows; i++) {
			for(int j = 0; j < cols; j++) {
				copy[i][j] = cells[i][j];
			}
		}
		return copy;
	}
	
	public static void printCells(boolean[][]cells) {
		for(int i = 0; i < cells.length; i++) {
			for(int j = 0; j < cells[i].length; j++) {
				if(cells[i][j]) {
					System.out.print("#");
				}
				else {
					System.out.print("-");
				}
			}
			System.out.println();
		}
	}
	
	public static boolean[][] neighborhood(boolean[][] cells, int row, int col) {
		if (!Life2.isValid(cells, row, col)) {
			throw new IllegalArgumentException();
		}
		boolean[][] nhood = {
				{false, false, false},
				{false, false, false},
				{false, false, false}
		};
		int left = col - 1;
		int top = row - 1;
		int cellsRow;
		int cellsCol;
		for(int i = 0; i < 3; i++) {
			cellsRow = top + i;
			for(int j = 0; j < 3; j++) {
				cellsCol = left + j;
				if(isValid(cells, cellsRow, cellsCol)) {
					nhood[i][j] = cells[cellsRow][cellsCol];
				}
			}
		}
		return nhood;
	}
	
	public static boolean isAlive(boolean[][] cells, int row, int col) {
		/**
		 * Returns true if the specified cell is alive.
		 * 
		 * @param cells a two-dimensional array
		 * @param row a row index
		 * @param col a column index
		 * @return true if the specified cell is alive
		 * @throws IllegalArgumentException if row or col is not a valid index for
		 * cells
		 */
		if (!Life2.isValid(cells, row, col)) {
			throw new IllegalArgumentException();
		}
		return cells[row][col];
	}
	
	public static int numAlive(boolean[][]cells) {
		int nAlive = 0;
		for(int i = 0; i < cells.length; i++) {
			for(int j = 0; j < cells[i].length; j++) {
				if(cells[i][j]) {
					nAlive++;
				}
			}
		}
		return nAlive;
	}
	
    public static boolean isBorn(boolean[][] cells, int row, int col) {
    	/**
         * Returns true if the cell {@code cells[row][col]} becomes alive in the next
         * generation, false otherwise. The method does not modify the array; it
         * simply determines whether or not a cell should become alive in the 
         * next generation.
         * 
         * <p>
         * A cell becomes alive if it is currently dead and its neighborhood has
         * exactly 3 alive cells.
         * 
         * @param cells a two-dimensional array
         * @param row   a row index
         * @param col   a column index
         * @return true if the cell {@code cells[row][col]} becomes alive in the next
         *         generation, false otherwise
         * @throws IllegalArgumentException if row or col is not a valid index for
         *                                   cells
         */ 
    	if (!Life2.isValid(cells, row, col)) {
			throw new IllegalArgumentException();
		}
    
    	boolean[][] nhood = Life2.neighborhood(cells, row, col);
    	int aliveCount = 0;
    	
    	for(boolean[] nhoodRow : nhood) {
    		for(boolean cell : nhoodRow) {
    			if(cell == true) {
    				aliveCount += 1;
    			}
    		}
    	}
    	
    	if(aliveCount == 3) {
    		return true;
    	}
    	else {
    		return false;
    	}
    }
    

    public static boolean survives(boolean[][] cells, int row, int col) {
    	/**
     	* Returns true if the cell {@code cells[row][col]} survives into the next
     	* generation, false otherwise. The method does not modify the array; it
     	* simply determines whether or not a cell should remain alive in the 
     	* next generation.
     	* 
     	* <p>
     	* A cell survives into the next generation if it is currently alive and if its
     	* 8 neighbors have 2 or 3 alive cells.
     	* 
     	* @param cells a two-dimensional array
     	* @param row   a row index
     	* @param col   a column index
     	* @return true if the cell {@code cells[row][col]} survives into the next
     	*         generation, false otherwise
     	* @throws IllegalArgumentException if row or col is not a valid index for
     	*                                   cells
     	*/
    	if (!Life2.isValid(cells, row, col)) {
			throw new IllegalArgumentException();
		}
    	
    	boolean[][] nhood = Life2.neighborhood(cells, row, col);
    	int aliveCount = 0;
    	
    	for(boolean[] nhoodRow : nhood) {
    		for(boolean cell : nhoodRow) {
    			if(cell == true) {
    				aliveCount += 1;
    			}
    		}
    	}
    	
    	if(aliveCount == 3 || aliveCount == 4) {
    		return true;
    	}
    	else {
    		return false;
    	}
    }
    
    public static void evolve(boolean[][]cells) {
        /**
         * Updates {@code cells} so that it is equal to the next generation of cells.
         * 
         * <p>
         * See the assignment document for details.
         * 
         * @param cells a two-dimensional array
         */
    	boolean [][] cellsCopy = Life2.clone(cells);
    	for(int i = 0; i < cells.length; i++) {
    		for(int j = 0; j < cells[i].length; j++) {
    			if(cells[i][j]) {
    				if(!Life2.survives(cellsCopy,i,j)) {
    					cells[i][j] = false;
    				}
    			}
    			else {
    				if(Life2.isBorn(cellsCopy, i, j)) {
    					cells[i][j] = true;
    				}
    			}
    		}
    	}
    	
    }
    
    public static void randomize(boolean[][]cells) {
        /**
         * Randomly sets each element of {@code cells} to true or false with
         * equal probability.
         * 
         * @param cells a two-dimensional array
         */
    	Random random = new Random();
    	for(int i = 0; i < cells.length; i++) {
    		for(int j = 0; j < cells[i].length; j++) {
    			cells[i][j] = random.nextBoolean();
    		}
    	}
    }
    
    public static boolean insert(boolean[][] pattern, int row, int col, boolean[][] cells) {
    	/**
         * Inserts a pattern of cells into another array of cells. The pattern replaces
         * the elements in {@code cells} starting at {@code cells[row][col]}. The
         * pattern must fit completely within the array {@code cells}, otherwise no
         * cells are replaced and false is returned.
         * 
         * @param pattern a 2d array of replacement cells
         * @param row     the row index of the upper-left corner of cells where the
         *                replacement should begin
         * @param col     the column index of the upper-left corner of cells where the
         *                replacement should begin
         * @param cells   a 2d array of cells
         * @return true if the pattern fits within cells, false otherwise
         * @throws IllegalArgumentException if row or col is not a valid index for cells
         */
    	if (!Life2.isValid(cells, row, col)) {
			throw new IllegalArgumentException();
		}
    	int colHold = col;
    	if((col + Life2.numCols(pattern)) > Life2.numCols(cells) - 1) {
    		return false;
    	}
    	else if(row + Life2.numRows(pattern) > Life2.numRows(cells) - 1) {
    		return false;
    	}
    	else {
    		for(int i = 0; i < Life2.numRows(pattern); i++) {
    			for(int j = 0; j < Life2.numCols(pattern); j++) {
    				cells[row-1][col-1] = pattern[i][j];
    				col++;
    			}
    			row++;
    			col = colHold;
    		}
    		return true;
    	}
    }
    
    public static boolean[][] read(String filename) {
    	/**
         * Reads a pattern of cells from a file. The pattern format is identical
         * to the output of {@code printCells}. The pattern files must be located
         * in the {@code patterns} folder of the eclipse project.
         * 
         * @param filename the filename of a pattern file
         * @return a 2d array of cells
         */
        try {
            Path path = FileSystems.getDefault().getPath("patterns", filename);
            List<String> lines = Files.readAllLines(path);
            
            boolean[][] pattern = new boolean[lines.size()][lines.get(0).length()];
            for(int i = 0; i < lines.size(); i++) {
            	for(int j = 0; j < lines.get(0).length(); j++) {
            		if(lines.get(i).charAt(j) == '#') {
            			pattern[i][j] = true;
            		}
            	}
            }
            return pattern;
        } catch (Exception ex) {
            // some sort of error occurred while reading the file
            ex.printStackTrace();
            System.exit(1);
        }
        return null;
    }
	
}
