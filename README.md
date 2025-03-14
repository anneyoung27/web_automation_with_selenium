# Web Automation with Selenium

## What's in this repository?
This project is a web automation using Selenium WebDriver. It automates web interactions for testing purposes, ensuring the functionality of web applications.

## What's the purpose of this project?
The purpose of this project is challenging myself to implement what I have learned in web automation testing using Selenium with Java programming language
by implementing Page Object Model and Page Factory Design Pattern.

## Key Features
- TestNG for test execution
- Maven for dependency management
- Console logging and reporting capabilities
- POM and Page Factory Design for code reusability and easy maintenance
- Cross-browser testing support using WebDriver

## Technologies
- Java 21
- Apache Maven
- Selenium
- TestNG Framework
- Log4j2

## Project Structure
```
logs/                                  # Log files generated during execution
│
src/
├── main/
│   ├── java/
│   │     └──── com/
│   │            ├── base/             # Base class for WebDriver setup and test configuration
│   │            └── pages/            # Page Object Model (POM) for each application page
│   ├── resources/
│   │     └──  config/                 # Configuration files like setUp.properties
│   │       
├── test/
│   ├── java/
│   │    └──── com/                    
│   │          └── automation/         
│   │                ├── rough/        # Experimental test scripts
│   │                ├── tests/        # Main test case implementation
│   │                └── utils/        # Utility classes for test data and helpers
│   └── resources/
│       └── files/                     # Test data
└── pom.xml                            # Maven configuration
```

### 1. Project URL
```https://automationexercise.com/```

### 2. UI Test
The UI tests cover the functionality of the website, focusing on key features such as:
- Login
    - Register User
    - Register User with existing email
    - Login User with valid credentials
    - Login User with invalid credentials
    - Logout User
- Home
    - Verify subscription in home page
    - Verify scroll up using 'Arrow' button and Scroll down functionality
    - Verify scroll up without 'Arrow' button and Scroll down functionality
- Product
    - Search product
    - Verify all products and product detail
    - View category product
    - Search products and verify cart after login
    - Add review on product
- Cart
    - Add products in cart
    - Verify product quantity in cart
    - Verify subscription cart page
    - View & cart brand products
    - Remove product quantity in cart
    - Add to cart from Recommended items
- Contact Us
    - Contact us
- Checkout
    - Download Invoice after purchase order
    - Verify address details in checkout page
- E2E (End to End)
    - Place order register before checkout
    - Place order register while checkout
    - Place order login before checkout

## Installation
1. Clone this repository:<br />
   `git clone https://github.com/anneyoung27/web_automation_with_selenium.git`

2. Navigate to the project directory:<br />
   `cd PageObjectModel`

3. Install dependencies using Maven:<br />
   `mvn clean install`

4. Run<br />
   `src/test/java/com/automation/tests`