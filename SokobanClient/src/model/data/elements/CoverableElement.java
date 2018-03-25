package model.data.elements;

public class CoverableElement extends SokobanElement {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 
	 */
	private SokobanElement onTop;
	
	
	public CoverableElement() {
		this.onTop = null;
	}
	public CoverableElement(SokobanElement onTop) {
		this.onTop = onTop;
	}

	public SokobanElement getOnTop() {
		return onTop;
	}

	public void setOnTop(SokobanElement onTop) {
		this.onTop = onTop;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((onTop == null) ? 0 : onTop.hashCode());
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
		CoverableElement other = (CoverableElement) obj;
		if (onTop == null) {
			if (other.onTop != null)
				return false;
		} else if (!onTop.equals(other.onTop))
			return false;
		return true;
	}

	
	
}
