# Required Software

### Dependencies/Tools
* Node v8+ for npm
* Java 8+
* Visual Studio Code

### Optional IDE for Java
* Eclipse - Oxygen+ - (Embedded Maven From Eclipse)
* IntelliJ 

# Running the Frontend

### Configure
Go to /frontend/buddy19-app/package.json and check under "scripts"
* Windows: `"start": "set PORT=4200 && react-scripts start"`
* Mac: `"start": "PORT=4200 react-scripts start"`

### Run
Navigate back to /frontend/buddy19-app and run the commands `npm install` followed by `npm start` in the terminal.

The app will automatically launch on *localhost:4200*

# Running the Backend

### Configure
In the folder
```
CG1P/backend/todo/src/main/java/com/app/buddy19/scraper/`
```
Both the `ScraperConfig.java` and `ScraperServiceImpl.java` files have this line:
```
System.setProperty("webdriver.chrome.driver", "src/main/resources/chromedriver.exe");
```
This configuration is set for the CI pipeline.<br><br>

No additional changes are required if running on **Windows** with these IDEs:
* VSCode, via Spring Boot Dashboard (Spring Boot Extension Pack)
* IntelliJ

Otherwise, use either of the following configurations accordingly:
* Windows:
```
System.setProperty("webdriver.chrome.driver", "backend/todo/src/main/resources/chromedriver.exe");
```
* Mac:
```
System.setProperty("webdriver.chrome.driver", "src/main/resources/chromedriver");
```

### Run
```
CG1P/backend/todo/src/main/java/com/app/buddy19/Application.java
```
In VSCode / IntelliJ, just "Run Application / Run Java"

# Database
After running SPRING
Go to localhost:8080/h2-console

![image](https://user-images.githubusercontent.com/69102738/130642725-31a43a69-2dc9-4a07-b2f9-77ba636b25ff.png)

Ensure JDBC config is same as above and URL is
`jdbc:h2:mem:testdb`
