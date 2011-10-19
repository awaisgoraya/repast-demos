/*
 * Version info:
 *     $HeadURL: https://cscs-repast-demos.googlecode.com/svn/richard/StupidModel/tags/2011_06_18_model_16/test/stupidmodel/observer/TestBugStyleOGL2D.java $
 *     $LastChangedDate: 2011-06-13 16:41:17 +0200 (H, 13 jún. 2011) $
 *     $LastChangedRevision: 355 $
 *     $LastChangedBy: richard.legendi@gmail.com $
 */
package stupidmodel.observer;

import static org.mockito.Mockito.*;

import java.awt.Color;

import org.junit.Assert;
import org.junit.Test;

import repast.simphony.random.RandomHelper;
import stupidmodel.agents.Bug;

/**
 * Simple tests for the created custom style.
 * 
 * <p>
 * Bug colors shade from white when size is zero to red.
 * </p>
 * 
 * @author Richard O. Legendi (richard.legendi)
 * @since 2.0-beta, 2011
 * @version $Id: TestBugStyleOGL2D.java 183 2011-05-29 17:09:27Z
 *          richard.legendi@gmail.com $
 * @see BugStyleOGL2D
 */
public class TestBugStyleOGL2D {

	/** Style object to test. */
	private final BugStyleOGL2D style = new BugStyleOGL2D();

	/**
	 * Test if returned color is white when size is <code>0</code>.
	 */
	@Test
	public void testWhiteColor() {
		final Bug bug = new Bug();
		bug.setSize(0);

		final Color color = style.getColor(bug);

		// Color "white" was modified to be able to see zero-sized bug agents on
		// empty cells (which are also white)
		Assert.assertEquals(new Color(255, 200, 200), color);
	}

	/**
	 * Test if invalid size value is assigned to a {@link Bug}.
	 */
	@Test(expected = IllegalStateException.class)
	public void testInvalid() {
		final Bug bug = mock(Bug.class);
		final double wrongValue = RandomHelper.nextDoubleFromTo(
				-Double.MAX_VALUE, -Double.MIN_VALUE);
		when(bug.getSize()).thenReturn(wrongValue);

		style.getColor(bug); // Should fail
	}

	/**
	 * Test default color value for non {@link Bug} parameters.
	 */
	@Test
	public void testNonBugColor() {
		final Color color = style.getColor(null); // Pass anything but a Bug

		// The default color is BLUE in DefaultStyleOGL2D
		Assert.assertEquals(Color.BLUE, color);
	}

	/**
	 * Test if returned color is red when size is <code>255</code>.
	 */
	@Test
	public void testRedColor() {
		final Bug bug = new Bug();
		bug.setSize(255);

		final Color color = style.getColor(bug);
		Assert.assertEquals(Color.RED, color);
	}

	/**
	 * Test if returned color is pink when size is (intermediate state).
	 */
	@Test
	public void testPinkColor() {
		final Bug bug = new Bug();
		// Results in color strength 175, which is color pink
		bug.setSize(1.25);

		final Color color = style.getColor(bug);
		Assert.assertEquals(Color.PINK, color);
	}

}
