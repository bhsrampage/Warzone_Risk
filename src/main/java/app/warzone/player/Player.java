package app.warzone.player;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import app.warzone.game.orders.Deploy;
import app.warzone.game.orders.Order;
import app.warzone.map.*;

public class Player {
    public String d_playerName;
    public List<Country> d_holdingCountries;
    public int d_currentArmyCount;

    private List<Order> d_givenOrders;

    public Player(String p_name){
        d_playerName = p_name;
        d_holdingCountries = new ArrayList<>();
        d_currentArmyCount = 3;
        d_givenOrders = new ArrayList<>();
    }

    public void addCountryToHolderList(Country p_country,int p_armyCount){

        p_country.assignHolderWithArmies(this,p_armyCount);
        d_holdingCountries.add(p_country);

    }

    private Country getHoldingCountryByName(String p_name) {
        for (Country l_holdingCountry: d_holdingCountries) {
            if (l_holdingCountry.getD_countryName() == p_name)
                return l_holdingCountry;
        }
        return null;
    }

    public void issue_order() {
        Scanner l_scanner = new Scanner(System.in);
        String l_userCommand = l_scanner.nextLine();

        String[] l_cmdTokens = l_userCommand.split(" ");
        if (l_cmdTokens[0] != "deploy") {
            System.out.println("Invalid Command!");
            return;
        }
        d_givenOrders.add(new Deploy(this, Integer.parseInt(l_cmdTokens[2]), getHoldingCountryByName(l_cmdTokens[1])));

    }

    public Order next_order() {
        if (d_givenOrders.isEmpty())
            return null;
        Order l_nextOrder = d_givenOrders.get(0);
        d_givenOrders.remove(0);
        return l_nextOrder;
    }


}

