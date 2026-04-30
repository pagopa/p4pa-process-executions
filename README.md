# p4pa-process-executions

This application belong to the **entity** tier of the **Piattaforma Unitaria** product.

See [PU Microservice Architecture](https://raw.githubusercontent.com/pagopa/p4pa-doc/refs/heads/main/reference/technical-docs/Architettura_microservizi.pdf) for more details.

See [p4pa-doc](https://github.com/pagopa/p4pa-doc) for further documentation.

## 🧱 Role

* To handle massive ingestion flow;
* To request a data export.

## 🌐 APIs
See [OpenAPI](openapi/generated.openapi.json), exposed through the following path:
* `/swagger-ui/index.html`

### 📌 Relevant APIs
* `POST /ingestion-flow-files`: To start the ingestion of a file;
* `POST /export-files/paid`: To request a data export of paid installments;
* `POST /export-files/classifications`: To request a data export of classifications;
* `POST /export-files/payments-reporting`: To request a data export of payments reporting;
* `POST /export-files/receipts-archiving`: To request a data export of receipts for archiving purposes;

### 📌 Common HTTP status returned:
* `401`: Invalid access token provided, thus a new login is required;
* `403`: Trying to access a not authorized resource.

## 🔎 Monitoring
See available actuator endpoints through the following path:
* `/actuator`

### 📌 Relevant endpoints
* Health (provide an accessToken to see details): `/actuator/health`
  * Liveness: `/actuator/health/liveness`
  * Readiness: `/actuator/health/readiness`
* Metrics: `/actuator/metrics`
  * Prometheus: `/actuator/prometheus`

Further endpoints are exposed through the JMX console.

## ✏️ Logging
See [log configured pattern](/src/main/resources/logback-spring.xml).

## 🔗 Dependencies

### 🗄️ Resources
* PostgreSQL

### 🧩 Microservices
* [p4pa-workflow-hub](https://github.com/pagopa/p4pa-workflow-hub):
  * To start the workflow related to the ingestion of the file;
  * To start the workflow related to the export of data.

## 🗃️ Entities handled
* `ingestion_flow_file`
* `export_file`

## 🔧 Configuration

See [application.yml](src/main/resources/application.yml) for each configurable property.

### 📌 Relevant configurations

#### 🌐 Application Server
| ENV         | DESCRIPTION                       | DEFAULT |
|-------------|-----------------------------------|---------|
| SERVER_PORT | Application server listening port | 8080    |

#### ✏️ Logging
| ENV                                   | DESCRIPTION                                                                                                                                                                     | DEFAULT |
|---------------------------------------|---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|---------|
| LOG_LEVEL_ROOT                        | Base level                                                                                                                                                                      | INFO    |
| LOG_LEVEL_PAGOPA                      | Base level of custom classes                                                                                                                                                    | INFO    |
| LOG_LEVEL_SPRING                      | Level applied to Spring framework                                                                                                                                               | INFO    |
| LOG_LEVEL_SPRING_BOOT_AVAILABILITY    | To print availability events                                                                                                                                                    | DEBUG   |
| LOGGING_LEVEL_API_REQUEST_EXCEPTION   | Level applied to APIs exception                                                                                                                                                 | INFO    |
| LOG_LEVEL_PERFORMANCE_LOG             | Level applied to [PerformanceLog](https://pagopa.atlassian.net/wiki/spaces/SPAC/pages/1540096383/Logging#2.2.-Log-di-performance)                                               | INFO    |
| LOG_LEVEL_PERFORMANCE_LOG_API_REQUEST | Level applied to [API Performance Log](https://pagopa.atlassian.net/wiki/spaces/SPAC/pages/1540096383/Logging#2.2.2.1.-Log-di-perfomance-per-le-API)                            | INFO    |
| LOG_LEVEL_PERFORMANCE_LOG_REST_INVOKE | Level applied to [REST invoke Performance Log](https://pagopa.atlassian.net/wiki/spaces/SPAC/pages/1540096383/Logging#2.2.2.2.-Log-di-performance-per-i-servizi-REST-integrati) | INFO    |

#### 🔁 Integrations

##### 🗄️ Resources
| ENV                                                          | DESCRIPTION                                                                           | DEFAULT                                                                                                                      |
|--------------------------------------------------------------|---------------------------------------------------------------------------------------|------------------------------------------------------------------------------------------------------------------------------|
| SHOW_SQL                                                     | To print SQL statements                                                               | false                                                                                                                        |
| PROCESS_EXECUTIONS_DB_URL                                    | PostgreSQL connection string (to use in order to customize the entire string)         | jdbc:postgresql://${CLASSIFICATION_DB_HOST}:${CLASSIFICATION_DB_PORT}/${CLASSIFICATION_DB_NAME}?currentSchema=debt_positions |
| PROCESS_EXECUTIONS_DB_HOST                                   | PostgreSQL Host                                                                       | localhost                                                                                                                    |
| PROCESS_EXECUTIONS_DB_PORT                                   | PostgreSQL port                                                                       | 5432                                                                                                                         |
| PROCESS_EXECUTIONS_DB_NAME                                   | PostgreSQL Database name                                                              | payhub                                                                                                                       |
| PROCESS_EXECUTIONS_DB_USER                                   | PostgreSQL username                                                                   |                                                                                                                              |
| PROCESS_EXECUTIONS_DB_PASSWORD                               | PostgreSQL password                                                                   |                                                                                                                              |
| PROCESS_EXECUTIONS_DB_CONNECTION_IDLE_TIMEOUT_MILLISECONDS   | PostgreSQL connection idle timeout (milliseconds)                                     | 600000                                                                                                                       |
| PROCESS_EXECUTIONS_DB_CONNECTION_TIMEOUT_MILLISECONDS        | PostgreSQL connection timeout (milliseconds)                                          | 30000                                                                                                                        |
| PROCESS_EXECUTIONS_DB_CONNECTION_KEEPALIVE_TIME_MILLISECONDS | PostgreSQL connection keepalive time (milliseconds)                                   | 120000                                                                                                                       |
| PROCESS_EXECUTIONS_DB_CONNECTION_MAX_LIFETIME_MILLISECONDS   | PostgreSQL connection max lifetime (milliseconds)                                     | 1800000                                                                                                                      |
| PROCESS_EXECUTIONS_DB_CONNECTION_MAX_POOL_SIZE               | PostgreSQL connection max pool size                                                   | 10                                                                                                                           |
| PROCESS_EXECUTIONS_DB_CONNECTION_MIN_IDLE                    | PostgreSQL connection min idle                                                        | 10                                                                                                                           |

##### 🔗 REST
| ENV                                               | DESCRIPTION                               | DEFAULT |
|---------------------------------------------------|-------------------------------------------|---------|
| DEFAULT_REST_CONNECTION_POOL_SIZE                 | Default connection pool size              | 10      |
| DEFAULT_REST_CONNECTION_POOL_SIZE_PER_ROUTE       | Default connection pool size per route    | 5       |
| DEFAULT_REST_CONNECTION_POOL_TIME_TO_LIVE_MINUTES | Default connection pool TTL (minutes)     | 10      |
| DEFAULT_REST_TIMEOUT_CONNECT_MILLIS               | Default connection timeout (milliseconds) | 120000  |
| DEFAULT_REST_TIMEOUT_READ_MILLIS                  | Default read timeout (milliseconds)       | 120000  |

##### 🧩 Microservices
| ENV                                  | DESCRIPTION                                                                       | DEFAULT |
|--------------------------------------|-----------------------------------------------------------------------------------|---------|
| WORKFLOW_HUB_BASE_URL                | WorkflowHub microservice URL                                                      |         |
| WORKFLOW_HUB_MAX_ATTEMPTS            | WorkflowHub API max attempts                                                      | 3       |
| WORKFLOW_HUB_WAIT_TIME_MILLIS        | WorkflowHub retry waiting time (milliseconds)                                     | 500     |
| WORKFLOW_HUB_PRINT_BODY_WHEN_ERROR   | To print body when an error occurs                                                | true    |


#### 🔑 keys
| ENV                          | DESCRIPTION                                         | DEFAULT |
|------------------------------|-----------------------------------------------------|---------|
| JWT_TOKEN_PUBLIC_KEY         | p4pa-auth JWT public key                            |         |

## 🛠️ Getting Started

### 📝 Prerequisites

Ensure the following tools are installed on your machine:

1. **Java 21+**
2. **Gradle** (or use the Gradle wrapper included in the repository)
3. **Docker** (to build and run on an isolated environment, optional)

### 🔐 Write Locks

```sh
./gradlew dependencies --write-locks
```

### ⚙️ Build

```sh
./gradlew clean build
```

### 🧪 Test

#### 📌 JUnit
```sh
./gradlew test
```

### 🚀 Run local

```sh
./gradlew bootRun
```

### 🐳 Build & run through Docker
```sh
docker build -t <APP_NAME> .
docker run --env-file <ENV_FILE> <APP_NAME>
```

### ⚖️ Generate dependencies licenses
```sh
./gradlew generateLicenseReport
```
