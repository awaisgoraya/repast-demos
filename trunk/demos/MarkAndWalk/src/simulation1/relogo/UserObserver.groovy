package simulation1.relogo

import static repast.simphony.relogo.Utility.*;
import static repast.simphony.relogo.UtilityG.*;
import repast.simphony.relogo.BaseObserver;
import repast.simphony.relogo.Stop;
import repast.simphony.relogo.Utility;
import repast.simphony.relogo.UtilityG;

class UserObserver extends BaseObserver{

	def full = 1
	def vc=0
	def v=0
	def v0 =0
	def v1 =0
	def v2=0
	def v3=0
	def v4=0
	def v5=0
	
	def setup(){
			clearAll()
			setDefaultShape(Mote,"pentagon")
			createMotes(numMotes){
				setxy(0,0)
				setColor(red())
						}
			patch(0,0).visited=1
		}
	
		def go(){
			full=1
			vc=0
			v0 =0
			 v1 =0
			 v2=0
			 v3=0
			 v4=0
			 v5=0
			
			 ask(patches())
			{
				connected=0
			}
			ask(patches()){
				setColor()
				if(visited<1)
				{   v0++
					full=0
				}
				else
				    vc++
				if(visited==1)
				{
					v1++
				}
				if(visited==2)
				{
					v2++
				}
				if(visited==3)
				{
					v3++
				}
				if(visited==4)
				{
					v4++
				}
				if(visited==5)
				{
					v5++
				}
				
			}
			
			v=((vc/1089)*100)
			
			ask(motes()){
				if(full)
					die()
				else
					step()
			}
			tick()
		}
		

}