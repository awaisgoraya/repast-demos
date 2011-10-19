package moth_advance.relogo
import static repast.simphony.relogo.Utility.*;
import static repast.simphony.relogo.UtilityG.*;
import repast.simphony.relogo.BasePatch;
import repast.simphony.relogo.BaseTurtle;
import repast.simphony.relogo.Plural;
import repast.simphony.relogo.Stop;
import repast.simphony.relogo.Utility;
import repast.simphony.relogo.UtilityG;

class Moth extends BaseTurtle {

		def x
		def y
		def inLight = false
		def step()
		{
	
	def N = count(inRadius(lights(),(source+intensity)))
	if(N>0)	
		{
			inLight = true
			x = (random((source+intensity)))
			y = random(sqrt(((source+intensity)*(source+intensity))-(x*x)))
			if(random(2))
			{
				x=(-1)*x
			}
			if(random(2))
			{
				y=(-1)*y
			}
			x = x + oneOf(inRadius(lights(),(source+intensity))).x 
			y = y + oneOf(inRadius(lights(),(source+intensity))).y 
			facexy(x,y)
			fd(exSpeed*0.5)
		
		}
		
		
else{
			x=random(200)
			y=random(200)
			if(random(2))
			{
				x=(-1)*x
			}
			if(random(2))
			{
				y=(-1)*y
			}
			facexy(x,y)
			forward(nrmlSpeed*0.1)
	}
	
			
		
			
			}
		
	
	
}
