# Spring Core (Framework Fundamentals) - Complete Notes

---

## PART 0 - TIGHT COUPLING VS LOOSE COUPLING (the problem Spring Core solves)

Before understanding IoC or Dependency Injection, you must understand the actual PROBLEM they solve. Without this, Spring's annotations feel like ceremony for no reason.

### What is coupling

Coupling means how much one class DEPENDS ON or KNOWS ABOUT another class. If Class A directly creates and controls Class B, they are tightly coupled. If Class A just needs "something that behaves like B" without caring how it's built, they are loosely coupled.

### Tight coupling - the problem

```java
class MySQLDatabase {
    void save(String data) {
        System.out.println("Saving to MySQL: " + data);
    }
}

class UserService {
    // UserService creates its own dependency directly using "new"
    private MySQLDatabase database = new MySQLDatabase();

    void registerUser(String name) {
        database.save(name);
    }
}
```

This looks fine at first, but it creates real problems:

**Problem 1 - Hard to switch implementations.** What if tomorrow you want to switch to PostgreSQL, or MongoDB, or a mock database for testing? You have to go INSIDE `UserService` and change the `new MySQLDatabase()` line. Every class that uses `MySQLDatabase` directly needs to be hunted down and edited.

**Problem 2 - Hard to test.** You cannot test `UserService` without ALSO creating a real `MySQLDatabase` connection, because they're welded together. You can't substitute a fake/mock database for testing purposes.

**Problem 3 - Violates flexibility.** `UserService` should only care about WHAT it needs (something that can save data), not WHICH specific class provides that (MySQL vs Postgres vs Mongo). Right now it knows too much - it's tightly bound to one specific implementation.

```java
class PostgresDatabase {
    void save(String data) {
        System.out.println("Saving to Postgres: " + data);
    }
}

// To switch databases, you must MODIFY UserService's source code:
class UserService {
    private PostgresDatabase database = new PostgresDatabase(); // had to edit this line
    void registerUser(String name) {
        database.save(name);
    }
}
// This breaks the Open/Closed Principle - code should be open for extension,
// closed for modification. Here, every database change forces a modification.
```

### Loose coupling - the fix

The fix is: `UserService` should depend on an INTERFACE (a contract), not a concrete class. And instead of `UserService` creating its own dependency, something OUTSIDE should hand it the dependency it needs.

```java
// Step 1: define a contract - what a "database" must be able to do
interface Database {
    void save(String data);
}

// Step 2: multiple implementations can fulfill this contract
class MySQLDatabase implements Database {
    public void save(String data) {
        System.out.println("Saving to MySQL: " + data);
    }
}

class PostgresDatabase implements Database {
    public void save(String data) {
        System.out.println("Saving to Postgres: " + data);
    }
}

class MockDatabase implements Database {  // perfect for testing
    public void save(String data) {
        System.out.println("Pretending to save: " + data);
    }
}

// Step 3: UserService depends on the INTERFACE, not a specific class
class UserService {
    private Database database; // just "a Database", doesn't care which one

    // the dependency is GIVEN to it from outside (constructor), not created inside
    UserService(Database database) {
        this.database = database;
    }

    void registerUser(String name) {
        database.save(name); // doesn't know or care if it's MySQL, Postgres, or Mock
    }
}

// Now switching implementation requires ZERO changes inside UserService
UserService service1 = new UserService(new MySQLDatabase());
UserService service2 = new UserService(new PostgresDatabase());
UserService testService = new UserService(new MockDatabase()); // easy testing!
```

```
Tight Coupling                        |  Loose Coupling
---------------------------------------|---------------------------------------
Class creates its own dependencies     |  Dependencies are given from outside
  (new SomeClass() inside the class)   |  (passed in via constructor/setter)
Depends on a CONCRETE class            |  Depends on an INTERFACE/abstraction
Hard to swap implementations            |  Easy to swap implementations
Hard to test (can't use mocks easily)  |  Easy to test (just pass a mock)
Changes ripple through many classes     |  Changes are isolated
```

This pattern - "give me my dependencies from outside instead of me creating them" - is called **Dependency Injection**, and it's exactly what Spring automates for you. Spring Core's entire job is managing this process so YOU don't have to manually write `new UserService(new MySQLDatabase())` everywhere by hand.

---

## PART 1 - IoC CONTAINER AND DEPENDENCY INJECTION

### What is Inversion of Control (IoC)

In normal Java, YOUR code controls object creation. You decide when to call `new`, you decide the order, you wire everything together manually.

Inversion of Control means: you give up that control. Instead of YOUR code creating and wiring objects, a CONTAINER (a framework) does it for you. The "control" of creating and managing objects is inverted - moved from your code to the framework.

```
Normal control (you are in charge):
UserService service = new UserService(new MySQLDatabase());
   - YOU decide when this object is created
   - YOU decide what gets passed into it

Inverted control (Spring is in charge):
@Autowired
private UserService userService;
   - SPRING decides when this object is created
   - SPRING decides what gets passed into it
   - You just declare "I need a UserService" and trust the container to provide one
```

This is why it's called "Inversion" - the natural flow of control (you build things) is reversed (the container builds things for you).

### What is the IoC Container

The IoC Container is the core part of Spring that actually does this work. It is responsible for:

1. Creating objects (called **beans** in Spring terminology)
2. Configuring them (setting their dependencies)
3. Managing their entire lifecycle (from creation to destruction)
4. Wiring them together (injecting dependencies where needed)

Think of the IoC container as a factory manager that knows the blueprint of every object your app needs, knows what each object depends on, and assembles everything in the correct order automatically - you never call `new` yourself for these objects.

### What is Dependency Injection (DI)

Dependency Injection is the actual TECHNIQUE the IoC container uses to give objects their dependencies. "Injection" means the dependency is "injected into" (handed to) the class, rather than the class creating it itself - exactly like the loose coupling example in Part 0, but now Spring does the wiring automatically instead of you doing it by hand.

```java
@Service
class MySQLDatabase implements Database {
    public void save(String data) {
        System.out.println("Saving to MySQL: " + data);
    }
}

@Service
class UserService {
    private Database database;

    @Autowired // Spring sees this and automatically INJECTS a Database implementation
    UserService(Database database) {
        this.database = database;
    }

    void registerUser(String name) {
        database.save(name);
    }
}

// You never write: new UserService(new MySQLDatabase())
// Spring's IoC container does this wiring for you behind the scenes
```

IoC is the PRINCIPLE (giving up control of object creation). DI is the PRACTICAL MECHANISM that implements that principle (dependencies get handed/injected into your classes).

### Three ways to inject dependencies

**1. Constructor Injection (recommended, most used in real projects)**

The dependency is passed through the class constructor. This is the Spring team's officially recommended approach.

```java
@Service
class UserService {
    private final Database database; // can be final - immutable once set

    @Autowired // optional on constructors if there's only ONE constructor (Spring 4.3+)
    UserService(Database database) {
        this.database = database;
    }
}
```

Why it's preferred:

- The field can be `final` - guaranteed to never change after construction, which is safer
- The object is never in a half-built state - all required dependencies must be present the moment the object is created
- Makes dependencies obvious - just look at the constructor signature to see everything this class needs
- Makes unit testing easy - just call `new UserService(mockDatabase)` directly, no Spring needed for the test
- If a dependency is missing, the app fails to start immediately (fail-fast), instead of failing later with a confusing NullPointerException

**2. Setter Injection**

The dependency is passed through a setter method, after the object is already constructed.

```java
@Service
class UserService {
    private Database database; // cannot be final - set after construction

    @Autowired
    void setDatabase(Database database) {
        this.database = database;
    }
}
```

When to use it: for OPTIONAL dependencies - things the class can function without, or that might be reconfigured later after the object already exists. Rarely the right choice for required dependencies.

**3. Field Injection (discouraged, but extremely common in tutorials and old code)**

The dependency is injected directly into the field, no constructor or setter involved.

```java
@Service
class UserService {
    @Autowired
    private Database database; // injected directly, no constructor needed
}
```

Why it's discouraged despite being common:

- The field cannot be `final` - it's mutable, less safe
- Cannot create the object without Spring's reflection magic - makes plain unit testing harder (you can't just call `new UserService()` and pass a mock cleanly)
- Hides the class's real dependencies - you have to read through the entire class body to know what it needs, instead of seeing it all in one constructor signature
- Easy to end up with too many dependencies without noticing, since there's no constructor getting uncomfortably long as a visual warning sign

```
Constructor Injection          |  Setter Injection            |  Field Injection
---------------------------------|---------------------------------|---------------------------------
Recommended, most common         |  Use for optional dependencies  |  Avoid in real projects
Allows final fields              |  Cannot use final               |  Cannot use final
Fail-fast if dependency missing  |  Object can exist incompletely  |  Object can exist incompletely
Easy to unit test without Spring |  Slightly easier than field     |  Hardest to unit test cleanly
Dependencies visible at a glance |  Spread across setter methods   |  Hidden inside the class body
```

---

## PART 2 - BEAN LIFECYCLE

### What is a "Bean"

A bean is simply an object that the Spring IoC container creates, configures, and manages. Not every object in your Spring app is a bean - only the ones you've told Spring to manage (via `@Component`, `@Service`, `@Bean`, etc. - covered in Part 4). Regular objects you create yourself with `new` inside your code are NOT beans; Spring doesn't know or care about them.

### Why lifecycle matters

Since Spring creates and owns these objects, it follows a fixed, predictable sequence of steps every single time it creates one. Understanding this sequence matters when you need to run setup code (like opening a database connection) right after a bean is built, or cleanup code (like closing that connection) right before the app shuts down.

### The lifecycle stages

```
1. Instantiation        -> Spring creates the object (calls the constructor)
2. Populate Properties  -> Spring injects dependencies (DI happens here)
3. Initialization       -> custom setup logic runs (your "ready to use" hook)
4. Bean is Ready         -> bean sits in the container, ready to be used by your app
5. Destruction           -> custom cleanup logic runs (only for singleton beans, when context closes)
```

```java
@Component
class DatabaseConnection {

    DatabaseConnection() {
        // STEP 1: Instantiation
        System.out.println("1. Constructor called - object created");
    }

    @Autowired
    void setConfig(AppConfig config) {
        // STEP 2: Populate Properties (Dependency Injection happens)
        System.out.println("2. Dependencies injected");
    }

    @PostConstruct // STEP 3: runs AFTER construction AND dependency injection are both done
    void init() {
        System.out.println("3. @PostConstruct - opening DB connection");
        // perfect place to: open a connection, load initial data, validate config
    }

    // ... bean sits here, fully ready, used throughout the app's lifetime ...

    @PreDestroy // STEP 5: runs right before the bean is destroyed
    void cleanup() {
        System.out.println("5. @PreDestroy - closing DB connection");
        // perfect place to: close connections, release resources, flush data
    }
}
```

### Why @PostConstruct matters - the constructor isn't always enough

You might think "just put setup logic in the constructor" - but at constructor time, dependency injection may NOT be complete yet (especially with field/setter injection). `@PostConstruct` guarantees the object is FULLY built, with ALL dependencies injected, before your setup logic runs.

```java
@Component
class EmailService {
    @Autowired
    private SmtpConfig config; // field injection happens AFTER constructor runs

    EmailService() {
        // WRONG - config is still null here! Field injection hasn't happened yet
        // System.out.println(config.getHost()); // would throw NullPointerException
    }

    @PostConstruct
    void init() {
        // CORRECT - by now, config is guaranteed to be injected
        System.out.println("SMTP Host: " + config.getHost());
    }
}
```

### Alternative ways to hook into the lifecycle

```java
// Option 1: @PostConstruct / @PreDestroy annotations (most common, cleanest)
@PostConstruct
void init() { }

@PreDestroy
void cleanup() { }

// Option 2: implement Spring's interfaces directly (older style, less common now)
class MyBean implements InitializingBean, DisposableBean {
    public void afterPropertiesSet() { } // same purpose as @PostConstruct
    public void destroy() { }            // same purpose as @PreDestroy
}

// Option 3: specify init/destroy methods in @Bean definition (for beans you don't own the source of)
@Bean(initMethod = "init", destroyMethod = "cleanup")
public ThirdPartyService thirdPartyService() {
    return new ThirdPartyService();
}
```

### Important note on destruction

`@PreDestroy` only reliably fires for **Singleton-scoped** beans, because Spring keeps full control over a singleton's entire lifecycle - it knows exactly when the application context shuts down and the bean should die. For **Prototype-scoped** beans (see Part 3), Spring hands off the object and stops tracking it after creation - so it does NOT call destruction callbacks for them. You'd have to clean those up yourself.

---

## PART 3 - BEAN SCOPES

A bean's "scope" determines HOW MANY instances of that bean exist, and HOW LONG each instance lives. By default, every Spring bean is a Singleton unless you say otherwise.

### Singleton (default scope)

ONLY ONE instance of this bean exists in the entire Spring container. Every time something asks for this bean, Spring hands back the SAME object.

```java
@Service
@Scope("singleton") // this is the default - you don't even need to write it
class ConfigService {
    private int requestCount = 0;

    void increment() {
        requestCount++;
        System.out.println("Count: " + requestCount);
    }
}

// Anywhere this is injected, it's the EXACT SAME object
@Autowired ConfigService configA;
@Autowired ConfigService configB;
// configA == configB -> true, they point to the identical instance
```

When to use: stateless services, configuration objects, anything that should be shared and doesn't hold per-request mutable data. Most of your `@Service`, `@Repository` classes are perfectly fine as singletons - this is also why Singleton is the default.

Be careful: since there's only ONE instance shared by everyone, you should NOT store request-specific or user-specific mutable state in a singleton bean's fields - all users would see/overwrite each other's data.

### Prototype

A NEW instance is created EVERY TIME the bean is requested from the container.

```java
@Component
@Scope("prototype")
class ShoppingCart {
    private List<String> items = new ArrayList<>();

    void addItem(String item) {
        items.add(item);
    }
}

// Every injection point or every explicit lookup gets a BRAND NEW instance
ShoppingCart cartA = context.getBean(ShoppingCart.class);
ShoppingCart cartB = context.getBean(ShoppingCart.class);
// cartA == cartB -> false, completely separate objects
```

When to use: objects that hold mutable, per-use state that should never be shared - like a shopping cart, a builder object, or anything where each "use" needs its own fresh copy.

### Request scope (web applications only)

A NEW instance is created for EVERY single HTTP request. Lives only for the duration of that one request, then is discarded.

```java
@Component
@Scope(value = WebApplicationContext.SCOPE_REQUEST, proxyMode = ScopedProxyMode.TARGET_CLASS)
class RequestLogger {
    private String requestId = UUID.randomUUID().toString();

    void log(String message) {
        System.out.println("[" + requestId + "] " + message);
    }
}

// Every incoming HTTP request gets its own RequestLogger instance
// Request A and Request B never share the same RequestLogger
```

When to use: data that is specific to one HTTP request and must not leak into another - request-specific logging, tracking a single request's metadata.

### Session scope (web applications only)

A NEW instance is created for EVERY user SESSION (one logged-in user's browsing session), and the SAME instance is reused across all requests within that session, until the session ends.

```java
@Component
@Scope(value = WebApplicationContext.SCOPE_SESSION, proxyMode = ScopedProxyMode.TARGET_CLASS)
class UserSessionCart {
    private List<String> items = new ArrayList<>();

    void addItem(String item) {
        items.add(item);
    }
}

// One instance PER LOGGED IN USER, shared across all their requests during that session
// User A's session cart and User B's session cart are completely separate
```

When to use: data tied to one user's ongoing session - like a shopping cart that should persist as the user browses multiple pages, but reset when they leave.

### Quick comparison

```
Scope      | New instance created when...     | Shared across...
-----------|-----------------------------------|-----------------------------
Singleton  | Once, at container startup        | The entire application
Prototype  | Every single bean request          | Nobody, always brand new
Request    | Every HTTP request                 | That one request only
Session    | Every new user session             | All requests in that session
```

---

## PART 4 - APPLICATIONCONTEXT VS BEANFACTORY

Both are implementations of the IoC container. They both create and manage beans, but ApplicationContext is the more powerful, more commonly used one.

### BeanFactory - the basic container

The most basic IoC container interface. Provides core DI functionality, but very lightweight.

```java
BeanFactory factory = new XmlBeanFactory(new ClassPathResource("beans.xml")); // old-school style
UserService service = (UserService) factory.getBean("userService");
```

Key trait: **lazy initialization**. Beans are NOT created when the container starts. They're only created the moment you actually call `getBean()` and ask for them.

### ApplicationContext - the full-featured container

A superset of BeanFactory. It extends BeanFactory and adds a lot of enterprise-level features. This is what virtually every real Spring application actually uses.

```java
ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
UserService service = context.getBean(UserService.class);
```

Key trait: **eager initialization** by default for singleton beans. ALL singleton beans are created immediately when the container starts up - not when first requested. This means configuration mistakes are caught immediately at startup (fail-fast), instead of silently failing later during actual use.

### What ApplicationContext adds on top of BeanFactory

```
- Internationalization support (i18n) - messages in different languages
- Event publishing - beans can publish and listen to application events
- AOP integration - makes features like @Transactional and @Aspect work
- Easy access to resources (files, classpath resources)
- Automatic BeanPostProcessor and BeanFactoryPostProcessor registration
  (these power a LOT of Spring's "magic" annotations behind the scenes)
- Web-application specific contexts (WebApplicationContext for Spring MVC)
```

### Practical difference - what you'll actually use

```
BeanFactory          |  ApplicationContext
-----------------------|------------------------------------------------
Lazy loading           |  Eager loading (for singletons) by default
Basic DI only           |  DI + AOP + events + i18n + much more
Rarely used directly    |  Used in virtually every real Spring project
Lightweight, minimal    |  Heavier, but gives you the full framework
```

In a real Spring Boot application, you almost never interact with either of these directly - Spring Boot creates and manages the ApplicationContext for you automatically when the app starts (specifically, an `AnnotationConfigApplicationContext` or `WebApplicationContext` depending on the app type). You just use `@Autowired`/`@Component` and Spring Boot wires the container behind the scenes.

---

## PART 5 - STEREOTYPE ANNOTATIONS: @Component, @Service, @Repository, @Configuration, @Bean

### How Spring knows what to manage

Spring needs to know WHICH classes should become beans. You tell it by annotating the class. Spring then scans your codebase (called "component scanning") and automatically registers any annotated class as a bean in the IoC container.

### @Component - the generic, base annotation

Marks any class as a Spring-managed bean. This is the most generic stereotype - "hey Spring, manage this object for me."

```java
@Component
class EmailValidator {
    boolean isValid(String email) {
        return email.contains("@");
    }
}
```

### @Service, @Repository, @Controller - specialized versions of @Component

These are all internally just `@Component` with extra meaning attached. Functionally, Spring treats them almost identically for the purpose of bean creation - the difference is mainly about COMMUNICATING INTENT to other developers (and in some cases, triggering extra behavior).

```java
@Service  // marks this as a class containing BUSINESS LOGIC
class UserService {
    void registerUser(String name) { /* business rules here */ }
}

@Repository  // marks this as a class that talks to the DATABASE
class UserRepository {
    User findById(int id) { /* database access here */ }
}

@Controller  // marks this as a class that handles WEB REQUESTS (covered in Spring MVC)
class UserController {
    // handles incoming HTTP requests
}
```

**Why use the specific ones instead of just always writing @Component?**

1. **Readability** - anyone reading your code instantly understands the ROLE of each class, just from the annotation
2. **@Repository has a real functional benefit** - it automatically translates database-specific exceptions (like SQL exceptions) into Spring's own consistent `DataAccessException` hierarchy. This means your service layer code doesn't need to know or care whether you're using MySQL, Postgres, or MongoDB underneath - the exception types stay consistent.

```
@Component   -> generic, any Spring-managed object
@Service     -> business logic layer
@Repository  -> data access layer (gets exception translation as a bonus)
@Controller  -> web layer, handles HTTP requests
```

### @Configuration and @Bean - manual bean definitions

Sometimes you need to create a bean for a class you DON'T own the source code of (a third-party library class), so you cannot just slap `@Component` on it. For these cases, you manually define HOW to build the bean inside a `@Configuration` class.

```java
@Configuration // tells Spring: "this class contains bean definitions"
class AppConfig {

    @Bean // tells Spring: "call this method, and register whatever it returns as a bean"
    public RestTemplate restTemplate() {
        return new RestTemplate(); // a third-party class, you can't add @Component to it
    }

    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule()); // custom setup logic before returning
        return mapper;
    }
}

// Now anywhere in your app:
@Autowired
private RestTemplate restTemplate; // Spring gives you the bean defined in AppConfig
```

### @Component vs @Bean - when to use which

```
@Component (and @Service/@Repository/@Controller)  |  @Bean
-----------------------------------------------------|--------------------------------------
Put directly on YOUR OWN class                       |  Put on a METHOD inside @Configuration
Used when you own/can modify the source code         |  Used for third-party classes you can't annotate
Spring discovers it via component scanning           |  You explicitly write the creation logic
Simple, less control over construction                |  Full control - run custom logic before returning
```

### A full example tying these together

```java
@Configuration
class AppConfig {
    @Bean
    public Database database() {
        return new MySQLDatabase(); // third-party-ish, manually constructed
    }
}

@Repository
class UserRepository {
    private final Database database;

    UserRepository(Database database) { // injected automatically from AppConfig's @Bean
        this.database = database;
    }

    void save(String user) {
        database.save(user);
    }
}

@Service
class UserService {
    private final UserRepository userRepository; // injected automatically, since @Repository is a @Component

    UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    void registerUser(String name) {
        userRepository.save(name);
    }
}
```

---

## PART 6 - ASPECT-ORIENTED PROGRAMMING (AOP)

### The problem AOP solves - cross-cutting concerns

Some pieces of logic don't belong to any ONE business feature, but need to happen across MANY different parts of your application. Logging, security checks, performance timing, transaction management - these are called **cross-cutting concerns**, because they "cut across" multiple classes/layers instead of belonging to just one.

```java
// WITHOUT AOP - logging logic is duplicated everywhere, mixed with business logic
@Service
class UserService {
    void registerUser(String name) {
        System.out.println("LOG: registerUser called"); // duplicated boilerplate
        long start = System.currentTimeMillis();          // duplicated boilerplate

        // actual business logic
        System.out.println("Registering user: " + name);

        long end = System.currentTimeMillis();
        System.out.println("LOG: registerUser took " + (end - start) + "ms"); // duplicated boilerplate
    }

    void deleteUser(int id) {
        System.out.println("LOG: deleteUser called"); // same boilerplate again
        long start = System.currentTimeMillis();
        // actual business logic
        long end = System.currentTimeMillis();
        System.out.println("LOG: deleteUser took " + (end - start) + "ms");
    }
}
// Every method has the SAME logging/timing code mixed into it.
// If you want to change the logging format, you must edit EVERY method, in EVERY class.
```

AOP lets you pull this repeated logic OUT into one separate place, and tell Spring "automatically apply this logic to all these methods", without touching the original business logic code at all.

```java
// WITH AOP - business logic is clean, logging is handled separately
@Service
class UserService {
    void registerUser(String name) {
        System.out.println("Registering user: " + name); // ONLY business logic, nothing else
    }

    void deleteUser(int id) {
        System.out.println("Deleting user: " + id); // ONLY business logic
    }
}

// Logging logic lives in exactly ONE place, applied to MANY methods automatically
@Aspect
@Component
class LoggingAspect {
    @Around("execution(* com.example.service.*.*(..))")
    public Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        System.out.println("LOG: " + joinPoint.getSignature() + " called");
        long start = System.currentTimeMillis();

        Object result = joinPoint.proceed(); // runs the actual method

        long end = System.currentTimeMillis();
        System.out.println("LOG: took " + (end - start) + "ms");
        return result;
    }
}
```

### Key AOP terminology

```
Aspect      -> the class containing the cross-cutting logic (e.g., LoggingAspect)
Advice      -> the actual code that runs (the "what to do") - @Before, @After, @Around, etc.
Join Point  -> a point in your code where advice CAN be applied (e.g., a method call)
Pointcut    -> an expression that defines WHICH join points the advice applies to
              (e.g., "all methods in the service package")
Weaving     -> the process of actually applying aspects to your code
              (Spring does this at runtime using dynamic proxies)
```

```java
@Aspect
@Component
class LoggingAspect {

    // Pointcut expression: WHERE should this advice apply?
    // execution(* com.example.service.*.*(..))
    //           ^ ^                  ^ ^   ^
    //           | |                  | |   any parameters
    //           | |                  | any method name
    //           | |                  any class in this package
    //           | any return type
    //          "execution of a method"
}
```

### @Before - runs before the method executes

```java
@Aspect
@Component
class SecurityAspect {

    @Before("execution(* com.example.service.UserService.deleteUser(..))")
    public void checkPermission() {
        System.out.println("Checking if user has permission to delete...");
        // runs BEFORE deleteUser() executes
        // commonly used for: security checks, validation, logging entry
    }
}
```

### @After - runs after the method executes (regardless of outcome)

```java
@Aspect
@Component
class CleanupAspect {

    @After("execution(* com.example.service.*.*(..))")
    public void logCompletion() {
        System.out.println("Method execution finished");
        // runs AFTER the method completes - whether it succeeded OR threw an exception
        // commonly used for: cleanup, logging that something finished
    }
}

// Related variants:
// @AfterReturning - runs only if the method completed SUCCESSFULLY (no exception)
// @AfterThrowing  - runs only if the method THREW an exception
```

### @Around - wraps the method completely (most powerful)

`@Around` gives you full control - you decide if/when the actual method runs, you can modify its return value, you can catch exceptions, you can skip calling it entirely.

```java
@Aspect
@Component
class TransactionAspect {

    @Around("execution(* com.example.service.*.*(..))")
    public Object manageTransaction(ProceedingJoinPoint joinPoint) throws Throwable {
        System.out.println("Starting transaction...");
        try {
            Object result = joinPoint.proceed(); // THIS is what actually calls the real method
            System.out.println("Committing transaction...");
            return result;
        } catch (Exception e) {
            System.out.println("Rolling back transaction due to: " + e.getMessage());
            throw e;
        }
    }
}
```

`joinPoint.proceed()` is the key difference from `@Before`/`@After` - it's YOUR code's responsibility to call it. If you forget to call it, the actual business method never runs at all. This is exactly the mechanism behind Spring's own `@Transactional` annotation internally.

### Comparison of advice types

```
@Before         -> runs BEFORE the method, cannot stop it from running, cannot see the result
@After          -> runs AFTER the method (success or failure), cannot change the result
@AfterReturning -> runs AFTER, only on success, CAN access the returned value
@AfterThrowing  -> runs AFTER, only if an exception was thrown, CAN access the exception
@Around         -> runs BEFORE and AFTER, full control - can skip, modify, retry, catch
```

### Real-world uses of AOP you'll encounter constantly

```
- @Transactional (Spring's own annotation) - wraps methods in DB transactions, uses AOP internally
- Logging - automatically log entry/exit/timing of methods across the whole app
- Security - check permissions before sensitive methods run, without cluttering business logic
- Caching - check a cache before running an expensive method, store result after
- Exception handling - centralize how certain exceptions are logged or transformed
```

This is exactly why understanding AOP matters even if you never write a custom `@Aspect` yourself - many of Spring's most-used annotations (`@Transactional`, `@Cacheable`, `@Async`) are built ON TOP of AOP. Knowing AOP demystifies how they actually work under the hood.

---

## PART 7 - QUICK REFERENCE

```
TIGHT VS LOOSE COUPLING:
  Tight   - class creates its own dependencies with `new`, depends on concrete classes
  Loose   - dependencies are given from outside, class depends on interfaces/abstractions
  Loose coupling = easy to swap implementations, easy to test, less ripple effect

IoC (Inversion of Control):
  Control of object creation moves from YOUR code to the FRAMEWORK (Spring container)

DEPENDENCY INJECTION (the mechanism IoC uses):
  Constructor Injection -> recommended, allows final fields, fail-fast, easiest to test
  Setter Injection      -> for optional dependencies
  Field Injection       -> discouraged, hides dependencies, harder to test

BEAN LIFECYCLE:
  Instantiation -> Populate Properties (DI) -> Initialization (@PostConstruct) ->
  Ready/In Use -> Destruction (@PreDestroy, singleton only)

BEAN SCOPES:
  Singleton  -> ONE instance, shared app-wide (default)
  Prototype  -> NEW instance every time it's requested
  Request    -> NEW instance per HTTP request (web apps)
  Session    -> NEW instance per user session, reused across that session's requests

APPLICATIONCONTEXT VS BEANFACTORY:
  BeanFactory       -> basic container, lazy loading, rarely used directly
  ApplicationContext-> full-featured (AOP, events, i18n), eager loading for singletons,
                       what Spring Boot actually uses under the hood

STEREOTYPE ANNOTATIONS:
  @Component  -> generic Spring-managed bean
  @Service    -> business logic layer (semantic clarity)
  @Repository -> data access layer (+ automatic exception translation)
  @Controller -> web layer, handles HTTP requests
  @Configuration + @Bean -> manually define beans, used for third-party classes you can't annotate

AOP (Aspect-Oriented Programming):
  Solves: cross-cutting concerns (logging, security, transactions) duplicated across many classes
  Aspect     -> class holding the cross-cutting logic
  Advice     -> the actual code that runs (@Before/@After/@Around)
  Join Point -> a place in code where advice can apply (a method call)
  Pointcut   -> expression defining WHICH join points get the advice
  @Before         -> runs before method, can't stop/see result
  @After          -> runs after (success or failure)
  @AfterReturning -> runs after success, can access return value
  @AfterThrowing  -> runs after exception, can access the exception
  @Around         -> full control, must call joinPoint.proceed() to run the actual method
  Powers Spring's own @Transactional, @Cacheable, @Async internally
```
