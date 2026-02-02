# 🍕 Food Delivery Backend Service

> A production-ready, enterprise-grade food delivery platform backend built with Spring Boot, featuring JWT authentication, role-based access control, payment integration, and comprehensive RESTful APIs.

[![Java Version](https://img.shields.io/badge/Java-17+-007396?style=for-the-badge&logo=java)](https://www.oracle.com/java/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.5.9-6DB33F?style=for-the-badge&logo=spring-boot)](https://spring.io/projects/spring-boot)
[![MySQL](https://img.shields.io/badge/MySQL-8.0-4479A1?style=for-the-badge&logo=mysql)](https://www.mysql.com/)
[![Maven](https://img.shields.io/badge/Maven-Build-C71A36?style=for-the-badge&logo=apache-maven)](https://maven.apache.org/)
[![Swagger](https://img.shields.io/badge/API%20Docs-Swagger-85EA2D?style=for-the-badge&logo=swagger)](https://swagger.io/)

## 📊 System Overview

A complete backend solution for food delivery platforms with advanced features including user authentication, restaurant management, shopping cart, order processing, and integrated payment gateway.

### 🏗️ Architecture Highlights

- **Clean Architecture**: Layered design with clear separation of concerns
- **RESTful APIs**: Industry-standard REST endpoints with proper HTTP methods
- **JWT Security**: Stateless authentication with role-based authorization
- **Payment Integration**: Razorpay payment gateway for secure transactions
- **Data Validation**: Comprehensive input validation with Bean Validation
- **Exception Handling**: Global exception handling with meaningful error responses
- **API Documentation**: Auto-generated interactive API docs with Swagger/OpenAPI

## 🎯 Key Features

### 🔐 Authentication & Authorization
- ✅ **JWT-based Authentication**: Secure token-based authentication system
- ✅ **Role-Based Access Control**: USER and ADMIN roles with fine-grained permissions
- ✅ **Password Encryption**: BCrypt password hashing for security
- ✅ **Secure Endpoints**: Protected routes with Spring Security

### 👥 User Management
- ✅ **User Registration**: Email validation and duplicate prevention
- ✅ **User Login**: JWT token generation on successful authentication
- ✅ **Profile Management**: Users can view and update their profiles
- ✅ **Admin Controls**: Admins can manage all users and assign roles

### 🏪 Restaurant Management
- ✅ **Restaurant CRUD**: Complete restaurant lifecycle management
- ✅ **Menu Items**: Add, update, and manage menu items per restaurant
- ✅ **Search & Filter**: Browse restaurants and menu items
- ✅ **Admin Controls**: Only admins can create/modify restaurants

### 🛒 Shopping Cart
- ✅ **Add to Cart**: Add menu items with quantity control
- ✅ **Update Quantities**: Modify item quantities in real-time
- ✅ **Remove Items**: Delete items from cart
- ✅ **Cart Summary**: View total price and all cart items
- ✅ **User Isolation**: Each user has their own cart

### 📦 Order Management
- ✅ **Place Orders**: Convert cart to order with one click
- ✅ **Order History**: Users can view their past orders
- ✅ **Order Tracking**: Real-time order status updates
- ✅ **Status Management**: Admins can update order status (PENDING, CONFIRMED, PREPARING, OUT_FOR_DELIVERY, DELIVERED, CANCELLED)
- ✅ **Admin Dashboard**: View all orders across the platform

### 💳 Payment Processing
- ✅ **Razorpay Integration**: Secure payment gateway integration
- ✅ **Order Creation**: Generate payment orders with amount calculation
- ✅ **Payment Tracking**: Track payment status (PENDING, COMPLETED, FAILED)
- ✅ **Multiple Methods**: Support for various payment methods

## 🔌 API Endpoints

### 🔐 Authentication APIs
| Method | Endpoint | Description | Access |
|--------|----------|-------------|--------|
| `POST` | `/api/auth/register` | Register new user | Public |
| `POST` | `/api/auth/login` | User login | Public |

**Registration Request:**
```json
{
  "name": "John Doe",
  "email": "john@example.com",
  "password": "securePassword123",
  "phoneNumber": "1234567890",
  "address": "123 Main St, City"
}
```

**Login Response:**
```json
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
  "email": "john@example.com",
  "role": "USER"
}
```

### 👤 User Management APIs
| Method | Endpoint | Description | Access |
|--------|----------|-------------|--------|
| `GET` | `/api/users` | Get all users | ADMIN |
| `GET` | `/api/users/{id}` | Get user by ID | ADMIN |
| `GET` | `/api/users/me` | Get current user profile | USER |
| `PUT` | `/api/users/me` | Update own profile | USER |
| `PUT` | `/api/users/{id}` | Update any user | ADMIN |
| `DELETE` | `/api/users/{id}` | Delete user | ADMIN |

### 🏪 Restaurant APIs
| Method | Endpoint | Description | Access |
|--------|----------|-------------|--------|
| `POST` | `/api/restaurants` | Create restaurant | ADMIN |
| `GET` | `/api/restaurants` | Get all restaurants | ALL |
| `GET` | `/api/restaurants/{id}` | Get restaurant details | ALL |

**Create Restaurant:**
```json
{
  "name": "Pizza Palace",
  "address": "456 Food Street",
  "phoneNumber": "9876543210",
  "cuisineType": "Italian"
}
```

### 🍔 Menu Item APIs
| Method | Endpoint | Description | Access |
|--------|----------|-------------|--------|
| `POST` | `/api/menu-items` | Add menu item | ADMIN |
| `GET` | `/api/menu-items` | Get all menu items | ALL |
| `GET` | `/api/menu-items/{id}` | Get item details | ALL |
| `GET` | `/api/menu-items/restaurant/{restaurantId}` | Get restaurant menu | ALL |
| `PUT` | `/api/menu-items/{id}` | Update menu item | ADMIN |
| `DELETE` | `/api/menu-items/{id}` | Delete menu item | ADMIN |

**Menu Item Example:**
```json
{
  "name": "Margherita Pizza",
  "description": "Classic Italian pizza with fresh mozzarella",
  "price": 299.99,
  "category": "Pizza",
  "available": true,
  "restaurantId": 1
}
```

### 🛒 Cart APIs
| Method | Endpoint | Description | Access |
|--------|----------|-------------|--------|
| `POST` | `/api/cart/add` | Add item to cart | USER |
| `GET` | `/api/cart` | Get user's cart | USER |
| `PUT` | `/api/cart/items/{cartItemId}` | Update cart item quantity | USER |
| `DELETE` | `/api/cart/items/{cartItemId}` | Remove item from cart | USER |
| `DELETE` | `/api/cart/clear` | Clear entire cart | USER |

**Add to Cart:**
```json
{
  "menuItemId": 1,
  "quantity": 2
}
```

**Cart Response:**
```json
{
  "id": 1,
  "userId": 5,
  "items": [
    {
      "id": 1,
      "menuItemName": "Margherita Pizza",
      "quantity": 2,
      "price": 299.99,
      "subtotal": 599.98
    }
  ],
  "totalPrice": 599.98
}
```

### 📦 Order APIs
| Method | Endpoint | Description | Access |
|--------|----------|-------------|--------|
| `POST` | `/api/orders` | Place order from cart | USER |
| `GET` | `/api/orders/me` | Get my order history | USER |
| `GET` | `/api/orders` | Get all orders | ADMIN |
| `PUT` | `/api/orders/{orderId}/status` | Update order status | ADMIN |

**Order Response:**
```json
{
  "id": 1,
  "userId": 5,
  "restaurantId": 1,
  "status": "PENDING",
  "totalAmount": 599.98,
  "orderDate": "2026-02-02T15:30:00",
  "items": [
    {
      "menuItemName": "Margherita Pizza",
      "quantity": 2,
      "price": 299.99
    }
  ]
}
```

### 💳 Payment APIs
| Method | Endpoint | Description | Access |
|--------|----------|-------------|--------|
| `POST` | `/api/payments/create-order` | Create Razorpay order | USER |
| `GET` | `/api/payments/order/{orderId}` | Get payment details | USER/ADMIN |

## 🚀 Quick Start

### 🔧 Prerequisites
- **Java 17+** installed
- **MySQL 8.0+** running
- **Maven 3.6+** installed
- **Git** for version control

### 📦 Installation & Setup

#### 1️⃣ Clone the Repository
```bash
git clone https://github.com/yourusername/food-delivery-backend.git
cd food-delivery-backend
```

#### 2️⃣ Configure Database
Create a MySQL database:
```sql
CREATE DATABASE food_delivery_db;
```

Update `src/main/resources/application.properties`:
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/food_delivery_db
spring.datasource.username=your_mysql_username
spring.datasource.password=your_mysql_password
```

#### 3️⃣ Configure Razorpay (Optional)
Get your Razorpay credentials from [Razorpay Dashboard](https://dashboard.razorpay.com/) and update:
```properties
razorpay.key-id=your_razorpay_key_id
razorpay.key-secret=your_razorpay_key_secret
```

#### 4️⃣ Build the Project
```bash
# Clean and build
mvn clean install

# Skip tests (if needed)
mvn clean install -DskipTests
```

#### 5️⃣ Run the Application
```bash
# Using Maven
mvn spring-boot:run

# Or run the JAR
java -jar target/backend-0.0.1-SNAPSHOT.jar
```

🎉 **Application will be running at:** `http://localhost:8081`

### 📚 Access API Documentation
Once the application is running, access the interactive API documentation:

**Swagger UI:** `http://localhost:8081/swagger-ui.html`

## 🧪 Testing the APIs

### Using cURL

**1. Register a User**
```bash
curl -X POST http://localhost:8081/api/auth/register \
  -H "Content-Type: application/json" \
  -d '{
    "name": "John Doe",
    "email": "john@example.com",
    "password": "password123",
    "phoneNumber": "1234567890",
    "address": "123 Main St"
  }'
```

**2. Login**
```bash
curl -X POST http://localhost:8081/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "email": "john@example.com",
    "password": "password123"
  }'
```

**3. Get All Restaurants (with JWT)**
```bash
curl -X GET http://localhost:8081/api/restaurants \
  -H "Authorization: Bearer YOUR_JWT_TOKEN"
```

**4. Add Item to Cart**
```bash
curl -X POST http://localhost:8081/api/cart/add \
  -H "Authorization: Bearer YOUR_JWT_TOKEN" \
  -H "Content-Type: application/json" \
  -d '{
    "menuItemId": 1,
    "quantity": 2
  }'
```

**5. Place Order**
```bash
curl -X POST http://localhost:8081/api/orders \
  -H "Authorization: Bearer YOUR_JWT_TOKEN"
```

### Using Postman

1. Import the API collection from Swagger
2. Set up environment variables:
   - `BASE_URL`: `http://localhost:8081`
   - `JWT_TOKEN`: (obtained from login)
3. Use `{{BASE_URL}}` and `{{JWT_TOKEN}}` in requests

## 🏗️ Project Structure

```
food-delivery-backend/
├── src/
│   ├── main/
│   │   ├── java/com/fooddelivery/backend/
│   │   │   ├── config/                    # 🔧 Configuration classes
│   │   │   │   ├── JwtUtil.java          # JWT token generation & validation
│   │   │   │   ├── SecurityConfig.java   # Spring Security configuration
│   │   │   │   ├── PasswordConfig.java   # Password encoder bean
│   │   │   │   ├── RazorpayConfig.java   # Razorpay client setup
│   │   │   │   ├── OpenApiConfig.java    # Swagger/OpenAPI config
│   │   │   │   └── UserDetailConfig.java # User details service
│   │   │   │
│   │   │   ├── controller/                # 📡 REST Controllers
│   │   │   │   ├── AuthController.java   # Authentication endpoints
│   │   │   │   ├── UserController.java   # User management
│   │   │   │   ├── RestaurantController.java
│   │   │   │   ├── MenuItemController.java
│   │   │   │   ├── CartController.java
│   │   │   │   ├── OrderController.java
│   │   │   │   └── PaymentController.java
│   │   │   │
│   │   │   ├── service/                   # ⚙️ Business Logic Layer
│   │   │   │   ├── AuthService.java
│   │   │   │   ├── UserService.java
│   │   │   │   ├── RestaurantService.java
│   │   │   │   ├── MenuItemService.java
│   │   │   │   ├── CartService.java
│   │   │   │   ├── OrderService.java
│   │   │   │   └── PaymentService.java
│   │   │   │
│   │   │   ├── repository/                # 💾 Data Access Layer
│   │   │   │   ├── UserRepository.java
│   │   │   │   ├── RestaurantRepository.java
│   │   │   │   ├── MenuItemRepository.java
│   │   │   │   ├── CartRepository.java
│   │   │   │   ├── CartItemRepository.java
│   │   │   │   ├── OrderRepository.java
│   │   │   │   └── PaymentRepository.java
│   │   │   │
│   │   │   ├── entity/                    # 📋 JPA Entities
│   │   │   │   ├── User.java
│   │   │   │   ├── Role.java             # Enum: USER, ADMIN
│   │   │   │   ├── Restaurant.java
│   │   │   │   ├── MenuItem.java
│   │   │   │   ├── Cart.java
│   │   │   │   ├── CartItem.java
│   │   │   │   ├── Order.java
│   │   │   │   ├── OrderItem.java
│   │   │   │   ├── OrderStatus.java      # Enum: PENDING, CONFIRMED, etc.
│   │   │   │   ├── Payment.java
│   │   │   │   ├── PaymentStatus.java    # Enum: PENDING, COMPLETED, FAILED
│   │   │   │   └── PaymentMethod.java
│   │   │   │
│   │   │   ├── dto/                       # 📦 Data Transfer Objects
│   │   │   │   ├── LoginRequestDto.java
│   │   │   │   ├── LoginResponseDto.java
│   │   │   │   ├── UserRequestDto.java
│   │   │   │   ├── UserResponseDto.java
│   │   │   │   ├── RestaurantRequestDto.java
│   │   │   │   ├── RestaurantResponseDto.java
│   │   │   │   ├── MenuItemRequestDto.java
│   │   │   │   ├── MenuItemResponseDto.java
│   │   │   │   ├── CartResponseDto.java
│   │   │   │   ├── AddToCartRequestDto.java
│   │   │   │   ├── OrderResponseDto.java
│   │   │   │   └── PaymentOrderRequestDto.java
│   │   │   │
│   │   │   ├── exceptions/                # ⚠️ Exception Handling
│   │   │   │   ├── GlobalExceptionHandler.java
│   │   │   │   ├── ResourceNotFoundException.java
│   │   │   │   ├── DuplicateResourceException.java
│   │   │   │   ├── EmailAlreadyExistsException.java
│   │   │   │   └── ErrorResponse.java
│   │   │   │
│   │   │   └── FoodDeliveryApplication.java  # 🚀 Main Application
│   │   │
│   │   └── resources/
│   │       └── application.properties     # ⚙️ Configuration
│   │
│   └── test/                              # 🧪 Unit & Integration Tests
│       └── java/com/fooddelivery/backend/
│
├── pom.xml                                # 📦 Maven Dependencies
├── mvnw                                   # Maven Wrapper (Unix)
├── mvnw.cmd                               # Maven Wrapper (Windows)
└── README.md                              # 📖 Documentation
```

## 🛠️ Technology Stack

### Core Framework
- **Spring Boot 3.5.9**: Modern Java framework for building production-ready applications
- **Spring Data JPA**: Simplified data access with Hibernate ORM
- **Spring Security**: Comprehensive security framework
- **Spring Web**: RESTful web services

### Database
- **MySQL 8.0**: Relational database for persistent storage
- **Hibernate**: ORM for object-relational mapping

### Security
- **JWT (JSON Web Tokens)**: Stateless authentication
- **BCrypt**: Password hashing algorithm
- **Spring Security**: Authentication and authorization

### Validation & Documentation
- **Bean Validation**: Input validation with annotations
- **SpringDoc OpenAPI**: Auto-generated API documentation
- **Swagger UI**: Interactive API explorer

### Payment Integration
- **Razorpay Java SDK**: Payment gateway integration

### Build & Development
- **Maven**: Dependency management and build automation
- **Lombok**: Reduce boilerplate code
- **Jackson**: JSON serialization/deserialization

## ⚙️ Configuration

### Application Properties

| Property | Default | Description |
|----------|---------|-------------|
| `server.port` | 8081 | Application server port |
| `spring.datasource.url` | jdbc:mysql://localhost:3306/food_delivery_db | Database connection URL |
| `spring.jpa.hibernate.ddl-auto` | update | Hibernate DDL mode |
| `spring.jpa.show-sql` | true | Show SQL queries in logs |
| `razorpay.key-id` | - | Razorpay API key ID |
| `razorpay.key-secret` | - | Razorpay API secret |

### Security Configuration

- **JWT Secret**: Configured in `JwtUtil.java` (should be externalized in production)
- **Token Expiration**: 24 hours (configurable)
- **Password Strength**: Enforced through validation annotations

## 🔒 Security Features

- ✅ **JWT Authentication**: Stateless token-based authentication
- ✅ **Password Encryption**: BCrypt hashing with salt
- ✅ **Role-Based Access Control**: Fine-grained permissions
- ✅ **CORS Configuration**: Cross-origin resource sharing
- ✅ **Input Validation**: Prevent injection attacks
- ✅ **SQL Injection Protection**: Parameterized queries via JPA
- ✅ **XSS Protection**: Input sanitization
- ✅ **Secure Headers**: Security headers configuration

## 📈 Database Schema

### Key Entities & Relationships

```
User (1) ──────── (1) Cart
  │                  │
  │                  │
  │                  └── (N) CartItem ──── (1) MenuItem
  │
  └── (N) Order ──── (N) OrderItem ──── (1) MenuItem
        │
        └── (1) Payment

Restaurant (1) ──── (N) MenuItem
```

### Order Status Flow
```
PENDING → CONFIRMED → PREPARING → OUT_FOR_DELIVERY → DELIVERED
   │
   └──→ CANCELLED
```

### Payment Status Flow
```
PENDING → COMPLETED
   │
   └──→ FAILED
```

## 🚀 Deployment

### Production Checklist

- [ ] Update `application.properties` with production database credentials
- [ ] Externalize JWT secret to environment variables
- [ ] Configure Razorpay production keys
- [ ] Set `spring.jpa.hibernate.ddl-auto=validate` (never use `update` in production)
- [ ] Enable HTTPS/SSL
- [ ] Configure proper CORS origins
- [ ] Set up database connection pooling
- [ ] Configure logging levels
- [ ] Set up monitoring and health checks
- [ ] Implement rate limiting
- [ ] Set up database backups

### Environment Variables (Recommended)
```bash
export DB_URL=jdbc:mysql://prod-db:3306/food_delivery_db
export DB_USERNAME=prod_user
export DB_PASSWORD=secure_password
export JWT_SECRET=your_secure_jwt_secret_key
export RAZORPAY_KEY_ID=rzp_live_xxxxx
export RAZORPAY_KEY_SECRET=xxxxx
```

### Docker Deployment (Optional)
```dockerfile
FROM openjdk:17-jdk-slim
WORKDIR /app
COPY target/backend-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8081
ENTRYPOINT ["java", "-jar", "app.jar"]
```

```bash
# Build Docker image
docker build -t food-delivery-backend .

# Run container
docker run -p 8081:8081 \
  -e DB_URL=jdbc:mysql://host.docker.internal:3306/food_delivery_db \
  -e DB_USERNAME=root \
  -e DB_PASSWORD=root123 \
  food-delivery-backend
```

## 🧪 Testing

### Run Tests
```bash
# Run all tests
mvn test

# Run with coverage
mvn test jacoco:report

# Run specific test class
mvn test -Dtest=UserServiceTest
```

### Test Coverage
- Unit tests for service layer
- Integration tests for repositories
- Controller tests with MockMvc

## 🐛 Troubleshooting

### Common Issues

**1. Database Connection Failed**
```
Error: Communications link failure
Solution: Ensure MySQL is running and credentials are correct
```

**2. JWT Token Invalid**
```
Error: 401 Unauthorized
Solution: Check token expiration and secret key configuration
```

**3. Port Already in Use**
```
Error: Port 8081 is already in use
Solution: Change port in application.properties or kill the process
```

**4. Razorpay Integration Issues**
```
Error: Invalid API key
Solution: Verify Razorpay credentials in application.properties
```

## 📝 API Response Format

### Success Response
```json
{
  "id": 1,
  "name": "John Doe",
  "email": "john@example.com"
}
```

### Error Response
```json
{
  "timestamp": "2026-02-02T15:30:00",
  "status": 400,
  "error": "Bad Request",
  "message": "Email already exists",
  "path": "/api/auth/register"
}
```

## 🤝 Contributing

Contributions are welcome! Please follow these steps:

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/amazing-feature`)
3. Commit your changes (`git commit -m 'Add some amazing feature'`)
4. Push to the branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request

### Coding Standards
- Follow Java naming conventions
- Write meaningful commit messages
- Add unit tests for new features
- Update documentation as needed
- Use Lombok annotations to reduce boilerplate


## 🙏 Acknowledgments

- Spring Boot team for the amazing framework
- Razorpay for payment gateway integration
- OpenAPI/Swagger for API documentation tools
- MySQL for reliable database management

---

<div align="center">

**⭐ Star this repository if you find it helpful!**

Made with ❤️ using Spring Boot

</div>
