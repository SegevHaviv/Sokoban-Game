package controller;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;
import controller.commands.SokobanCommand;

public class Controller {

	// Variable Declaration
	private volatile boolean stopExecution;
	private BlockingQueue<SokobanCommand> commandQueue;

	public Controller() {
		commandQueue = new ArrayBlockingQueue<SokobanCommand>(10);
	}

	/** Inserting a command into the queue */
	public void insertCommand(SokobanCommand command) {
		if (command != null)
			try {
				commandQueue.put(command);
				;
			} catch (InterruptedException e) {
				System.out.println("Error executing a command, exitting now.\n");
				System.exit(0);
			}
	}

	/** Starting to pull commands from the queue and execute them */
	public void start() {

		Thread thread = new Thread(new Runnable() {
			@Override
			public void run() {
				while (!stopExecution) {
					try {
						SokobanCommand command = commandQueue.poll(1, TimeUnit.SECONDS);
						if (command != null)
							command.execute();

					} catch (InterruptedException e) {
						System.out.println("Error executing a command, exitting now.\n");
						System.exit(0);
					}
				}
			}
		});
		thread.start();
	}

	/** Stopping the commands executing */
	public void stop() {
		stopExecution = true;
	}
}