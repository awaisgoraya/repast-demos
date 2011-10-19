package moth_advance.relogo


import static repast.simphony.relogo.Utility.*;
import static repast.simphony.relogo.UtilityG.*;
import repast.simphony.relogo.BaseObserver;
import repast.simphony.relogo.Stop;
import repast.simphony.relogo.Utility;
import repast.simphony.relogo.UtilityG;

class UserObserver extends BaseObserver{

	def x = 0
	def y = 0
	def max=0
	
	def setup()
	{
	clearAll()
		
		setDefaultShape(Moth,"default")
		createMoths(numMoths){
			setxy(randomXcor(),randomYcor())
			setColor(brown())
		}
		ask(patches())
		{
			makeEnv()
		}	
		setMax()
		ask(patches())
		{ 
			setM(max)
			makeLight()
		}
		/*
		 
		setDefaultShape(Light,"circle")
		createLights(numLights){
			x=randomXcor()
			y=randomYcor()
				setxy(x,y)
			patch(x,y).light=1
		}*/
		}
				
		  
		def go(){
			ask(lights()){
				step()
			}
			ask(moths()){
				step()
			}
			}
		def setMax()
		{
			ask(patches())
			{
				if(intensity>max)
				{
					max=intensity
				}
			}
		}
		
		}
