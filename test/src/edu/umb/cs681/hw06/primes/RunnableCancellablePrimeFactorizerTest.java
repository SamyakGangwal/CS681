package edu.umb.cs681.hw06.primes;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import java.util.ArrayList;
import java.util.LinkedList;
import org.junit.jupiter.api.Test;

public class RunnableCancellablePrimeFactorizerTest {
	@Test
	public void cancelFactorizing() {
		RunnableCancellablePrimeFactorizer runnable =
				new RunnableCancellablePrimeFactorizer(36, 2, (long) Math.sqrt(36));
		Thread thread = new Thread(runnable);

		LinkedList<RunnableCancellablePrimeFactorizer> runnables =
				new LinkedList<RunnableCancellablePrimeFactorizer>();
		LinkedList<Thread> threads = new LinkedList<Thread>();
		System.out.println("Factorization of 2489");
		runnables.add(new RunnableCancellablePrimeFactorizer(2489, 2, (long) Math.sqrt(2489) / 2));
		runnables.add(new RunnableCancellablePrimeFactorizer(2489, 1 + (long) Math.sqrt(2489) / 2,
				(long) Math.sqrt(2489)));

		thread = new Thread(runnables.get(0));
		threads.add(thread);
		System.out
				.println("Thread #" + thread.threadId() + " performs factorization in the range of "
						+ runnables.get(0).getFrom() + "->" + runnables.get(0).getTo());

		thread = new Thread(runnables.get(1));
		threads.add(thread);
		System.out
				.println("Thread #" + thread.threadId() + " performs factorization in the range of "
						+ runnables.get(1).getFrom() + "->" + runnables.get(1).getTo());

		threads.forEach((t) -> t.start());
		runnables.forEach((r) -> r.setDone());
		threads.forEach((t) -> {
			try {
				t.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		});

		LinkedList<Long> factors2 = new LinkedList<Long>();
		runnables.forEach((factorizer) -> factors2.addAll(factorizer.getPrimeFactors()));
		ArrayList<Long> expected = new ArrayList<>();
		expected.add(19l);
		assertArrayEquals(expected.toArray(), factors2.toArray());
	}

}
