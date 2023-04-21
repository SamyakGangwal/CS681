package edu.umb.cs681.hw06.primes;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import java.util.ArrayList;
import org.junit.jupiter.api.Test;

public class RunnableCancellablePrimeGeneratorTest {
	@Test
	public void cancelPrimeGenerating() {
		RunnableCancellablePrimeGenerator gen = new RunnableCancellablePrimeGenerator(1, 100);
		Thread thread = new Thread(gen);
		thread.start();
		gen.setDone();
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		gen.getPrimes().forEach((Long prime) -> System.out.print(prime + ", "));


		ArrayList<Long> actual = new ArrayList<>();

		assertArrayEquals(actual.toArray(), gen.getPrimes().toArray());
	}
}
