# IK1203-Networks-and-Communications-Socket-Programming

This repository contains my code for the Socket programming assignments for the Networks course at KTH. 

Task 1
---
The task is a programming assignment to implement a simple TCP client, as a Java class. The class is called TCPClient, and works in a straight-forward manner:

- Open a TCP connection to a server at a given host address and port number.
- Send data to the server.
- Take the data that the server sends back in response, and return that as the result. 

Task 2
---
In Task 1, the askServer method returns when the server closes the connection. Here we will add two other conditions for askServer to return: 

- Timeout. When askServer has not received any data from the server during a period of time, it closes the connection and returns.
- Data limit. When askServer has received a certain amount of bytes from the server, it closes the connection and returns.

Furthermore, we will add the possibility for TCPClient to shut down the connection first.

Task 3
---
In this part, we implement another web server – HTTPAsk. This is a web server that uses the client you did in Task 1 and 2. When you send an HTTP request to HTTPAsk, you provide the parameters for TCPClient as parameters in the HTTP request.

When HTTPAsk receives the HTTP request, it will call the method TCPClient.askServer(), and return the output as an HTTP response. Instead of running TCPAsk from the command line, we build a web server that runs TCPAsk for us, and presents the result as a web page (in an HTTP response).

For this task we:

- Read and analyse an HTTP GET request, and extract a query string from it. 
- Compose and return a valid HTTP response.

Task 4
---

In this task, we take the HTTPAsk server we did in Task 3, and turn it into a concurrent server. The server we did for Task 3 deals with one client at a time (most likely). A concurrent server can handle multiple clients in parallel.
We us Java threading to implement a concurrent server that can handle many clients at the same time. 
