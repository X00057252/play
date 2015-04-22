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
// It will be mapped to a StuCourse table in DB
// Entity extends Model
@Entity
public class StuCourse extends Model {
 
  // add an ID field
  // mapped to primary key field 'Id' in the StuCourse table
  @Id
  public Long stuCourseId;

  
 // Annotate Required fields
 @Constraints.Required
  public Long studentId;
 @Constraints.Required
  public Long courseId;
 @Constraints.Required
  public String courseStatus;
 @Constraints.Required
  public String courseStartDate;
 @Constraints.Required
  public String courseEndDate; 
 @Constraints.Required
  public String courseLevel; 
 @Constraints.Required
  public int courseAttendance; // int 1 to 100 indicates % of attendance
 @Constraints.Required
  public String courseAccommodation;  // host family or private rental
 @Constraints.Required
  public Boolean courseIsPaid;  
 
  public StuCourse() {
  }

  public StuCourse(Long studentId, Long courseId, String courseStatus, 
				   String courseStartDate, String courseEndDate,
				   String courseLevel, int courseAttendance, String courseAccommodation,
				   Boolean courseIsPaid) {
    this.studentId = studentId;
    this.courseId = courseId;
    this.courseStatus = courseStatus;
    this.courseStartDate = courseStartDate;
    this.courseEndDate = courseEndDate;
    this.courseLevel = courseLevel;
    this.courseAttendance = courseAttendance;
    this.courseAccommodation = courseAccommodation;	
	this.courseIsPaid = courseIsPaid;
  }
  
  
  
  
  // An Ebean 'helper' which simplifies querying of the model.
  // It specifies that StuCourses are identified by the Id field (which is of type Long).
  public static Finder<Long, StuCourse> find = new Finder<Long, StuCourse>(Long.class, StuCourse.class);
  
  // Call the find.all() method of StuCourse (inherited from the Ebean Model)
  // Return the list of StuCourses found in the database
  public static List<StuCourse> findAll() {
    return StuCourse.find.all();
  }

  
  
  
}
