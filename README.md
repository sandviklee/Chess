# Chess game in JavaFX ♟

Java Chess game made during the course **[TDT4100 - Object oriented programming](https://www.ntnu.no/studier/emner/TDT4100#tab=omEmnet)** at NTNU.

![](https://i.pinimg.com/originals/5e/45/c3/5e45c3f6445fba750c3b4776c7a298fb.gif)

## How does it work?

This Chess game is made with Java and JavaFX. The project is not fully finished and may contain some bugs. 
I am also not that happy with the quality/efficiency of the code.

### The main menu consists of "New Game" and "Load game". 
![The Main menu](https://i.gyazo.com/49bc9ac4e41f710badad9683408f15e7.png)

_Here you can change the player names, and pick white or black_

If you dont choose your own names, it will be automaticly set as PLAYER1 and PLAYER2. 

![Players in game](https://i.gyazo.com/3481bd6153cd91ca1ac86d4c56bd38b4.png)

_How the players are represented ingame_

**New Game** creates a new chessboard, and places the pieces in their _startposition_. 

**Load Game** loads an already existing chessboard from a saved file. 

This chess game follows the most basic rules of chess. How the pieces move ( -Castling), white starts then black,
Check and Checkmate. Autoqueen is also implemented, so that when a Pawn reaches the other side, it will turn into a Queen. 

[![Pieces moving on the chessboard](https://i.gyazo.com/560d87c9fceb0397f795e2cbc2b9cd0b.gif)](https://gyazo.com/560d87c9fceb0397f795e2cbc2b9cd0b)

_Here we can see the pieces moving on the chessboard_

All the pieces that have been "knocked" gets stored in **CHESSPIECES OUT**.

[![Chesspieces out](https://i.gyazo.com/7915511efb4484a6980ee8e2f3e3df48.gif)](https://gyazo.com/7915511efb4484a6980ee8e2f3e3df48)

_Here we can see how Chesspieces Out works_

[![Black king in check](https://i.gyazo.com/c879bb2302d88052c7c41d372724bf09.png)](https://gyazo.com/c879bb2302d88052c7c41d372724bf09)

_Here we can see what happends when the black king is in check_

[![Black won](https://i.gyazo.com/8bc52385e4ec66f78dbeedf1a255591d.png)](https://gyazo.com/8bc52385e4ec66f78dbeedf1a255591d)

_Here we can see what happends when black wins_

The buttons **Offer draw** and **Claim Draw** are options to end the game when you feel like it.

You are also able to **save** and **load** your progress in the game with the save button and load button as seen on the gameplay screen.

[![Saving](https://i.gyazo.com/ddf76ca9a15668333188c728035a69c5.gif)](https://gyazo.com/ddf76ca9a15668333188c728035a69c5)
_Here we can see what happends when you click save_


## Yet to be implemented:

- Pieces moving. ☑
- Piece patterns and logic. ☑
- IO system, storing and accessing files. ☑
- Switching between White and Black logic. ☑
- Check game state (Checkmate, Check and draw). ☑
- Show all garbage pieces. ☑
- Add Players. ☑
- JUnit 5 Tests ☑



## What i want to implement in the future:
- AI that you can play against.
- Better coding structure, with more efficient code.
- Show last move (With the chess terms).

## Project Keypoints

| Keypoints                                | Description                             |
| ---------------------------------------- | --------------------------------------- |
| Precentage of grade                      | 35% (App) + 15% (Documentation/Theory)  |
| Deadline                                 | 29. April 2022                          |
| Demonstration due                        | 03. May 2022                            |


