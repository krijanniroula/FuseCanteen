# FuseCanteen 

<h3>AUTH API</h3>

 <b>POST  localhost:8080/api/auth/signup</b>
 
   Example : 
   
    {
      "username":"prakriti",
      "firstName":"Prakriti",
      "lastName":"Karki",
      "password":"123456789" ,
      "email":"prakriti@gmail.com",
      "mobileNumber":"9876534212"
    }

If signup is for admin role, add "roles":["admin"] to above.

<b> POST  localhost:8080/api/auth/signin </b> 
 
 Example : 
 
  {
    "username":"prakriti",
    "password":"123456789"
  }

<b><i>The signin request will return the token of type Bearer, which needs to be added to Header Authorization with value Bearer <token>. Then only use can access the below api with respect to roles.</i></b>

 <h3>FOODITEM API</h3> 
 
 ADMIN

     GET      localhost:8080/api/fooditem        - retrieve all fooditem list
     GET      localhost:8080/api/fooditem/<id>   - retrieve fooditem by id 
     POST     localhost:8080/api/fooditem        - create new fooditem
						{ "name":"Samosa", "price":"60"}
     PUT      localhost:8080/api/fooditem/<id>   - update fooditem by id
						 { "name":"Samosa", "price":"60"}
     DELETE   localhost:8080/api/fooditem/<id>   - delete fooditem by id  
     
Example for retrieve all fooditem list 

     [
    {
        "id": "5f5db2e09cfde45dc693934f",
        "name": "Samosa",
        "price": 60
    },
    {
        "id": "5f5db3259cfde45dc6939350",
        "name": "Chicken Khaja Set",
        "price": 150
    },
    {
        "id": "5f5db4149cfde45dc6939353",
        "name": "Veg Chowminn",
        "price": 120
    },
    {
        "id": "5f5db41f9cfde45dc6939354",
        "name": "Buff Chowminn",
        "price": 130
    },
    {
        "id": "5f5dd3a78e23105a0681f91d",
        "name": "Pakoda",
        "price": 40
    }
    ]

 <h3>MENU API</h3> 

 ADMIN:
 

    GET      localhost:8080/api/menu          - retrieve all menu list
    GET      localhost:8080/api/menu/<date>   - retrieve menu by entered date
    GET      localhost:8080/api/menu/today    - retrieve menu by today
    POST     localhost:8080/api/menu          - create new menu list
                    {"foodItems":["Samosa","Chicken Khaja Set","Pakoda"] } 
    PUT      localhost:8080/api/menu/<date>   - Update Menu for entered date 
                    {"foodItems":[<Updated list>] } 
    DELETE   localhost:8080/api/menu/<date>   - Delete menu by date
   
 
 EMPLOYEE:

      GET  localhost:8080/api/menu/today - retrieve menu for today
      
Example for retrieve menu for today      
      
      {
    "id": "5f609dce0ccea616b03c2b7d",
    "foodItems": [
        {
            "id": "5f5db3259cfde45dc6939350",
            "name": "Chicken Khaja Set",
            "price": 150
        },
        {
            "id": "5f5db41f9cfde45dc6939354",
            "name": "Buff Chowminn",
            "price": 130
        },
        {
            "id": "5f5dd3a78e23105a0681f91d",
            "name": "Pakoda",
            "price": 40
        }
    ],
    "date": "2020-09-15"
    }

<h3>ORDER API</h3> 

 ADMIN:
 
    GET       localhost:8080/api/order                      - retrieve all order list
    GET       localhost:8080/api/order/<date>               - retrieve all order list by date entered
    GET       localhost:8080/api/order/<date>/<username>    - retrieve order by date and username entered
    DELETE    localhost:8080/api/order/<date>/<username>    - Delete order by date and username entered
    
    PUT       localhost:8080/api/order/<date>/<username>    - Update orderStatus by date and username
    						   {  "status":"INPROCESS" }
   
Example for retrieve all order list by date - localhost:8080/api/order/2020-09-15

     [
    {
        "foodItems": [
            {
                "id": "5f5db3259cfde45dc6939350",
                "name": "Chicken Khaja Set",
                "price": 150
            },
            {
                "id": "5f5db2e09cfde45dc693934f",
                "name": "Samosa",
                "price": 60
            }
        ],
        "date": "2020-09-15",
        "totalPrice": 210,
        "username": "ramesh",
        "fullName": "Ramesh Adhikari",
        "mobile": "9876543210",
        "orderStatus": "READY"
    },
    {
        "foodItems": [
            {
                "id": "5f5db3259cfde45dc6939350",
                "name": "Chicken Khaja Set",
                "price": 150
            },
            {
                "id": "5f5dd3a78e23105a0681f91d",
                "name": "Pakoda",
                "price": 40
            }
        ],
        "date": "2020-09-15",
        "totalPrice": 190,
        "username": "paras",
        "fullName": "Paras Khadka",
        "mobile": "9886536702",
        "orderStatus": "READY"
    },
    {
        "foodItems": [
            {
                "id": "5f5db3259cfde45dc6939350",
                "name": "Chicken Khaja Set",
                "price": 150
            },
            {
                "id": "5f5dd3a78e23105a0681f91d",
                "name": "Pakoda",
                "price": 40
            }
        ],
        "date": "2020-09-15",
        "totalPrice": 190,
        "username": "mahesh",
        "fullName": "Mahesh Lamichhane",
        "mobile": "9875934210",
        "orderStatus": "INPROCESS"
    }
    ]        
   
 EMPLOYEE : 

    GET	    localhost:8080/api/order/<date>   - retrieve order by date entered of logged in user
    GET     localhost:8080/api/order          - retrieve all order list made by logged in user
    POST    localhost:8080/api/order          - Create order for logged in user 
                                   {"foodItems":["Chicken Khaja Set","Samosa"], "date":"2020-09-15"}
				                           If date is not provided, it will save with local date
    PUT     localhost:8080/api/order/<date>   - Update logged in user order    
                                   {"foodItems":["Pakoda","Samosa"] }
                                 
    DELETE  localhost:8080/api/order/<date>   - Delete order of logged in user by date entered
    
Example for retrieve order by date entered - localhost:8080/api/order/2020-09-15

    {
    "foodItems": [
        {
            "id": "5f5db3259cfde45dc6939350",
            "name": "Chicken Khaja Set",
            "price": 150
        },
        {
            "id": "5f5db2e09cfde45dc693934f",
            "name": "Samosa",
            "price": 60
        }
    ],
    "date": "2020-09-15",
    "totalPrice": 210,
    "orderStatus": "INPROCESS"
    }

<h3>FOODREQUEST API</h3>


 ADMIN

    GET    localhost:8080/api/foodrequest                - retrieve all foodrequest list
    GET    localhost:8080/api/foodrequest/today          - retrieve todays food request
    GET    localhost:8080/api/foodrequest/today/popular  - retrieve todays food request by popularity
   
Example for retrieve todays food request 

    [
    {
        "name": "Thuppa",
        "date": "2020-09-15",
        "username": "ramesh",
        "fullName": "Ramesh Adhikari",
        "mobile": "9876543210"
    },
    {
        "name": "Paneer momo",
        "date": "2020-09-15",
        "username": "prakriti",
        "fullName": "Prakriti Karki",
        "mobile": "9876534212"
    },
    {
        "name": "Thuppa",
        "date": "2020-09-15",
        "username": "prakriti",
        "fullName": "Prakriti Karki",
        "mobile": "9876534212"
    },
    {
        "name": "Chicken Cheese Pizza",
        "date": "2020-09-15",
        "username": "paras",
        "fullName": "Paras Khadka",
        "mobile": "9886536702"
    },
    {
        "name": "Chicken Burger",
        "date": "2020-09-15",
        "username": "paras",
        "fullName": "Paras Khadka",
        "mobile": "9886536702"
    },
    {
        "name": "Thuppa",
        "date": "2020-09-15",
        "username": "mahesh",
        "fullName": "Mahesh Lamichhane",
        "mobile": "9875934210"
    },
    {
        "name": "Chicken Burger",
        "date": "2020-09-15",
        "username": "mahesh",
        "fullName": "Mahesh Lamichhane",
        "mobile": "9875934210"
    }
    ]
    

Example for retrieve todays food request by popularity

    [
    {
        "name": "Thuppa",
        "requestCount": 3,
        "usernameList": [
            "mahesh",
            "prakriti",
            "ramesh"
        ]
    },
    {
        "name": "Chicken Burger",
        "requestCount": 2,
        "usernameList": [
            "mahesh",
            "paras"
        ]
    },
    {
        "name": "Chicken Cheese Pizza",
        "requestCount": 1,
        "usernameList": [
            "paras"
        ]
    },
    {
        "name": "Paneer momo",
        "requestCount": 1,
        "usernameList": [
            "prakriti"
        ]
    }
    ]
 
 EMPLOYEE

    GET    localhost:8080/api/foodrequest/today  - retrieve todays food request by logged in user
    GET  localhost:8080/api/foodrequest          - retrieve all request made by logged in user
    GET  localhost:8080/api/foodrequest/<id>     - retrieve request made by id
    POST localhost:8080/api/foodrequest          - create new food request
 					     {  "name":"Thupa" }
    PUT localhost:8080/api/foodrequest/<id>      - update food request by id
                                            {  "name":"Thuppa" }
    DELETE localhost:8080/api/foodrequest/<id>  - delete by id
    
<h3>FEEDBACK API</h3>

ADMIN

    GET  localhost:8080/api/feedback                          - retrieve all users feedback list
    GET localhost:8080/api/feedback/user/<username>           - retrieve feedback list by username
    GET localhost:8080/api/feedback/fooditem/<foodItemName>   - retrieve feedback list by food item name
    
Example for retrieve all users feedback list 

     [
    {
        "foodItemName": "Veg Chowminn",
        "comment": "It would be better with less oil for me!",
        "rating": 6,
        "date": "2020-09-15",
        "userFullName": "Prakriti Karki"
    },
    {
        "foodItemName": "Pakoda",
        "comment": "I like it!",
        "rating": 7,
        "date": "2020-09-15",
        "userFullName": "Prakriti Karki"
    },
    {
        "foodItemName": "Chicken Khaja Set",
        "comment": "It was quite delicious!",
        "rating": 9,
        "date": "2020-09-15",
        "userFullName": "Ramesh Adhikari"
    }
    ]

 EMPLOYEE

    GET localhost:8080/api/feedback                      - retrieve feedback list of user logged in 
    POST localhost:8080/api/feedback                     - create new feedback
                        {   "foodItemName":"Pakoda",   "comment":"I like it!",   "rating":"7"  }
    PUT   localhost:8080/api/feedback/<foodItemName>     - update feedback by fooditem for logged in user
	                 {   "comment":"I like it!",   "rating":"5"  }
    DELETE   localhost:8080/api/feedback/<foodItemName>  - delete feedback by fooditem for logged in user
