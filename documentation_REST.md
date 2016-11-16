# Waterfall REST API Documentation
## Users
### Get all users
**Url:** /Waterfall/api/users  
**Method:** `GET`  
**Success Response:** [200 OK]  
**Example response content:**  
`{  
    "birthdate": "1989-09-06T00:00:00+02:00",  
    "city": "Gothenburg",  
    "country": ”Sweden”,  
    "email": ”sigrid@mail.com",  
    "firstName": ”Sigrid”,  
    "gender": "Female",  
    "lastName": ”Gunnarsson”,  
    "links": [  
      {  
        "relation": "Self",  
        "uri": "http://localhost:8080/Waterfall/api/users/1”  
      },  
      {  
        "relation": "Drops",  
        "uri": "http://localhost:8080/Waterfall/api/users/1/drops"  
      }  
    ],  
    "userid": 1,  
    "username": ”Sigge”  
  },  
  {  
    "birthdate": "1902-09-08T00:00:00+01:00",  
    "city": ”Halmstad”,  
    "country": ”Sweden”,  
    "email": ”joel@mail.com”,  
    "firstName": ”Joel”,  
    "gender": "Male",  
    "lastName": ”Nilsson”,  
    "links": [  
      {  
        "relation": "Self",  
        "uri": "http://localhost:8080/Waterfall/api/users/2”  
      },  
      {  
        "relation": "Drops",  
        "uri": "http://localhost:8080/Waterfall/api/users/2/drops"  
      }  
    ],  
    "userid": 2,  
    "username": ”Parab00la”  
  }`    
**Error response:** [404 NOT FOUND]  
## Get user by ID
**URL:** /Waterfall/api/users/:id  
**URL params:** id=[Long]  
**Method:** `GET`  
**Success Response:** [200 OK]  
**Example response content:**  
`{  
  "birthdate": "1989-09-06T00:00:00+02:00",  
  "city": "Gothenburg",  
  "country": ”Sweden”,  
  "email": ”sigrid@mail.com",  
  "firstName": ”Sigrid”,  
  "gender": "Female",  
  "lastName": ”Gunnarsson”,  
  "links": [  
    {  
      "relation": "Self",  
      "uri": "http://localhost:8080/Waterfall/api/users/1”  
    },  
    {  
      "relation": "Drops",  
      "uri": "http://localhost:8080/Waterfall/api/users/1/drops"  
    }  
  ],  
  "userid": 1,  
  "username": ”Sigge”  
}`  
**Error response:** [404 NOT FOUND]  
## Get all drops by user-ID  
**URL:** /Waterfall/api/users/:id/drops  
**URL params:** id=[Long]  
**Method:** `GET`  
**Success Response:** [200 OK], [204 NO CONTENT]  
**Example content:**  
`{  
    "dropid": 6,  
    "content": "Hi everyone, what are you doing?”,  
    "creationDate": "2016-10-27T18:40:06.532",  
    "links": [  
      {  
        "rel": "Self",  
        "uri": "http://localhost:8080/Waterfall/api/drops/6"  
      },  
      {  
        "rel": "Comments",  
        "uri": "http://localhost:8080/Waterfall/api/drops/6/comments"  
      }  
  }`    
**Error response:** [404 NOT FOUND]  
## Update user  
**URL:** /Waterfall/api/users/  
**Method:** `PUT`  
**Content type:** Json  
**Success Response:** [200 OK]  
**Example request:**  
`{  
    "birthdate": "1989-09-06T00:00:00+02:00",  
    "city": "Gothenburg",  
    "country": "Sweden",  
    "email": ”sigrid@mail.com",  
    "firstName": ”Anna”,  
    "gender": "Female",  
    "lastName": ”Gunnarsson”,  
    "userid": 1,  
    "username": ”Siggis”  
}`  
**Example response:**  
`{  
  "birthdate": "1989-09-06T00:00:00+02:00",  
  "city": "Gothenburg",  
  "country": "Sweden",  
  "email": "sigrid@mail.com",  
  "firstName": ”Anna”,  
  "gender": "Female",  
  "lastName": "Gunnarsson",  
  "links": [],  
  "userid": 1,  
  "username": "Siggis"  
}`  
**Error response:** [// todo lägg in något här]  

## Create new user  
**URL:** /Waterfall/api/users/  
**Method:** `POST`  
**Success Response:** [201 CREATED]  
**Content type:** Json  
**Example request:**  
`{  
  "birthdate": "1970-11-20T00:00:00+02:00",  
  "city": ”Uddevalla”,  
  "country": "Sweden",  
  "email": ”arne@gmail.com",  
  "firstName": ”Arne”,  
  "gender": ”Other”,  
  "lastName": ”Arnesson”,  
  "password": "123",  
  "username": ”ArneMannen”  
}`  
**Example response:**  
`{  
  "birthdate": "1970-11-20T00:00:00+02:00",  
  "city": ”Uddevalla”,  
  "country": "Sweden",  
  "email": "arne@gmail.com",  
  "firstName": ”Arne”,  
  "gender": ”Other”,  
  "lastName": ”Arnesson”,  
  "links": [],  
  "username": ”ArneMannen”  
}`  
**Error response:** [204 NO CONTENT]  

## Drops  
## Get all drops
**Url:** /Waterfall/api/drops  
**Method:** `GET`  
**Success Response:** [200 OK]  
**Example response content:**  
`{  
    "dropid": 1,  
    "content": "Hi everyone, what are you doing?”,  
    "creationDate": "2016-10-27T18:40:06.532",  
    "links": [  
      {  
        "relation": "Self",  
        "uri": "http://localhost:8080/Waterfall/api/drops/1”  
      },  
      {  
        "relation": "Comments",  
        "uri": "http://localhost:8080/Waterfall/api/drops/1/comments"  
      },  
      {  
        "relation": "Owner",  
        "uri": "http://localhost:8080/Waterfall/api/users/7"  
      }  
    ],  
  },  
  {  
    "dropid": 2,  
    "content": ”This is a great webpage!”,  
    "creationDate": "2016-10-30T10:46:03.432646",  
    "links": [  
      {  
        "relation": "Self",  
        "uri": "http://localhost:8080/Waterfall/api/drops/2”  
      },  
      {  
        "relation": "Comments",  
        "uri": "http://localhost:8080/Waterfall/api/drops/2/comments"  
      },  
      {  
        "relation": "Owner",  
        "uri": "http://localhost:8080/Waterfall/api/users/8"  
      }  
    ],  
  }`  
## Create new drop  
**URL:** /Waterfall/api/drops/:userid  
**URL params:** id=[Long]  
**Method:** `POST`  
**Success Response:** [201 CREATED]  
**Content type:** Json  
**Example request:**  
`{  
  "content": "HEJHEJHEJ"  
}`  
**Example response:**  
`{  
  "content": "HEJHEJHEJ",  
  "creationDate": "2016-11-04T14:55:26.295",  
  "links": [],  
}`  
## Get drop by ID
**URL:** /Waterfall/api/drops/:id  
**URL params:** id=[Long]  
**Method:** `GET`  
**Success Response:** [200 OK]  
**Example response content:**  
`{  
  "dropid": 2,  
  "content": "Hello out there!”,  
  "creationDate": "2016-10-27T18:40:06.532",  
  "links": [  
    {  
      "rel": "Self",  
      "uri": "http://localhost:8080/Waterfall/api/drops/2"  
    },  
    {  
      "rel": "Owner",  
      "uri": "http://localhost:8080/Waterfall/api/users/8"  
    },  
    {  
      "rel": "Comments",  
      "uri": "http://localhost:8080/Waterfall/api/drops/2/comments"  
    }  
  ]  
}`  

