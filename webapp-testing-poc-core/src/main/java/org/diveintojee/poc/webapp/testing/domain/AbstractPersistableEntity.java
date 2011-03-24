/**
 * 
 */
package org.diveintojee.poc.webapp.testing.domain;

import java.io.Serializable;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * @author louis.gueye@gmail.com
 */
public class AbstractPersistableEntity implements PersistableEntity, Serializable {

   /**
    * 
    */
   private static final long serialVersionUID = 5640233827691445521L;

   protected Long id;

   /**
    * @see org.diveintojee.poc.webapp.testing.domain.PersistableEntity#getId()
    */
   @Override
   public Long getId() {
      return this.id;
   }

   public void setId(Long id) {
      this.id = id;
   }

   /**
    * @see java.lang.Object#hashCode()
    */
   @Override
   public final int hashCode() {
      final int prime = 31;
      int result = 1;
      result = prime * result + ((id == null) ? 0 : id.hashCode());
      return result;
   }

   /**
    * @see java.lang.Object#equals(java.lang.Object)
    */
   @Override
   public final boolean equals(Object obj) {

      if (this == obj) {
         return true;
      }
      if (obj == null) {
         return false;
      }
      if (!(obj instanceof AbstractPersistableEntity)) {
         return false;
      }
      AbstractPersistableEntity other = (AbstractPersistableEntity) obj;
      if (id == null) {
         if (other.id != null) {
            return false;
         }
      } else if (!id.equals(other.id)) {
         return false;
      }
      return true;
   }

   /**
    * @see java.lang.Object#toString()
    */
   @Override
   public String toString() {
      return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
   }

}
