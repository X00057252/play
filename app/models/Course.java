package models;

// import Play Framework Validation class
import play.data.validation.Constraints;

// import Java persistence and ebean:
import javax.persistence.*;
import play.db.ebean.Model;
import play.db.ebean.*;

import java.util.ArrayList;
import java.util.List;

// annotate to mark this class as an entity
// It will be mapped to a Course table in DB
// Entity extends Model
@Entity
public class Course extends Model {
 
  // add an ID field
  // mapped to primary key field 'Id' in the Course table
  @Id
  public Long courseId;

  
 // Annotate Required fields
 @Constraints.Required
  public String courseType; //general, exam or acc year
 @Constraints.Required
  public String courseName;
 @Constraints.Required
  public String courseDescription;
 @Constraints.Required
  public String courseMornEvn;  // are classes on mornings or evening
 @Constraints.Required
  public int courseLength;  // length in weeks
 @Constraints.Required
  public Double coursePPW;  //price per week
 
  
  public Course() {
  }

  public Course(String courseType, String courseName, String courseDescription,
				String courseMornEvn, Double coursePPW){
    this.courseType = courseType;
    this.courseName = courseName;
    this.courseDescription = courseDescription;
    this.courseMornEvn = courseMornEvn;
    if (coursePPW < 0) {
		this.coursePPW = 0;
	}
	else {
		this.coursePPW = coursePPW;
	}
  }
  
  
  
  
  // An Ebean 'helper' which simplifies querying of the model.
  // It specifies that Courses are identified by the Id field (which is of type Long).
  public static Finder<Long, Course> find = new Finder<Long, Course>(Long.class, Course.class);
  
  // Call the find.all() method of Course (inherited from the Ebean Model)
  // Return the list of Courses found in the database
  public static List<Course> findAll() {
    return Course.find.all();
  }

  
  
  
}
