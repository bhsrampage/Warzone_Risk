package app.warzone.player;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import app.warzone.map.Country;
import app.warzone.player.orders.Deploy;
import app.warzone.player.orders.Order;

public class Player {
	public String d_playerName;
	public List<Country> d_holdingCountries;
	public int d_currentArmyCount;

	private List<Order> d_givenOrders;

	public Player(String p_name) {
		d_playerName = p_name;
		d_holdingCountries = new ArrayList<>();
		d_currentArmyCount = 3;
		d_givenOrders = new ArrayList<>();
	}

	public List<Order> getD_givenOrders() {
		return d_givenOrders;
	}

	public void printPlayerStatus() {
		System.out.printf("\nPlayer Name:- %s\nArmies Left:- %d\n", d_playerName, d_currentArmyCount);
		for (Country l_country : d_holdingCountries) {
			System.out.printf("%s\t Army Count:- %d\n", l_country.getD_countryName(), l_country.getCurrentArmyCount());
		}
	}

	public void addCountryToHolderList(Country p_country, int p_armyCount) {

		p_country.assignHolderWithArmies(this, p_armyCount);
		d_holdingCountries.add(p_country);

	}

	private Country getHoldingCountryByName(String p_name) {
		for (Country l_holdingCountry : d_holdingCountries) {
			if (l_holdingCountry.getD_countryName().equals(p_name))
				return l_holdingCountry;
		}
		return null;
	}

	public void issue_order() {
		System.out.printf("\nEnter your command %s\n", d_playerName);
		Scanner l_scanner = new Scanner(System.in);
		String l_userCommand = l_scanner.nextLine();

		String[] l_cmdTokens = l_userCommand.split(" ");
		if (!l_cmdTokens[0].equals("deploy")) {
			System.out.println("Invalid Command!");
			return;
		}
		int l_armyToDeploy = Integer.parseInt(l_cmdTokens[2]);
		if (l_armyToDeploy > d_currentArmyCount) {
			System.out.println("You cannot deploy more armies than you have..");
			return;
		}

		Country l_deployingToCountry = getHoldingCountryByName(l_cmdTokens[1]);
		if (l_deployingToCountry == null) {
			System.out.println("You do not own the country you are trying to deploy to..");
			return;
		}

		d_givenOrders.add(new Deploy(this, l_armyToDeploy, l_deployingToCountry));
	}

	public Order next_order() {
		if (d_givenOrders.isEmpty())
			return null;
		Order l_nextOrder = d_givenOrders.get(0);
		d_givenOrders.remove(0);
		return l_nextOrder;
	}

}
