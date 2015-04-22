package models;

// import Play Framework Validation class
import play.data.validation.Constraints;

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
  public Long eventId;

  
 // Annotate Required fields
 @Constraints.Required
  public String eventName;
 @Constraints.Required
  public String eventDescription;
 @Constraints.Required
  public String eventDate;
 @Constraints.Required
  public String eventTime;
 @Constraints.Required
  public int eventMaxCapacity;  
  @Constraints.Required
  public int eventCurrCapacity; 
 
  
  public Event() {
  }

  public Event(String eventName, String eventDescription, String eventDate, String eventTime,
			   int eventMaxCapacity, int eventCurrCapacity) {
    this.eventName = eventName;
    this.eventDescription = eventDescription;
    this.eventDate = eventDate;
    this.eventTime = eventTime;
    this.eventMaxCapacity = eventMaxCapacity;
    this.eventCurrCapacity = eventCurrCapacity;
  }
  
  
  
  
  // An Ebean 'helper' which simplifies querying of the model.
  // It specifies that Events are identified by the Id field (which is of type Long).
  public static Finder<Long, Event> find = new Finder<Long, Event>(Long.class, Event.class);
  
  // Call the find.all() method of Event (inherited from the Ebean Model)
  // Return the list of Events found in the database
  public static List<Event> findAll() {
    return Event.find.all();
  }

  
  
  
}
