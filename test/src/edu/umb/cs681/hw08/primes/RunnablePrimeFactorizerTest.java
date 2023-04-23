package edu.umb.cs681.hw08.primes;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

public class RunnablePrimeFactorizerTest {
	@Test
	public void twoStepTermination() {
		RunnableCancellableInterruptiblePrimeFactorizer fac =
				new RunnableCancellableInterruptiblePrimeFactorizer(5745, 2, 100);

		Thread thread = new Thread(fac);
		thread.start();
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		fac.setDone();
		thread.interrupt();
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		assertEquals(0, fac.getPrimeFactors().size());

	}
}
