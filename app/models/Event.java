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
import java.util.*;

// annotate to mark this class as an entity
// It will be mapped to a Event table in DB
// Entity extends Model
@Entity
public class Event extends Model {
 
  // add an ID field
  // mapped to primary key field 'Id' in the Event table
  @Id
  @GeneratedValue(strategy=GenerationType.IDENTITY)
  public Long eventId;

  
 // Annotate Required fields
 @Constraints.Required
  public String eventName;
 @Constraints.Required
  public String eventDescription1;
  public String eventDescription2;
  public String eventDescription3;   
 @Constraints.Required
 @Formats.DateTime(pattern="dd/MM/yyyy")
  public Date eventDate;
 @Constraints.Required
  public String eventTime;
 @Constraints.Required
  public int eventMaxCapacity;  
  @Constraints.Required
  public int eventCurrCapacity; 
    @Transient
    public String status;
  
  public Event() {
  }

  public Event(String eventName, String eventDescription1, String eventDescription2, 
			   String eventDescription3, Date eventDate, String eventTime,
			   int eventMaxCapacity, int eventCurrCapacity) {
    this.eventName = eventName;
    this.eventDescription1 = eventDescription1;
    this.eventDescription2 = eventDescription2;
    this.eventDescription3 = eventDescription3;
    this.eventDate = eventDate;
    this.eventTime = eventTime;
    this.eventMaxCapacity = eventMaxCapacity;
    this.eventCurrCapacity = eventCurrCapacity;
  }
  
  
  
  
  // An Ebean 'helper' which simplifies querying of the model.
  // It specifies that Events are identified by the Id field (which is of type Long).
  public static Finder<Long, Event> find = new Finder<Long, Event>(Long.class, Event.class);
  

  // Return the future event:
  public static List<Event> findEvent() {
	Date today = new Date(); 
	
	return Event.find.where()
		.gt("eventDate", today)
	.findList();
  }

    public static Event findById(Long id) {
        return Event.find.where().eq("eventId", id).findUnique();
    }
  
}
