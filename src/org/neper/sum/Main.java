package org.neper.sum;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {

	public static void main(String[] args) {
		Builder b = new Builder();
		String ar= "-p 1 -q -o result.txt -t 3";
		b.setArguments(ar);
		b.parseNumber();
		
		WorkerThread.initilizeLogger();
		WorkerThread workerThread = new WorkerThread(1,2,"");
		workerThread.calculate(2);
		PathSegments p = new PathSegments();
		p.setCounter(17);
		p.setThreadCount(5);
		
		workerThread.perm(1);
		
		
		NeperSum n = new NeperSum(p);
		n.calculateThreadSpread();
		
	}
	
	
	

}
