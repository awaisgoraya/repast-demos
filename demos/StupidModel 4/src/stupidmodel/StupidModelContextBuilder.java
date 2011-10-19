/*
 * Version info:
 *     $HeadURL: https://cscs-repast-demos.googlecode.com/svn/richard/StupidModel/tags/2011_05_29_model_04/src/stupidmodel/StupidModelContextBuilder.java $
 *     $LastChangedDate: 2011-07-14 18:07:44 +0200 (Cs, 14 júl. 2011) $
 *     $LastChangedRevision: 678 $
 *     $LastChangedBy: richard.legendi@gmail.com $
 */
package stupidmodel;

import repast.simphony.context.Context;
import repast.simphony.context.DefaultContext;
import repast.simphony.context.space.continuous.ContinuousSpaceFactoryFinder;
import repast.simphony.context.space.grid.GridFactoryFinder;
import repast.simphony.dataLoader.ContextBuilder;
import repast.simphony.space.continuous.ContinuousSpace;
import repast.simphony.space.continuous.NdPoint;
import repast.simphony.space.continuous.RandomCartesianAdder;
import repast.simphony.space.grid.Grid;
import repast.simphony.space.grid.GridBuilderParameters;
import repast.simphony.space.grid.SimpleGridAdder;
import repast.simphony.space.grid.WrapAroundBorders;
import repast.simphony.valueLayer.GridValueLayer;
import stupidmodel.agents.Bug;
import stupidmodel.agents.HabitatCell;
import stupidmodel.common.Constants;

/**
 * Custom {@link ContextBuilder} implementation for the <i>StupidModel 1</i>.
 * 
 * @author Richard O. Legendi (richard.legendi)
 * @since 2.0-beta, 2011
 * @version $Id: StupidModelContextBuilder.java 150 2011-05-26 19:06:40Z
 *          richard.legendi@gmail.com $
 */
public class StupidModelContextBuilder extends DefaultContext<Object> implements
		ContextBuilder<Object> {

	@Override
	public Context<Object> build(final Context<Object> context) {
		// Set a specified context ID
		context.setId(Constants.CONTEXT_ID);

		// Create a toridal space with random positioning with the specified
		// dimensions
		final ContinuousSpace<Object> space = ContinuousSpaceFactoryFinder
				.createContinuousSpaceFactory(null) // No hints
				.createContinuousSpace(
						Constants.SPACE_ID,
						context,
						new RandomCartesianAdder<Object>(),
						new repast.simphony.space.continuous.WrapAroundBorders(),
						Constants.GRID_SIZE, Constants.GRID_SIZE);

		final Grid<Object> grid = GridFactoryFinder
				.createGridFactory(null)
				.createGrid(
						Constants.GRID_ID,
						context,
						new GridBuilderParameters<Object>(
								new repast.simphony.space.grid.WrapAroundBorders(),
								// This is a simple implementation of an adder
								// that doesn't perform any action
								new SimpleGridAdder<Object>(),
								// Each cell in the grid is multi-occupancy
								true,
								// Size of the grid (defined constants)
								Constants.GRID_SIZE, Constants.GRID_SIZE));

		// Create the specified number of Bug agents and assign them to the
		// space and the grid
		for (int i = 0; i < Constants.BUG_COUNT; ++i) {
			final Bug bug = new Bug();
			context.add(bug);
			final NdPoint pt = space.getLocation(bug);
			grid.moveTo(bug, (int) pt.getX(), (int) pt.getY());
		}

		// Create a background layer for the displayed grid that represents the
		// available (grown) food amount
		final GridValueLayer foodValueLayer = new GridValueLayer(
				Constants.FOOD_VALUE_LAYER_ID, // Access layer through context
				true, // Densely populated
				new WrapAroundBorders(), // Toric world
				// Size of the grid (defined constants)
				Constants.GRID_SIZE, Constants.GRID_SIZE);

		context.addValueLayer(foodValueLayer);

		// Fill up the context with cells, and set the initial food values for
		// the new layer. Also add them to the created grid.
		for (int i = 0; i < Constants.GRID_SIZE; ++i) {
			for (int j = 0; j < Constants.GRID_SIZE; ++j) {
				final HabitatCell cell = new HabitatCell(i, j);
				context.add(cell); // First add it to the context
				grid.moveTo(cell, i, j);
				foodValueLayer.set(cell.getFoodAvailability(), i, j);
			}
		}

		return context;
	}
}
