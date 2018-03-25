package model.data.elements;

public class Target extends CoverableElement{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 
	 */
	public Target() {}

	public Target(SokobanElement onTop) {
		super(onTop);
	}
	
	@Override
	public int hashCode() {
		String onTopClassString = "";
		if (getOnTop() != null) {
			onTopClassString = getOnTop().getClass().toString();
		}
		
		String codeString = getClass().toString() + onTopClassString;
		return codeString.hashCode();
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof Target)
			return true;
		
		else return false;
	}


}
