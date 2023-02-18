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
| 2 | Move from start location to start location | Invalid move |
| 3 | Call move on piece that is not owned by current player | Invalid move |
| 4 | Move piece onto location with another piece (no rules) | Invalid move |
| 5 | Player turn does not change when inputting invalid move | Maintain turns on invalid move |
| 6 | Player turn changes when inputting valid move | Switch turn on valid move |
| * | _**Refactored by extracting board hashmap and validation checks into separate board class**_ | Refactor |
| 7 | Move to location that is out of bounds | Invalid move |
| 8 | Move to negative location on infinite board | Valid move (creates new location) |
| 9 | Check if piece can move linearly (no obstacles, no distance) | linear valid move |
| 10 | Check if piece cannot move non-linearly (no obstacles, no distance) | linear invalid move |
| * | _**Refactored by extracting valid movement type checks into MoveChecker class **_ | Refactor |
| 11 | Check if piece can move in straight line (no obstacles, max distance) | linear valid move |
| 12 | Check if piece cannot move in straight line (no obstacles, max distance+1) | linear invalid move |
| * | _**Refactored by extracting valid path checks into PathChecker class **_ | Refactor |
| 13 | Check if piece can move diagonally (no obstacles, positive direction) | linear valid move |
| 14 | Check if piece can move diagonally (no obstacles, negative direction, infinite board) | linear valid move |
| 15 | Check if piece cannot move (piece in the way) | linear invalid move |
| 16 | Check if piece can move in straight (N,S) line (no obstacles, max distance) | orthogonal valid move |
| 17 | Check if piece can move in straight (E,W) line (no obstacles, max distance) | orthogonal valid move |
| 18 | Check if piece cannot move in diagonal line (no obstacles, max distance) | orthogonal invalid move |
| 19 | Check if piece can move in L around obstacle (max distance) | orthogonal valid move |
| 20 | Check if piece cannot move in L around obstacle (> max distance) | orthogonal invalid move |
| 21 | Check if piece can move in straight (N,S) line (no obstacles, max distance) | omni valid move |
| 22 | Check if piece can move in straight (E,W) line (no obstacles, max distance) | omni valid move |
| 23 | Check if piece can move in diagonal line (no obstacles, max distance) | omni valid move |
| 24 | Check if piece can move in diagonal and straight line around obstacle (max distance) | omni valid move |
| 25 | Check if piece cannot move in diagonal and straight line around obstacle (max distance) | omni invalid move |
| 26 | Check if piece can fly over (test 14 with flight) (piece in the way) | linear fly valid move |
| 27 | Check if piece cannot fly over (piece in the way) end blocked with piece | linear fly invalid move |
| 28 | Check if piece can fly over obstacle (test 19 with flight) | orthogonal valid move |
| 29 | Check if piece can fly over obstacle (test 24 with flight) | omni fly valid move |
| 30 | Check finite board only valid path is out of bounds | omni invalid move |



