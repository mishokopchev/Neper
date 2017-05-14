package org.neper.sum;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PathSegments {

	private int threadCount;
	private String file;
	private int counter;
	private boolean quiteMode;

	public int getThreadCount() {
		return threadCount;
	}

	public void setThreadCount(int threadCount) {
		this.threadCount = threadCount;
	}

	public String getFile() {
		return file;
	}

	public void setFile(String file) {
		this.file = file;
	}

	public int getCounter() {
		return counter;
	}

	public void setCounter(int counter) {
		this.counter = counter;
	}

	public boolean isQuiteMode() {
		return quiteMode;
	}

	public void setQuiteMode(boolean quiteMode) {
		this.quiteMode = quiteMode;
	}

	PathSegments() {

	}

}

class Builder {

	public static String REGEX_COUNTER = "(([p])(.+?\\d)([^\\s]+))";
	public static String REGEX_CONTAIS_P_PARAMETER = "([p])";
	public static String REGEX_QUITE_MODE = "-q";
	public static String REGEX_FILE_PATH = "(([^\\s]+)(.txt))";
	public static String REGEX_FILE_EXISTS = "-o";
	public static String REGEX_THREADS_COUNTER = "(-t)\\s[\\d]\\s";

	private Matcher matcher;
	private Pattern pattern;

	private String arguments;

	private PathSegments pathSegments;

	Builder setArguments(String arg) {
		this.arguments = arg;
		return this;
	}

	private void initilizePatternAndMatcher(String regex) {
		pattern = Pattern.compile(regex);
		matcher = pattern.matcher(this.arguments);
	}

	Builder parseThreadCounts() {
		if (this.arguments.contains("-t")) {
			this.initilizePatternAndMatcher(REGEX_THREADS_COUNTER);
			
			if (matcher.find()) {
				String threadsCounter = matcher.group(1);
				threadsCounter = threadsCounter.substring(2).trim();
				this.pathSegments.setThreadCount(Integer.parseInt(threadsCounter));
			}
		}

		return this;
	}

	Builder parseFile() {
		String file = "result.txt";
		if (this.arguments.contains(REGEX_FILE_EXISTS)) {
			pattern = Pattern.compile(REGEX_FILE_PATH);
			matcher = pattern.matcher(this.arguments);
			if (matcher.find()) {
				file = matcher.group(1);
			}

			this.pathSegments.setFile(file);
		}

		return this;
	}

	Builder parseQuite() {
		if (this.arguments.contains(REGEX_QUITE_MODE)) {
			this.pathSegments.setQuiteMode(true);
		} else {
			this.pathSegments.setQuiteMode(false);
		}
		return this;
	}

	Builder parseNumber() {
		pattern = Pattern.compile(REGEX_COUNTER);
		matcher = pattern.matcher(this.arguments);
		if (matcher.find()) {
			String counter = matcher.group(1);
			counter = counter.substring(2);
			System.out.println(counter);
		}
		return this;
	}

	PathSegments build() {
		return this.pathSegments;
	}

}