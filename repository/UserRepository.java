package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.User;

//The @Repository annotation is a specialization of the @Component annotation and is used to indicate that the interface is a repository component, providing data access and persistence operations.
@Repository//@Repository to enable Spring to manage the repository bean.
public interface UserRepository extends JpaRepository<User,Long>{
    //JpaRepository is a Spring Data JPA interface that provides built-in methods for common data access operations, such as saving, updating, deleting, and querying entities.
    // the UserRepository inherits these methods and can also define additional custom methods if needed.

    //The @Repository annotation is a stereotype annotation that is itself annotated with @Component.
    //when you annotate an interface with @Repository, it is treated as a bean and can
    // benefit from the features provided by the Spring container for component scanning,
    // dependency injection, and lifecycle management.

    //By using @Repository, you enable Spring to automatically detect and create an instance of the repository bean during application startup.
    // The repository bean acts as a bridge between your application and the underlying data source, providing a way to interact with the data and perform CRUD operations.


    //User represents the entity class associated with the repository.
    //JpaRepository<User, Long> indicates that the UserRepository interface extends the JpaRepository interface, which is a Spring Data JPA interface providing CRUD operations and additional query methods for the User entity class.
    //The Long type represents the data type of the primary key in the User entity class.
}
