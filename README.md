# rms-application — Local Development Guide

## 📑 Table of Contents
- [Prerequisites](#-prerequisites)
- [Getting Started](#-getting-started)
- [Project Directory Structure](#-project-directory-structure)
- [Branch Naming Convention](#-branch-naming-convention)
- [Flyway Migration File Naming Convention](#-flyway-migration-file-naming-convention)
- [Generating JWT Keys (.pem)](#-generating-jwt-keys-pem)

---

## 📦 Prerequisites

Make sure the following are installed on your local machine (Windows):

| Tool | Version |
| :--- | :--- |
| Docker | Latest |
| Java | 25 |

---

## 🚀 Getting Started

### 1. Ensure Docker is installed and running
```bash
docker --version
```

### 2. Clone the project
```bash
git clone <this-repository-url>
cd <project-folder>
```

### 3. Set up environment variables
```bash
cp .env.example .env
```

Then edit the `.env` file and update the values as needed:
```env
DB_HOST=localhost
DB_PORT=5432
DB_NAME=your_db
DB_USER=your_user
DB_PASSWORD=your_password
```

### 4. Build and start the Docker container

> ⚠️ Make sure to build and run the database container **before** starting the project.

```bash
docker compose up -d
```

**Optional:** Verify the container is running:
```bash
docker ps
```

### 5. Run the project

Start the project in development mode:
```bash
quarkus dev
```

## 📁 Project Directory Structure

```text
.
├── src/
│   ├── main/
│   │   ├── java/                         # Java source files (Resources, Services, Entities)
│   │   │   └── org.acme/                 # Main package for all backend code
│   │   │       ├── staff/               # Module-specific folders
│   │   │       └── order/, payment/,   # etc.
│   │   ├── docker/                       # Quarkus-generated Dockerfiles (JVM, Native)
│   │   └── resources/
│   │       ├── db/migration/             # Flyway migration SQL files
│   │       ├── import.sql
│   │       └── application.properties    # Main configuration file
│   └── test/
│       └── java/                         # Integration and unit tests
├── .env                                  # Local environment variables (Git ignored)
├── .env.example                          # Template for environment variables
├── docker-compose.yml                    # Infrastructure setup (DB, Cache, etc.)
├── pom.xml                               # Maven project configuration
└── README.md                             # Project documentation
```

---

## 🌿 Branch Naming Convention

Always use lowercase and separate words with hyphens (`kebab-case`).

**Format:** `type/short-description`

| Type | Purpose | Example |
| :--- | :--- | :--- |
| `feature/` | A new feature or functionality | `feature/user-authentication` |
| `fix/` | A bug fix | `fix/connection-timeout` |

---

## 🗄️ Flyway Migration File Naming Convention

All Flyway migration files live in `src/main/resources/db/migration/` and must follow this strict naming format:

**Format:** `V{version}__{description}.sql`

> ⚠️ Note the **double underscore** (`__`) between the version number and description.

### Version Numbering

Use an incrementing integer or timestamp — do **not** reuse or modify existing version numbers.

| Type | Format | Example |
| :--- | :--- | :--- |
| Timestamped | `V{yyyyMMddHHmm}__description.sql` | `V202506011200__add_orders_table.sql` |

### Naming Rules
- Always use **lowercase** with **underscores** for the description.
- Keep descriptions **short but meaningful** — they should reflect what the migration does.
- Never edit or delete a migration file that has already been applied.
- Repeatable migrations (run every time the checksum changes) use the prefix `R__` instead of a version number.

### Examples

```text
db/migration/
├── V20260101__create_staffs_table.sql
├── V20260102__create_orders_table.sql
├── V20260103__add_status_column_to_orders.sql
├── V20260104__create_payments_table.sql
└── R__create_or_replace_views.sql     ← repeatable migration
```

---

## 🔐 Generating JWT Keys (.pem)

Quarkus JWT (via SmallRye JWT) requires an RSA key pair — a **private key** for signing tokens and a **public key** for verifying them. These steps follow the [official Quarkus JWT guide](https://quarkus.io/guides/security-jwt).

### Prerequisites
Make sure **OpenSSL** is installed. On Windows, you can install it via [Git Bash](https://git-scm.com/) (which bundles OpenSSL) or [Win64 OpenSSL](https://slproweb.com/products/Win32OpenSSL.html).

### Step 1 — Generate the RSA private key
```bash
openssl genrsa -out rsaPrivateKey.pem 2048
```

### Step 2 — Extract the public key
```bash
openssl rsa -pubout -in rsaPrivateKey.pem -out publicKey.pem
```

### Step 3 — Convert the private key to PKCS#8 format
Java requires the private key in PKCS#8 format:
```bash
openssl pkcs8 -topk8 -nocrypt -inform pem -in rsaPrivateKey.pem -outform pem -out privateKey.pem
```

> You can now safely delete `rsaPrivateKey.pem` — it was only needed for step 3.

### Step 4 — Move the keys into the project
Place both generated files into:
```text
src/main/resources/
├── privateKey.pem    ← used for signing tokens
└── publicKey.pem     ← used for verifying tokens
```

### Step 5 — Configure `application.properties`
```properties
mp.jwt.verify.publickey.location=publicKey.pem
mp.jwt.verify.issuer=https://example.com/issuer
smallrye.jwt.sign.key.location=privateKey.pem

# Required for native builds
quarkus.native.resources.includes=publicKey.pem
```

> 🔒 **Security reminder:** Both `.pem` files are sensitive. Make sure they are listed in `.gitignore` and never committed to version control.
