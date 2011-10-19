/*
 * Version info:
 *     $HeadURL: https://cscs-repast-demos.googlecode.com/svn/richard/StupidModel/tags/2011_06_18_model_16/test/stupidmodel/agents/TestBug.java $
 *     $LastChangedDate: 2011-08-19 12:34:40 +0200 (P, 19 aug. 2011) $
 *     $LastChangedRevision: 1006 $
 *     $LastChangedBy: richard.legendi@gmail.com $
 */
package stupidmodel.agents;

import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import junit.framework.Assert;

import org.junit.Test;
import org.mockito.Mockito;

import repast.simphony.context.Context;
import repast.simphony.context.DefaultContext;
import repast.simphony.engine.environment.RunState;
import repast.simphony.random.RandomHelper;
import repast.simphony.space.grid.Grid;
import repast.simphony.space.grid.GridPoint;
import repast.simphony.util.collections.IndexedIterable;
import stupidmodel.agents.Bug.BugSizeComparator;
import stupidmodel.common.CellData;
import stupidmodel.common.Constants;
import stupidmodel.common.TestUtils;

/**
 * Simple tests for the created {@link Bug} agents.
 * 
 * @author Richard O. Legendi (richard.legendi)
 * @since 2.0-beta, 2011
 * @version $Id: TestBug.java 428 2011-06-18 14:19:31Z richard.legendi@gmail.com
 *          $
 * @see Bug
 */
public class TestBug {

	/**
	 * Test invalid argument for the {@link Bug#setSize(double)} function.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testInvalidParameterForSetSize() {
		final Bug bug = new Bug();
		final double wrongValue = RandomHelper.nextDoubleFromTo(
				-Double.MAX_VALUE, -Double.MIN_VALUE);
		bug.setSize(wrongValue);
	}

	/**
	 * Test illegal argument for {@link BugSizeComparator}.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testCompareToInvalidFirstParameter() {
		final Bug bug = new Bug();
		new BugSizeComparator().compare(null, bug); // Should fail
	}

	/**
	 * Test illegal argument for {@link BugSizeComparator}.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testCompareToInvalidSecondParameter() {
		final Bug bug = new Bug();
		new BugSizeComparator().compare(bug, null); // Should fail
	}

	/**
	 * Making sure if the default comparison works correctly for two agents, and
	 * sorting returns an agent list in descending size order.
	 */
	@Test
	public void testComparisonWithTwoElements() {
		final Bug b1 = new Bug();
		final Bug b2 = new Bug();

		b1.setSize(5.0);
		b2.setSize(10.0);

		final List<Bug> list = Arrays.asList(b1, b2);
		Collections.sort(list, new BugSizeComparator());

		Assert.assertEquals(b2, list.get(0));
		Assert.assertEquals(b1, list.get(1));
	}

	/**
	 * Making sure if the default comparison works correctly for three agents,
	 * and sorting returns an agent list in descending size order.
	 */
	@Test
	public void testComparisonWithThreeElements() {
		final Bug b1 = new Bug();
		final Bug b2 = new Bug();
		final Bug b3 = new Bug();

		b1.setSize(5.0);
		b2.setSize(10.0);
		b3.setSize(2.0);

		final List<Bug> list = Arrays.asList(b1, b2, b3);
		Collections.sort(list, new BugSizeComparator());

		Assert.assertEquals(b2, list.get(0));
		Assert.assertEquals(b1, list.get(1));
		Assert.assertEquals(b3, list.get(2));
	}

	/**
	 * Test if setting an unexpected value for maximum consumption rate.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testSettingWrongMaxCosnumptionRate() {
		final Bug bug = new Bug();

		final double wrongValue = RandomHelper.nextDoubleFromTo(
				-Double.MAX_VALUE, -Double.MIN_VALUE);

		bug.setMaxConsumptionRate(wrongValue); // Should fail
	}

	/**
	 * Test if setting a value for maximum consumption rate works as expected.
	 */
	@Test
	public void testSettingMaxCosnumptionRate() {
		final Bug bug = new Bug();

		final double value = RandomHelper.nextDoubleFromTo(0, Double.MAX_VALUE);
		bug.setMaxConsumptionRate(value);

		Assert.assertEquals(value, bug.getMaxConsumptionRate(), Constants.DELTA);
	}

	/**
	 * Test if setting an unexpected (negative) value for survival probability.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testSettingNegativeSurvivalProbability() {
		final Bug bug = new Bug();

		final double wrongValue = RandomHelper.nextDoubleFromTo(
				-Double.MAX_VALUE, -Double.MIN_VALUE);

		bug.setSurvivalProbability(wrongValue); // Should fail
	}

	/**
	 * Test if setting an unexpected (greater than one) value for survival
	 * probability.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testSettingAboveOneSurvivalProbability() {
		final Bug bug = new Bug();

		final double wrongValue = RandomHelper.nextDoubleFromTo(1.0,
				Double.MAX_VALUE);

		bug.setSurvivalProbability(wrongValue); // Should fail
	}

	/**
	 * Test if setting a value for survival probability works as expected.
	 */
	@Test
	public void testSettingSurvivalProbability() {
		final Bug bug = new Bug();

		final double value = RandomHelper.nextDoubleFromTo(0, 1.0);
		bug.setSurvivalProbability(value);

		Assert.assertEquals(value, bug.getSurvivalProbability(),
				Constants.DELTA);
	}

	/**
	 * Test if the bug dies it is removed from the context.
	 */
	@Test
	public void testDying() {
		final Bug bug = new Bug();
		final Context<Object> context = new DefaultContext<Object>();
		RunState.init().setMasterContext(context);
		context.add(bug);

		Assert.assertTrue(context.contains(bug));
		bug.die();
		Assert.assertFalse(context.contains(bug));
	}

	/**
	 * Test invalid argument for the
	 * {@link Bug#setInitialSize(cern.jet.random.Normal)} function.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testInvalidParameterForSetInitialSize() {
		final Bug bug = new Bug();
		bug.setInitialSize(null);
	}

	/**
	 * Test if {@link Bug#grow()} increases the size as required.
	 * 
	 * <p>
	 * Caution, used partial mocking here.
	 * </p>
	 */
	@Test
	public void testGrow() {
		// Given
		final Bug bugSpy = Mockito.spy(new Bug());

		final double prevSize = RandomHelper.nextDoubleFromTo(0,
				Double.MAX_VALUE);
		bugSpy.setSize(prevSize);

		final double availableFood = RandomHelper.nextDoubleFromTo(0,
				Double.MAX_VALUE);

		doReturn(availableFood).when(bugSpy).foodConsumption();

		// When
		bugSpy.grow();

		// Then
		Assert.assertEquals(prevSize + availableFood, bugSpy.getSize(),
				Constants.DELTA);
	}

	/**
	 * Test {@link Bug#getUnderlyingCell()} when there is no associated
	 * underlying cell.
	 */
	@Test(expected = IllegalStateException.class)
	public void testGetUnderlyingCellWhenNoCellGiven() {
		final Context<Object> context = TestUtils.initContext();
		final Grid<Object> grid = TestUtils.initEmptyGrid(context);

		final Bug bug = new Bug();
		context.add(bug);
		grid.moveTo(bug, 5, 5);

		bug.getUnderlyingCell(); // Should fail: No underlying HabitatCell
	}

	/**
	 * Test {@link Bug#getUnderlyingCell()} when there is multiple associated
	 * underlying cells.
	 */
	@Test(expected = IllegalStateException.class)
	public void testGetUnderlyingCellWhenMultipleCellGiven() {
		final Context<Object> context = TestUtils.initContext();
		final Grid<Object> grid = TestUtils.initGrid(context);

		final Bug bug = new Bug();
		context.add(bug);
		grid.moveTo(bug, 5, 5);

		final HabitatCell secondCell = new HabitatCell(new CellData(5, 5, 0.02));
		context.add(secondCell);
		grid.moveTo(secondCell, 5, 5);

		bug.getUnderlyingCell(); // Should fail: Multiple HabitatCells
	}

	/**
	 * Test the default behaviour of {@link Bug#getUnderlyingCell()}.
	 */
	@Test
	public void testGetUnderlying() {
		// Given
		final Context<Object> context = TestUtils.initContext();
		final Grid<Object> grid = TestUtils.initGrid(context);

		// Leave here: after moving the bug agent to the same position the test
		// may fail 50% of the times
		final HabitatCell expectedCell = (HabitatCell) grid.getObjectAt(5, 5);

		final Bug bug = new Bug();
		context.add(bug);
		grid.moveTo(bug, 5, 5);

		// When
		final HabitatCell actualCell = bug.getUnderlyingCell();

		// Then
		Assert.assertSame(expectedCell, actualCell);
	}

	/**
	 * Test if food consumption of a {@link Bug} is capped at the food
	 * availability of the current cell.
	 */
	@Test
	public void testFoodConsumptionWhenCellHasLesserFood() {
		// Given
		// Bug consumes 1 food by default
		final Bug bugSpy = Mockito.spy(new Bug());

		// Cell has 0.5 food
		final double food = 0.5;
		final HabitatCell underlyingCell = new HabitatCell(new CellData(5, 5,
				0.5));
		underlyingCell.setFoodAvailability(food);

		doReturn(underlyingCell).when(bugSpy).getUnderlyingCell();

		// When
		final double consumption = bugSpy.foodConsumption();

		// Then consumption should be 0.5
		Assert.assertEquals(food, consumption, Constants.DELTA);
	}

	/**
	 * Test if food consumption of a {@link Bug} is capped at the food
	 * consumption level of the current agent (which is <code>1.0</code> by
	 * default).
	 */
	@Test
	public void testFoodConsumptionWhenCellHasMoreFood() {
		// Given
		// Bug consumes 1 food by default
		final Bug bugSpy = Mockito.spy(new Bug());

		// Cell has 1.5 food
		final double food = 1.5;
		final HabitatCell underlyingCell = new HabitatCell(new CellData(5, 5,
				0.5));
		underlyingCell.setFoodAvailability(food);

		doReturn(underlyingCell).when(bugSpy).getUnderlyingCell();

		// When
		final double consumption = bugSpy.foodConsumption();

		// Then consumption should be 1.0
		Assert.assertEquals(1.0, consumption, Constants.DELTA);
	}

	/**
	 * Test if the Bug has <code>100%</code> survival probability, it always
	 * survives.
	 */
	@Test
	public void testMortalityWhenBugAlwaysSurvives() {
		final Bug bugSpy = spy(new Bug());
		bugSpy.setSize(0);
		bugSpy.setSurvivalProbability(1.0);
		doNothing().when(bugSpy).die();

		bugSpy.mortality();

		verify(bugSpy, never()).die();
	}

	/**
	 * Test if the Bug has <code>0%</code> survival probability, it never
	 * survives.
	 */
	@Test
	public void testMortalityWhenBugDoesNotSurvives() {
		final Bug bugSpy = spy(new Bug());
		bugSpy.setSize(0);
		bugSpy.setSurvivalProbability(0.0);
		doNothing().when(bugSpy).die();

		bugSpy.mortality();

		verify(bugSpy).die();
	}

	/**
	 * Test if the {@link Bug#mortality()} action results in reproduction when
	 * the bug reaches a size of <code>10.0</code>.
	 */
	@Test
	public void testMortalityReproduction() {
		final Bug bugSpy = spy(new Bug());
		bugSpy.setSize(10.0);

		doNothing().when(bugSpy).reproduce();
		doNothing().when(bugSpy).die();

		bugSpy.mortality();

		verify(bugSpy).reproduce();
		verify(bugSpy).die();
	}

	/**
	 * Test reproduction by checking the number of freshly spawned {@link Bug}
	 * agents.
	 */
	@Test
	public void testReproduceWithEmptyCellsInRange() {
		// Given
		final Context<Object> context = TestUtils.initContext();
		final Grid<Object> grid = TestUtils.initGrid(context);

		final Bug bug = new Bug();
		bug.setSize(10);
		context.add(bug);
		grid.moveTo(bug, 5, 5);

		// When
		bug.reproduce();
		bug.die();

		// Then
		final int bugs = context.getObjects(Bug.class).size();
		Assert.assertEquals(Constants.BUG_REPRODUCTION_RATE, bugs);
	}

	/**
	 * Test reproduction by checking there is no new {@link Bug} agents if there
	 * is no place to put them.
	 */
	@Test
	public void testReproduceWithNoEmptyCellsInRange() {
		// Given
		final Context<Object> context = TestUtils.initContext();
		final Grid<Object> grid = TestUtils.initGrid(context);

		final Bug bug = new Bug();
		bug.setSize(10);
		context.add(bug);
		grid.moveTo(bug, 5, 5);

		final ArrayList<Bug> prevBugs = new ArrayList<Bug>();

		final int n = Constants.BUG_REPRODUCTION_RANGE;
		for (int i = 5 - n; i <= 5 + n; ++i) {
			for (int j = 5 - n; j <= 5 + n; ++j) {
				if (5 == i && 5 == j) {
					continue;
				}

				final Bug act = new Bug();
				context.add(act);
				grid.moveTo(act, i, j);
				prevBugs.add(act);
			}
		}

		// When
		bug.reproduce();
		bug.die();

		// Then
		final IndexedIterable<Object> newBugs = context.getObjects(Bug.class);
		final int bugs = newBugs.size();

		// There should be no new bug, and the reproduced one should be dead
		Assert.assertEquals(prevBugs.size(), bugs);

		// And only the original bugs should exist
		for (final Object o : newBugs) {
			Assert.assertTrue(prevBugs.contains(o));
		}
	}

	/**
	 * Just to suppress <code>toString()</code> coverage noise.
	 */
	@Test
	public void testToStringWithoutGrid() {
		final Bug bug = new Bug();
		Assert.assertNotNull(bug.toString());
	}

	/**
	 * Just to suppress <code>toString()</code> coverage noise.
	 */
	@Test
	public void testToStringWithGrid() {
		final Bug bug = new Bug();
		final int x = RandomHelper.nextIntFromTo(0, Integer.MAX_VALUE);
		final int y = RandomHelper.nextIntFromTo(0, Integer.MAX_VALUE);

		final Context<Object> context = new DefaultContext<Object>();
		RunState.init().setMasterContext(context);

		@SuppressWarnings("unchecked")
		final Grid<Object> grid = mock(Grid.class);
		when(grid.getName()).thenReturn(Constants.GRID_ID);
		when(grid.getLocation(bug)).thenReturn(new GridPoint(x, y));

		context.addProjection(grid);

		context.add(bug);
		Assert.assertNotNull(bug.toString());
	}

}
