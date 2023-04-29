# ChatApplication
A simple chat service using Websocket, Spring Boot backend and React client
Added explanations on a code reference 
[ [Github Link](https://github.com/JayaramachandranAugustin/ChatApplication) ]
[ [Tutorial Link](https://youtu.be/o_IjEDAuo8Y) ]


## ROADMAP :
    + Websocket_v1_SpringBoot_React
    + [Websocket_v2_SpringBoot_React_Redis](https://github.com/davidoh-0808/Websocket_v2_SpringBoot_React_Redis.git)
    + [Websocket_v3_SpringBoot_React_Redis_Docker_ECS]()
    + [Websocket_v4_SpringBoot_React_Redis_MSA]()


## TODO :
    + [DONE]    Understand the code reference / add notes on code
    + [DONE]    Write out the front and backend logic (based on reference)
    + [DONE]    Make a sequence diagram 
    + [DONE]    Plan for WebsocketChat_ver2_SpringBoot_React_Redis


## Web Architecture

    Server:   Spring Boot 
    
        Websocket, 
        
        STOMP Endpoint Registry, 
        
        Redis PubSub / Stream (planned), 
        
        Postgres(planned),

        Docker & AWS ECR & AWS ECS (planned),
        
        AWS API Gateway & AWS Lambda (planned)

    Client:   ReactJS 
    
        SockJS -> open WebSocket, 
        
        STOMP client -> ws endpoint/topics and wrap/unwrap messages



## Sequence Diagram w/ WS endpoints and APIs
   
![image](https://user-images.githubusercontent.com/75977587/233823295-5067fed7-1824-40bd-8370-76e02475fc6f.png)
   
    

## Frontend and Backend Logic (reference code basis)

    **Frontend Logic (React)**
    -------------------------------------------------------------------------------------------
        1. open websocket clients
            1. Sock.js
            2. Stomp.js
                1. stomp client is used to subscribe to the public and private topics (to receive messages sent to the server from the client itself)
        2. what is the flow?
            1. the user enters the chat service
                1. open and connect to websocket
            2. the user sends public message
                1. html input for msg
                2. function to handle public msg and update the state on change
                3. function to use STOMP client to send message
            3. the same with sending private message
            4. subscribe and handle the incoming public message
                1. html to display the message
                2. use STOMP client to receive all of the incoming messages
                3. update the state upon receiving the message
            5. subscribe and handle the incoming private message
                1. fetch username or userId from the user state
                2. similar to the public message except the STOMP endpoint to subscribe to is a user info (userId or username) variable 
        3. need to use global variable ( called “State” in React.js)
            1. user info (to save username/userId & user input such as messages)
            2. public chat (its contents)
            3. private chat (its contents)
            4. tab (indicates either “Public Chatroom” or “{username/userId}”)
        4. determine the functions to be used in .js file
            1. connect(), onConnected()
                1. connect to the websocket
                2. set the user state as connected
                3. send the user join message (via public chatroom)
                4. subscribe to the topics : 
                    1. when public message is received
                    2. when private message is received
            2. userJoin()
                1. it is triggered when the user join message is received
                2. sends a message payload with the type, “JOIN”
            3. onMessageRecevied(payload)
                1. it is triggered when payloads are received through the topic, /chatroom/public/
                2. case : the message status JOIN
                    1. insert the username or userId into the public chat state
                    (render html with it)
                3. case : the message status MESSAGE
                    1. push the entire payload message (the message model) into the public chat state
                    (render html with it)
            4. onPrivateMessageReceived(payload)
                1. push the entire payload message into the private chat state
                ( key : value  →  ‘username or userid’ :  ‘the message model’ )
                2. insert an empty payload message into the private chat state
                if there is no message sent between the two users
            5. OnError(err)
            6. handleMessage(event)
                1. set userdata (userInfo) with the user input message
                (triggered when the message type changes)
            7. sendMsg()
            or sendValue()
                1. get the userdata (userInfo), namely the username and user input message
                2. make a message payload map, according to the user model
                3. send the payload via the STOMP client endpoint (ie. /app/message/public  or  /app/message/private)
            8. sendPrivateMsg()
                1. get the userdata (userInfo), namely the username and user input message
                2. make a message payload via the 
                3. send the payload via the STOMP client endpoint (ie. /app/message/public  or  /app/message/private)
            9. handleUsername(event)
                1. insert the username (or userId) into the userdata (userInfo) State
                ( used in registerUser() and connect() )
            10. registerUser()
                1. invokes connect()


    **Backend Logic (Spring Boot)**
    ----------------------------------------------------------------------------------------

        1. make an uri for opening the web socket: 
        /apiUrl/ws   to open web socket

        2. endpoint uri where the client sends the user input messages (for the general / for each users) :
        /app/message/public
        /app/message/private → 

        3. topic uri for receiving messages   :

        /chatroom/public
        /user/<userid>/**chatroom/private
        ** this uri should be added

        4. message model :
        msgId
        senderId
        receiverId
        msgType 
        (  join   - ‘entered the chat service, 
                     but indicates there is no message content’
        , message - ‘indicates the message content is present’  )
        content
        createdAt

        1. Spring Boot config :

        Websocketconfig
        - set websocket endpoint

        ~/ws
        - set application endpoint prefix

        /app
        - set topic prefix

        /chatroom
        /user

        controllers
        use @MessageMapping & @SendTo in Spring Boot

        - one for pdublic chat
            @MessageMapping("/message")
            @SendTo("/chatroom/public")

        - another for private chat
            @MessageMapping("/private-message")
            ~
            public Message recMessage(@PayloadMessagemessage)
                ~
                simpMessagingTemplate.convertAndSendToUser(message.getReceiverName(),"/private",message)
            
            


## Infra Architecture (planning)



## Websocket Principles

![image](https://user-images.githubusercontent.com/75977587/233829276-90c20029-8b37-4301-b739-4781ecf9d6db.png)
Websocket vs. TCP

    - WebSocket is a framed and bidirectional protocol. 
        On the contrary to this, HTTP is a unidirectional protocol functioning above the TCP protocol.
    
    - WebSocket is faster than TCP. 
        WebSocket allows uninterrupted pushing or transmitting data in the already open connection.
    
    - WebSocket uses a unified TCP connection and needs one party to terminate the connection.

![image](https://user-images.githubusercontent.com/75977587/233829287-5fa64948-09ef-42d0-9496-cc90dcf08e91.png)
Types of Data allowed in websocket:

    - Data that can be serialized into a string or binary format, including
        - text,
        - images,
        - and multimedia

        all these in real-time


## ReactJS screen

![Chat screen](img/chat_screen.jpg "Chat screen")

To start:
    
### Client
        - npm install (in the react-client folder)
        - npm start
    
### Server
        - mvn spring-boot:run (in the spring-ws-server)



### Plan for the next Git Repo 
    [WebsocketChat_v2_SpringBoot_React_Redis]()
    +  Edit the original code 
       (edit/add websocket endpoints, topic urls, models, controllers, etc)

    +  Add Redis PubSub and Streaming 
       (Use Redis as the primary DB or use Postgres)

    +  Deploy via Docker, ECR, ECS (or K8), Service Registry 
       (perhaps on WebsocketChat_v3)



