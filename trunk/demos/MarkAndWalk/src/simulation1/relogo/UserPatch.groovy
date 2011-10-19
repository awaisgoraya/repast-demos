package simulation1.relogo

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
	
	def connected=0
	
	def k
	def setColor()
	{
		if(count(motesHere())>0)
		{ 
			k=count(motesHere())
			connected=k
			setPcolor(yellow())
		//	if(set==1)
		//	{
				ask(neighbors())
				{
						setPcolor(yellow())
						connected=k
					//	if(set==2)
						//{
							ask(neighbors())
							{
								setPcolor(yellow())
								connected=k
							//	if(set==3)
							//	{
							//		ask(neighbors()){
							//		setPcolor(yellow())
							//		connected=k
							//	}
							//}
							
						}
					//}
				}
			//}
		}
		else	
		{
			if(connected==0)
				pcolor = scaleColor(blue(),visited,10,0)
		}
	}
}