package com.example.demo.controller;

import java.util.ArrayList;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.User;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.repository.UserRepository;

@RequestMapping("api/users")//This annotation is used to specify the base URL mapping for the controller.all the HTTP endpoints in this controller will be relative to the base URL "api/users".
@RestController//@RestController annotation: This annotation is used to indicate that the class is a RESTful controller that handles HTTP requests and returns the response directly.
public class UserController {

	@Autowired//@Autowired annotation: This annotation is used to inject an instance of the
	private UserRepository userRepository;
	
	@GetMapping//@GetMapping without a specific path, which means it will handle GET requests to the base URL "api/users".
	public ArrayList<User> getAllusers(){
		
		return (ArrayList<User>) this.userRepository.findAll();
		
	}
	
	@GetMapping("/{uid}")
	public User getUserById(@PathVariable (value = "uid") long userId ) {
		
		return this.userRepository.findById(userId).orElseThrow(() ->new ResourceNotFoundException("user not Fondd with id : "+userId));
	}
	
	
	@PostMapping
	public User createUser(@Valid @RequestBody User user) {// we will be requiring request body to get data to fill
		// The @Valid annotation is used for validating the input data based on any
		// validation annotations specified in the User class, such as @NotNull, @NotBlank, or @Size
		ArrayList<User> allUsers=getAllusers();
		for(int i=0;i<allUsers.size();i++) {
			User curr=allUsers.get(i);
			
			if((curr.getFirstName().compareTo(user.getFirstName())==0) && (curr.getLastName().compareTo(user.getLastName())==0)) {
				throw new ResourceNotFoundException("This User Name already Exist");
		     }
			else if((curr.getEmail().compareTo(user.getEmail())==0)) {
				throw new ResourceNotFoundException("This Mail already Exist");
			}
			else if ((curr.getMobile().compareTo(user.getMobile())==0)){
				throw new ResourceNotFoundException("This Mobile alreeady Exist");
			}
			
		}
		
		return this.userRepository.save(user);
		
	}
	
	@PutMapping("/{uid}")
	public User updateUser(@RequestBody User user, @PathVariable("uid") long userId) {
		/*

		    @RequestBody User user: This annotation binds the request body to the user parameter, which represents the updated user information sent in the PUT request. The User object contains the updated values for the user's properties.

    @PathVariable("uid") long userId: This annotation binds the path variable {uid} to the userId parameter. It retrieves the user ID from the URL and assigns it to the userId variable.
		*/
		
		User existingUser = this.userRepository.findById(userId).orElseThrow(() ->new ResourceNotFoundException("user not Fondd with id : "+userId));
		
		existingUser.setFirstName(user.getFirstName());
		existingUser.setLastName(user.getLastName());
		existingUser.setEmail(user.getEmail());
		existingUser.setAddress1(user.getAddress1());
		existingUser.setAddress2(user.getAddress2());
		existingUser.setMobile(user.getMobile());
		

		// saves or updates the existingUser object in the database using the save() . After the save() method is executed, it returns the saved or updated User object.
		return this.userRepository.save(existingUser);//this will directly save into database
	}

	// it will return a ResponseEntity containing the deleted user object if the deletion is successful.
	@DeleteMapping("/{uid}")
	public ResponseEntity<User> deleteUser(@PathVariable("uid") long userId){
		
		User existingUser = this.userRepository.findById(userId).orElseThrow(() ->new ResourceNotFoundException("user not Fondd with id : "+userId));
		this.userRepository.delete(existingUser);
		//After successfully deleting the user, this line returns a ResponseEntity with an "OK" status code (200)
		// using ResponseEntity.ok(). The build() method is called to create the response entity without
		// providing any body content.
		return ResponseEntity.ok().build();

		
	}
	/* Path variable vs Request Variable
	    @PathVariable:
        @PathVariable is used to extract a specific part of the URL path and bind it to a method parameter.
        It is typically used to capture dynamic values from the URL, such as IDs or names.
        Example: @GetMapping("/users/{id}"), where {id} is the path variable.

    Request Variable:
        Request variables are used to extract data from the query parameters or the request body.
        Query parameters are key-value pairs appended to the URL, such as ?key1=value1&key2=value2.
        Request variables can also be used to extract form data or JSON data from the request body.
        Example: @GetMapping("/users"), where the request can include query parameters like /users?key=value.

Here's a comparison between @PathVariable and request variables:

    Usage:
        @PathVariable is used when you want to extract a specific part of the URL path.
        Request variables are used when you want to extract data from query parameters or the request body.

    Syntax:
        @PathVariable is annotated on a method parameter, preceded by the variable name in curly braces within the URL mapping.
        Request variables can be accessed using annotations like @RequestParam, @RequestBody, or implicitly using method parameters.

    Data Source:
        @PathVariable extracts data from the URL path.
        Request variables extract data from query parameters or the request body.

    Example:
        For a URL like /users/{id}, you can use @PathVariable to extract the id from the URL path.
        For a URL like /users?key=value, you can use request variables to extract the key and value from the query parameters.

In summary, @PathVariable is used to extract data from the URL path, while request variables are used to extract data from query parameters or the request body. The choice between them depends on where the data is located within the request.
	* */
}
