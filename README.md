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

      GET  localhost:8080/api/menu/today - Get menu for today

<h3>ORDER API</h3> 

 ADMIN:
 
    GET       localhost:8080/api/order                      - retrieve all order list
    GET       localhost:8080/api/order/<date>               - retrieve all order list by date entered
    GET       localhost:8080/api/order/<date>/<username>    - retrieve order by date and username entered
    DELETE    localhost:8080/api/order/<date>/<username>    - Delete order by date and username entered
    
    PUT       localhost:8080/api/order/<date>/<username>    - Update orderStatus by date and username
    						   {  "status":"INPROCESS" }
   
 EMPLOYEE : 

    GET	    localhost:8080/api/order/<date>   - retrieve order list by date entered of logged in user
    GET     localhost:8080/api/order          - retrieve all order list made by logged in user
    POST    localhost:8080/api/order          - Create order for logged in user 
                                   {"foodItems":["Chicken Khaja Set","Samosa"], "date":"2020-09-15"}
				                           If date is not provided, it will save with local date
    PUT     localhost:8080/api/order/<date>   - Update logged in user order    
                                   {"foodItems":["Pakoda","Samosa"] }
                                 
    DELETE  localhost:8080/api/order/<date>   - Delete order of logged in user by date entered

<h3>FOODREQUEST API</h3>


 ADMIN

    GET    localhost:8080/api/foodrequest                - retrieve all foodrequest list
    GET    localhost:8080/api/foodrequest/today          - retrieve todays food request
    GET    localhost:8080/api/foodrequest/today/popular  - retrieve todays food request by popularity
 
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

 EMPLOYEE

    GET localhost:8080/api/feedback                      - retrieve feedback list of user logged in 
    POST localhost:8080/api/feedback                     - create new feedback
                        {   "foodItemName":"Pakoda",   "comment":"I like it!",   "rating":"7"  }
    PUT   localhost:8080/api/feedback/<foodItemName>     - update feedback by fooditem for logged in user
	                 {   "comment":"I like it!",   "rating":"5"  }
    DELETE   localhost:8080/api/feedback/<foodItemName>  - delete feedback by fooditem for logged in user
