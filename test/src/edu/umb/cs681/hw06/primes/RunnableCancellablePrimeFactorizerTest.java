package edu.umb.cs681.hw06.primes;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import org.junit.jupiter.api.Test;

public class RunnableCancellablePrimeFactorizerTest {
	@Test
	public void factorizing() throws InterruptedException {
		// private static List<Long> arrayToList(Integer... values) {
		// List<Long> result = new ArrayList<Long>();
		// for (int value : values) {
		// result.add(Long.valueOf(value));
		// }
		// return result;
		// }
		RunnableCancellablePrimeFactorizer runnable =
				new RunnableCancellablePrimeFactorizer(1024, 2, (long) Math.sqrt(36));
		Thread thread = new Thread(runnable);

		thread.start();
		thread.join();

		LinkedList<Long> expected =
				new LinkedList<>(Arrays.asList(2l, 2l, 2l, 2l, 2l, 2l, 2l, 2l, 2l, 2l));

		assertEquals(expected, runnable.getPrimeFactors());

	}

	@Test
	public void cancelFactorizing() throws InterruptedException {
		// private static List<Long> arrayToList(Integer... values) {
		// List<Long> result = new ArrayList<Long>();
		// for (int value : values) {
		// result.add(Long.valueOf(value));
		// }
		// return result;
		// }
		RunnableCancellablePrimeFactorizer runnable =
				new RunnableCancellablePrimeFactorizer(1024, 2, (long) Math.sqrt(36));
		Thread thread = new Thread(runnable);

		thread.start();
		runnable.setDone();
		thread.join();

		LinkedList<Long> expected = new LinkedList<>();

		assertEquals(expected, runnable.getPrimeFactors());

	}

	@Test
	public void cancelFactorizingSleep() throws InterruptedException {
		// private static List<Long> arrayToList(Integer... values) {
		// List<Long> result = new ArrayList<Long>();
		// for (int value : values) {
		// result.add(Long.valueOf(value));
		// }
		// return result;
		// }
		RunnableCancellablePrimeFactorizer runnable =
				new RunnableCancellablePrimeFactorizer(1024, 2, (long) Math.sqrt(36));
		Thread thread = new Thread(runnable);

		thread.start();
		Thread.sleep(100);
		runnable.setDone();
		thread.join();

		LinkedList<Long> expected =
				new LinkedList<>(Arrays.asList(2l, 2l, 2l, 2l, 2l, 2l, 2l, 2l, 2l, 2l));

		assertEquals(expected, runnable.getPrimeFactors());

	}

	@Test
	public void cancelFactorizingList() throws InterruptedException {
		// private static List<Long> arrayToList(Integer... values) {
		// List<Long> result = new ArrayList<Long>();
		// for (int value : values) {
		// result.add(Long.valueOf(value));
		// }
		// return result;
		// }
		List<RunnableCancellablePrimeFactorizer> runnables = new ArrayList<>();

		runnables.add(new RunnableCancellablePrimeFactorizer(5678, 2, (long) Math.sqrt(100)));
		runnables.add(new RunnableCancellablePrimeFactorizer(6996, 2, (long) Math.sqrt(100)));

		LinkedList<Thread> threads = new LinkedList<Thread>();

		Thread thread = new Thread(runnables.get(0));
		threads.add(thread);

		thread = new Thread(runnables.get(1));
		threads.add(thread);

		threads.forEach((t) -> t.start());
		runnables.forEach((r) -> r.setDone());
		threads.forEach((t) -> {
			try {
				t.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		});

		LinkedList<Long> expected = new LinkedList<>(Arrays.asList(2l));
		LinkedList<Long> expected2 = new LinkedList<>(Arrays.asList(2l, 2l, 3l));
		assertEquals(expected, runnables.get(0).getPrimeFactors());
		assertEquals(expected2, runnables.get(1).getPrimeFactors());

	}

}
