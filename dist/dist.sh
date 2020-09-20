clear
jar cfm Server.jar server/META-INF/MANIFEST.MF ../server/Server.class ../server/message/User.class ../server/message/ServerNode.class ../server/message/ServerHub.class ../general/message/Chattype.class ../server/message/Chat.class
jar cfm Client.jar client/META-INF/MANIFEST.MF ../client/ClientFront.class ../client/message/ClientInterface.class ../client/message/Client.class ../client/message/Client\$1.class ../general/message/Chattype.class ../client/gui/LoginPanel.class ../client/gui/ChatTab.class ../client/gui/ChatPanel.class ../client/gui/ChatPanel\$1.class ../client/gui/ChatPanel\$2.class
echo Created .jar file
