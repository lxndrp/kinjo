package edu.udo.kinjo.common.resource;

import com.wallabystreet.kinjo.common.resource.Value;

import junit.framework.TestCase;


public class ValueTest extends TestCase {

	private Value<String> one, two;
	private Value<Long> three, four;
	
	public ValueTest(String name) {
		super( name );
	}

	protected void setUp()
			throws Exception {
		super.setUp();
		one = new Value<String>("blah");
		two = new Value<String>("blih");
		three = new Value<Long>(3L);
		four = new Value<Long>(5L);
	}

	protected void tearDown()
			throws Exception {
		super.tearDown();
	}

	public final void testHashCode() {
		/* one and two */
		assertFalse(one.hashCode() == two.hashCode());
		two = new Value<String>("blah");
		assertTrue(one.hashCode() == two.hashCode());
		/* three and four */
		assertFalse(three.hashCode() == four.hashCode());
		four = new Value<Long>(3L);
		assertTrue(three.hashCode() == four.hashCode());
		/* incompatible types */
		assertFalse(one.hashCode() == three.hashCode());
	}

	public final void testCompareTo() {
		assertTrue(one.compareTo(two) < 0);
		assertTrue(three.compareTo(four) < 0);
	}

	public final void testEqualsObject() {
	//TODO Implement equals().
	}

	public final void testToString() {
	//TODO Implement toString().
	}

	public final void testGetPayload() {
	//TODO Implement getPayload().
	}

}
