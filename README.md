# Distributed-Systems--Assignment1

This java program implements a simple client-server application for a Mattress Store. The server (MattressServer) listens for connections on port 3500. When a client connects(MattressClient), an instance of MattressThread is created to handle the client requests. The MattressThread displays the various MattressTypes and the corresponding prices. The client can choose the mattress type and have a receipt order saved locally. The server can handle multiple clients by the use of multithreading. 

How to run this program: 

Compile the files using:
javac MattressServer.java 
javac MattressClient.java

Start the Server: 
java MattressServer

Run the Client:
java MattressClient

Multiple client windows can be opened to test connections to the server.

Receipts for each mattress order will be saved in the Client’s directory. 
