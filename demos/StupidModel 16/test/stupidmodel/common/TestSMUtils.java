/*
 * Version info:
 *     $HeadURL: https://cscs-repast-demos.googlecode.com/svn/richard/StupidModel/tags/2011_06_18_model_16/test/stupidmodel/common/TestSMUtils.java $
 *     $LastChangedDate: 2011-08-19 12:34:40 +0200 (P, 19 aug. 2011) $
 *     $LastChangedRevision: 1006 $
 *     $LastChangedBy: richard.legendi@gmail.com $
 */
package stupidmodel.common;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.lang.reflect.Constructor;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import repast.simphony.context.Context;
import repast.simphony.context.DefaultContext;
import repast.simphony.engine.environment.RunState;
import repast.simphony.query.space.grid.GridCell;
import repast.simphony.query.space.grid.TestGridCellFactory;
import repast.simphony.random.RandomHelper;
import repast.simphony.space.grid.Grid;
import repast.simphony.space.grid.GridPoint;
import stupidmodel.agents.Bug;

/**
 * Test methods for the {@link SMUtils} class.
 * 
 * @author Richard O. Legendi (richard.legendi)
 * @since 2.0-beta, 2011
 * @version $Id: TestSMUtils.java 183 2011-05-29 17:09:27Z
 *          richard.legendi@gmail.com $
 * @see SMUtils
 */
public class TestSMUtils {

	/**
	 * Easy win to get coverage and eliminate noise in the report: putting the
	 * idealism aside the following test does the job.
	 */
	@Test
	public void giveMeCoverageForMyPrivateConstructor() throws Exception {
		final Constructor<?> constructor = SMUtils.class
				.getDeclaredConstructor();
		constructor.setAccessible(true);
		constructor.newInstance();
	}

	/**
	 * Testing {@link SMUtils#getFreeGridCells(List)} for <code>null</code>
	 * parameter.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testGetFreeGridCellsForNull() {
		SMUtils.getFreeGridCells(null);
	}

	/**
	 * Testing {@link SMUtils#getFreeGridCells(List)} for empty list.
	 */
	@Test
	public void testGetFreeGridCellsForEmptyList() {
		final List<GridCell<Object>> ret = SMUtils
				.getFreeGridCells(new ArrayList<GridCell<Object>>());
		Assert.assertEquals(ret, Collections.emptyList());
	}

	/**
	 * Testing {@link SMUtils#getFreeGridCells(List)} for one empty element.
	 */
	@Test
	public void testGetFreeGridCellsForSimpleEmptyList() {
		final ArrayList<GridCell<Object>> neighborhood = new ArrayList<GridCell<Object>>();

		final GridCell<Object> gridCell = new GridCell<Object>(new GridPoint(1,
				1), Object.class);
		neighborhood.add(gridCell);

		final List<GridCell<Object>> ret = SMUtils
				.getFreeGridCells(neighborhood);
		// NB: Function equals() is not specified for GridCell
		Assert.assertEquals(ret, Collections.singletonList(gridCell));
	}

	/**
	 * Testing {@link SMUtils#getFreeGridCells(List)} for empty elements.
	 */
	@SuppressWarnings("unchecked")
	@Test
	public void testGetFreeGridCellsForMultipleEmptyElements() {
		final ArrayList<GridCell<Object>> neighborhood = new ArrayList<GridCell<Object>>();

		final GridCell<Object> gridCell1 = new GridCell<Object>(new GridPoint(
				1, 1), Object.class);
		final GridCell<Object> gridCell2 = new GridCell<Object>(new GridPoint(
				2, 2), Object.class);

		neighborhood.add(gridCell1);
		neighborhood.add(gridCell2);

		final List<GridCell<Object>> ret = SMUtils
				.getFreeGridCells(neighborhood);
		// NB: Function equals() is not specified for GridCell
		Assert.assertEquals(ret, Arrays.asList(gridCell1, gridCell2));
	}

	/**
	 * Testing {@link SMUtils#getFreeGridCells(List)} for one empty and one
	 * occupied element.
	 */
	@Test
	public void testGetFreeGridCellsForSimpleList() {
		final ArrayList<GridCell<String>> neighborhood = new ArrayList<GridCell<String>>();

		final GridCell<String> oneObjectCell = TestGridCellFactory
				.createGridCellToTest(new GridPoint(1, 1), String.class, "A");
		final GridCell<String> emptyCell = new GridCell<String>(new GridPoint(
				2, 2), String.class);

		neighborhood.add(oneObjectCell);
		neighborhood.add(emptyCell);

		final List<GridCell<String>> ret = SMUtils
				.getFreeGridCells(neighborhood);

		Assert.assertEquals(ret, Collections.singletonList(emptyCell));
	}

	/**
	 * Testing {@link SMUtils#getFreeGridCells(List)} for occupied elements.
	 */
	@Test
	public void testGetFreeGridCellsForSimpleOccupiedList() {
		final ArrayList<GridCell<String>> neighborhood = new ArrayList<GridCell<String>>();

		final GridCell<String> cell = TestGridCellFactory.createGridCellToTest(
				new GridPoint(1, 1), String.class, "A");

		final GridCell<String> bell = TestGridCellFactory.createGridCellToTest(
				new GridPoint(2, 2), String.class, "B");

		neighborhood.add(cell);
		neighborhood.add(bell);

		final List<GridCell<String>> ret = SMUtils
				.getFreeGridCells(neighborhood);

		Assert.assertEquals(ret, Collections.emptyList());
	}

	// ========================================================================
	// === Testing function randomElementOf() =================================

	/**
	 * Testing {@link SMUtils#randomElementOf(List)} for <code>null</code>
	 * parameter.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testRandomElementOfForNull() {
		SMUtils.randomElementOf(null);
	}

	/**
	 * Testing {@link SMUtils#randomElementOf(List)} for empty list.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testRandomElementOfForEmptyList() {
		SMUtils.randomElementOf(Collections.emptyList());
	}

	/**
	 * Testing {@link SMUtils#randomElementOf(List)} for a singleton list.
	 */
	@Test
	public void testRandomElementOfForSingletonList() {
		final String value = "A";
		final String randomElement = SMUtils.randomElementOf(Collections
				.singletonList(value));

		Assert.assertEquals(value, randomElement);
	}

	/**
	 * Testing {@link SMUtils#randomElementOf(List)} for a sample list.
	 */
	@Test
	public void testRandomElementOfContainsResult() {
		final List<String> list = Arrays.asList("A", "B", "C", "D", "E");
		final String randomElement = SMUtils.randomElementOf(list);

		Assert.assertTrue(list.contains(randomElement));
	}

	/**
	 * Assert that {@link SMUtils#randomElementOf(List)} does not modify the
	 * original list.
	 */
	@Test
	public void testRandomElementOfDoesNotModifiesOriginalList() {
		final List<String> list = Arrays.asList("A", "B", "C", "D", "E");
		final List<String> backupList = new ArrayList<String>(list);

		SMUtils.randomElementOf(list);
		Assert.assertEquals(backupList, list);
	}

	/**
	 * Test if {@link SMUtils#prob(double)} fails for negative parameters.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testProbNegativeValue() {
		final double wrongValue = RandomHelper.nextDoubleFromTo(
				-Double.MAX_VALUE, -Double.MIN_VALUE);

		SMUtils.prob(wrongValue); // Should fail
	}

	/**
	 * Test if {@link SMUtils#prob(double)} fails for threshold parameters above
	 * one.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testProbParamAboveOne() {
		final double wrongValue = RandomHelper.nextDoubleFromTo(
				1.0 + Double.MIN_VALUE, Double.MAX_VALUE);

		SMUtils.prob(wrongValue); // Should fail
	}

	/**
	 * Assert that a threshold level of one always return <code>false</code>.
	 */
	@Test
	public void testProbAlwaysFalse() {
		Assert.assertFalse(SMUtils.prob(1.0));
	}

	/**
	 * Assert that a threshold level of zero always return <code>true</code>.
	 */
	@Test
	public void testProbAlwaysTrue() {
		Assert.assertTrue(SMUtils.prob(0.0));
	}

	/**
	 * Test failure if there is no grid associated to this agent.
	 */
	@Test(expected = IllegalStateException.class)
	public void testNoGrid() {
		// Some workaround to make the test see like if it was running
		final Context<Object> context = new DefaultContext<Object>();
		RunState.init().setMasterContext(context);

		final Bug bug = new Bug();
		context.add(bug);
		SMUtils.getGrid(bug); // Should fail
	}

	/**
	 * Test if grid is set it is returned by the agent.
	 */
	@Test
	public void testGridQuery() {
		// Some workaround to make the test see like if it was running
		final Context<Object> context = new DefaultContext<Object>();
		RunState.init().setMasterContext(context);

		@SuppressWarnings("unchecked")
		final Grid<Object> grid = mock(Grid.class);
		when(grid.getName()).thenReturn(Constants.GRID_ID);

		context.addProjection(grid);

		final Bug bug = new Bug();
		context.add(bug);
		Assert.assertSame(grid, SMUtils.getGrid(bug));
	}

	/**
	 * Test {@link SMUtils#readDataFile(String)} fails for <code>null</code>
	 * argument.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testReadDataFileWithNullParameter() {
		SMUtils.readDataFile(null); // Should fail
	}

	/**
	 * Test {@link SMUtils#readDataFile(String)} fails for empty file name.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testReadDataFileWithEmptyParameter() {
		SMUtils.readDataFile(""); // Should fail
	}

	/**
	 * Test {@link SMUtils#readDataFile(String)} fails for some nonexistent
	 * file.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testReadDataFileWithNoTarget() {
		// Check if the file is not existing (it would be a miracle, but it is
		// always good to be sure
		Assert.assertFalse(new File("/some/unexistent/file/here").exists());

		SMUtils.readDataFile("/some/unexistent/file/here"); // Should fail
	}

	/**
	 * Test {@link SMUtils#readDataFile(String)} fails when opening can be done
	 * but reading fails due to some reason (here we create a lock on the file
	 * with <code>java.nio</code>).
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testReadDataFileWithLockedFile() {
		final String lockedFile = "test/stupidmodel/common/Stupid_Cell_LockedFile.Data";
		try {
			final FileChannel in = new RandomAccessFile(lockedFile, "rw")
					.getChannel();
			final java.nio.channels.FileLock lock = in.lock();
			try {
				SMUtils.readDataFile(lockedFile); // Should fail
			} finally {
				lock.release();
				in.close();
			}
		} catch (final IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Test {@link SMUtils#readDataFile(String)} fails when the specified input
	 * file is missing data (here there is only <code>x</code> and
	 * <code>y</code> coordinates in one of the lines).
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testReadDataFileWithMissingData() {
		SMUtils.readDataFile("test/stupidmodel/common/Stupid_Cell_Missing.Data"); // Should
																					// fail
	}

	/**
	 * Test {@link SMUtils#readDataFile(String)} fails when the specified input
	 * file has invalid data (here we have a string instead of a number in one
	 * of the lines).
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testReadDataFileWithInvalidData() {
		// Chtulhu makes the test fail :-)
		SMUtils.readDataFile("test/stupidmodel/common/Stupid_Cell_Invalid.Data"); // Should
																					// fail
	}

	/**
	 * Test {@link SMUtils#readDataFile(String)} when we have a well-formed
	 * sample data.
	 */
	@Test
	public void testReadDataFileWithSampleData() {
		final List<CellData> data = SMUtils
				.readDataFile("test/stupidmodel/common/Stupid_Cell_Sample.Data");

		Assert.assertEquals(new CellData(5, 5, 0.1), data.get(0));
		Assert.assertEquals(new CellData(10, 15, 0.2), data.get(1));
		Assert.assertEquals(new CellData(3, 7, 0.3), data.get(2));
	}

}
