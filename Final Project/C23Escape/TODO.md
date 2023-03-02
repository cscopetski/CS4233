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
| 5 | Check square coordinate distance | square coordinate distance |
| * | _**Refactored by creating square and hex coordinate classes**_ | Refactor |
| 6 | Check hex coordinate distance | hex coordinate distance |

**Square Movement Tests**

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
| * | _**Refactored by extracting valid movement type checks into MoveChecker class**_ | Refactor |
| 11 | Check if piece can move in straight line (no obstacles, max distance) | linear valid move |
| 12 | Check if piece cannot move in straight line (no obstacles, max distance+1) | linear invalid move |
| * | _**Refactored by extracting valid path checks into PathChecker class**_ | Refactor |
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
| * | _**Flight Tests**_ | Flight |
| 26 | Check if piece can fly over (test 14 with flight) (piece in the way) | linear fly valid move |
| 27 | Check if piece cannot fly over (piece in the way) end blocked with piece | linear fly invalid move |
| 28 | Check if piece can fly over obstacle (test 19 with flight) | orthogonal fly valid move |
| 29 | Check if piece can fly over obstacle (test 24 with flight) | omni fly valid move |
| 30 | Check finite board only valid path is out of bounds | omni invalid move |
| * | |  |
| 31 | Check that piece can move diagonally in one direction | diagonal valid move |
| 32 | Check that piece can move diagonally in multiple directions | diagonal valid move |
| 33 | Check that piece cannot move orthogonally | diagonal invalid move |
| * | _**Unblock Tests**_ | Unblock |
| 34 | Check if piece can unblock over (test 14 with unblock) (block in the way) | linear unblock valid move |
| 35 | Check if piece cannot unblock end blocked with block | linear unblock invalid move |
| 36 | Check if piece can unblock over obstacle (test 19 with unblock) | orthogonal unblock valid move |
| 37 | Check if piece can unblock over obstacle (test 24 with unblock) | omni unblock valid move |
| * | |  |
| * | _**Jump Tests**_ | Jump |
| 38 | Check if piece can jump over (test 14 with jump) (piece in the way) | linear jump valid move |
| 39 | Check if piece cannot double jump over (piece and exit in a row) | linear jump invalid move |
| 40 | Check if piece can jump over two obstacles in L shape | orthogonal jump valid move |
| 41 | Check if piece cannot change directions mid air while jumping | orthogonal jump invalid move |
| 42 | Check if piece can unblock over obstacle (test 24 with jump) | omni jump valid move |
| 43 | Check if piece cannot change directions mid air while jumping | omni jump invalid move |
| 44 | Check if piece cannot jump out of bounds | omni jump invalid move |
| 45 | Check that piece will fly before jumping (test 44 w/ fly and jump) | omni fly jump valid move |
| 46 | Check that piece will unblock before jumping (test 44 w/ fly and unblock) | omni unblock jump valid move |
| * | |  |

**Hex Movement Tests**

| **#** | Test                                                            | Comments                        |
|:-----:|:----------------------------------------------------------------|:--------------------------------|
| 1 | Call move on location with no piece | Invalid move |
| 2 | Move from start location to start location | Invalid move |
| 3 | Call move on piece that is not owned by current player | Invalid move |
| 4 | Move piece onto location with another piece (no rules) | Invalid move |
| 5 | Player turn does not change when inputting invalid move | Maintain turns on invalid move |
| 6 | Player turn changes when inputting valid move | Switch turn on valid move |
| 7 | Move to location that is out of bounds | Invalid move |
| 8 | Move to negative location on infinite board | Valid move (creates new location) |
| 9 | Check if piece can move linearly (no obstacles, no distance) | linear valid move |
| 10 | Check if piece cannot move non-linearly (no obstacles, no distance) | linear invalid move |
| 11 | Check if piece can move in straight line (no obstacles, max distance) | linear valid move |
| 12 | Check if piece cannot move in straight line (no obstacles, max distance+1) | linear invalid move |
| 13 | Check if piece can move diagonally (no obstacles, positive direction) | linear valid move |
| 14 | Check if piece can move diagonally (no obstacles, negative direction, infinite board) | linear valid move |
| 15 | Check if piece cannot move (piece in the way) | linear invalid move |
| 16 | Check if piece can move in straight (N,S) line (no obstacles, max distance) | omni valid move |
| 17 | Check if piece can move in straight (E,W) line (no obstacles, max distance) | omni valid move |
| 18 | Check if piece can move in diagonal line (no obstacles, max distance) | omni valid move |
| 19 | Check if piece can move in diagonal and straight line around obstacle (max distance) | omni valid move |
| 20 | Check if piece cannot move in diagonal and straight line around obstacle (max distance) | omni invalid move |
| 21 | Check finite board only valid path is out of bounds | omni invalid move |

**Win Conditions/Game Status Tests**

| **#** | Test                                                            | Comments                        |
|:-----:|:----------------------------------------------------------------|:--------------------------------|
| 1 | Check loss after invalid move | invalid move loss check |
| 2 | Check winner after TURN_LIMIT | turn limit rule check |
| 3 | Check tie after TURN_LIMIT | turn limit rule check |
| 4 | Check win after removing all pieces | remove all pieces win check |
| 5 | Check winner after SCORE reached player1 | score rule check |
| 6 | Check winner after SCORE reached player2 | score rule check |
| 7 | Check winner by SCORE with TURN_LIMIT | score turn limit rule check |
| 8 | Check winner tie by TURN_LIMIT with SCORE | score turn limit rule check |
| 9 | Check winner by no valid moves | valid moves rule check |
POINT_CONFLICT
OBSERVERS





