There are 2 ways to run this program

|| Command Line || IDE ||

To run on command line do the following:

In command prompt

Change directory to the one of Application.java
-> cd {PATH_TO_INITIALIZEUI.JAVA}

Compile code
-> javac --module-path "PATH_TO_FX" --add-modules javafx.controls,javafx.fxml InitializeUI.java
note: PATH_TO_FX is the path to the javafx /lib folder

Run Code
-> java --module-path "PATH_TO_FX" --add-modules javafx.controls,javafx.fxml YourFile

this should work

To Run in IDE:

Open IDE (Intellij is example)
-> Intellij

Open Project
-> Open Project -> sphere-image-manipulation

Run Application.java
-> Run -> InitializeUI.java