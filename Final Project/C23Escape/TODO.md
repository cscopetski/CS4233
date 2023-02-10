Name: Caleb Scopetski

TDD TODO/Task list

**Build Tests**

These are for implementing the EscapeGameBuilder's `makeGameManager()` method.

| **#** | Test                                                            | Comments                        |
|:-----:|:----------------------------------------------------------------|:--------------------------------|
| 1 | Build game manager | create game object |
| 2 | Build game manager 2 X 2, 2 players, and coordinate type SQUARE | create correct game object |
| 3 | Build game manager 4 X 6, 2 players, and coordinate type HEX | create correct game object |

**Coordinate Tests**

| **#** | Test                                                            | Comments                        |
|:-----:|:----------------------------------------------------------------|:--------------------------------|
| 1 | Check two coordinates with the same row and column are equal | coordinate equals |
| 2 | Check two coordinates with different row and column are not equal | coordinate unequal |
| 3 | Check coordinate creation from game manager | created correct coordinate |