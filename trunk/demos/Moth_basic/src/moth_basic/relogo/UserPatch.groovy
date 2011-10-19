package moth_basic.relogo

import static repast.simphony.relogo.Utility.*;
import static repast.simphony.relogo.UtilityG.*;
import repast.simphony.relogo.BasePatch;
import repast.simphony.relogo.Stop;
import repast.simphony.relogo.Utility;
import repast.simphony.relogo.UtilityG;
import repast.simphony.relogo.ast.Diffusible;

class UserPatch extends BasePatch{

	def dis
	
	def setLight()
	{
			dis=distancexy(0,0)
			if(dis<5&&dis>0)
		    	pcolor = scaleColor(blue(),dis,6,1)
			else
			   	setPcolor(black())
	}
}