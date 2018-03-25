package commons;


import java.io.InputStream;
import java.io.OutputStream;

public interface ClientHandler {
	
	public void handleClient();
	
	public void setInput(InputStream istream);
	
	public void setOutput(OutputStream output);

}
