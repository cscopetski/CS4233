package dice;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DiceRollerTest {

    @Test
    void testRoll() throws DiceException {

        DiceRoller roller = new DiceRollerImpl(8, 10);
        int rollValue = roller.roll();
        int diceSum = 0;
        for (int d = 1; d <= 10; d++) {
            int dieValue = roller.getDieValue(d);
            assertTrue(dieValue > 0 && dieValue <= 8);
            diceSum += dieValue;
        }
        assertEquals(rollValue, diceSum);
    }

    @Test
    void testRoll2() throws DiceException {

        DiceRoller roller = new DiceRollerImpl(2, 10);
        int rollValue = roller.roll();
        int diceSum = 0;
        for (int d = 1; d <= 10; d++) {
            int dieValue = roller.getDieValue(d);
            assertTrue(dieValue > 0 && dieValue <= 2);
            diceSum += dieValue;
        }
        assertEquals(rollValue, diceSum);
    }

    @Test
    void testGetDiceTotal() throws DiceException {
        DiceRoller roller = new DiceRollerImpl(8, 6);
        int rollValue = roller.roll();
        assertEquals(rollValue, roller.getDiceTotal());
    }

    @Test
    void testGetDiceTotalWithoutRoll() throws DiceException {
        DiceRoller roller = new DiceRollerImpl(3, 3);
        assertEquals(-1, roller.getDiceTotal());
    }

    @Test
    void testGetDiceCount() throws DiceException {
        DiceRoller roller = new DiceRollerImpl(8, 20);
        assertEquals(20, roller.getDiceCount());
    }

    @Test
    void testGetDieValueBeforeARoll() throws DiceException {
        DiceRoller roller = new DiceRollerImpl(6, 3);
        assertThrows(DiceException.class, () -> roller.getDieValue(1));
    }

    @Test
    void testConfigurationSides() {
        assertThrows(DiceException.class, () -> new DiceRollerImpl(1, 3));
    }

    @Test
    void testConfigurationDice() {
        assertThrows(DiceException.class, () -> new DiceRollerImpl(10, 0));
    }

    @Test
    void testConfigurationSidesAndDice() {
        assertThrows(DiceException.class, () -> new DiceRollerImpl(-1, -1));
    }

    @Test
    void testConfigurationNoError() {
        assertDoesNotThrow(() -> new DiceRollerImpl(2, 4));
    }
}