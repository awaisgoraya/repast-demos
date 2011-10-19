package simulation1.relogo

import repast.simphony.relogo.factories.AbstractReLogoGlobalsAndPanelFactory

public class UserGlobalsAndPanelFactory extends AbstractReLogoGlobalsAndPanelFactory{
	public void addGlobalsAndPanelComponents(){
	
		addButtonWL("setup","Setup") 	// Button with label ( method name, button label )
		addButtonWL("go","Go Once")
					  addToggleButtonWL("go","Go")	// Toggle Button with label ( method name, button label )
					  addSliderWL("numMotes", "Number of Motes", 1,1,100,50)
					  addSliderWL("set", "Communication Range of each Mote", 1,1,3,2)
	}
}