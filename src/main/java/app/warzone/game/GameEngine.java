package app.warzone.game;

import app.warzone.Main.Phase;
import java.util.Scanner;

/**
 * This class is responsible for maintaining the rules for the Warzone game along with generating the flow
 * of gameplay.
 */
public class GameEngine {
    Scanner SCAN;
    Phase d_currPhase;

    public GameEngine(){
        SCAN = new Scanner(System.in);
    }

    public Phase getD_currPhase() {
        return d_currPhase;
    }

    public void initialize() {
        System.out.println("Welcome to Risk (Warzone) by U6 build1");
        int choice;
        boolean playing = true;
        while (playing){
            System.out.println("1. Map Editor\n2. Play Game\nEnter your choice:- ");
            choice = SCAN.nextInt();
            switch (choice){
               case 1: d_currPhase = Phase.MAP_ACTIONS; break;
               case 2: d_currPhase = Phase.GAMEPLAY; break;
               default: playing = false; break;
            }

        }

    }

}
