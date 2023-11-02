package app.warzone.player;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.risk.models.Player;

import app.warzone.game.GameUtils;
import app.warzone.map.Country;
import app.warzone.player.orders.Advance;
import app.warzone.player.orders.Deploy;
import app.warzone.player.orders.Order;

/**
 * Represents a player in the Warzone game, with methods for managing player
 * actions and orders.
 */
public class Player {

    public String d_playerName;
    public List<Country> d_holdingCountries;
    public int d_currentArmyCount;
    public boolean d_hasCommittedOrders;
    private List<Order> d_givenOrders;
    public boolean d_hasLost;

    public List<String> d_holdingCards;
    public List<String> d_diplomacyPlayers;

    /**
     * Constructor for the Player class.
     *
     * @param p_name The name of the player.
     */
    public Player(String p_name) {
        d_playerName = p_name;
        d_holdingCountries = new ArrayList<>();
        d_currentArmyCount = 3;
        d_givenOrders = new ArrayList<>();
        d_hasCommittedOrders = false;
        d_hasLost = false;
        d_holdingCards = new ArrayList<>();
    }
    
    /**
     * adds a new card to the list of holding cards.
     *
     * @param p_card card to be added.
     */
    public void addCardToHolding(String p_card) {
    	d_holdingCards.add(p_card);
    }

    /**
     * Removes a card from the holding list if it is present
     *
     * @param p_card card to be removed
     */
    public void removeCardFromHolding(String p_card) {
    	if (d_holdingCards.contains(p_card)) {
    		d_holdingCards.remove(i);
        }
    }

    /**
     * Get the list of orders given by the player.
     *
     * @return The list of orders.
     */
    public List<Order> getD_givenOrders() {
        return d_givenOrders;
    }

    /**
     * Print the current status of the player, including their armies and held
     * countries.
     */
    public void printPlayerStatus() {
        System.out.printf("\nPlayer Name:- %s\nArmies Left:- %d\n", d_playerName, d_currentArmyCount);
        for (Country l_country : d_holdingCountries) {
            System.out.printf("%s\t Army Count:- %d\n", l_country.getD_countryName(), l_country.getCurrentArmyCount());
        }
    }

    /**
     * Add a country to the player's holding list with the specified number of
     * armies.
     *
     * @param p_country   The country to add.
     * @param p_armyCount The number of armies to assign to the country.
     */
    public void addCountryToHolderList(Country p_country, int p_armyCount) {
        if(p_country.getCountryHolder() != null) {
            p_country.getCountryHolder().d_holdingCountries.remove(p_country); //Remove country from previous owners holding list
        }
        p_country.assignHolderWithArmies(this, p_armyCount);
        d_holdingCountries.add(p_country);
    }

    /**
     * Get a holding country by its name.
     *
     * @param p_name The name of the country to find.
     * @return The Country object if found, or null if not found.
     */
    private Country getHoldingCountryByName(String p_name) {
        for (Country l_holdingCountry : d_holdingCountries) {
            if (l_holdingCountry.getD_countryName().equals(p_name))
                return l_holdingCountry;
        }
        return null;
    }

    /**
     * Issue a deployment order based on user input.
     */
    public void issue_order() {
        System.out.printf("\n PLAYING:-  %s\n", d_playerName);
        System.out.println("Note: Your current status is:");
        printPlayerStatus();
        System.out.printf("\nEnter your command %s\n", d_playerName);
        Scanner l_scanner = new Scanner(System.in);
        String l_userCommand = l_scanner.nextLine();

        String[] l_cmdTokens = l_userCommand.split(" ");
        switch (l_cmdTokens[0]) {
            case "deploy":
                System.out.println("Deploy order Received!!");
                if(l_cmdTokens.length < 3){
                    System.out.println("Invalid Arguments");
                }
                int l_armyToDeploy = Integer.parseInt(l_cmdTokens[2]);
                Country l_deployingToCountry = getHoldingCountryByName(l_cmdTokens[1]);
                d_givenOrders.add(new Deploy(this, l_armyToDeploy, l_deployingToCountry));
                break;
            case "advance":
                System.out.println("Advance order Received!!");
                if(l_cmdTokens.length < 4){
                    System.out.println("Invalid Arguments");
                }
                d_givenOrders.add(new Advance(this,
                                GameUtils.d_currTargetMap.getCountryByName(l_cmdTokens[1]),
                                GameUtils.d_currTargetMap.getCountryByName(l_cmdTokens[2]),
                                Integer.parseInt(l_cmdTokens[3])
                        )
                );
                break;
            case "commit":
                System.out.println("Committing orders for " + d_playerName);
                d_hasCommittedOrders = true;
                break;
            default:
                System.out.println("Invalid Game Command");
                break;
        }
    }

    /**
     * Get the next order in the player's list of given orders.
     *
     * @return The next order to execute or null if there are no more orders.
     */
    public Order next_order() {
        if (d_givenOrders.isEmpty())
            return null;
        Order l_nextOrder = d_givenOrders.get(0);
        d_givenOrders.remove(0);
        return l_nextOrder;
    }

    /**
     * To add a player to the Diplomacy Players list
     *
     * @param p_player player name
     */
    public void addDiplomacyPlayer(Player p_player) {
    	d_diplomacyPlayers.add(p_player);
    }

    /**
     * To remove a player from the Diplomacy Players list
     *
     * @param p_player player name
     */
    public void removeDiplomacyPlayer(String p_player) {
        Player l_player = GameUtils.getPlayerByName(p_player);
        d_diplomacyPlayers.remove(l_player);
    }
}
