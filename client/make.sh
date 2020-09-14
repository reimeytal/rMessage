clear
cd message
javac -classpath ../.. ClientInterface.java
javac -classpath ../.. Client.java
cd ..
javac -classpath .. ClientFront.java
cd gui
javac -classpath ../.. LoginPanel.java
javac -classpath ../.. ChatPanel.java
cd ../..
cd general/message
javac -classpath ../.. Chattype.java
echo Done
