# Latza.p0

## Description
a simple pharacy program to be used by pharmacy employees to manage patients and the drugs they have been perscribed.
## Technologies Used
- Java
- Maven
- Spring Boot
- Spring Web
- PostgreSQL
- AWS
- Kubernetes
- Log4J
- FluentD
- Loki
- Grafana

## Features
- get a list of drugs or patients in the DB
- get a drug by brand or generic name
- Add a drug or patient to the DB
- change the fills remaining that a patient has for a particular meication 

## How to Use
- drug related actions 
  - obtain a list of all drugs in the DB: send a GET request to "~/api/v1/drugs"
  - find a drug by name: send a GET request to "~/api/v1/drugs/[name of drug]"
    - works for brand or genaric drug names
  - add a new drug to the DB: send a POST request to "~/api/v1/drugs" with the body containing the new drug info in the form of a JSON object, i.e.:
    {
      "drugName":"ibuprofen",
      "brandName":"Motrin"
    }

- patient related actions
  - obtain a list of all patients: send a GET request to "~/api/v1/patients"
  - obtain a list of all patients with a given last name: send a GET request to "~/api/v1/patients/last_name/[search_chriteria]"
  - find a single patient
    - by username: send a GET request to "~/api/v1/patients/usernames/[search_chriteria]"
    - by username: send a GET request to "~/api/v1/patients/ids/[search_chriteria]"
  - add a new Patient to the DB: send a PUT request to: "~/api/v1/patients" with the new patient as a JSON object i.e.:
    {
    "firstName":"",
    "LastName":"",
    "addr":"",
    "username":""
    }
          
  - remove a Patient from the DB: send a PUT request to: "~api/v1/patients/antipatients/[username of user to delete]"
  - update a Patient's address: send a PUT request to "~api/v1/patients/addr/[username of user to update]/[new address]"

- med-list related actions
  - add a drug to a patient's medllist
    - without any refils: send a PUT request to "~api/v1/Rx/[username]/[drug/brand name]"
    - with a given number of refills: send a PUT request to "~api/v1/Rx/[username]/[drug/brand name]/[number of refils]"
  - change the number of refills a patient has: send a PUT request to "~api/v1/Rx/refils/[username]/[drug/brand name]"
    

## To Do
- add functionality for tracking which perscriber wrote each Rx for each patient\
- add a queue for tracking which patients have requested Rxs to be filled
