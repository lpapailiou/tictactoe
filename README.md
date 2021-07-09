# README

This is a __tic tac toe game__ written in java 8, using a javafx ui.

The goal was to play around with machine learning by using __qlearning__, so the game would learn by itself.  
Tic tac toe was chosen, because it is quite simple, thus easy to test.

## Table of Contents
1. [About the game](#about-the-game)  
	1.1 [Before you start the game](#before-you-start-the-game)  
	1.2 [The application](#the-application)  
		1.2.1 [The rules](#the-rules)  
		1.2.2 [How to play](#how-to-play)  
		1.2.3 [The parameters](#the-parameters)  
		1.2.4 [How the AI works](#how-the-ai-works)  
		1.2.5 [How to obtain a perfect game](#how-to-obtain-a-perfect-game)  
		1.2.6 [Thoughts about this project](#thoughts-about-this-project)  
2. [Project structure](#project-structure)  
3. [How to get it](#how-to-get-it)  

## About the game
### Before you start the game
You actually have two options to launch the game. If you starting it via console, you will be able to use this application fully in console mode.  
If you start it up from the IDE or Jar file (by double click) you will get the gui application.  
  
If you use it in console mode, you will have the option to switch to the gui mode later on.

#### Preview of console mode
Right after startup, all possible options will be printed to the console. There is no further explanation/manual though.

![screenshot of console mode after startup](https://github.com/lpapailiou/tictactoe/blob/master/src/main/resources/img/tictactoe_screenshot_startup_console.png)

#### Preview of gui mode
The gui mode has one scene only. There are short explanations provided by tooltip texts.

![screenshot of console mode after startup](https://github.com/lpapailiou/tictactoe/blob/master/src/main/resources/img/tictactoe_screenshot_startup_gui.png)

### The application

#### The rules
There are two players playing on a 3x3 grid. After each move, the players switch. One player plays with a __x__ symbol, the other one has the __o__ symbol. The player who succeeds first to get three symbols in a row (or column or diagonal) wins. 

#### How to play
If no manual player is involved, starting the game is sufficient. If you play manually, use clicks on the grid fields to set your symbol (gui mode) or enter a number between 1 and 9 when prompted (console mode). The number 1 will correspond with the grid field on top left, then the grid field will in crease by one from left to right, top to bottom.

#### The parameters
As this application focuses on machine learning, there are several parameters to control. Following, a quick explanation:  
  
* __source__: in general, there are two options to use this application. You can either play games with a fully trained AI (_source=library_) or choose a _blank_ source to train it yourself.
* __who starts?__: by default, player one has the x-symbol. If you like to switch symbols, use this option. This setting may be confusing if you want to make so statistics (keep that in mind). 
* __player one__: this is the game mode for player one. If you choose _manual_ you will be able to control this player. Otherwise, you get to choose between the options _random_, _best play_ and _worst play_.
* __player two__: same as above, but for player two.
* __speed__: this is the delay in ms between moves. On the gui, this might be helpful if you want to follow the game. On the console you might not need it, as you get a history of moves automatically.
* __learn?__: if switched on, the AI will learn from the played games. This only works when the source is not set to _library_ (as the library is read only). It is useful to switch this option off to do statistics about the current state of the training.
* __alpha__: this parameter is the learning curve, values between 0 and 1 are valid. The higher the alpha value is, the more impact each single game has in training.
* __gamma__: this is the reward parameter, values between 0 and 1 are valid. The higher the gamma value is, the more impact are immediate rewards having. This will make a difference between draws (where all grid cells have to be filled) and games which are won before.
* __epsilon__: this is the parameter for controlling the behavior regarding exploration/exploitation. Also here, values between 0 and 1 are valid. The higher the value is, the more random moves are made, regardless the mode. This means, that during the training, a player might try to optimize his strategy, but still explore a specific percentage of new games. This leads to broader learning experience while part of the games are played with optimal strategy.
* __rounds__: for non-manual-games only: if you are happy with a specific parameter setting, you can 'batch play' with that setting.
* __start__: as soon as this button is hit, the game(s) will start. The games cannot be interrupted. The statistics will be updated during/after.
* __clear statistics__: this button clears the current statistics.

#### How the AI works
As mentioned before, I used __qlearning__ for this project.  
  
It works as following:  
In the background, the game board is represented as flattened out array. It contains '1' for a player one symbol, '-1' for a player two symbol and '0' for a free field. During a game, every board state is recorded in a history. After a game ends, the whole history is fed to the ``Qnet`` instance for evaluation. With the board, we also feed the result of the game (which is '1' for a player one win, '-1' for a player two win and '0' for a draw).  
  
The ``Qnet`` will then evaluate backwards (i.e. start with the last board state and go backwards step by step). The result will be backpropagated then with the Bellman equation.  
In the code, it as realized as following. The reward is the value which is backpropagated.

    private static void updatePolicy(DataNode node, double nextMaxQ, double nextMinQ, int move, double reward) {
		double newQx = ((1-alpha) * node.getxValue(move)) + (alpha * (reward + (gamma * nextMaxQ)));
		double newQo = ((1-alpha) * node.getoValue(move)) + (alpha * (reward + (gamma * nextMinQ)));
		node.setValue(move, newQx, newQo);
	}

The results will be saved in two separate matrices per board state (one for each player). The first matrix is always maximizing, the second always minimizing.  
  
During a game, the ai will read the according matrix for the board and make a decision on the present values.  
    
Here an example:  

    -------------
    | x | x |   |  
    -------------  
    | o |   |   |  
    -------------  
    |   |   | o |  
    -------------  
  
This board will be represented as ``[1, 1, 0, -1, 0, 0, 0, 0, -1]`` within the system.  
The player one matrix is: ``[0, 0, 1, 0, 0.442485772, 0.623211147, 0.288224478, 0.84532113, 0]``  
The player two matrix is: ``[0, 0, 0.999801705, 0, 0.500448683, 0.527828707, 0.038373504, 0.569956665, 0]``  
   
As you see, as it is now player one's turn (X), he will most likely pick the field on the upper right corner as the result is the most promising.

#### How to obtain a perfect game
First, make sure you have following settings:
* library: blank (better reload to be sure)
* player one: random
* player two: best play
* learn: true
* alpha: 0.25
* gamma: 0.2
* epsilon: 0.7
* rounds: 10'000
  
We set player two to best play because he is harder to train. If he plays perfectly, player one will do most likely as well. 
Now, the training is done in following order:
  
1. play the pre-set 10'000 rounds with that setting
2. set epsilon to 0.6 and play 10'000 more rounds
3. set epsilon to 0.5 and play 10'000 more rounds
4. set epsilon to 0.4 and play 10'000 more rounds
5. set epsilon to 0.3 and play 10'000 more rounds
6. set epsilon to 0.2 and play 10'000 more rounds
7. set epsilon to 0.1 and play 10'000 more rounds
8. set epsilon to 0.0 and play 300'000 - 400'000 more rounds
  
As you see, the idea is that we slowly drift from exploration to exploitation (i.e. first focus on learning about many different games, then focus more and more on what we learned).  
If you find a more efficient way, let me know.

#### Thoughts about this project
Technically, tic tac toe may not be a good project for machine learning as it is too simple. On the other hand, a very simple task is great for testing.  
During this project I also experimented with different ways to store the board values (one matrix, two matrices). The approach with two matrices seemed more efficient. Currently, both matrices are strictly separated - what I did not try was updating the other table for a player when it's not his turn. This could probably deliver even better results.  
Some other day, this project could be reused to test neural networks.  
Another thing: I know, the gui is quite pointless here, the application is far more performing and flexible when it's used without. On the other hand, I thought it would be cool having a gui if someone not that close to programming would like to play with it a bit without having to fiddle around with the code (e.g. to change training parameters). 

## Project structure

* ``application``     this package contains the main method (in ``TicTacToe.java``), as well as gui related classes
* ``model``               enums, container classes, data classes
* ``resources``        text and image files
* ``root``        	       contains the 'game engine' (bot, ai, parsers, game logic)

## How to get it

Clone the repository with:

    git clone https://github.com/lpapailiou/tictactoe your-target-path

For further help, click [here](https://gist.github.com/lpapailiou/d4d63338ccb1413363970ac571aa71c9).
