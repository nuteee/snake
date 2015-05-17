package pkg;

/**
 * Class for the cells.
 */
public class Cell {
	/**
	 * The value of the Cell.
	 * 
	 * If the value is: 0 - the cell is empty; 1 - it has the part of the
	 * snake; 2 - it contains a food.
	 */
	private int value;

	/**
	 * Constructor of this object.
	 * 
	 * @param <code>value</code> Contains the value of that cell.
	 */
	public Cell(int value) {
		this.value = value;
	}

	/**
	 * Returns the value of this object.
	 * 
	 * @return The value of this object.
	 */
	public int getValue() {
		return value;
	}

	/**
	 * Sets the value of this object.
	 * 
	 * @param <code>value</code> The value of this object.
	 */
	public void setValue(int value) {
		this.value = value;
	}
}
