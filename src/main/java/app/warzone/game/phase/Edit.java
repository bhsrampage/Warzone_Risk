package app.warzone.game.phase;

import app.warzone.game.GameEngine;

import java.util.List;

/**
 *	ConcreteState of the State pattern. In this example, defines behavior 
 *  for commands that are valid in this state, and for the others signifies  
 *  that the command is invalid. 
 *  
 *  This state represents a group of states, and defines the behavior 
 *  that is common to all the states in its group. All the states in its 
 *  group need to extend this class. 
 *  
 */
public abstract class Edit extends Phase {

	Edit(GameEngine p_ge) {
		super(p_ge);
	}
	@Override
	public void setPlayers(List<String> arguments) {
		printInvalidCommandMessage();
	}

	public void assignCountries() {
		printInvalidCommandMessage(); 
	}

	public void createOrders(){
		printInvalidCommandMessage();
	}

	public void executeOrders(){
		printInvalidCommandMessage();
	}

	public void endGame() {
		printInvalidCommandMessage(); 
	}
}
