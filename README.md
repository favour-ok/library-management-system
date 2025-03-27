# User Verification and Management System

## Project Overview

This is a Java-based web application that demonstrates a comprehensive user management system with the following key features:
- Random user generation
- User verification through multiple API integrations
- Nationality and gender validation
- Database persistence
- Flexible user retrieval with advanced sorting and pagination

## Key Features

### API Integrations
1. **Random User Generation**: Fetches user details from RandomUser.me API
2. **Nationality Validation**: Uses Nationalize.io API to verify user nationality
3. **Gender Verification**: Utilizes Genderize.io API to confirm user gender

### User Verification Process
- Validates user nationality and gender against API responses
- Assigns verification status:
  - `VERIFIED`: When nationality and gender match
  - `TO_BE_VERIFIED`: When criteria don't match

### REST Endpoints

#### POST `/users`
- Creates new users
- Request Parameters:
  - `size`: Number of users to create (1-5)
- Returns list of saved users

#### GET `/users`
- Retrieves users with advanced filtering and sorting
- Query Parameters:
  - `sortType`: Sort by Name or Age
  - `sortOrder`: EVEN or ODD sorting
  - `limit`: Number of users to return (1-5)
  - `offset`: Number of users to skip

## Technical Highlights

### Design Patterns
- Singleton Pattern for Validators
- Factory Pattern for Validator Instantiation
- Strategy Pattern for Sorting
- Executor Framework for Parallel API Calls

### Validation
- Custom Validators:
  - `NumericValidator`: For numeric parameters
  - `EnglishAlphabetsValidator`: For character-based parameters

### Technology Stack
- Java 8+
- Spring Boot
- WebClient for API calls
- JUnit & Mockito for testing

### API Configuration
- Configurable timeouts for external API calls
- Parallel execution of nationality and gender verification

## Testing
- Comprehensive JUnit test cases
- Mockito for mocking API responses
- Coverage for POST and GET endpoints

## Getting Started

### Prerequisites
- Java 11+
- Maven
- Spring Boot
- Internet connection (for external API calls)

### Installation
1. Clone the repository
2. Run `mvn clean install`
3. Start the application

## Contribution
Please read the coding guidelines in the assignment document before contributing.

## Coding Best Practices
- SOLID design principles
- Use of Java 8+ Stream API
- Proper naming conventions
- Error handling with custom error messages
