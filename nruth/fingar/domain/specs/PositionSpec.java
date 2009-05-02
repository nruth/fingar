package nruth.fingar.domain.specs;

import static org.junit.Assert.*;

import nruth.fingar.domain.guitar.Position;
import nruth.fingar.domain.guitar.Guitar.GuitarString;
import org.junit.Test;

public class PositionSpec {
	@Test
	public void testEqualsObject() {
		Position pos = test_position();
		assertEquals("same object equal",pos, pos);
		assertFalse("null object equality fail",pos.equals(null));
		assertEquals("same values different objects", pos, new Position(pos.fret(), pos.string()));
		assertFalse("string difference ignored", pos.equals(new Position(pos.fret(), GuitarString.values()[pos.string().ordinal()+1])));
		assertFalse("fret difference ignored", pos.equals(new Position(pos.fret()+1, pos.string())));
	}

	@Test
	public void testHashCode() {
		Position pos = test_position();
		assertEquals("same object equal",pos.hashCode(), pos.hashCode());
		assertEquals("same values different objects", pos.hashCode(), new Position(pos.fret(), pos.string()).hashCode());
		assertFalse("string difference ignored", pos.hashCode() == new Position(pos.fret(), GuitarString.values()[pos.string().ordinal()+1]).hashCode());
		assertFalse("fret difference ignored", pos.hashCode() == new Position(pos.fret()+1, pos.string()).hashCode());
	}
	
	@Test
	public void testFret() { assertEquals(test_position().fret(), 3); }
	
	@Test
	public void testString() {	assertEquals(test_position().string(), GuitarString.B); }
	
	//factory helper
	public static Position test_position(){	return new Position(3, GuitarString.B);	}
}
