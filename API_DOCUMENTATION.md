# API Documentation

This document provides detailed information about the AI Prompt Management Service API endpoints.

## Base URL
```
http://localhost:8080
```

## Authentication
Currently, the API does not require authentication. This should be added for production use.

---

## Prompt Management Endpoints

### 1. Create a New Prompt

**Endpoint:** `POST /api/prompts`

**Description:** Creates a new AI system prompt with version 1.

**Request Body:**
```json
{
  "name": "customer-service-bot",
  "content": "You are a professional customer service AI assistant. Always be helpful, polite, and patient.",
  "category": "customer-service",
  "active": true
}
```

**Response (201 Created):**
```json
{
  "id": 1,
  "name": "customer-service-bot",
  "content": "You are a professional customer service AI assistant. Always be helpful, polite, and patient.",
  "category": "customer-service",
  "version": 1,
  "active": true,
  "createdAt": "2024-02-08T15:30:00",
  "updatedAt": "2024-02-08T15:30:00"
}
```

**Error Responses:**
- `409 Conflict` - Prompt with the same name already exists
- `400 Bad Request` - Invalid request body

**cURL Example:**
```bash
curl -X POST http://localhost:8080/api/prompts \
  -H "Content-Type: application/json" \
  -d '{
    "name": "customer-service-bot",
    "content": "You are a professional customer service AI assistant.",
    "category": "customer-service",
    "active": true
  }'
```

---

### 2. Update a Prompt (Create New Version)

**Endpoint:** `PUT /api/prompts/{name}`

**Description:** Updates an existing prompt by creating a new version. The old version is automatically marked as inactive.

**Path Parameters:**
- `name` (string) - The unique name of the prompt to update

**Request Body:**
```json
{
  "content": "You are a professional customer service AI assistant. Always be helpful, polite, patient, and empathetic.",
  "category": "customer-service",
  "active": true
}
```

**Response (200 OK):**
```json
{
  "id": 2,
  "name": "customer-service-bot",
  "content": "You are a professional customer service AI assistant. Always be helpful, polite, patient, and empathetic.",
  "category": "customer-service",
  "version": 2,
  "active": true,
  "createdAt": "2024-02-08T15:35:00",
  "updatedAt": "2024-02-08T15:35:00"
}
```

**Error Responses:**
- `404 Not Found` - Prompt with the specified name does not exist
- `400 Bad Request` - Invalid request body

**cURL Example:**
```bash
curl -X PUT http://localhost:8080/api/prompts/customer-service-bot \
  -H "Content-Type: application/json" \
  -d '{
    "content": "Updated content here",
    "active": true
  }'
```

---

### 3. Get Prompt by Name

**Endpoint:** `GET /api/prompts/{name}`

**Description:** Retrieves the currently active version of a prompt by its name.

**Path Parameters:**
- `name` (string) - The unique name of the prompt

**Response (200 OK):**
```json
{
  "id": 2,
  "name": "customer-service-bot",
  "content": "You are a professional customer service AI assistant. Always be helpful, polite, patient, and empathetic.",
  "category": "customer-service",
  "version": 2,
  "active": true,
  "createdAt": "2024-02-08T15:35:00",
  "updatedAt": "2024-02-08T15:35:00"
}
```

**Error Responses:**
- `404 Not Found` - No active prompt found with the specified name

**cURL Example:**
```bash
curl http://localhost:8080/api/prompts/customer-service-bot
```

---

### 4. Get Prompt by ID

**Endpoint:** `GET /api/prompts/id/{id}`

**Description:** Retrieves a specific prompt by its unique ID (can be any version, active or inactive).

**Path Parameters:**
- `id` (long) - The unique ID of the prompt

**Response (200 OK):**
```json
{
  "id": 1,
  "name": "customer-service-bot",
  "content": "You are a professional customer service AI assistant. Always be helpful, polite, and patient.",
  "category": "customer-service",
  "version": 1,
  "active": false,
  "createdAt": "2024-02-08T15:30:00",
  "updatedAt": "2024-02-08T15:35:00"
}
```

**Error Responses:**
- `404 Not Found` - Prompt with the specified ID does not exist

**cURL Example:**
```bash
curl http://localhost:8080/api/prompts/id/1
```

---

### 5. Get All Active Prompts

**Endpoint:** `GET /api/prompts`

**Description:** Retrieves all currently active prompts in the system.

**Response (200 OK):**
```json
[
  {
    "id": 2,
    "name": "customer-service-bot",
    "content": "You are a professional customer service AI assistant. Always be helpful, polite, patient, and empathetic.",
    "category": "customer-service",
    "version": 2,
    "active": true,
    "createdAt": "2024-02-08T15:35:00",
    "updatedAt": "2024-02-08T15:35:00"
  },
  {
    "id": 3,
    "name": "sales-bot",
    "content": "You are a sales AI assistant. Be persuasive and knowledgeable.",
    "category": "sales",
    "version": 1,
    "active": true,
    "createdAt": "2024-02-08T16:00:00",
    "updatedAt": "2024-02-08T16:00:00"
  }
]
```

**cURL Example:**
```bash
curl http://localhost:8080/api/prompts
```

---

### 6. Get Prompts by Category

**Endpoint:** `GET /api/prompts/category/{category}`

**Description:** Retrieves all active prompts in a specific category.

**Path Parameters:**
- `category` (string) - The category name

**Response (200 OK):**
```json
[
  {
    "id": 2,
    "name": "customer-service-bot",
    "content": "You are a professional customer service AI assistant. Always be helpful, polite, patient, and empathetic.",
    "category": "customer-service",
    "version": 2,
    "active": true,
    "createdAt": "2024-02-08T15:35:00",
    "updatedAt": "2024-02-08T15:35:00"
  }
]
```

**cURL Example:**
```bash
curl http://localhost:8080/api/prompts/category/customer-service
```

---

### 7. Get Prompt Version History

**Endpoint:** `GET /api/prompts/{name}/versions`

**Description:** Retrieves all versions of a specific prompt, ordered by version number (descending).

**Path Parameters:**
- `name` (string) - The unique name of the prompt

**Response (200 OK):**
```json
[
  {
    "id": 2,
    "name": "customer-service-bot",
    "content": "You are a professional customer service AI assistant. Always be helpful, polite, patient, and empathetic.",
    "category": "customer-service",
    "version": 2,
    "active": true,
    "createdAt": "2024-02-08T15:35:00",
    "updatedAt": "2024-02-08T15:35:00"
  },
  {
    "id": 1,
    "name": "customer-service-bot",
    "content": "You are a professional customer service AI assistant. Always be helpful, polite, and patient.",
    "category": "customer-service",
    "version": 1,
    "active": false,
    "createdAt": "2024-02-08T15:30:00",
    "updatedAt": "2024-02-08T15:35:00"
  }
]
```

**Error Responses:**
- `404 Not Found` - No versions found for the specified prompt name

**cURL Example:**
```bash
curl http://localhost:8080/api/prompts/customer-service-bot/versions
```

---

### 8. Delete Prompt (Deactivate)

**Endpoint:** `DELETE /api/prompts/{name}`

**Description:** Deactivates the currently active version of a prompt. The prompt is not physically deleted but marked as inactive.

**Path Parameters:**
- `name` (string) - The unique name of the prompt to deactivate

**Response (204 No Content):**
No response body.

**Error Responses:**
- `404 Not Found` - No active prompt found with the specified name

**cURL Example:**
```bash
curl -X DELETE http://localhost:8080/api/prompts/customer-service-bot
```

---

## AI Integration Endpoints

These endpoints are specifically designed for AI integration services to fetch current system prompts.

### 1. Get Current Prompt for AI Services

**Endpoint:** `GET /api/integration/prompts/{name}`

**Description:** Retrieves the currently active version of a prompt. This is the recommended endpoint for AI services to fetch system prompts.

**Path Parameters:**
- `name` (string) - The unique name of the prompt

**Response (200 OK):**
```json
{
  "id": 2,
  "name": "customer-service-bot",
  "content": "You are a professional customer service AI assistant. Always be helpful, polite, patient, and empathetic.",
  "category": "customer-service",
  "version": 2,
  "active": true,
  "createdAt": "2024-02-08T15:35:00",
  "updatedAt": "2024-02-08T15:35:00"
}
```

**Error Responses:**
- `404 Not Found` - No active prompt found with the specified name

**cURL Example:**
```bash
curl http://localhost:8080/api/integration/prompts/customer-service-bot
```

**Usage in AI Services:**
```java
// Example Java code for AI integration service
RestTemplate restTemplate = new RestTemplate();
String url = "http://prompt-service:8080/api/integration/prompts/customer-service-bot";
PromptResponse prompt = restTemplate.getForObject(url, PromptResponse.class);
String systemPrompt = prompt.getContent();
```

---

### 2. Health Check

**Endpoint:** `GET /api/integration/health`

**Description:** Simple health check endpoint to verify the service is running.

**Response (200 OK):**
```
AI Integration Service is running
```

**cURL Example:**
```bash
curl http://localhost:8080/api/integration/health
```

---

## Error Response Format

All error responses follow this format:

```json
{
  "status": 404,
  "message": "Prompt with name 'non-existent' not found",
  "timestamp": "2024-02-08T15:30:00"
}
```

### Common HTTP Status Codes

- `200 OK` - Request successful
- `201 Created` - Resource created successfully
- `204 No Content` - Request successful, no content to return
- `400 Bad Request` - Invalid request parameters or body
- `404 Not Found` - Resource not found
- `409 Conflict` - Resource already exists
- `500 Internal Server Error` - Server error

---

## Data Models

### PromptRequest (Create)
```json
{
  "name": "string (required)",
  "content": "string (required)",
  "category": "string (required)",
  "active": "boolean (required)"
}
```

### UpdatePromptRequest
```json
{
  "content": "string (required)",
  "category": "string (optional)",
  "active": "boolean (optional)"
}
```

### PromptResponse
```json
{
  "id": "long",
  "name": "string",
  "content": "string",
  "category": "string",
  "version": "integer",
  "active": "boolean",
  "createdAt": "datetime",
  "updatedAt": "datetime"
}
```

---

## Use Case Example: Chatbot Prompt Management

### Scenario
You have a chatbot application that needs to use the latest system prompt. Here's how to use this service:

#### 1. Create Initial Prompt
```bash
curl -X POST http://localhost:8080/api/prompts \
  -H "Content-Type: application/json" \
  -d '{
    "name": "chatbot-v1",
    "content": "You are a helpful chatbot. Be friendly.",
    "category": "chatbot",
    "active": true
  }'
```

#### 2. Your Chatbot Service Fetches the Prompt
```bash
# On startup or periodically
curl http://localhost:8080/api/integration/prompts/chatbot-v1
```

#### 3. Update the Prompt (Based on User Feedback)
```bash
curl -X PUT http://localhost:8080/api/prompts/chatbot-v1 \
  -H "Content-Type: application/json" \
  -d '{
    "content": "You are a helpful chatbot. Be friendly, concise, and informative.",
    "active": true
  }'
```

#### 4. Chatbot Service Automatically Gets Updated Prompt
```bash
# Same endpoint, now returns version 2
curl http://localhost:8080/api/integration/prompts/chatbot-v1
```

---

## Best Practices

1. **Use Descriptive Names**: Use clear, descriptive names for prompts (e.g., `customer-support-bot`, `sales-assistant`)

2. **Categorize Prompts**: Group related prompts using categories for better organization

3. **Version Control**: The service automatically versions your prompts. Use the version history endpoint to track changes

4. **AI Integration**: Use the `/api/integration/prompts/{name}` endpoint in your AI services for better separation of concerns

5. **Regular Updates**: Update prompts based on user feedback and performance metrics

6. **Testing**: Test prompt changes in a development environment before deploying to production

7. **Monitoring**: Monitor which prompts are being used most frequently and their effectiveness

---

## H2 Database Console

For development and debugging, you can access the H2 database console at:
```
http://localhost:8080/h2-console
```

**Connection Details:**
- JDBC URL: `jdbc:h2:mem:promptdb`
- Username: `sa`
- Password: (leave empty)

---

## Future Enhancements

Potential features for future versions:
- Authentication and authorization
- Prompt analytics and usage tracking
- A/B testing support
- Prompt templates
- Prompt validation rules
- Export/import functionality
- Webhook notifications on prompt updates
- Caching layer for better performance
- Support for multiple languages
- Rollback to previous versions
