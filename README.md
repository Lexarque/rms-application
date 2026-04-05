# rms-application Project Setup Guide (Local Development)

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
