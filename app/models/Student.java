/* ===========================================================

	IT Tallaght, 
	Bart Bula, X00107883, 
	Andro Haavandi, X00057252
	April 2015 

   =========================================================== 
*/



package models;

// import Play Framework Validation class
import play.data.validation.Constraints;
import play.data.format.Formats;

// import Java persistence and ebean:
import javax.persistence.*;
import play.db.ebean.Model;
import play.db.ebean.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Date;

// annotate to mark this class as an entity
// It will be mapped to a Student table in DB
// Entity extends Model
@Entity
public class Student extends Model {
 
  // add an ID field
  // mapped to primary key field 'Id' in the Student table
  @Id
  public Long studentId;

  
 // Annotate Required fields
 // account info:
 @Constraints.Required
  public String studentUsername;
 @Constraints.Required
  public String studentPassword;
 // name: 
 @Constraints.Required
  public String studentFName;
 @Constraints.Required
  public String studentLName;
 // gender (not required): 
  public String studentGender;
 // nationality and date of birth: 
 @Constraints.Required
  public String studentNationality;
 @Constraints.Required
 @Formats.DateTime(pattern="dd/MM/yyyy")
  public Date studentDOB; 
 @Constraints.Required
// address: 
  public String studentStreet;  
 @Constraints.Required
  public String studentCity; 
  public String studentProvince;  
 @Constraints.Required
  public String studentCountry; 
 // contact details: 
  public String studentPhone;
   // Valid email required
 @Constraints.Required
 @Constraints.Email
  public String studentEmail;
 
  
  public Student() {
  }

  public Student(String studentUsername, String studentPassword, 
			     String studentFName, String studentLName, String studentGender,
				 String studentNationality, Date studentDOB,
				 String studentStreet, String studentCity, String studentProvince, String studentCountry,
				 String studentPhone, String studentEmail) {
    this.studentUsername = studentUsername;
    this.studentPassword = studentPassword;
    this.studentFName = studentFName;
    this.studentLName = studentLName;	
    this.studentGender = studentGender;	
    this.studentNationality = studentNationality;	
    this.studentDOB = studentDOB;	
    this.studentStreet = studentStreet;	
    this.studentCity = studentCity;	
    this.studentProvince = studentProvince;		
    this.studentCountry = studentCountry;	
    this.studentPhone = studentPhone;	
    this.studentEmail = studentEmail;		
  }
  
  
  
  
  // An Ebean 'helper' which simplifies querying of the model.
  // It specifies that Students are identified by the Id field (which is of type Long).
  public static Finder<Long, Student> find = new Finder<Long, Student>(Long.class, Student.class);
  
  // Call the find.all() method of Student (inherited from the Ebean Model)
  // Return the list of Students found in the database
  public static List<Student> findAll() {
    return Student.find.all();
  }

      /**
     * Retrieve a Student from studentUsername.
     */
    public static Student findByStudentUsername(String studentUsername) {
        return find.where().eq("studentUsername", studentUsername).findUnique();
    }
  
  /**
     * Authenticate a Student.
     */
    public static Student authenticate(String studentUsername, String studentPassword) {
        return find.where()
            .eq("studentUsername", studentUsername)
            .eq("studentPassword", studentPassword)
            .findUnique();
    }
  
  
}
