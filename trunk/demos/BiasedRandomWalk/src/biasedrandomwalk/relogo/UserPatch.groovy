package biasedrandomwalk.relogo

import org.jfree.chart.annotations.XYAnnotation;



import static repast.simphony.relogo.Utility.*;
import static repast.simphony.relogo.UtilityG.*;
import repast.simphony.relogo.BasePatch;
import repast.simphony.relogo.Stop;
import repast.simphony.relogo.Utility;
import repast.simphony.relogo.UtilityG;
import repast.simphony.relogo.ast.Diffusible;

class UserPatch extends BasePatch{
	
	@Diffusible
	visited=0
	
	def setBiasPoint()
	{
		if(pxcor == 9)
			setPcolor(red())
	}
	
	/*	
	def colorCovered()
	{
	if(visited&&s)
	{
		if(!done||getPcolor()==red())
			setPcolor(red())
		else
			setPcolor(green())
	}}*/
}