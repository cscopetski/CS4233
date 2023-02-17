Name: Caleb Scopetski

TDD TODO/Task list

**Build Tests**

These are for implementing the EscapeGameBuilder's `makeGameManager()` method.

| **#** | Test                                                            | Comments                        |
|:-----:|:----------------------------------------------------------------|:--------------------------------|
| 1 | Build game manager | create game object |
| 2 | Build game manager 2 X 2, 2 players, and coordinate type SQUARE | create correct game object |
| 3 | Build game manager 4 X 6, 2 players, and coordinate type HEX | create correct game object |
| 4 | Build game manager with 2X2, 4 locations, 2, pieces, 2 players and coordinate type SQUARE | create correct game object |

**Coordinate Tests**

| **#** | Test                                                            | Comments                        |
|:-----:|:----------------------------------------------------------------|:--------------------------------|
| 1 | Check two coordinates with the same row and column are equal | coordinate equals |
| 2 | Check two coordinates with different row and column are not equal | coordinate unequal |
| 3 | Check coordinate creation from game manager | created correct coordinate |
| 4 | Check coordinate hashing | coordinate hashing |

**Movement Tests**

| **#** | Test                                                            | Comments                        |
|:-----:|:----------------------------------------------------------------|:--------------------------------|
| 1 | Call move on location with no piece | Invalid move |
| 2 | Call move on piece that is not owned by current player | Invalid move |
| 3 | Move piece onto location with another piece (no rules) | Invalid move |
| 4 | Player turn does not change when inputting invalid move | Maintain turns on invalid move |
| 5 | Player turn changes when inputting valid move | Switch turn on valid move |
| * | _**Refactored by extracting board hashmap into separate board class**_ | Refactor |
