package com.amplifyframework.datastore.generated.model;

import com.amplifyframework.core.model.annotations.BelongsTo;
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

/** This is an auto generated class representing the Food type in your schema. */
@SuppressWarnings("all")
@ModelConfig(pluralName = "Foods")
@Index(name = "byCategory", fields = {"categoryID"})
public final class Food implements Model {
  public static final QueryField ID = field("Food", "id");
  public static final QueryField NAME = field("Food", "name");
  public static final QueryField DESCRIPTION = field("Food", "description");
  public static final QueryField CREATED_AT = field("Food", "createdAt");
  public static final QueryField UPDATED_AT = field("Food", "updatedAt");
  public static final QueryField CATEGORY = field("Food", "categoryID");
  private final @ModelField(targetType="ID", isRequired = true) String id;
  private final @ModelField(targetType="String", isRequired = true) String name;
  private final @ModelField(targetType="String") String description;
  private final @ModelField(targetType="String", isRequired = true) String createdAt;
  private final @ModelField(targetType="String", isRequired = true) String updatedAt;
  private final @ModelField(targetType="Category") @BelongsTo(targetName = "categoryID", type = Category.class) Category category;
  private final @ModelField(targetType="Ingredient") @HasMany(associatedWith = "food", type = Ingredient.class) List<Ingredient> ingredients = null;
  public String getId() {
      return id;
  }
  
  public String getName() {
      return name;
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
  
  public Category getCategory() {
      return category;
  }
  
  public List<Ingredient> getIngredients() {
      return ingredients;
  }
  
  private Food(String id, String name, String description, String createdAt, String updatedAt, Category category) {
    this.id = id;
    this.name = name;
    this.description = description;
    this.createdAt = createdAt;
    this.updatedAt = updatedAt;
    this.category = category;
  }
  
  @Override
   public boolean equals(Object obj) {
      if (this == obj) {
        return true;
      } else if(obj == null || getClass() != obj.getClass()) {
        return false;
      } else {
      Food food = (Food) obj;
      return ObjectsCompat.equals(getId(), food.getId()) &&
              ObjectsCompat.equals(getName(), food.getName()) &&
              ObjectsCompat.equals(getDescription(), food.getDescription()) &&
              ObjectsCompat.equals(getCreatedAt(), food.getCreatedAt()) &&
              ObjectsCompat.equals(getUpdatedAt(), food.getUpdatedAt()) &&
              ObjectsCompat.equals(getCategory(), food.getCategory());
      }
  }
  
  @Override
   public int hashCode() {
    return new StringBuilder()
      .append(getId())
      .append(getName())
      .append(getDescription())
      .append(getCreatedAt())
      .append(getUpdatedAt())
      .append(getCategory())
      .toString()
      .hashCode();
  }
  
  @Override
   public String toString() {
    return new StringBuilder()
      .append("Food {")
      .append("id=" + String.valueOf(getId()) + ", ")
      .append("name=" + String.valueOf(getName()) + ", ")
      .append("description=" + String.valueOf(getDescription()) + ", ")
      .append("createdAt=" + String.valueOf(getCreatedAt()) + ", ")
      .append("updatedAt=" + String.valueOf(getUpdatedAt()) + ", ")
      .append("category=" + String.valueOf(getCategory()))
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
  public static Food justId(String id) {
    return new Food(
      id,
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
      description,
      createdAt,
      updatedAt,
      category);
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
    Food build();
    BuildStep id(String id);
    BuildStep description(String description);
    BuildStep category(Category category);
  }
  

  public static class Builder implements NameStep, CreatedAtStep, UpdatedAtStep, BuildStep {
    private String id;
    private String name;
    private String createdAt;
    private String updatedAt;
    private String description;
    private Category category;
    @Override
     public Food build() {
        String id = this.id != null ? this.id : UUID.randomUUID().toString();
        
        return new Food(
          id,
          name,
          description,
          createdAt,
          updatedAt,
          category);
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
     public BuildStep description(String description) {
        this.description = description;
        return this;
    }
    
    @Override
     public BuildStep category(Category category) {
        this.category = category;
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
    private CopyOfBuilder(String id, String name, String description, String createdAt, String updatedAt, Category category) {
      super.id(id);
      super.name(name)
        .createdAt(createdAt)
        .updatedAt(updatedAt)
        .description(description)
        .category(category);
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
     public CopyOfBuilder description(String description) {
      return (CopyOfBuilder) super.description(description);
    }
    
    @Override
     public CopyOfBuilder category(Category category) {
      return (CopyOfBuilder) super.category(category);
    }
  }
  
}
