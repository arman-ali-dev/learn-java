# Web Fundamentals (Before Spring) - Complete Notes

---

## PART 1 - CLIENT-SERVER ARCHITECTURE

Before touching Spring, you need to understand what is actually happening when you open a website or use an app. Spring is just a tool that helps you build the "server" side of this conversation. If you don't understand the conversation itself, Spring's annotations will feel like magic instead of logic.

### The basic idea

Two computers talk to each other. One asks for something, the other gives an answer back.

- **Client**: the one who asks. This is your browser, your mobile app, Postman, or any program that initiates a request. The client wants something - a webpage, data, a confirmation that an order was placed.
- **Server**: the one who answers. This is a program running on a machine (could be in your room, could be in a data center anywhere in the world) that listens for requests and sends back responses.

```
   CLIENT                                    SERVER
(Browser/App)                          (Your Spring Boot app)

   "Give me user with id 5"   ------>
                                        [server processes request,
                                         looks up database]
                              <------   "Here is user 5's data"
```

Think of it like a restaurant. You (client) tell the waiter (request) what you want. The kitchen (server) prepares it and the waiter brings it back (response). You never go into the kitchen yourself - you just send a request and wait for a response.

### Why this separation exists

The client and server are separate programs, often on separate machines, sometimes in separate countries. This separation is intentional:

- The server can serve thousands of clients at the same time (one restaurant, many customers)
- The client doesn't need to know HOW the server works internally - just that if you ask correctly, you get an answer
- You can update the server's code without touching every client device
- The same server (your API) can serve a website, a mobile app, AND another server - all using the same requests

```java
// This is literally what you build with Spring Boot
// You are writing the "server" - the part that LISTENS and RESPONDS

@RestController
public class UserController {

    @GetMapping("/users/5")  // server is now listening for this exact request
    public User getUser() {
        // server does the work
        return userService.findById(5); // server sends back the response
    }
}
```

### How they actually connect

Every server has an **IP address** (like a phone number for computers) and listens on a **port** (like an extension number within that phone number). A domain name like `google.com` is just a human-friendly nickname for an IP address.

```
http://localhost:8080/users/5
       \_________/ \__/\_____/
            |        |     |
       IP/domain   port   path

localhost = your own machine (127.0.0.1)
8080      = the port your Spring Boot app is listening on
/users/5  = the specific resource being requested
```

When you run a Spring Boot app, you are literally starting a server that begins listening on a port (8080 by default) and waiting for requests to arrive.

---

## PART 2 - HTTP PROTOCOL BASICS

### What is HTTP

HTTP (HyperText Transfer Protocol) is the agreed-upon set of rules for how clients and servers talk to each other on the web. Without a shared protocol, the client and server would have no common language.

Think of HTTP like a form letter format that everyone agrees to use. As long as both sides format their messages the same way, they can understand each other regardless of what programming language either side is written in. Your Java Spring Boot server can talk to a client written in JavaScript, Python, Swift - none of that matters because they all "speak HTTP".

### The request-response cycle

Every interaction follows the exact same pattern:

```
1. Client opens a connection to the server
2. Client SENDS a request (what it wants)
3. Server RECEIVES the request, processes it
4. Server SENDS a response (the answer)
5. Connection is typically closed (HTTP is stateless - see below)
```

This cycle repeats for EVERY single request. Loading one webpage might trigger dozens of these cycles - one for the HTML, one for each image, one for the CSS file, one for each API call the page makes.

```
Browser loads www.amazon.com:

Request 1:  GET /index.html        -> Response: the HTML page
Request 2:  GET /logo.png          -> Response: the logo image
Request 3:  GET /styles.css        -> Response: the CSS file
Request 4:  GET /api/recommendations -> Response: JSON data for "recommended for you"
Request 5:  GET /api/cart/count    -> Response: JSON saying "3 items in cart"

... and so on, all happening in the background while you see one "page"
```

### Synchronous nature

A basic HTTP request is synchronous from the client's perspective - the client sends the request and waits for the response before doing the next thing with that data. (This is different from async JavaScript on the frontend, which is a separate topic - the underlying HTTP call-and-response itself is still one request followed by one response.)

---

## PART 3 - HTTP METHODS

HTTP methods (also called "verbs") tell the server WHAT KIND of action the client wants to perform. Choosing the right method is not just a formality - it tells everyone reading your API (other developers, tools, browsers, caches) what to expect.

### GET - retrieve data

Used to READ/fetch data. Should never change anything on the server. Safe to call multiple times - calling GET 100 times should give the same result as calling it once (assuming nothing else changed the data).

```java
@GetMapping("/users/5")
public User getUser(@PathVariable int id) {
    return userService.findById(id); // just reading, nothing is modified
}

// GET requests typically do NOT have a request body
// Data is passed through the URL (path variables, query params)
// GET /users/5
// GET /products?category=electronics&minPrice=100
```

GET requests can be cached by browsers and CDNs because calling them repeatedly is harmless. This is why you should NEVER use GET to perform an action that changes data (like "delete this user" via GET) - browsers and proxies might cache or re-trigger it unexpectedly.

### POST - create new data

Used to CREATE something new. Each call typically creates a new resource, so calling POST twice with the same data usually creates two separate things.

```java
@PostMapping("/users")
public User createUser(@RequestBody User newUser) {
    return userService.save(newUser); // creates a NEW user, gets a new ID
}

// Calling this twice with the same JSON body creates TWO users
// POST is NOT idempotent (repeating it changes the outcome)
```

POST requests carry data in the **request body**, not the URL. This is also why POST is used for sensitive data like login forms - the data does not appear in the URL or browser history.

### PUT - update/replace entirely

Used to UPDATE an existing resource by REPLACING it completely. You must send the entire object - any field you don't send is treated as missing/null.

```java
@PutMapping("/users/5")
public User updateUser(@PathVariable int id, @RequestBody User updatedUser) {
    // updatedUser must contain ALL fields - name, email, age, etc.
    // even fields that didn't change must be included
    return userService.replace(id, updatedUser);
}

// PUT is idempotent - calling it 5 times with the same body
// results in the same final state as calling it once
```

### PATCH - update partially

Used to UPDATE only specific fields of a resource, leaving the rest untouched. Unlike PUT, you only send what you want to change.

```java
@PatchMapping("/users/5")
public User updateEmail(@PathVariable int id, @RequestBody Map<String, String> updates) {
    // client might send only: { "email": "new@email.com" }
    // name, age, and other fields remain unchanged
    return userService.partialUpdate(id, updates);
}
```

### DELETE - remove data

Used to DELETE a resource.

```java
@DeleteMapping("/users/5")
public void deleteUser(@PathVariable int id) {
    userService.delete(id);
}

// DELETE is generally idempotent in intent -
// deleting an already-deleted resource should not cause new problems
// (though the second call might return 404 since it's already gone)
```

### Quick comparison table

```
Method  | Purpose            | Has Body? | Idempotent? | Safe (no side effects)?
--------|---------------------|-----------|-------------|------------------------
GET     | Read data           | No        | Yes         | Yes
POST    | Create new data     | Yes       | No          | No
PUT     | Replace entirely    | Yes       | Yes         | No
PATCH   | Update partially    | Yes       | Usually     | No
DELETE  | Remove data         | Usually no| Yes         | No
```

"Idempotent" means: calling it multiple times has the same effect as calling it once. This is a common interview question - know it well.

### Real-world example - choosing the right method

```
Building a "Todo App" backend:

GET    /todos          -> get all todos
GET    /todos/3        -> get todo with id 3
POST   /todos          -> create a new todo
PUT    /todos/3        -> replace todo 3 entirely (send all fields)
PATCH  /todos/3        -> mark just "completed": true, leave rest untouched
DELETE /todos/3        -> delete todo 3
```

---

## PART 4 - HTTP STATUS CODES

Every response from a server includes a status code - a 3-digit number that tells the client what happened. The client (or the developer debugging) should be able to understand the outcome just from this number, without even reading the response body.

### The 5 categories

```
1xx - Informational  (rarely seen directly, request received, continuing process)
2xx - Success        (request was successfully received, understood, and processed)
3xx - Redirection    (further action needed, usually means "go look somewhere else")
4xx - Client Error   (the CLIENT made a mistake - bad request, not authorized, etc.)
5xx - Server Error   (the SERVER made a mistake - something broke internally)
```

A simple way to remember: 4xx means "you (client) did something wrong", 5xx means "we (server) messed up".

### Commonly used codes in real APIs

**200 OK** - the request succeeded, here is your data.

```java
@GetMapping("/users/5")
public ResponseEntity<User> getUser(@PathVariable int id) {
    User user = userService.findById(id);
    return ResponseEntity.ok(user); // sends 200 status with the user in the body
}
```

**201 Created** - a new resource was successfully created. Used after a successful POST.

```java
@PostMapping("/users")
public ResponseEntity<User> createUser(@RequestBody User newUser) {
    User saved = userService.save(newUser);
    return ResponseEntity.status(HttpStatus.CREATED).body(saved); // 201, not 200
}
```

**204 No Content** - the request succeeded but there is nothing to send back. Common after DELETE.

```java
@DeleteMapping("/users/5")
public ResponseEntity<Void> deleteUser(@PathVariable int id) {
    userService.delete(id);
    return ResponseEntity.noContent().build(); // 204, empty body
}
```

**400 Bad Request** - the client sent something the server cannot understand or process. Usually means missing/invalid data in the request.

```java
// example: client sends { "email": "" } when email is required
return ResponseEntity.badRequest().body("Email cannot be empty"); // 400
```

**401 Unauthorized** - the client did not provide valid credentials, or no credentials at all. "We don't know who you are."

```java
// example: trying to access a protected endpoint without logging in
return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Please log in"); // 401
```

**403 Forbidden** - the client IS known/authenticated, but does not have permission to do this. "We know who you are, but you can't do this."

```java
// example: a regular user trying to access an admin-only endpoint
return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Admins only"); // 403
```

The difference between 401 and 403 is a very common interview question: 401 = not logged in / invalid credentials. 403 = logged in, but lacks permission.

**404 Not Found** - the requested resource simply does not exist.

```java
@GetMapping("/users/{id}")
public ResponseEntity<User> getUser(@PathVariable int id) {
    User user = userService.findById(id);
    if (user == null) {
        return ResponseEntity.notFound().build(); // 404
    }
    return ResponseEntity.ok(user);
}
```

**500 Internal Server Error** - something broke on the server side, often an unhandled exception. This is the server's fault, not the client's.

```java
// if your code throws an unhandled NullPointerException or similar,
// Spring Boot automatically returns 500 by default
```

### Quick reference table

```
Code  | Meaning              | When it's used
------|----------------------|----------------------------------------
200   | OK                   | Successful GET, PUT, PATCH
201   | Created              | Successful POST (new resource made)
204   | No Content           | Successful DELETE, nothing to return
400   | Bad Request          | Invalid data sent by client
401   | Unauthorized         | Not logged in / missing or invalid credentials
403   | Forbidden            | Logged in, but not allowed to do this
404   | Not Found            | Resource doesn't exist
500   | Internal Server Error| Server crashed / unhandled exception
```

---

## PART 5 - REQUEST AND RESPONSE STRUCTURE

Both requests and responses are made of the same building blocks: a starting line, headers, and an optional body.

### Anatomy of a request

```
POST /api/users HTTP/1.1              <- method, path, HTTP version
Host: myapp.com                       <- headers start here
Content-Type: application/json
Authorization: Bearer eyJhbGc...
Accept: application/json

{                                      <- body starts here (blank line separates headers from body)
    "name": "Arman",
    "email": "arman@example.com"
}
```

### Headers

Headers are metadata about the request or response - extra information that is not the actual content, but describes it.

```java
// Common request headers
Content-Type: application/json   // "the body I'm sending you is JSON"
Authorization: Bearer <token>    // "here is my login credential"
Accept: application/json         // "I want the response back as JSON"
User-Agent: Mozilla/5.0 ...      // "this is what browser/client I'm using"

// Common response headers
Content-Type: application/json   // "the body I'm sending back is JSON"
Content-Length: 348              // "my response body is 348 bytes"
Set-Cookie: sessionId=abc123     // "store this cookie for future requests"
```

In Spring Boot, you can read headers like this:

```java
@GetMapping("/profile")
public String getProfile(@RequestHeader("Authorization") String token) {
    // token now holds "Bearer eyJhbGc..."
    return "Token received: " + token;
}
```

### Body

The body is the actual data/content being sent. GET requests usually don't have a body (since you're just asking for something, not sending data). POST, PUT, PATCH typically DO have a body.

```java
@PostMapping("/users")
public User createUser(@RequestBody User newUser) {
    // newUser is automatically built from the JSON body Spring receives
    // {"name": "Arman", "email": "arman@example.com"}  -> becomes a User object
    return userService.save(newUser);
}
```

### Query Parameters

Extra data attached to the URL after a `?`. Used for filtering, sorting, optional settings - things that modify WHAT you get back, not WHICH resource.

```
GET /products?category=electronics&minPrice=100&maxPrice=500&sort=price_asc
              \____key=value pairs separated by &____________________/
```

```java
@GetMapping("/products")
public List<Product> getProducts(
    @RequestParam String category,
    @RequestParam(required = false) Double minPrice,
    @RequestParam(required = false) Double maxPrice
) {
    return productService.filter(category, minPrice, maxPrice);
}
```

### Path Variables

Part of the URL path itself, used to identify a SPECIFIC resource - usually an ID.

```
GET /users/5/orders/102
        \_/      \___/
     path variable  path variable
     (which user)   (which order)
```

```java
@GetMapping("/users/{userId}/orders/{orderId}")
public Order getOrder(@PathVariable int userId, @PathVariable int orderId) {
    return orderService.find(userId, orderId);
}
```

### Path Variable vs Query Parameter - when to use which

```
Use Path Variable when:               Use Query Parameter when:
- identifying WHICH resource           - filtering, sorting, paging
- the value is required, part of       - the value is optional
  the resource's identity              - it modifies the result set,
- /users/5 (user 5 specifically)         not the resource identity
                                        - /products?category=shoes&page=2
```

---

## PART 6 - STATELESSNESS OF HTTP

HTTP is **stateless**. This means the server does NOT remember anything about previous requests from a client by default. Every single request must contain everything the server needs to understand and process it, as if it's the very first request ever made.

### What this means practically

```
Request 1: "Log me in with username=arman, password=xyz"
Server: "OK, you're logged in" -- but the server does NOT remember this for request 2!

Request 2: "Show me my profile"
Server: "...who are you? I don't know you." -- because HTTP forgot everything
```

This seems like a problem, and it is one that web developers solved using a few strategies:

### How servers "remember" you despite statelessness

**Cookies and Sessions** (traditional approach): server creates a session ID after login, stores session data on the server, and sends the session ID back as a cookie. The browser automatically attaches this cookie on every future request.

```
Login request -> Server creates session, sends back: Set-Cookie: sessionId=abc123
Every future request -> Browser automatically sends: Cookie: sessionId=abc123
Server looks up sessionId=abc123 in its memory/database -> "Oh, this is Arman"
```

**Tokens (JWT)** (modern approach, common in Spring Boot APIs): after login, server gives the client a token containing the user's identity (digitally signed so it can't be faked). Client sends this token with every request in the Authorization header. The server does NOT need to store anything - it just verifies the token each time.

```
Login request -> Server verifies credentials, sends back a JWT token
Every future request -> Client sends: Authorization: Bearer <jwt token>
Server verifies the token's signature -> "This is definitely Arman, and it hasn't been tampered with"
```

This is why nearly every Spring Boot REST API + JWT setup works the way it does - it exists specifically because HTTP itself has no memory.

### Why statelessness is actually a good design

Because the server doesn't need to remember individual clients, any server in a cluster can handle any request. This makes scaling much easier - you can add 10 more server instances behind a load balancer and none of them need to "know" about previous conversations with a particular client (as long as you use stateless tokens, not server-side sessions stored in memory).

---

## PART 7 - JSON AS A DATA EXCHANGE FORMAT

### What is JSON

JSON (JavaScript Object Notation) is a lightweight, text-based format for representing structured data. It became the standard way that clients and servers exchange data because it's simple, human-readable, and supported natively or easily in virtually every programming language.

```json
{
  "id": 5,
  "name": "Arman",
  "email": "arman@example.com",
  "age": 22,
  "isActive": true,
  "address": {
    "city": "Indore",
    "pincode": "452001"
  },
  "skills": ["Java", "Spring Boot", "SQL"]
}
```

### JSON data types

```
String   "hello"           -> always double quotes, never single quotes
Number   42, 3.14           -> no quotes around numbers
Boolean  true, false        -> lowercase, no quotes
null     null               -> represents "no value"
Object   { "key": "value" } -> curly braces, key-value pairs
Array    [1, 2, 3]          -> square brackets, ordered list of values
```

### Why JSON and not something else

Before JSON, XML was common - more verbose, harder to read, and harder to parse.

```xml
<!-- XML - verbose -->
<user>
    <name>Arman</name>
    <age>22</age>
</user>
```

```json
// JSON - same data, much lighter
{ "name": "Arman", "age": 22 }
```

### How JSON connects to Spring Boot (Jackson)

Spring Boot uses a library called Jackson behind the scenes to automatically convert between Java objects and JSON. You almost never write this conversion code yourself.

```java
// Your Java class
public class User {
    private int id;
    private String name;
    private String email;
    // getters and setters
}

@GetMapping("/users/5")
public User getUser() {
    User user = new User(5, "Arman", "arman@example.com");
    return user;
    // Spring Boot (via Jackson) AUTOMATICALLY converts this Java object
    // into JSON before sending it back to the client:
    // { "id": 5, "name": "Arman", "email": "arman@example.com" }
}

@PostMapping("/users")
public User createUser(@RequestBody User newUser) {
    // Spring Boot AUTOMATICALLY converts incoming JSON into a Java object
    // client sends: { "name": "Priya", "email": "priya@example.com" }
    // newUser.getName() -> "Priya"
    // newUser.getEmail() -> "priya@example.com"
    return userService.save(newUser);
}
```

This automatic conversion is exactly why `Content-Type: application/json` matters in headers - it tells both sides "interpret this body as JSON".

---

## PART 8 - WHAT IS AN API AND WHAT MAKES IT "RESTFUL"

### What is an API

API stands for Application Programming Interface. In the web context, an API is simply a set of URLs (endpoints) that a server exposes, which clients can call to perform actions or get data - without needing to know how the server implements those actions internally.

```
Think of an API like a restaurant menu.
You don't need to know how the kitchen cooks the food.
You just need to know what you can order (the menu = the API)
and what you'll get back (the dish = the response).
```

```java
// This entire class IS your API - the set of things a client can ask for
@RestController
@RequestMapping("/api/users")
public class UserController {

    @GetMapping              // GET /api/users       -> list of all users
    @GetMapping("/{id}")     // GET /api/users/5      -> one specific user
    @PostMapping             // POST /api/users       -> create a user
    @PutMapping("/{id}")     // PUT /api/users/5      -> update a user
    @DeleteMapping("/{id}")  // DELETE /api/users/5   -> delete a user
}
```

### What makes an API "RESTful"

REST (Representational State Transfer) is a set of architectural principles/conventions for designing APIs. An API following these principles is called "RESTful". REST is not a strict protocol or law - it's a widely agreed-upon style.

**1. Resources are represented as nouns in the URL, not verbs**

```
GOOD (RESTful):
GET    /users/5        -> get user 5
DELETE /users/5        -> delete user 5
POST   /users          -> create a user

BAD (not RESTful):
GET    /getUser?id=5
GET    /deleteUser?id=5     <- using GET to delete?! breaks the "safe" rule too
POST   /createNewUser
```

The HTTP method itself already says the action (GET/POST/DELETE) - the URL should only describe WHAT resource, not what action.

**2. Statelessness** (covered in Part 6) - each request is self-contained, server doesn't store client state between requests.

**3. Resources identified by URLs, manipulated using standard HTTP methods**

Each meaningful "thing" in your system (a user, a product, an order) gets its own URL, and the standard HTTP methods (GET, POST, PUT, PATCH, DELETE) are used consistently across all of them.

**4. Uses standard HTTP status codes** to communicate outcome (covered in Part 4) instead of inventing custom success/failure formats.

**5. Representation-based** - the client and server exchange a "representation" of the resource's current state, typically JSON. The actual resource lives in the database; JSON is just a snapshot/representation of it at that moment.

**6. Hierarchical relationships are shown via nested URLs**

```
GET /users/5/orders          -> all orders belonging to user 5
GET /users/5/orders/102      -> a specific order belonging to user 5
```

### A real, well-designed RESTful resource example

```
Resource: "Todo items" belonging to users

GET    /api/users/5/todos          -> get all todos for user 5
GET    /api/users/5/todos/12       -> get todo 12 belonging to user 5
POST   /api/users/5/todos          -> create a new todo for user 5
PUT    /api/users/5/todos/12       -> replace todo 12 entirely
PATCH  /api/users/5/todos/12       -> mark todo 12 as completed
DELETE /api/users/5/todos/12       -> delete todo 12
```

Notice: nouns only, HTTP methods carry the verb, hierarchy reflects ownership.

---

## PART 9 - BASICS OF POSTMAN

Before you build a frontend, you need a way to test your backend API directly. Postman is a tool that lets you manually send HTTP requests to your server and inspect the response - without writing any client-side code.

### Why you need this

When you're building a Spring Boot backend, there's often no frontend yet. You need to verify your `/users` endpoint actually works BEFORE connecting a React app or mobile app to it. Postman lets you do exactly that.

```
Without Postman: write a frontend just to test if your backend works (slow, wasteful)
With Postman: send a request directly, see the raw response immediately (fast, focused)
```

### Basic workflow in Postman

1. **Choose the HTTP method** - select GET, POST, PUT, PATCH, or DELETE from the dropdown
2. **Enter the URL** - e.g., `http://localhost:8080/api/users/5`
3. **Add headers if needed** - e.g., `Authorization: Bearer <token>`, `Content-Type: application/json`
4. **Add a body if needed** (for POST/PUT/PATCH) - switch to the "Body" tab, select "raw" and "JSON", then type your JSON
5. **Hit Send**
6. **Inspect the response** - Postman shows you the status code, response headers, and response body (usually JSON), all clearly laid out

```
Example test in Postman:

Method: POST
URL: http://localhost:8080/api/users
Headers: Content-Type: application/json
Body (raw, JSON):
{
    "name": "Arman",
    "email": "arman@example.com"
}

Click Send ->

Response:
Status: 201 Created
Body: { "id": 7, "name": "Arman", "email": "arman@example.com" }
```

### Why this matters before learning Spring

Every single concept above (methods, status codes, headers, body, JSON) becomes concrete the moment you test it yourself in Postman. Reading about a 404 is abstract. Actually hitting a wrong URL in Postman and seeing `404 Not Found` pop up makes it permanent knowledge.

Once you start building Spring Boot controllers, Postman becomes your main testing tool until you build (or connect) a real frontend.

---

## PART 10 - QUICK REFERENCE

```
CLIENT-SERVER:
  Client  = the one who asks (browser, app, Postman)
  Server  = the one who answers (your Spring Boot app)
  Connection happens via IP address + port + path

HTTP:
  Shared protocol/format for client-server communication
  Cycle: client sends request -> server processes -> server sends response

HTTP METHODS:
  GET    -> read data           (safe, idempotent, no body usually)
  POST   -> create data         (not idempotent, has body)
  PUT    -> replace entirely    (idempotent, has body, must send all fields)
  PATCH  -> update partially    (has body, only send changed fields)
  DELETE -> remove data         (idempotent in intent)

STATUS CODES:
  2xx success   - 200 OK, 201 Created, 204 No Content
  4xx client err- 400 Bad Request, 401 Unauthorized, 403 Forbidden, 404 Not Found
  5xx server err- 500 Internal Server Error
  401 = not logged in / bad credentials
  403 = logged in, but not allowed

REQUEST/RESPONSE STRUCTURE:
  Headers       -> metadata (Content-Type, Authorization, Accept)
  Body          -> actual data being sent (mainly POST/PUT/PATCH)
  Query Params  -> ?key=value, for filtering/sorting/optional data
  Path Variable -> /users/{id}, for identifying a specific resource

STATELESSNESS:
  Server remembers nothing between requests by default
  Solved via: Cookies+Sessions (server stores state) or JWT tokens (stateless, client carries proof)

JSON:
  Lightweight text format: { "key": "value" }
  Types: String, Number, Boolean, null, Object, Array
  Spring Boot uses Jackson to auto-convert Java objects <-> JSON

API & REST:
  API = set of endpoints a server exposes for clients to use
  RESTful rules:
    - URLs are nouns (resources), not verbs
    - HTTP method carries the action
    - Stateless
    - Standard status codes
    - JSON as representation
    - Hierarchical nesting for relationships (/users/5/orders)

POSTMAN:
  Tool to manually send HTTP requests and inspect responses
  Test backend APIs before building/connecting a frontend
  Workflow: choose method -> enter URL -> add headers/body -> Send -> inspect response
```
