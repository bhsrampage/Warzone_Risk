package app.warzone.game;

import java.io.File;
import java.net.URL;
import java.util.Scanner;

/**
 * This class is responsible for maintaining the rules for the Warzone game along with generating the flow
 * of gameplay.
 */
public class GameEngine {
    Scanner SCAN;

    public GameEngine(){
        SCAN = new Scanner(System.in);
    }
    public void initialize() {
        System.out.println("Welcome to Risk (Warzone) by U6 build1");

    }

}
