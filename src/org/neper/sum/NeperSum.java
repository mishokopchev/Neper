package org.neper.sum;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import org.apfloat.Apfloat;

public class NeperSum {

	private PathSegments pathSegments;
	private List<Future<Apfloat>> results = new ArrayList<Future<Apfloat>>();
	private ExecutorService service;

	NeperSum(PathSegments segments) {
		this.pathSegments = segments;
		this.service = Executors.newFixedThreadPool(pathSegments.getThreadCount());
	}

	public void start() {

		int[] spreads = this.calculateThreadSpread();
		int allThreads = pathSegments.getThreadCount();
		boolean quiteMode = pathSegments.isQuiteMode();
		WorkerThread.setLevel(quiteMode);

		for (int nThread = 0; nThread < allThreads-1; nThread++) {
			Future<Apfloat> thread = service.submit((Callable<Apfloat>) new WorkerThread(spreads[nThread] + 1,
					spreads[nThread + 1], "Thread: " + nThread));
			results.add(thread);
		}

	}

	public void getThreadResults() {


		for (Future<Apfloat> future : this.results) {
			try {
				Apfloat threadResult = future.get();
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (ExecutionException e) {
				e.printStackTrace();
			}

		}
	}

	public int[] calculateThreadSpread() {
		int iteration = pathSegments.getCounter();
		int threadsNumber = pathSegments.getThreadCount();
		int[] counters = new int[threadsNumber + 1];
		counters[0] = -1;
		int spread = iteration / threadsNumber;
		counters[1] = spread;
		for (int i = 2; i < threadsNumber; i++) {
			counters[i] = i * counters[1];
		}
		counters[threadsNumber] = iteration;

		return counters;
	}

}
