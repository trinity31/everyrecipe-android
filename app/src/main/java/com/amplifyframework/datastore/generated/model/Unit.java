package com.amplifyframework.datastore.generated.model;


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

/** This is an auto generated class representing the Unit type in your schema. */
@SuppressWarnings("all")
@ModelConfig(pluralName = "Units")
@Index(name = "byName", fields = {"name"})
@Index(name = "byIngredient", fields = {"ingredientID"})
@Index(name = "byRecipe", fields = {"recipeID"})
public final class Unit implements Model {
  public static final QueryField ID = field("Unit", "id");
  public static final QueryField INGREDIENT_ID = field("Unit", "ingredientID");
  public static final QueryField RECIPE_ID = field("Unit", "recipeID");
  public static final QueryField NAME = field("Unit", "name");
  public static final QueryField CPCTY = field("Unit", "cpcty");
  public static final QueryField CREATED_AT = field("Unit", "createdAt");
  public static final QueryField UPDATED_AT = field("Unit", "updatedAt");
  private final @ModelField(targetType="ID", isRequired = true) String id;
  private final @ModelField(targetType="ID", isRequired = true) String ingredientID;
  private final @ModelField(targetType="ID", isRequired = true) String recipeID;
  private final @ModelField(targetType="String", isRequired = true) String name;
  private final @ModelField(targetType="String", isRequired = true) String cpcty;
  private final @ModelField(targetType="String", isRequired = true) String createdAt;
  private final @ModelField(targetType="String", isRequired = true) String updatedAt;
  public String getId() {
      return id;
  }
  
  public String getIngredientId() {
      return ingredientID;
  }
  
  public String getRecipeId() {
      return recipeID;
  }
  
  public String getName() {
      return name;
  }
  
  public String getCpcty() {
      return cpcty;
  }
  
  public String getCreatedAt() {
      return createdAt;
  }
  
  public String getUpdatedAt() {
      return updatedAt;
  }
  
  private Unit(String id, String ingredientID, String recipeID, String name, String cpcty, String createdAt, String updatedAt) {
    this.id = id;
    this.ingredientID = ingredientID;
    this.recipeID = recipeID;
    this.name = name;
    this.cpcty = cpcty;
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
      Unit unit = (Unit) obj;
      return ObjectsCompat.equals(getId(), unit.getId()) &&
              ObjectsCompat.equals(getIngredientId(), unit.getIngredientId()) &&
              ObjectsCompat.equals(getRecipeId(), unit.getRecipeId()) &&
              ObjectsCompat.equals(getName(), unit.getName()) &&
              ObjectsCompat.equals(getCpcty(), unit.getCpcty()) &&
              ObjectsCompat.equals(getCreatedAt(), unit.getCreatedAt()) &&
              ObjectsCompat.equals(getUpdatedAt(), unit.getUpdatedAt());
      }
  }
  
  @Override
   public int hashCode() {
    return new StringBuilder()
      .append(getId())
      .append(getIngredientId())
      .append(getRecipeId())
      .append(getName())
      .append(getCpcty())
      .append(getCreatedAt())
      .append(getUpdatedAt())
      .toString()
      .hashCode();
  }
  
  @Override
   public String toString() {
    return new StringBuilder()
      .append("Unit {")
      .append("id=" + String.valueOf(getId()) + ", ")
      .append("ingredientID=" + String.valueOf(getIngredientId()) + ", ")
      .append("recipeID=" + String.valueOf(getRecipeId()) + ", ")
      .append("name=" + String.valueOf(getName()) + ", ")
      .append("cpcty=" + String.valueOf(getCpcty()) + ", ")
      .append("createdAt=" + String.valueOf(getCreatedAt()) + ", ")
      .append("updatedAt=" + String.valueOf(getUpdatedAt()))
      .append("}")
      .toString();
  }
  
  public static IngredientIdStep builder() {
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
  public static Unit justId(String id) {
    return new Unit(
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
      ingredientID,
      recipeID,
      name,
      cpcty,
      createdAt,
      updatedAt);
  }
  public interface IngredientIdStep {
    RecipeIdStep ingredientId(String ingredientId);
  }
  

  public interface RecipeIdStep {
    NameStep recipeId(String recipeId);
  }
  

  public interface NameStep {
    CpctyStep name(String name);
  }
  

  public interface CpctyStep {
    CreatedAtStep cpcty(String cpcty);
  }
  

  public interface CreatedAtStep {
    UpdatedAtStep createdAt(String createdAt);
  }
  

  public interface UpdatedAtStep {
    BuildStep updatedAt(String updatedAt);
  }
  

  public interface BuildStep {
    Unit build();
    BuildStep id(String id);
  }
  

  public static class Builder implements IngredientIdStep, RecipeIdStep, NameStep, CpctyStep, CreatedAtStep, UpdatedAtStep, BuildStep {
    private String id;
    private String ingredientID;
    private String recipeID;
    private String name;
    private String cpcty;
    private String createdAt;
    private String updatedAt;
    @Override
     public Unit build() {
        String id = this.id != null ? this.id : UUID.randomUUID().toString();
        
        return new Unit(
          id,
          ingredientID,
          recipeID,
          name,
          cpcty,
          createdAt,
          updatedAt);
    }
    
    @Override
     public RecipeIdStep ingredientId(String ingredientId) {
        Objects.requireNonNull(ingredientId);
        this.ingredientID = ingredientId;
        return this;
    }
    
    @Override
     public NameStep recipeId(String recipeId) {
        Objects.requireNonNull(recipeId);
        this.recipeID = recipeId;
        return this;
    }
    
    @Override
     public CpctyStep name(String name) {
        Objects.requireNonNull(name);
        this.name = name;
        return this;
    }
    
    @Override
     public CreatedAtStep cpcty(String cpcty) {
        Objects.requireNonNull(cpcty);
        this.cpcty = cpcty;
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
    private CopyOfBuilder(String id, String ingredientId, String recipeId, String name, String cpcty, String createdAt, String updatedAt) {
      super.id(id);
      super.ingredientId(ingredientId)
        .recipeId(recipeId)
        .name(name)
        .cpcty(cpcty)
        .createdAt(createdAt)
        .updatedAt(updatedAt);
    }
    
    @Override
     public CopyOfBuilder ingredientId(String ingredientId) {
      return (CopyOfBuilder) super.ingredientId(ingredientId);
    }
    
    @Override
     public CopyOfBuilder recipeId(String recipeId) {
      return (CopyOfBuilder) super.recipeId(recipeId);
    }
    
    @Override
     public CopyOfBuilder name(String name) {
      return (CopyOfBuilder) super.name(name);
    }
    
    @Override
     public CopyOfBuilder cpcty(String cpcty) {
      return (CopyOfBuilder) super.cpcty(cpcty);
    }
    
    @Override
     public CopyOfBuilder createdAt(String createdAt) {
      return (CopyOfBuilder) super.createdAt(createdAt);
    }
    
    @Override
     public CopyOfBuilder updatedAt(String updatedAt) {
      return (CopyOfBuilder) super.updatedAt(updatedAt);
    }
  }
  
}
