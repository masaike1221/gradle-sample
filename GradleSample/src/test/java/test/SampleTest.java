package test;

import static org.junit.Assert.*;

import org.junit.Test;

import hello.Greeter;

public class SampleTest {

	@Test
	public void test() {
		Greeter greeter = new Greeter();
		assertNotNull(greeter);
	}
}
