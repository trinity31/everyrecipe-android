package com.amplifyframework.datastore.generated.model;

import com.amplifyframework.core.model.annotations.HasMany;

import java.util.List;
import java.util.UUID;
import java.util.Objects;

import androidx.core.util.ObjectsCompat;

import com.amplifyframework.core.model.Model;
import com.amplifyframework.core.model.annotations.Index;
import com.amplifyframework.core.model.annotations.ModelConfig;
import com.amplifyframework.core.model.annotations.ModelField;
import com.amplifyframework.core.model.query.predicate.QueryField;

import static com.amplifyframework.core.model.query.predicate.QueryField.field;

/** This is an auto generated class representing the Category type in your schema. */
@SuppressWarnings("all")
@ModelConfig(pluralName = "Categories")
@Index(name = "categoryByOrder", fields = {"order","createdAt"})
public final class Category implements Model {
  public static final QueryField ID = field("Category", "id");
  public static final QueryField NAME = field("Category", "name");
  public static final QueryField ORDER = field("Category", "order");
  public static final QueryField VEGTYPE = field("Category", "vegtype");
  public static final QueryField DESCRIPTION = field("Category", "description");
  public static final QueryField CREATED_AT = field("Category", "createdAt");
  public static final QueryField UPDATED_AT = field("Category", "updatedAt");
  private final @ModelField(targetType="ID", isRequired = true) String id;
  private final @ModelField(targetType="String", isRequired = true) String name;
  private final @ModelField(targetType="Int") Integer order;
  private final @ModelField(targetType="String") String vegtype;
  private final @ModelField(targetType="String") String description;
  private final @ModelField(targetType="String", isRequired = true) String createdAt;
  private final @ModelField(targetType="String", isRequired = true) String updatedAt;
  private final @ModelField(targetType="Food") @HasMany(associatedWith = "category", type = Food.class) List<Food> foods = null;
  public String getId() {
      return id;
  }
  
  public String getName() {
      return name;
  }
  
  public Integer getOrder() {
      return order;
  }
  
  public String getVegtype() {
      return vegtype;
  }
  
  public String getDescription() {
      return description;
  }
  
  public String getCreatedAt() {
      return createdAt;
  }
  
  public String getUpdatedAt() {
      return updatedAt;
  }
  
  public List<Food> getFoods() {
      return foods;
  }
  
  private Category(String id, String name, Integer order, String vegtype, String description, String createdAt, String updatedAt) {
    this.id = id;
    this.name = name;
    this.order = order;
    this.vegtype = vegtype;
    this.description = description;
    this.createdAt = createdAt;
    this.updatedAt = updatedAt;
  }
  
  @Override
   public boolean equals(Object obj) {
      if (this == obj) {
        return true;
      } else if(obj == null || getClass() != obj.getClass()) {
        return false;
      } else {
      Category category = (Category) obj;
      return ObjectsCompat.equals(getId(), category.getId()) &&
              ObjectsCompat.equals(getName(), category.getName()) &&
              ObjectsCompat.equals(getOrder(), category.getOrder()) &&
              ObjectsCompat.equals(getVegtype(), category.getVegtype()) &&
              ObjectsCompat.equals(getDescription(), category.getDescription()) &&
              ObjectsCompat.equals(getCreatedAt(), category.getCreatedAt()) &&
              ObjectsCompat.equals(getUpdatedAt(), category.getUpdatedAt());
      }
  }
  
  @Override
   public int hashCode() {
    return new StringBuilder()
      .append(getId())
      .append(getName())
      .append(getOrder())
      .append(getVegtype())
      .append(getDescription())
      .append(getCreatedAt())
      .append(getUpdatedAt())
      .toString()
      .hashCode();
  }
  
  @Override
   public String toString() {
    return new StringBuilder()
      .append("Category {")
      .append("id=" + String.valueOf(getId()) + ", ")
      .append("name=" + String.valueOf(getName()) + ", ")
      .append("order=" + String.valueOf(getOrder()) + ", ")
      .append("vegtype=" + String.valueOf(getVegtype()) + ", ")
      .append("description=" + String.valueOf(getDescription()) + ", ")
      .append("createdAt=" + String.valueOf(getCreatedAt()) + ", ")
      .append("updatedAt=" + String.valueOf(getUpdatedAt()))
      .append("}")
      .toString();
  }
  
  public static NameStep builder() {
      return new Builder();
  }
  
  /** 
   * WARNING: This method should not be used to build an instance of this object for a CREATE mutation.
   * This is a convenience method to return an instance of the object with only its ID populated
   * to be used in the context of a parameter in a delete mutation or referencing a foreign key
   * in a relationship.
   * @param id the id of the existing item this instance will represent
   * @return an instance of this model with only ID populated
   */
  public static Category justId(String id) {
    return new Category(
      id,
      null,
      null,
      null,
      null,
      null,
      null
    );
  }
  
  public CopyOfBuilder copyOfBuilder() {
    return new CopyOfBuilder(id,
      name,
      order,
      vegtype,
      description,
      createdAt,
      updatedAt);
  }
  public interface NameStep {
    CreatedAtStep name(String name);
  }
  

  public interface CreatedAtStep {
    UpdatedAtStep createdAt(String createdAt);
  }
  

  public interface UpdatedAtStep {
    BuildStep updatedAt(String updatedAt);
  }
  

  public interface BuildStep {
    Category build();
    BuildStep id(String id);
    BuildStep order(Integer order);
    BuildStep vegtype(String vegtype);
    BuildStep description(String description);
  }
  

  public static class Builder implements NameStep, CreatedAtStep, UpdatedAtStep, BuildStep {
    private String id;
    private String name;
    private String createdAt;
    private String updatedAt;
    private Integer order;
    private String vegtype;
    private String description;
    @Override
     public Category build() {
        String id = this.id != null ? this.id : UUID.randomUUID().toString();
        
        return new Category(
          id,
          name,
          order,
          vegtype,
          description,
          createdAt,
          updatedAt);
    }
    
    @Override
     public CreatedAtStep name(String name) {
        Objects.requireNonNull(name);
        this.name = name;
        return this;
    }
    
    @Override
     public UpdatedAtStep createdAt(String createdAt) {
        Objects.requireNonNull(createdAt);
        this.createdAt = createdAt;
        return this;
    }
    
    @Override
     public BuildStep updatedAt(String updatedAt) {
        Objects.requireNonNull(updatedAt);
        this.updatedAt = updatedAt;
        return this;
    }
    
    @Override
     public BuildStep order(Integer order) {
        this.order = order;
        return this;
    }
    
    @Override
     public BuildStep vegtype(String vegtype) {
        this.vegtype = vegtype;
        return this;
    }
    
    @Override
     public BuildStep description(String description) {
        this.description = description;
        return this;
    }
    
    /** 
     * @param id id
     * @return Current Builder instance, for fluent method chaining
     */
    public BuildStep id(String id) {
        this.id = id;
        return this;
    }
  }
  

  public final class CopyOfBuilder extends Builder {
    private CopyOfBuilder(String id, String name, Integer order, String vegtype, String description, String createdAt, String updatedAt) {
      super.id(id);
      super.name(name)
        .createdAt(createdAt)
        .updatedAt(updatedAt)
        .order(order)
        .vegtype(vegtype)
        .description(description);
    }
    
    @Override
     public CopyOfBuilder name(String name) {
      return (CopyOfBuilder) super.name(name);
    }
    
    @Override
     public CopyOfBuilder createdAt(String createdAt) {
      return (CopyOfBuilder) super.createdAt(createdAt);
    }
    
    @Override
     public CopyOfBuilder updatedAt(String updatedAt) {
      return (CopyOfBuilder) super.updatedAt(updatedAt);
    }
    
    @Override
     public CopyOfBuilder order(Integer order) {
      return (CopyOfBuilder) super.order(order);
    }
    
    @Override
     public CopyOfBuilder vegtype(String vegtype) {
      return (CopyOfBuilder) super.vegtype(vegtype);
    }
    
    @Override
     public CopyOfBuilder description(String description) {
      return (CopyOfBuilder) super.description(description);
    }
  }
  
}
