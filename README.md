# rms-application Project Setup Guide (Local Development)
---

## 📦 Prerequisites:
Make sure the following is installed in your local machine (Windows).
```
Docker
Java 25
```

## 🚀 Getting started:
### 1. Ensure docker is installed and running

```bash 
docker --version 
```

### 2. Clone the project

```bash
git clone <this-repository-url>
cd <project-folder>
```

### 3. Setup environment variable

```bash
cp .env.example .env
```
Then edit the .env file and update values as needed:
```bash
DB_HOST=localhost 
DB_PORT=5432 
DB_NAME=your_db 
DB_USER=your_user 
DB_PASSWORD=your_password
```

### 4. Build and start the docker container
For the project to successfully run, make sure to build and run the database container first.
```bash
docker compose up -d
```
#### Optional: Check if the container is running.
```bash
docker ps
```

### 5. Run the project
Start the project in development mode.
```bash
quarkus dev
```

## 🛑 Stopping the database container
You can stop it directly using the docker desktop app, or run this command instead.
```bash
docker compose down
```

## ✅ You're Ready!

The project should now run locally.

---

# Branch Naming Convention

To maintain a clean and organized workflow, follow a standard naming convention for branches. Always use lowercase and separate words with hyphens (`kebab-case`).

**Format:** `type/short-description`

| Type | Purpose | Example |
| :--- | :--- | :--- |
| `feature/feature-name` | A new feature or functionality | `ex: feat/user-authentication` |
| `fix/fix-branch-name` | A bug fix | `ex: fix/connection-timeout` |

---

## Project Directory Structure

Below is the layout of the primary directories and files in this Quarkus project:

```text
.
├── src/
│   ├── main/
│   │   ├── java/              # Java source files (Resources, Services, Entities)
│   │   |   ├── org.acme/      # The main package where all of the backend code exist
|   |   |   |   ├── staffs     # The folder is separated for each module, make sure that the folder naming is always in plural form (orders, payments, etc.)
|   |   |   |   └── orders, payments, etc. 
│   │   ├── docker/            # Quarkus-generated Dockerfiles (JVM, Native)
│   │   └── resources/
|   |       ├── db.migration/  # The directory of where all of the flyway migration files exist
│   │       ├── import.sql
│   │       └── application.properties # Main configuration file
│   └── test/
│       └── java/              # Integration and unit tests
├── .env                       # Local environment variables (Git ignored)
├── .env.example               # Template for environment variables
├── docker-compose.yml         # Infrastructure setup (DB, Cache, etc.)
├── pom.xml                    # Maven project configuration
└── README.md                  # Project documentation
