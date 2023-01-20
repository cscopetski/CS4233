/*
 * Implementation of a DiceRoller.
 *
 * Copyright © 2023, Gary F. Pollice
 */
package dice;

import java.util.concurrent.ThreadLocalRandom;

/**
 * Implementation of DiceRoller Interface
 * Represents a dice roller with a given number of sides and dice
 * The client can roll the dice, and query the sum of all dice, total number of dice, or value of a specific die
 * @author Caleb Scopetski
 */
public class DiceRollerImpl implements DiceRoller {


    private static final int MIN_DICE = 1;
    private static final int MIN_SIDES = 2;
    private static final int MIN_ROLL_VALUE = 1;

    private int[] rollHistory;
    private int numberOfSides;
    private int numberOfDice;

    //Tracks if the dice have been rolled once
    private boolean hasRolled = false;

    /**
     * Default constructor, creates a dice roller with the given number of sides and dice
     * @param numberOfSides The number of dice sides range(MIN_SIDES <= x)
     * @param numberOfDice The number of dice range(MIN_DICE <= x)
     * @throws DiceException throws an exception if the number of sides < MIN_SIDES or number of dice < MIN_DICE
     */
    public DiceRollerImpl(int numberOfSides, int numberOfDice) throws DiceException {

        if(numberOfSides < MIN_SIDES){
            throw new DiceException("Number of sides must be >= " + MIN_SIDES);
        }

        if(numberOfDice < MIN_DICE){
            throw new DiceException("Number of dice must be >= " + MIN_DICE);
        }

        this.numberOfDice = numberOfDice;
        this.numberOfSides = numberOfSides;
        this.rollHistory = new int[numberOfDice];
    }

    /**
     * Roll the dice.
     * @return Return the total of all of the dice after rolling them all.
     */
    @Override
    public int roll() {

        for (int i = 0; i < numberOfDice; i++) {
            singularRoll(i);
        }

        //dice have been rolled, set hasRolled to true
        hasRolled = true;

        return getDiceTotal();
    }

    /**
     * Helper function for roll(), rolls a singular die
     * Generates a random dice value between MIN_ROLL_VALUE and numberOfSides, and adds it to the roll history
     * @param diceNumber the dice index for inserting into roll history
     */
    private void singularRoll(int diceNumber){
        int rollValue = ThreadLocalRandom.current().nextInt(MIN_ROLL_VALUE, numberOfSides + 1);
        rollHistory[diceNumber] = rollValue;
    }

    /**
     * Get the total of all of the dice.
     * @return the total of all dice or -1 if there have been no calls to roll().
     */
    @Override
    public int getDiceTotal() {

        if(!hasRolled){
            return -1;
        }

        int sum = 0;

        for (int roll: rollHistory) {
            sum+= roll;
        }

        return sum;
    }

    /**
     * @return the number of dice
     */
    @Override
    public int getDiceCount() {
        return numberOfDice;
    }

    /**
     * Get the value of the specified die.
     *
     * @param dieNumber the die to query (1 to getDiceCount())
     * @return the value on the selected die.
     * @throws DiceException if the die number is invalid or if the die was never rolled.
     */
    @Override
    public int getDieValue(int dieNumber) throws DiceException {

        if(!hasRolled){
            throw new DiceException("Invalid die number: [" + dieNumber + "] Specified die number is larger than total number of dice ("+numberOfDice+")");
        }

        if(dieNumber <= 0 || dieNumber > numberOfDice){
            throw new DiceException("Invalid die number: [" + dieNumber + "] Die number must be >= " + MIN_DICE + " and <= " + numberOfDice);
        }

        return rollHistory[dieNumber-1];
    }


}
