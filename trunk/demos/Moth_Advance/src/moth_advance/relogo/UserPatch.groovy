package moth_advance.relogo


import static repast.simphony.relogo.Utility.*;
import static repast.simphony.relogo.UtilityG.*;
import repast.simphony.relogo.BasePatch;
import repast.simphony.relogo.Stop;
import repast.simphony.relogo.Utility;
import repast.simphony.relogo.UtilityG;
import repast.simphony.relogo.ast.Diffusible;

class UserPatch extends BasePatch{
	
	def l=0
	def intensity=0
	def m=0
	
	def makeEnv()
	{
		ask(patches())
		{
			if(l==1)
			{
				intensity=intensity+sqrt((x-getPxcor())*(x-getPxcor())+(y-getPycor())*(y-getPycor()))	
			}	
		}
	}
	
	def setM(value)
	{
		m=value	
	}
	
	def makeLight()
	{
		if(l==1)
			setPcolor(white())
		else
		 	pcolor = scaleColor(blue(),(m-intensity),0,5)
	}

}