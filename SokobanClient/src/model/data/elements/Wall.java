package model.data.elements;

public class Wall extends SokobanElement{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 
	 */
	
	@Override
	public int hashCode() {
		return getClass().toString().hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if(obj instanceof Wall)
			return true;
		else return false;
	}
}
