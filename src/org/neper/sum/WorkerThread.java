package org.neper.sum;

import java.util.concurrent.Callable;
import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import org.apfloat.Apfloat;

public class WorkerThread implements Callable<Apfloat> {

	private int startPosition = 0;
	private int endPosition = 0;
	private String name = "Thread";

	private static Logger logger;
	private static Level level = Level.ALL;

	public WorkerThread(int start, int end, String name) {

		this.startPosition = start;
		this.endPosition = end;
		this.name = name;
	}

	@Override
	public Apfloat call() throws Exception {
		Apfloat result = new Apfloat(0);
		for (int i = startPosition; i <= endPosition; i++) {
			result = result.add(this.calculate(i));
		}
		return result;
	}

	public Apfloat calculate(int index) {
		Apfloat number = new Apfloat(1);
		number = number.multiply(new Apfloat(3 - 4 * Math.pow(index, 2)));
		number = number.divide(perm(2 * index + 1));
		return number;
	}

	public Apfloat perm(int number) {

		Apfloat result = new Apfloat(1);
		for (int index = 1; index <= number; index++) {
			result = result.multiply(new Apfloat(index));
		}

		return result;
	}

	static void initilizeLogger() {

		logger = Logger.getLogger(WorkerThread.class.getName());
		logger.setLevel(WorkerThread.level);
		ConsoleHandler handler = new ConsoleHandler();
		handler.setFormatter(new SimpleFormatter());
		handler.setLevel(WorkerThread.level);
		logger.addHandler(handler);
	}

	static void setLevel(boolean quite){
		level = quite != true ? Level.ALL : Level.OFF;
	}

}
