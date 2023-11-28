package app.warzone.player.strategy;

import app.warzone.map.Country;
import app.warzone.player.Player;
import app.warzone.player.orders.*;

import java.util.Random;

public class RandomStrategy extends PlayerStrategy{
    boolean d_isExecuted = false;
    public RandomStrategy(Player p_player) {
        super(p_player);
    }

    /**
     * Decide the country to advance or attack.
     *
     * @return random territory adjacent to the player's country
     */
    protected Country anyCountry() {
        Random l_rand = new Random();
        Country attackCountry;
        int l_rndOwnCountry;
        l_rndOwnCountry = l_rand.nextInt(d_targetPlayer.d_holdingCountries.size() - 1);
        attackCountry = d_targetPlayer.d_holdingCountries.get(l_rndOwnCountry);
        if(attackCountry.getNeighbouringCountries().get(0)!=null){
            attackCountry = attackCountry.getNeighbouringCountries().get(0);
        }

        return(attackCountry);
    }

    /**
     * Determines a random country from list of owned countries.
     *
     * @return random territory owned by the player
     */
    protected Country ownCountry() {
        Random l_rand = new Random();
        int l_rndOwnCountry = 0;
        if(d_targetPlayer.d_holdingCountries.size()>1) {
            l_rndOwnCountry = l_rand.nextInt(d_targetPlayer.d_holdingCountries.size() - 1);
        }
        return (d_targetPlayer.d_holdingCountries.get(l_rndOwnCountry));
    }


    /**
     * Create an order for Random player
     *
     * @return created order
     */
    @Override
    public Order createOrder() {
        Random l_rand = new Random();
        int l_rndOrder = l_rand.nextInt(5);
        int l_numOfArmies;
            System.out.println(l_rndOrder);
            d_isExecuted = true;
            switch (l_rndOrder) {
                case (0):
                    // Deploy Order
                    System.out.println("Random Order : Deploy");
                    l_numOfArmies = l_rand.nextInt(d_targetPlayer.d_currentArmyCount);
                    return new Deploy(d_targetPlayer, l_numOfArmies, ownCountry());
                case (1):
                    // Advance Order
                    System.out.println("Random Order : Advance");
                    l_numOfArmies = l_rand.nextInt(d_targetPlayer.d_currentArmyCount);
                    return new Advance(d_targetPlayer, ownCountry(), anyCountry(), l_numOfArmies);
                case (2):
                    // AirLift
                    System.out.println("Random Order : Airlift");
                    l_numOfArmies = l_rand.nextInt(d_targetPlayer.d_currentArmyCount);
                    if (d_targetPlayer.d_holdingCards.contains("airlift")){
                        return new Airlift(d_targetPlayer, ownCountry(), ownCountry(), l_numOfArmies);
                    }
                    else{
                        System.out.println("Player does not hold Airlift card");
                    }
                    break;
                case (3):
                    //Blockade Card
                    System.out.println("Random Order : Blockade");
                    if (d_targetPlayer.d_holdingCards.contains("blockade")){
                        return new Blockade(d_targetPlayer, ownCountry());
                    }
                    else{
                        System.out.println("Player does not hold Blockade card");
                    }
                    break;
                case (4):
                    //Bomb Card
                    System.out.println("Random Order : Bomb");
                    if (d_targetPlayer.d_holdingCards.contains("bomb")){
                        return new Bomb(d_targetPlayer, anyCountry());
                    }
                    else{
                        System.out.println("Player does not hold Bomb card");
                    }
                    break;
                default:
                    break;
            }
        return null;
    }

    public String getStrategyName() {
        return "random";
    }
}
