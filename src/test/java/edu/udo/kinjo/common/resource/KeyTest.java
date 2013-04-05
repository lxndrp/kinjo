package edu.udo.kinjo.common.resource;

import com.wallabystreet.kinjo.common.resource.Key;

import junit.framework.TestCase;


public class KeyTest extends TestCase {

	private Key<String> one, two;
	private Key<Long> three,four;
	
	public KeyTest(String name) {
		super( name );
	}

	protected void setUp()
			throws Exception {
		super.setUp();
		one = new Key<String>("blah");
		two = new Key<String>("blih");
		three = new Key<Long>(3L);		
		four = new Key<Long>(5L);
	}

	protected void tearDown()
			throws Exception {
		super.tearDown();
	}

	public final void testHashCode() {
		/* one and two */
		assertFalse(one.hashCode() == two.hashCode());
		two = new Key<String>("blah");
		assertTrue(one.hashCode() == two.hashCode());
		/* three and four */
		assertFalse(three.hashCode() == four.hashCode());
		four = new Key<Long>(3L);
		assertTrue(three.hashCode() == four.hashCode());
		/* incompatible types */
		assertFalse(one.hashCode() == three.hashCode());
	}

	public final void testCompareTo() {
		assertTrue(one.compareTo(two) < 0);
		assertTrue(three.compareTo(four) < 0);
	}

	public final void testEqualsObject() {
		/* one and two */
		assertFalse(one.equals(two));
		two = new Key<String>("blah");
		assertTrue(one.equals(two));
		/* three and four */
		assertFalse(three.equals(four));
		four = new Key<Long>(3L);
		assertTrue(three.equals(four));
		/* incompatible types */
		assertFalse(one.equals(three));
	}

	public final void testToString() {
		assertNotNull(one.toString());
		assertNotNull(two.toString());
		assertNotNull(three.toString());
		assertNotNull(four.toString());
	}

	public final void testGetPayload() {
		assertEquals(one.getPayload(), "blah");
		assertEquals(two.getPayload(), "blih");
		assertEquals(three.getPayload(), new Long(3L));
		assertEquals(four.getPayload(), new Long(5L));
	}
}
