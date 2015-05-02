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
  @GeneratedValue(strategy=GenerationType.IDENTITY)
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
  public int coursePPW;  //price per week
 
  
  public Course() {
  }

  public Course(String courseType, String courseName, String courseDescription,
				String courseMornEvn, int coursePPW){
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
  


  // Return the list of General English Courses:
  public static List<Course> findGeneral() {
    return Course.find.where()
		.eq("courseType", "general")
		.orderBy("courseLength, courseMornEvn desc")
	.findList();
  }  
 
  // Return the list of Exam Preparation Courses:
  public static List<Course> findExamPrep() {
    return Course.find.where()
		.eq("courseType", "examprep")
	.findList();
  }  
  
  // Return the list of Accademic Year programmes:
  public static List<Course> findAcYear() {
    return Course.find.where()
		.eq("courseType", "acyear")
	.findList();
  }

    public static Course findById(Long courseId) {
        return Course.find.where().eq("courseId", courseId).findUnique();
    }
  
}
