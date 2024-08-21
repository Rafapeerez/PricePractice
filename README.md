# Getting Started

## Installation

To install and start the service, follow these steps:

1. **Clone the repository**

```bash
    git clone <REPOSITORY_URL>
```

2. **Navigate to Project Directory**

```bash
    cd <project_name>
```

To build the project run this command line:

```bash
    mvn clean install
```

To run tests:

```bash
    mvn test
```

To start the service:

```bash
   mvn spring-boot:run
```

## Usage

Make a GET request to the following API endpoint:

```bash
    GET /price/search
```

### Parameters

- date: The date in format yyyy-MM-dd-HH:mm:ss (required)
- productId: Product ID (required)
- brandId: Brand ID (required)

### Request Example

```bash
    curl -X GET "http://localhost:8080/price/search?date=2020-06-14-10.00.00&productId=35455&brandId=1"
```

### Response Example

```bash
{
    "productID": "35455",
    "brandID": "1",
    "rate": "1",
    "dateStart": "2020-06-14-00.00.00",
    "dateEnd": "2020-12-31-23.59.59",
    "price": "35.50 EUR"
}
```
