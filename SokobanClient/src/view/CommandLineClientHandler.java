package view;

import java.io.InputStream;
import java.io.OutputStream;

import commons.ClientHandler;

public class CommandLineClientHandler implements ClientHandler {

	private Cli cli;
	
	public CommandLineClientHandler(Cli cli){
		
		this.cli = cli;
		
	}
	public CommandLineClientHandler() {
		cli = new Cli();
	}
	@Override
	public void handleClient() {
		cli.start();
		
	}

	@Override
	public void setInput(InputStream istream) {
		cli.setInput(istream);
		
	}

	@Override
	public void setOutput(OutputStream ostream) {
		cli.setOutput(ostream);
		
	}

}
