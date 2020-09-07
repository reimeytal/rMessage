clear
javac -classpath .. Server.java
cd ../general/message
javac -classpath ../.. Chattype.java
cd ../..
cd server/message
javac -classpath ../.. ServerNode.java
javac -classpath ../.. Chat.java
javac -classpath ../.. User.java
javac -classpath ../.. ServerHub.java
echo Done
