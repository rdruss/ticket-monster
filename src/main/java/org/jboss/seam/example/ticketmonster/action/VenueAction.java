package org.jboss.seam.example.ticketmonster.action;

import java.io.Serializable;

import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;

import org.jboss.seam.example.ticketmonster.model.Venue;
import org.jboss.seam.persistence.transaction.Transactional;

/**
 * Venue action bean
 * 
 * @author Shane Bryzak
 *
 */
public @Named @ConversationScoped class VenueAction implements Serializable
{
   private static final long serialVersionUID = 2646300975197236221L;
  
   @Inject Conversation conversation;
   @Inject EntityManager entityManager;
   
   private Long venueId;
   private Venue venue;   
   
   public void createVenue()
   {
      conversation.begin();
      venue = new Venue();
   }
 
   public void loadVenue()
   {
      // Only load the venue if a venueId has been provided
      if (venueId != null)
      {      
         conversation.begin();      
         venue = entityManager.find(Venue.class, venueId);
      }
   }
   
   public @Transactional String save()
   {
      if (venue.getId() != null)
      {
         entityManager.merge(venue);
      }
      else
      {
         entityManager.persist(venue);
      }      
      
      conversation.end();
      return "success";
   }
   
   public void cancel()
   {
      conversation.end();
   }
   
   public Venue getVenue()
   {
      return venue;
   }
   
   public Long getVenueId()
   {
      return venueId;
   }
   
   public void setVenueId(Long venueId)
   {
      this.venueId = venueId;
   }
   
}