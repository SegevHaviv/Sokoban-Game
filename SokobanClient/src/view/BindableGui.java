package view;

import javafx.beans.property.StringProperty;

public interface BindableGui {

	public void bindStepsTakenProperty(StringProperty stepsTaken);
	public void bindElapsedTimeProperty(StringProperty elapsedTime);
	public void bindNamePropety(StringProperty name);
}
