# Android Login App

Application demonstrating authentication with local database storage, built using Jetpack Compose and following clean architecture principles.

## Features

- **User Authentication**
  - Login with email and password
  - Registration for new users (With no password confirmation, sorry:))
  - Persistent login state (users stay logged in)

- **Data Management**
  - Local data storage using Room / DataStore
  - Logged in user data persistence
  - Efficient data access patterns

- **User Interface**
  - UI built with Jetpack Compose
  - Responsive design with loading states
  - Error handling with user-friendly messages

## Architecture

This application follows Clean Architecture principles with a clear separation of concerns:

### Layers

1. **Presentation Layer**
   - UI components
   - ViewModels
   - Navigation

2. **Domain Layer**
   - Use Cases
   - Domain Models
   - Repositories Interfaces
   - Validators

3. **Data Layer**
   - Repository Implementations
   - Data Sources
   - Data Models
   - Mappers

### Key Components

- **Dependency Injection**: Hilt for managing dependencies
- **Database**: Room for local data storage
- **UI Framework**: Jetpack Compose for modern UI development
- **Navigation**: Navigation Compose for screen navigation
- **Coroutines & Flow**: For asynchronous operations and reactive programming
- **Timber**: For logging and debugging

## Implementation Details

### Authentication Flow

1. **Splash Screen**: Checks for existing authentication state
2. **Login/Register Screen**: User authentication or registration
3. **Home Screen**: Protected content for authenticated users

### Data Persistence

- User credentials stored in Room database
- User ID stored in DataStore for persistent authentication

### Validation

- Email format validation
- Password strength requirements
- User-friendly error messages

### Testing Levels

1. **Unit Tests**
   - Located in `app/src/test/`
   - Test individual components in isolation
   - Use JUnit and MockK for mocking dependencies
   - Example: `EmailValidatorTest`, `PasswordValidatorTest`

2. **UI Tests**
   - Not a big fan of UI tests and did not want to add some LLM generated ones that I could not fully understand and describe, partially covered by Screenshot testing.

3. **Screenshot Tests**
   - Located in `app/src/screenshotTest/`
   - Verify visual appearance of UI components
   - Use Android Compose Screenshot testing library
   - Example: `AuthScreenScreenshotTest`


## Security Considerations

### Current Implementation Limitations

This demo application intentionally does not implement comprehensive security measures for the following reasons:

1. **Backend Dependency**: In real-world applications, user authentication would primarily rely on a secure backend service rather than local storage. This demo focuses on the client-side architecture and UI implementation.

### Real-World Security Implementation

In a production environment, the following security measures would be essential:

1. **Backend Authentication**:
   - User credentials would be validated against a secure backend service
   - Authentication tokens (JWT, OAuth) would be used instead of storing passwords locally

2. **Local Data Protection**:
   - **Android Keystore**: Sensitive data would be encrypted using the Android Keystore system, which provides hardware-backed security when available
   - **Root Detection**: The app would check for device root status and potentially restrict functionality

3. **User Persistence Strategy**:
   - Store only a secure token or user ID in DataStore (never passwords)
   - Implement token refresh mechanisms

4. **Additional Security Measures**:
   - ProGuard/R8 obfuscation for release builds

## Getting Started

### Prerequisites

- Android Studio Arctic Fox or newer
- JDK 11 or newer
- Android SDK 24 or newer

### Installation

1. Clone the repository
   ```
   git clone https://github.com/EdgarsLocmelis/Login.git
   ```

2. Open the project in Android Studio

3. Build and run the application
