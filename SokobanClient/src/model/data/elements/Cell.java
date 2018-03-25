package model.data.elements;

import java.io.Serializable;

public class Cell implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */

	private SokobanElement elementOnCell;

	public Cell() {
		this.elementOnCell = null;
	}

	public Cell(SokobanElement elementOnCell) {
		this.elementOnCell = elementOnCell;
	}

	public SokobanElement getElementOnCell() {
		return elementOnCell;
	}

	public void setElementOnCell(SokobanElement elementOnCell) {
		this.elementOnCell = elementOnCell;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((elementOnCell == null) ? 0 : elementOnCell.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Cell other = (Cell) obj;
		if (elementOnCell == null) {
			if (other.elementOnCell != null)
				return false;
		} else if (!elementOnCell.equals(other.elementOnCell))
			return false;
		return true;
	}

	
	
}
