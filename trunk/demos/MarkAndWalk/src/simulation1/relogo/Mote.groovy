package simulation1.relogo

import static repast.simphony.relogo.Utility.*;
import static repast.simphony.relogo.UtilityG.*;
import repast.simphony.relogo.BasePatch;
import repast.simphony.relogo.BaseTurtle;
import repast.simphony.relogo.Plural;
import repast.simphony.relogo.Stop;
import repast.simphony.relogo.Utility;
import repast.simphony.relogo.UtilityG;

class Mote extends BaseTurtle {
	
	def x0 =0 
	def y0 =0
	def xf=0
	def yf=0
	def moved=0
	def f=0
	
	def step()
	{
	/*	if(f==0)
		{	x0=getXcor()
			y0=getYcor()
		}*/
		downhill("visited")
	/*	if((patch(getXcor(),getYcor()).connected-1)==0)
		{
			f=1
			//label="caught"
			step()
		}
		f=0*/
		visited++
		moved++
		
	}

}
