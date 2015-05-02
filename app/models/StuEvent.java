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
// It will be mapped to a StuEvent table in DB
// Entity extends Model
@Entity
public class StuEvent extends Model {
 
  // add an ID field
  // mapped to primary key field 'Id' in the StuEvent table
  @Id
  @GeneratedValue(strategy=GenerationType.IDENTITY)
  public Long stuEventId;

  
 // Annotate Required fields
 @Constraints.Required
  public Long eventID;
 @Constraints.Required
  public Long studentID;

  
  public StuEvent() {
  }

  public StuEvent(Long eventID, Long studentID) {
    this.eventID = eventID;
    this.studentID = studentID;
  }
  
  
  
  
  // An Ebean 'helper' which simplifies querying of the model.
  // It specifies that StuEvents are identified by the Id field (which is of type Long).
  public static Finder<Long, StuEvent> find = new Finder<Long, StuEvent>(Long.class, StuEvent.class);
  
  // Call the find.all() method of StuEvent (inherited from the Ebean Model)
  // Return the list of StuEvents found in the database
  public static List<StuEvent> findAll() {
    return StuEvent.find.all();
  }

    public static StuEvent findByStuIdEventId(Long studentId, Long eventId) {
        return StuEvent.find.where().eq("studentID", studentId).eq("eventID", eventId).findUnique();
    }

  
  
}
