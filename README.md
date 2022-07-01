# Social Asteroids

Social Asteroids is a place to play the classic Asteroids game while you share your scores, meet friends and discuss on the forum.

The purpose of the project is to serve as a portfolio to demonstrate my capabilities with the Spring ecosystem in Java. **DDD** architecture concepts are implemented, as well as the criteria of a **REST** application. I also want it to serve as a reference for those who need content for the technologies used.


## Architecture

In the application a simple layered architecture for a *monolith* was applied.
<div align = "center">
<img src="https://raw.githubusercontent.com/nelsondrp/social-asteroids-backend/main/diagram.svg">
</div>
 
 #### Domain
In the domain is the heart of the business, the entities that contain the data necessary for all operations and give purpose to the application, as well as their mappings are found here.

#### Security
The security layer contains its own entities, repositories, services and controllers. As it's not necessarily part of the application's main context, a layer was created especially for it.

#### Infrastructure
The infrastructure layer is responsible for the application backbone. Repositories, request interceptors, and error handling are its responsibility.

#### Business
All the application's logic is concentrated in the business layer, it is also responsible for handling the data that arrives and leaves it.

#### Presentation
The presentation layer receives the data from *requests*, forwards them for processing and receives the data that should be forwarded to the *response*.
<br>
### Stateless
Starting from the main principle of a REST application, this API is stateless, that is, it doesn't stores any client state on the server. Each request is independent and needs to contain everything necessary to access the intended resources. Thinking about it, the security layer uses ***J**son **W**eb **T**okens* for authentication.

#### JWT
It allows the user to navigate between server requests without having to authenticate at each request, as well as maintaining the Stateless server's aspect.

After the user is logged in, a token is created on the server with the user data and encrypted with a key. This token has a short validity time, and is sent to the user through a cookie. 

Along with the JWT, a Refresh Token is created, it's stored in a database table and has a longer validity time. Every time a expired JWT is sent, the server will capture the Refresh Token from its cookie, which is also encrypted with another secret, and perform its validation. Having passed the validation, a new JWT is generated and his cookie value is updated. When the Refresh Token expires the user will need to authenticate with their credentials again.

With the Refresh Token, if the user's JWT is spoofed, the attacker will have a short period of authentication time.

#### HATEOAS
Just as JWT is important to keep the server stateless, HATEOAS is an important feature for decoupling the backend from the frontend.

With HATEOAS there is no need for the client to know the paths used by the server to request resources beyond the Entry Point. Every resource will return with it the information needed to access related resources.

## Endpoints
#### Response Entities:

    User :
	    Long : id
	    String : username
	    String : email
	    Hypermedia Link, User : self - api/user/{id}
	    Hypermedia Link, List<Match> : matches - api/match/player/{id}
	    Hypermedia Link, List<Friendship> : friends - api/friend/get/{id}
	    Hypermedia Link, List<Thread> : threads - api/forum/threads-author/{id}
	    Hypermedia Link, List<Post> : posts - api/forum/posts-author/{id}	  
<br>

    Match:
		Long : id
		Duration : duration
		Long : score
		Long : ammoSpent
		Long : destroyedTargets
		Hypermedia Link, Match : self - api/match/{id}
		Hypermedia Link, User : player - api/user/{id}
<br>

    Friendship:
		 Hypermedia Link, User : user - api/user/{id}
		 Hypermedia Link, User : friend - api/user/{id}
<br>

    Thread:
	    Long : id
	    String : title
	    String : authorName
	    Instant : createdAt
	    Instant : updatedAt
	    Long : postsCount
	    Hypermedia Link, Thread : self - api/forum/thread/{id}
	    Hypermedia Link, List<Post> : posts - api/forum/posts-thread/{id}
	    Hypermedia Link, User : author - api/user/{id}
	    Hypermedia Link, List<Thread> : threads - api/forum/{id}
<br>

    Post:
	    Long : id
	    String : content
	    Instant : postedAt
	    Instant : editedAt
	    Hypermedia Link, Post : self - api/forum/post/{id}
	    Hypermedia Link, Thread : thread - api/forum/thread/{id}
	    Hypermedia Link, User : author - api/user/{id}
	    
<hr/>

## Authentication
#### Signup
|Request URI|Method|
|--|--|
|api/auth/signup|POST|
    Request 
	    Payload:
	    	String : username,
	    	String : email,
	    	String : password
    ------------------------------------------		 
    Response: 201 CREATED
	    Payload -
		    User 
<hr/>

#### Login
|Request URI|Method|
|--|--|
|api/auth/login|POST|
    Request 
	    Payload:
	    	String : username,
	    	String : password
    ------------------------------------------		 
    Response: 200 OK
	    Headers -
		    Cookie : auth,
		    Cookie : refresh-token
		Payload -
		    User 
<hr/>

#### Logout
|Request URI|Method|
|--|--|
|api/auth/logout|GET|
    Response: 200 OK
	    Headers -
		    Cookie : auth,
		    Cookie : refresh-token
<hr/>

## User 
#### Active
|Request URI|Method|
|--|--|
|api/user/active|GET|
    Response: 200 OK
	    Payload:
		    User
	------------------------------------------	    
	Response: 204 NO CONTENT
<hr/>

#### Update
|Request URI|Method|
|--|--|
|api/user/update|POST|
    Request 
	    Payload:
	    	Long  : id,
	    	String : username,
	    	String : email
	------------------------------------------	     	
    Response: 200 OK
		Payload -
		    User 
<hr/>

#### Update Password
|Request URI|Method|
|--|--|
|api/user/update-password|POST|
    Request 
	    Payload:
	    	Long  : id,
	    	String : actualPassword,
	    	String : newPassword
	------------------------------------------		     
    Response: 200 OK
		Payload -
		    User
<hr/>

#### Get User
|Request URI|Method|
|--|--|
|api/user/{id}|GET|
    Request 
	    Path variable:
			User ID
	------------------------------------------		     
    Response: 200 OK
		Payload -
		    User
<hr/>

#### Search
|Request URI|Method|
|--|--|
|api/user/{search}|GET|
    Request 
	    Path variable:
			Search Query
	------------------------------------------		     
    Response: 200 OK
		Payload -
		    List<User>
    ------------------------------------------	    
	Response: 204 NO CONTENT
<hr/>

## Match
#### Add
|Request URI|Method|
|--|--|
|api/match/add|POST|
    Request 
	    Payload:
		    Long : durationInMilis,
		    Long : score,
		    Long : ammoSpent,
		    Long : destroyedTargets,
		    Long : playerId
	------------------------------------------		     
    Response: 201 CREATED
	    Payload:
		    Match		    
<hr/>

#### Get Matches by Player
|Request URI|Method|Details|
|--|--|--|
|api/match/player/{id}|GET|Pageable|
    Request
	    Path Variable:
		    User ID
	------------------------------------------		     
    Response: 200 OK
	    Payload:
		    Page<Match>
<hr/>

#### Get Match
|Request URI|Method|
|--|--|
|api/match/{id}|GET
    Request
	    Path Variable:
		    Match ID
	------------------------------------------		     
    Response: 200 OK
	    Payload:
		    Match
<hr/>

## Friendship
#### Invite
|Request URI|Method|
|--|--|
|api/friend/send-invite|POST|
    Request 
	    Payload:
		    Long : userId,
		    Long: friendId
	------------------------------------------		     
    Response: 201 CREATED    
<hr/>

#### Answer Invite
|Request URI|Method|
|--|--|
|api/friend/answer-invite|POST|
    Request 
	    Payload:
		    Long : userId,
		    Long: friendId,
		    boolean : accepted
	------------------------------------------		     
    Response: 201 CREATED
	    Payload:
		    User : inviter
<hr/>

