package com.amplifyframework.datastore.generated.model;

import com.amplifyframework.core.model.annotations.BelongsTo;

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

/** This is an auto generated class representing the Ingredient type in your schema. */
@SuppressWarnings("all")
@ModelConfig(pluralName = "Ingredients")
@Index(name = "byFood", fields = {"foodID"})
@Index(name = "byName", fields = {"name"})
public final class Ingredient implements Model {
  public static final QueryField ID = field("Ingredient", "id");
  public static final QueryField CATEGORY_ID = field("Ingredient", "categoryID");
  public static final QueryField CATEGORY = field("Ingredient", "category");
  public static final QueryField NAME = field("Ingredient", "name");
  public static final QueryField DESCRIPTION = field("Ingredient", "description");
  public static final QueryField CREATED_AT = field("Ingredient", "createdAt");
  public static final QueryField UPDATED_AT = field("Ingredient", "updatedAt");
  public static final QueryField FOOD = field("Ingredient", "foodID");
  private final @ModelField(targetType="ID", isRequired = true) String id;
  private final @ModelField(targetType="ID") String categoryID;
  private final @ModelField(targetType="String") String category;
  private final @ModelField(targetType="String", isRequired = true) String name;
  private final @ModelField(targetType="String") String description;
  private final @ModelField(targetType="String", isRequired = true) String createdAt;
  private final @ModelField(targetType="String", isRequired = true) String updatedAt;
  private final @ModelField(targetType="Food") @BelongsTo(targetName = "foodID", type = Food.class) Food food;
  public String getId() {
      return id;
  }
  
  public String getCategoryId() {
      return categoryID;
  }
  
  public String getCategory() {
      return category;
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
  
  public Food getFood() {
      return food;
  }
  
  private Ingredient(String id, String categoryID, String category, String name, String description, String createdAt, String updatedAt, Food food) {
    this.id = id;
    this.categoryID = categoryID;
    this.category = category;
    this.name = name;
    this.description = description;
    this.createdAt = createdAt;
    this.updatedAt = updatedAt;
    this.food = food;
  }
  
  @Override
   public boolean equals(Object obj) {
      if (this == obj) {
        return true;
      } else if(obj == null || getClass() != obj.getClass()) {
        return false;
      } else {
      Ingredient ingredient = (Ingredient) obj;
      return ObjectsCompat.equals(getId(), ingredient.getId()) &&
              ObjectsCompat.equals(getCategoryId(), ingredient.getCategoryId()) &&
              ObjectsCompat.equals(getCategory(), ingredient.getCategory()) &&
              ObjectsCompat.equals(getName(), ingredient.getName()) &&
              ObjectsCompat.equals(getDescription(), ingredient.getDescription()) &&
              ObjectsCompat.equals(getCreatedAt(), ingredient.getCreatedAt()) &&
              ObjectsCompat.equals(getUpdatedAt(), ingredient.getUpdatedAt()) &&
              ObjectsCompat.equals(getFood(), ingredient.getFood());
      }
  }
  
  @Override
   public int hashCode() {
    return new StringBuilder()
      .append(getId())
      .append(getCategoryId())
      .append(getCategory())
      .append(getName())
      .append(getDescription())
      .append(getCreatedAt())
      .append(getUpdatedAt())
      .append(getFood())
      .toString()
      .hashCode();
  }
  
  @Override
   public String toString() {
    return new StringBuilder()
      .append("Ingredient {")
      .append("id=" + String.valueOf(getId()) + ", ")
      .append("categoryID=" + String.valueOf(getCategoryId()) + ", ")
      .append("category=" + String.valueOf(getCategory()) + ", ")
      .append("name=" + String.valueOf(getName()) + ", ")
      .append("description=" + String.valueOf(getDescription()) + ", ")
      .append("createdAt=" + String.valueOf(getCreatedAt()) + ", ")
      .append("updatedAt=" + String.valueOf(getUpdatedAt()) + ", ")
      .append("food=" + String.valueOf(getFood()))
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
  public static Ingredient justId(String id) {
    return new Ingredient(
      id,
      null,
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
      categoryID,
      category,
      name,
      description,
      createdAt,
      updatedAt,
      food);
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
    Ingredient build();
    BuildStep id(String id);
    BuildStep categoryId(String categoryId);
    BuildStep category(String category);
    BuildStep description(String description);
    BuildStep food(Food food);
  }
  

  public static class Builder implements NameStep, CreatedAtStep, UpdatedAtStep, BuildStep {
    private String id;
    private String name;
    private String createdAt;
    private String updatedAt;
    private String categoryID;
    private String category;
    private String description;
    private Food food;
    @Override
     public Ingredient build() {
        String id = this.id != null ? this.id : UUID.randomUUID().toString();
        
        return new Ingredient(
          id,
          categoryID,
          category,
          name,
          description,
          createdAt,
          updatedAt,
          food);
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
     public BuildStep categoryId(String categoryId) {
        this.categoryID = categoryId;
        return this;
    }
    
    @Override
     public BuildStep category(String category) {
        this.category = category;
        return this;
    }
    
    @Override
     public BuildStep description(String description) {
        this.description = description;
        return this;
    }
    
    @Override
     public BuildStep food(Food food) {
        this.food = food;
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
    private CopyOfBuilder(String id, String categoryId, String category, String name, String description, String createdAt, String updatedAt, Food food) {
      super.id(id);
      super.name(name)
        .createdAt(createdAt)
        .updatedAt(updatedAt)
        .categoryId(categoryId)
        .category(category)
        .description(description)
        .food(food);
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
     public CopyOfBuilder categoryId(String categoryId) {
      return (CopyOfBuilder) super.categoryId(categoryId);
    }
    
    @Override
     public CopyOfBuilder category(String category) {
      return (CopyOfBuilder) super.category(category);
    }
    
    @Override
     public CopyOfBuilder description(String description) {
      return (CopyOfBuilder) super.description(description);
    }
    
    @Override
     public CopyOfBuilder food(Food food) {
      return (CopyOfBuilder) super.food(food);
    }
  }
  
}
