# 🍳 ReciMe - Recipe Management API

A Spring Boot REST API for managing and searching recipes with PostgreSQL database support.

## Some Technical Decisions:

### 1. **PostgreSQL for Ingredients as Arrays**
- Ingredients are stored as `text[]` arrays in PostgreSQL for flexible, efficient storage and querying.

### 2. **Native SQL for Advanced Search**
- Ingredient search uses a native SQL query with `unnest` and `LIKE` for partial, case-insensitive matching.
- AND logic is enforced so all search terms must be present in the recipe's ingredients.
- This approach is more efficient and expressive than JPA Criteria API for array operations. Also it's less code haha.

### 3. **@ModelAttribute for Search DTO**
- The search endpoint uses `@ModelAttribute` to automatically bind query parameters to a DTO, supporting multiple values and type conversion.
- Simplifies controller code and improves maintainability.

### 4. **Flyway for Database Migrations**
- Ensures consistent schema management.

---

## 🛠 Tech Stack

- **Backend**: Spring Boot 3.5.0
- **Database**: PostgreSQL 14.17
- **Migration**: Flyway
- **Build Tool**: Maven
- **Language**: Java 17

## 📋 Prerequisites

- Java 17
- Maven
- Docker & Docker Compose
- Git

## 🚀 Installation

1. **Clone the repository**
   ```bash
   git clone <repository-url>
   cd ReciMe
   ```

2. **Start the database**
   ```bash
   ./scripts/db.sh start
   ```

3. **Run the application**
   ```bash
   mvn spring-boot:run
   ```

## 🗄 Database Setup

### Using the provided script:
```bash
# Start database
./scripts/db.sh start

# Check status
./scripts/db.sh status

# View logs
./scripts/db.sh logs

# Stop database
./scripts/db.sh stop

# Recreate database(Deletes all the data)
./scripts/db.sh recreate
```

### Manual setup:
```bash
docker compose up -d
```

## 🏃‍♂️ Running the Application
```bash
mvn spring-boot:run
```

## 📚 API Documentation

### Base URL
```
http://localhost:8080/api/v1/recipes
```

### Endpoints

#### 1. Get All Recipes
```http
GET /api/v1/recipes
```

#### 2. Get Recipe by ID
```http
GET /api/v1/recipes/{id}
```

#### 3. Get Recipe by Name
```http
GET /api/v1/recipes/recipe?name={recipeName}
```

#### 4. Search Recipes
```http
GET /api/v1/recipes/search?{parameters}
```

**Search Parameters:**
- `vegetarian` (boolean): Filter by vegetarian status
- `servings` (integer): Filter by number of servings
- `includedIngredients` (string): Ingredients that must be present (supports multiple)
- `excludedIngredients` (string): Ingredients that must not be present (supports multiple)
- `instructionSearch` (string): Search in instructions text

#### 5. Create Recipe
```http
POST /api/v1/recipes
Content-Type: application/json
```

#### 6. Update Recipe
```http
PUT /api/v1/recipes/{id}
Content-Type: application/json
```

#### 7. Delete Recipe
```http
DELETE /api/v1/recipes/{id}
```

## 💡 Usage Examples

### Recipe Creation
```json
POST /api/v1/recipes
{
  "title": "Spaghetti Carbonara",
  "description": "A classic Italian pasta dish with eggs, cheese, and pancetta",
  "ingredients": [
    "400g spaghetti",
    "200g pancetta or guanciale",
    "4 large eggs",
    "100g Pecorino Romano cheese",
    "100g Parmigiano Reggiano",
    "Black pepper",
    "Salt"
  ],
  "instructions": "1. Cook pasta in salted water until al dente\n2. Meanwhile, cook pancetta until crispy\n3. Beat eggs with grated cheese and pepper\n4. Drain pasta, add to pancetta pan\n5. Remove from heat, add egg mixture and toss quickly\n6. Serve immediately with extra cheese and pepper",
  "vegetarian": false,
  "servings": 4
}
```

### Search Examples

#### Basic Search
```http
GET /api/v1/recipes/search?vegetarian=true
GET /api/v1/recipes/search?servings=4
GET /api/v1/recipes/search?instructionSearch=microwave
```

#### Ingredient Search (Multiple ways)
```http
GET /api/v1/recipes/search?includedIngredients=chicken,rice
GET /api/v1/recipes/search?excludedIngredients=onion&excludedIngredients=garlic
```

#### Complex Search
```http
GET /api/v1/recipes/search?vegetarian=true&servings=2&includedIngredients=quinoa&excludedIngredients=onion&instructionSearch=cook
```

### Recipe Update
```json
PUT /api/v1/recipes/1
{
  "title": "Updated Spaghetti Carbonara",
  "description": "An improved version of the classic Italian pasta dish",
  "ingredients": [
    "400g spaghetti",
    "200g pancetta or guanciale",
    "4 large eggs",
    "100g Pecorino Romano cheese",
    "100g Parmigiano Reggiano",
    "Black pepper",
    "Salt",
    "Fresh parsley (optional)"
  ],
  "instructions": "1. Cook pasta in salted water until al dente\n2. Meanwhile, cook pancetta until crispy\n3. Beat eggs with grated cheese and pepper\n4. Drain pasta, add to pancetta pan\n5. Remove from heat, add egg mixture and toss quickly\n6. Garnish with fresh parsley and serve immediately",
  "vegetarian": false,
  "servings": 4
}
```
