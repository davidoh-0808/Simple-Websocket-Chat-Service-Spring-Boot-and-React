# ChatApplication
A simple Spring boot websocket backend and reactjs client
Added explanations on a code reference 
[ [Github Link](https://github.com/JayaramachandranAugustin/ChatApplication) ]
[ [Tutorial Link](https://youtu.be/o_IjEDAuo8Y) ]


------
## Plan to :
    
    ### Edit some parts of code (edit/add websocket endpoints, topic urls, models, controllers, etc)
    ### Add Redis Queue for messaging (DB as MySql or Postgres)
    ### Deploy all the above via Docker, ECR, ECS (or K8), Service Registry


------
## Web Architecture

Server:
    - Spring Boot (Websocket, STOMP Endpoint Registry, Redis as Queue(planned), Postgres(planned) )

Client
    - ReactJS (SockJS -> open WebSocket, STOMP client -> ws endpoint/topics and wrap/unwrap messages)


------
## Flow 


------
## ReactJS screen

![Chat screen](img/chat_screen.jpg "Chat screen")

To start:
    
### Client
        - npm install (in the react-client folder)
        - npm start
    
### Server
        - mvn spring-boot:run (in the spring-ws-server)
