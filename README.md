# Tetris Fighter
This project aims at developing a better version of Tetris entitled Tetris Fighter, supporting an unrestricted level choosing and customizing feature, a complete score management system, and a clean and modern GUI.

### Group Members
Cao Yuan, Ashley: Software Engineer (Develop & Debug)<br />
Feng Li, Felicia: Software Engineer (Develop & Debug)<br />
Gong Zifan, Cisco: Software Engineer (Design & Refactor)<br />
Qu Yang, Young: Software Engineer (Design & Test)<br />
Wu Qianyi, Michelle: Project Manager (Manage & Refactor)<br />
Yim Yau Wai, Andy: Software Engineer (Test & Debug)<br />

### User Manual
#### Before starting a new game
1.  Input a username within 10 characters.<br />
2.  Choose a level among ‘Low’,’Medium’, ‘High’ and ‘Custom’; Different levels have different piece acceleration, initial game speed, and piece types. The player can select the ‘Custom’ level to self-define the three attributes mentioned earlier.<br />
3.  Click the ‘Submit’ button and submit the information inputs.<br />
4.  Press ‘Enter’ to start a new game.<br />
![start game](https://github.com/shellmik/Tetris-Fighter/blob/master/doc/README1.png)

#### During the game
1.  Pieces with randomly generated colors and shapes descend from the top of the screen onto the playing field. During the descent, the player can move the pieces laterally and rotate them until they reach the field bottom or land on a piece that had been placed before it.<br /> 
2.  Here are the operations:<br />
Press ‘A’ to move the piece left<br />
Press ‘D’ to move the piece right<br />
Press ‘S’ to quickly drop the piece down<br />
Press ‘J’ to rotate the piece anti-clockwise<br />
Press ‘K’ to rotate the piece clockwise<br />
Press ‘P’ to pause and continue the game<br />
3.  The player can neither slow down the pieces nor stop them but can accelerate them.<br />
4.  The Player is supposed to fit together pieces and create solid lines. The completed lines will disappear, the blocks placed above it fall one rank, and points will be granted to players. Players can also clear multiple lines directly, which can grant bonus points.<br /> 
5.  The piece falling speed increases as time eclipses, leaving the player with less time to think about the placement.<br />
6.  If the user wants to manually end a game, click the ‘End Game’ to end a game whenever the game is running or paused。<br />
7.  If the player cannot make the pieces disappear quickly enough, the field will start to fill. The game ends when the pieces reach the top of the playing field and prevent the arrival of new pieces.<br />
![during game](https://github.com/shellmik/Tetris-Fighter/blob/master/doc/README2.png)

#### After a game is over
1.  The game over page would be shown.<br />
2.  Click the ‘Store Score’ button to store the score in this round. If the player can also choose not to click the button and abandon the score.<br />
3.  Click the ‘Show Rank’ button to view the whole ranking list.<br />
4.  Click the ‘Clear Rank’ button to clear the ranking list.<br />
5.  Fill in the required information, click ‘Submit’, and press ‘Enter’ can start a new game again.<br />
6.  If the player needs to quit the program, click the ‘x’ button on the top-right corner.<br />
![game over](https://github.com/shellmik/Tetris-Fighter/blob/master/doc/README3.png)

### Installation Guide
#### Method 1
Directly go to the Tetris.jar file in the project folder and enjoy the game.<br />

#### Method 2
Download zip file or git clone this folder from the link below:<br />
https://github.com/shellmik/Tetris-Fighter.git<br />
Create a Java project in eclipse or other IDEs that support Java and compile and run the project.<br />
