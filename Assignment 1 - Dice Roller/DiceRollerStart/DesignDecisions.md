# Design Decisions
Caleb Scopetski

## Created singularRoll() helper method:
* Made the code more readable and intention of my code clearer, by extracting the code for a single dice roll from the roll() loop
* Increased maintainability, to modify the way singular die rolling works, the helper function can be modified without changing the logic of rolling multiple dice

## Utilized constant variables for MIN_DICE, MIN_SIDES, and MIN_ROLL_VALUE:
* Makes the code more maintainable, if the minimum number of sides was changed from 2 to 3, the constant would only have to be changed in one place
* Makes the code more readable and intentional by naming constants

## Used an array to store roll history:
* The number of dice is constant once defined by the client
* Used an array due to the static nature of the dice number, this is faster than the dynamically sized ArrayList