# Summary #

  * Model        : Fire Model
  * Platform     : RepastS Java
  * Version(s)	: Repast Simphony 2.0
  * Download     : http://repast-demos.googlecode.com/files/Fire-1.0.1.zip
  * SVN Source  : http://repast-demos.googlecode.com/svn/trunk/demos/Fire
  * Documentation : This Wiki Page & PDF in project docs directory
  * Author       : Grace Lin


---


# Contents #



---


# Introduction #

The fire model simulates a forest fire.  How far the fire can spread is dependent on the density of the trees, the direction and probability of the fire, and the direction and probability of the wind.  The running model should look like the following figure:

![https://repast-demos.googlecode.com/svn/trunk/demos/Fire/docs/latex_files/Images/fire_screen.png](https://repast-demos.googlecode.com/svn/trunk/demos/Fire/docs/latex_files/Images/fire_screen.png)

Each tree goes through three states: FRESH, BURN, and ASH.  Initially, all trees are fresh (state=FRESH, indicated by bright green).  Trees then get burned depending on the probability set by the user state=BURN, indicated by darker green), and finally becomes ash (state=ASH, indicated by even darker green).  The fire always start from the middle of the forest and spreads out to neighborhood trees.  The size of neighborhood visible to a tree is determined by the user.  The fire can spread in eight directions: north, east, south, west, north-east, north-west, south-east, and south-west.  The wind can blow in any of the eight directions as well.

# Prerequisites #

Proper installation of Repast Simphony (version 2.0 Beta, released 12/3/2010 or later).

# Model Parameters #

After running the fire, set up _Scenario Tree -> Value Layer Details_ with black as the base color and with "Green" color checked.  For the Parameters tab, the figure and descriptions are shown as follow:

![https://repast-demos.googlecode.com/svn/trunk/demos/Fire/docs/latex_files/Images/fire_param.png](https://repast-demos.googlecode.com/svn/trunk/demos/Fire/docs/latex_files/Images/fire_param.png)

  * **Grid Height**:  Number of rows.
  * **Grid Width**:  Number of columns.
  * **Initial Trees**:  Number of trees to populate initially.
  * **Neighbor Size**:  How far a fire can reach.
  * **Percentage to Burn**:  Percentage of trees within neighborhood to burn.
  * **Probability All Direction**:  Apply same probability in all eight directions.  Set to 0 for individual directions (see following eight parameters).
  * **Probability East**:  Probability applied towards east direction.
  * **Probability North**:  Probability applied towards north direction.
  * **Probability North East**:  Probability applied towards north-east direction.
  * **Probability North West**:  Probability applied towards north-west direction.
  * **Probability South**:  Probability applied towards south direction.
  * **Probability South East**:  Probability applied towards south-east direction.
  * **Probability South West**:  Probability applied towards south-west direction.
  * **Probability West**:  Probability applied towards west direction.
  * **Wind Direction**:  Wind can blow in one of the eight directions.
  * **Wind Strength**:  Wind strength determines the burn probability of of a tree (strong=0.9, medium=0.5, weak=0.1, none=0.0).

# References #

  * NetLogo fire model: http://ccl.northwestern.edu/netlogo/models/Fire
  * Fire!, Probability, and Chaos: http://www.shodor.org/interactivate/lessons/FireProbabilityChaos/