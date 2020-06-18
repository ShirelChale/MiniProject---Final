/**
 * 
 */
package unittests;

import static org.junit.jupiter.api.Assertions.*;
import static primitives.Util.isZero;

import org.junit.jupiter.api.Test;

import primitives.Vector;


/**
 * 
 * Unit tests for primitives.Vector class
 * 
 * @author Shirel Chale.
 * @author Riky Francois.
 *
 */
class VectorTests {
	
	/**
	 * Test method for {@link primitives.Vector#subtract(primitives.Vector)}.
	 */
	@Test
	void testZeroVector() {
		try { // test zero vector
			new Vector(0, 0, 0);
			fail("ERROR: zero vector does not throw an exception");
			} catch (Exception e) {}
	}

    Vector v1 = new Vector(1, 2, 3);
    Vector v2 = new Vector(-2, -4, -6);
    Vector v3 = new Vector(0, 3, -2);


	/**
	 * Test method for {@link primitives.Vector#dotProduct(primitives.Vector)}.
	 */
	@Test
	void testDotProduct() {
		if (!isZero(v1.dotProduct(v3)))
            fail("ERROR: dotProduct() for orthogonal vectors is not zero");
        if (!isZero(v1.dotProduct(v2) + 28))
            fail("ERROR: dotProduct() wrong value");
	}

	/**
	 * Test method for {@link primitives.Vector#crossProduct(primitives.Vector)}.
	 */
	@Test
	void testCrossProduct() {
		try { // test zero vector
            v1.crossProduct(v2);
            fail("ERROR: crossProduct() for parallel vectors does not throw an exception");
        } catch (Exception e) {}
        Vector vr = v1.crossProduct(v3);
        if (!isZero(vr.length() - v1.length() * v3.length()))
        	fail("ERROR: crossProduct() wrong result length");
        if (!isZero(vr.dotProduct(v1)) || !isZero(vr.dotProduct(v3)))
        	fail("ERROR: crossProduct() result is not orthogonal to its operands");
	}


	/**
	 * Test method for {@link primitives.Vector#length()}.
	 */
	@Test
	void testLength() {
		if (!isZero(v1.lengthSquared() - 14))
            fail("ERROR: lengthSquared() wrong value");
        if (!isZero(new Vector(0, 3, 4).length() - 5))
           fail("ERROR: length() wrong value");
	}

	
    Vector v = new Vector(1, 2, 3);
    
	/**
	 * Test method for {@link primitives.Vector#normalize()}.
	 */
	@Test
	void testNormalize() {
        Vector vCopy = new Vector(v);
        Vector vCopyNormalize = vCopy.normalize();
        if (vCopy != vCopyNormalize)
        	fail("ERROR: normalize() function creates a new vector");
        if (!isZero(vCopyNormalize.length() - 1))
        	fail("ERROR: normalize() result is not a unit vector");
	}

	/**
	 * Test method for {@link primitives.Vector#normalized()}.
	 */
	@Test
	void testNormalized() {
		Vector u = v.normalized();
        if (u == v)
        	fail("ERROR: normalizated() function does not create a new vector");
	}

}
