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

/** This is an auto generated class representing the Recipe type in your schema. */
@SuppressWarnings("all")
@ModelConfig(pluralName = "Recipes")
public final class Recipe implements Model {
  public static final QueryField ID = field("Recipe", "id");
  public static final QueryField NAME = field("Recipe", "name");
  public static final QueryField DESCRIPTION = field("Recipe", "description");
  public static final QueryField NATION = field("Recipe", "nation");
  public static final QueryField TYPE = field("Recipe", "type");
  public static final QueryField COOKING_TIME = field("Recipe", "cookingTime");
  public static final QueryField CALORIE = field("Recipe", "Calorie");
  public static final QueryField QUANTITY = field("Recipe", "Quantity");
  public static final QueryField IMAGE_URL = field("Recipe", "imageUrl");
  public static final QueryField CREATED_AT = field("Recipe", "createdAt");
  public static final QueryField UPDATED_AT = field("Recipe", "updatedAt");
  private final @ModelField(targetType="ID", isRequired = true) String id;
  private final @ModelField(targetType="String", isRequired = true) String name;
  private final @ModelField(targetType="String") String description;
  private final @ModelField(targetType="String") String nation;
  private final @ModelField(targetType="String") String type;
  private final @ModelField(targetType="String") String cookingTime;
  private final @ModelField(targetType="String") String Calorie;
  private final @ModelField(targetType="String") String Quantity;
  private final @ModelField(targetType="String") String imageUrl;
  private final @ModelField(targetType="String", isRequired = true) String createdAt;
  private final @ModelField(targetType="String", isRequired = true) String updatedAt;
  private final @ModelField(targetType="Unit") @HasMany(associatedWith = "recipeID", type = Unit.class) List<Unit> units = null;
  public String getId() {
      return id;
  }
  
  public String getName() {
      return name;
  }
  
  public String getDescription() {
      return description;
  }
  
  public String getNation() {
      return nation;
  }
  
  public String getType() {
      return type;
  }
  
  public String getCookingTime() {
      return cookingTime;
  }
  
  public String getCalorie() {
      return Calorie;
  }
  
  public String getQuantity() {
      return Quantity;
  }
  
  public String getImageUrl() {
      return imageUrl;
  }
  
  public String getCreatedAt() {
      return createdAt;
  }
  
  public String getUpdatedAt() {
      return updatedAt;
  }
  
  public List<Unit> getUnits() {
      return units;
  }
  
  private Recipe(String id, String name, String description, String nation, String type, String cookingTime, String Calorie, String Quantity, String imageUrl, String createdAt, String updatedAt) {
    this.id = id;
    this.name = name;
    this.description = description;
    this.nation = nation;
    this.type = type;
    this.cookingTime = cookingTime;
    this.Calorie = Calorie;
    this.Quantity = Quantity;
    this.imageUrl = imageUrl;
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
      Recipe recipe = (Recipe) obj;
      return ObjectsCompat.equals(getId(), recipe.getId()) &&
              ObjectsCompat.equals(getName(), recipe.getName()) &&
              ObjectsCompat.equals(getDescription(), recipe.getDescription()) &&
              ObjectsCompat.equals(getNation(), recipe.getNation()) &&
              ObjectsCompat.equals(getType(), recipe.getType()) &&
              ObjectsCompat.equals(getCookingTime(), recipe.getCookingTime()) &&
              ObjectsCompat.equals(getCalorie(), recipe.getCalorie()) &&
              ObjectsCompat.equals(getQuantity(), recipe.getQuantity()) &&
              ObjectsCompat.equals(getImageUrl(), recipe.getImageUrl()) &&
              ObjectsCompat.equals(getCreatedAt(), recipe.getCreatedAt()) &&
              ObjectsCompat.equals(getUpdatedAt(), recipe.getUpdatedAt());
      }
  }
  
  @Override
   public int hashCode() {
    return new StringBuilder()
      .append(getId())
      .append(getName())
      .append(getDescription())
      .append(getNation())
      .append(getType())
      .append(getCookingTime())
      .append(getCalorie())
      .append(getQuantity())
      .append(getImageUrl())
      .append(getCreatedAt())
      .append(getUpdatedAt())
      .toString()
      .hashCode();
  }
  
  @Override
   public String toString() {
    return new StringBuilder()
      .append("Recipe {")
      .append("id=" + String.valueOf(getId()) + ", ")
      .append("name=" + String.valueOf(getName()) + ", ")
      .append("description=" + String.valueOf(getDescription()) + ", ")
      .append("nation=" + String.valueOf(getNation()) + ", ")
      .append("type=" + String.valueOf(getType()) + ", ")
      .append("cookingTime=" + String.valueOf(getCookingTime()) + ", ")
      .append("Calorie=" + String.valueOf(getCalorie()) + ", ")
      .append("Quantity=" + String.valueOf(getQuantity()) + ", ")
      .append("imageUrl=" + String.valueOf(getImageUrl()) + ", ")
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
  public static Recipe justId(String id) {
    return new Recipe(
      id,
      null,
      null,
      null,
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
      name,
      description,
      nation,
      type,
      cookingTime,
      Calorie,
      Quantity,
      imageUrl,
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
    Recipe build();
    BuildStep id(String id);
    BuildStep description(String description);
    BuildStep nation(String nation);
    BuildStep type(String type);
    BuildStep cookingTime(String cookingTime);
    BuildStep calorie(String calorie);
    BuildStep quantity(String quantity);
    BuildStep imageUrl(String imageUrl);
  }
  

  public static class Builder implements NameStep, CreatedAtStep, UpdatedAtStep, BuildStep {
    private String id;
    private String name;
    private String createdAt;
    private String updatedAt;
    private String description;
    private String nation;
    private String type;
    private String cookingTime;
    private String Calorie;
    private String Quantity;
    private String imageUrl;
    @Override
     public Recipe build() {
        String id = this.id != null ? this.id : UUID.randomUUID().toString();
        
        return new Recipe(
          id,
          name,
          description,
          nation,
          type,
          cookingTime,
          Calorie,
          Quantity,
          imageUrl,
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
     public BuildStep description(String description) {
        this.description = description;
        return this;
    }
    
    @Override
     public BuildStep nation(String nation) {
        this.nation = nation;
        return this;
    }
    
    @Override
     public BuildStep type(String type) {
        this.type = type;
        return this;
    }
    
    @Override
     public BuildStep cookingTime(String cookingTime) {
        this.cookingTime = cookingTime;
        return this;
    }
    
    @Override
     public BuildStep calorie(String calorie) {
        this.Calorie = calorie;
        return this;
    }
    
    @Override
     public BuildStep quantity(String quantity) {
        this.Quantity = quantity;
        return this;
    }
    
    @Override
     public BuildStep imageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
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
    private CopyOfBuilder(String id, String name, String description, String nation, String type, String cookingTime, String calorie, String quantity, String imageUrl, String createdAt, String updatedAt) {
      super.id(id);
      super.name(name)
        .createdAt(createdAt)
        .updatedAt(updatedAt)
        .description(description)
        .nation(nation)
        .type(type)
        .cookingTime(cookingTime)
        .calorie(calorie)
        .quantity(quantity)
        .imageUrl(imageUrl);
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
     public CopyOfBuilder nation(String nation) {
      return (CopyOfBuilder) super.nation(nation);
    }
    
    @Override
     public CopyOfBuilder type(String type) {
      return (CopyOfBuilder) super.type(type);
    }
    
    @Override
     public CopyOfBuilder cookingTime(String cookingTime) {
      return (CopyOfBuilder) super.cookingTime(cookingTime);
    }
    
    @Override
     public CopyOfBuilder calorie(String calorie) {
      return (CopyOfBuilder) super.calorie(calorie);
    }
    
    @Override
     public CopyOfBuilder quantity(String quantity) {
      return (CopyOfBuilder) super.quantity(quantity);
    }
    
    @Override
     public CopyOfBuilder imageUrl(String imageUrl) {
      return (CopyOfBuilder) super.imageUrl(imageUrl);
    }
  }
  
}
