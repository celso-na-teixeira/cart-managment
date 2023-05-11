# Cart Management
Welcome to my Cart Management application! This application is built using the Spring framework and managed with Maven.

---
## Prerequisites

Before running the application, ensure you have the following prerequisites installed:

- Java 8 or higher
- Apache Maven
- Spring Web
- Spring Data JPA
- H2 Database

---
## Installation

To get started with Cart Management application, follow these steps:

1. Clone the repository: `git clone git@github.com:celso-na-teixeira/cart-managment.git`
2. Navigate to the project directory: `cd cart-management`
3. Run Maven to resolve dependencies: `mvn clean install`

---
###  Usage

To interact with the application, use the provided **API endpoints**:
>`GET /cart/items`: Get all items
> 
>`POST /cart/items`: Adds an item to the cart
> 
>`DELETE /cart/items/{itemId}`: Removes an item from the cart
> 
>`GET /cart/total`: Calculates the total cost of the items in the cart

---
### Contributing
Contributions are welcome!