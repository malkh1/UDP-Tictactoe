# Online_Tictactoe

Play tictactoe with your friends on Online Tictactoe


How it works:

Uses UDP protocol to connect players together.

1 person hosts it on their ip by running the server application,

the player hosting the server will then run the client program.

Another player will connect to the host using the hosts' ip by entering it into the batch file provided. The batch file is location in the 
Online_Tictactoe/out/artifacts/tictactoe_online_jar folder. Batch file must be run in the same location as the jar file.

The server application supports multiple games going at the same time.


# known issues
Currently, the game works only on LAN. Games played through the internet are not working.
