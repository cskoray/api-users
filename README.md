# Actuator Health Check URL
http://localhost:8083/health

# Swagger Hub URL
https://app.swaggerhub.com/organizations/CSKORAY_1/projects/zilch-project

# K8s folder has all the yaml files for the deployment
### api-users-deployment.yaml

# Open API URL
http://localhost:8083/swagger-ui/index.html

### USER API
``` 1st step:```

Add new user

localhost:8082/v1/api/users/register

```
{
"name": "horay",
"email": "ga@g.com",
"cardNumber": "1111222233334444",
"expiryDate": "0525",
"cvv": "275"
}
```
response
```
{
"id": 4,
"userKey": "a3d9993c-66e5-4ebb-8eb6-b7ff1538751a",
"name": "horay",
"email": "ga@g.com",
"paymentToken": null,
"debitCardNumber": "1111222233334444",
"debitCvv": "275",
"debitExpiry": "0525",
"zilchCardNumber": null,
"zilchCvv": null,
"zilchExpiry": null,
"zilchCreditLimit": null,
"createdDate": "2023-07-30T11:05:03.108+00:00"
}
```

``` 2st step:```

Register current user to ZILCH as customer

localhost:8082/v1/api/users/zilch/register
```
{
  "userKey": "a3d9993c-66e5-4ebb-8eb6-b7ff1538751a"
}
```
Reponse
```
{
"paymentToken": "6165e18a-81d8-4c47-8358-d864ab978fe6",
"cardNumber": "5169824574275218",
"expiryDate": "0428",
"cvv": "537",
"creditLimit": 100
}
```