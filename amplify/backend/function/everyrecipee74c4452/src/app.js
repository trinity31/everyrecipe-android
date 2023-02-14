/*
Copyright 2017 - 2017 Amazon.com, Inc. or its affiliates. All Rights Reserved.
Licensed under the Apache License, Version 2.0 (the "License"). You may not use this file except in compliance with the License. A copy of the License is located at
    http://aws.amazon.com/apache2.0/
or in the "license" file accompanying this file. This file is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and limitations under the License.
*/

/* Amplify Params - DO NOT EDIT
	API_EVERYRECIPE_CATEGORYTABLE_ARN
	API_EVERYRECIPE_CATEGORYTABLE_NAME
	API_EVERYRECIPE_FOODTABLE_ARN
	API_EVERYRECIPE_FOODTABLE_NAME
	API_EVERYRECIPE_GRAPHQLAPIENDPOINTOUTPUT
	API_EVERYRECIPE_GRAPHQLAPIIDOUTPUT
	API_EVERYRECIPE_GRAPHQLAPIKEYOUTPUT
	API_EVERYRECIPE_INGREDIENTTABLE_ARN
	API_EVERYRECIPE_INGREDIENTTABLE_NAME
	API_EVERYRECIPE_RECIPETABLE_ARN
	API_EVERYRECIPE_RECIPETABLE_NAME
	API_EVERYRECIPE_UNITTABLE_ARN
	API_EVERYRECIPE_UNITTABLE_NAME
	ENV
	REGION
Amplify Params - DO NOT EDIT */
const op = require("./operations.js");

var express = require("express");
var bodyParser = require("body-parser");
var awsServerlessExpressMiddleware = require("aws-serverless-express/middleware");
var request = require("request");

// declare a new express app
var app = express();
app.use(bodyParser.json());
app.use(awsServerlessExpressMiddleware.eventContext());

// Enable CORS for all methods
app.use(function (req, res, next) {
  res.header("Access-Control-Allow-Origin", "*");
  res.header("Access-Control-Allow-Headers", "*");
  next();
});

async function asyncForEach(array, callback) {
  for (let index = 0; index < array.length; index++) {
    await callback(array[index], index, array);
  }
}

/**********************
 * Example get method *
 **********************/

app.get("/ingredients", async function (req, res) {
  const ingredients = await op.getAllIngredients();
  //const categories = await op.getAllCategories();
  //const foods = await op.getAllFoodss();

  // await asyncForEach(ingredients.Items, async (ingredient) => {
  //   // ingredientfoodID 각각에 대해
  //   // OPEN API 로 재료를 검색한다.
  //   //검색된 재료 각각에 대해 Unit 을 생성한다.

  //   console.log(ingredient);
  // });

  // console.log("Total " + ingredients.length + " items.");

  // const foodID = "ddcd1350-9428-11ec-8d8f-0f27ee9f8183";

  //console.log("Total number of ingredients: ", ingredients.length);

  res.json({
    success: "get call succeed!",
    url: req.url,
    body: {},
  });
});

function shuffleArray(array) {
  for (let i = array.length - 1; i > 0; i--) {
    const j = Math.floor(Math.random() * (i + 1));
    [array[i], array[j]] = [array[j], array[i]];
  }
}

const searchIngredients = async (items) => {
  const recipes = new Array();
  const recipeIDs = new Array();

  await asyncForEach(items, async (item) => {
    //console.log(item);
    const ingredients = await op.getIngredientsByName(item);
    //console.log(ingredients);

    await asyncForEach(ingredients, async (ingredient) => {
      if (recipeIDs.includes(ingredient.recipeID)) {
        const index = recipes.findIndex(
          (item) => item.id == ingredient.recipeID
        );
        recipes[index].ingredients.push(ingredient.name);
      } else {
        const recipe = {
          id: ingredient.recipeID,
          ingredients: new Array(),
        };
        recipe.ingredients.push(ingredient.name);
        recipes.push(recipe);
        recipeIDs.push(ingredient.recipeID);
      }
    });
  });

  //console.log(recipes);

  //console.log("Total " + recipeIDs.length + "recipe IDs.");
  return recipes;
};

/****************************
 * Example post method *
 ****************************/
app.post("/recommand", async function (req, res) {
  // recipe = {
  //   id: xxx,
  //   ingredients: [xxx, xxx]
  // }

  const excludeNameList = ["마늘", "양파", "쌀", "밀가루", "다시마"];
  const excludeCategoryList = [
    "1e0d3130-9423-11ec-b5d2-43970bc42d5d", //소스, 장류, 시럽
    "1dfe1600-9423-11ec-b5d2-43970bc42d5d", //조미료, 향신료
    "1e874330-9423-11ec-b5d2-43970bc42d5d", //쌀, 곡류, 가루
    "1e3d68f0-9423-11ec-b5d2-43970bc42d5d", //밀가루, 빵, 면
  ];

  // await asyncForEach(req.body.freezerItems, async (item) => {
  //   console.log(item);
  // });

  const filteredItems1 = req.body.searchItems.filter(
    (item) =>
      !excludeCategoryList.includes(item.categoryID) &&
      !excludeNameList.includes(item.name)
  );

  let recipes = await searchIngredients(filteredItems1);

  console.log(recipes);
  console.log("Total " + recipes.length + " recipes.");

  if (recipes.length < 10) {
    // console.log("Less than 10 items without filtered category & names");
    const filteredItems2 = req.body.searchItems.filter(
      (item) => !excludeCategoryList.includes(item.categoryID)
    );
    recipes = await searchIngredients(filteredItems2);
    if (recipes.length < 10) {
      // console.log("Less than 10 items without filtered category");
      recipes = await searchIngredients(req.body.searchItems);
    }
  } else {
    shuffleArray(recipes);
  }

  // console.log(recipeIDs.sort());
  //console.log("Total " + recipes.length + " recipes.");

  const results = recipes
    .sort(function (a, b) {
      return b.ingredients.length - a.ingredients.length;
    })
    .slice(0, 10);

  const finalResults = new Array();

  //console.log(results);

  await asyncForEach(results, async (recipe) => {
    const result = await op.getRecipeByID(recipe.id);
    // if (result.length > 0) {
    finalResults.push({
      recipe: result.Item,
      ingredients: recipe.ingredients,
    });
    // }
    //console.log(result[0]);
  });

  console.log("final result: " + finalResults);

  res.json({ result: "success", body: finalResults });
});

app.post("/search", async function (req, res) {
  let recipes = await searchIngredients(req.body.searchItems);

  //console.log(recipes);
  //console.log("Total " + recipes.length + " recipes.");

  const results = recipes
    .sort(function (a, b) {
      return b.ingredients.length - a.ingredients.length;
    })
    .slice(0, 10);

  const finalResults = new Array();

  //  console.log(results);

  await asyncForEach(results, async (recipe) => {
    const result = await op.getRecipeByID(recipe.id);

    finalResults.push({
      recipe: result.Item,
      ingredients: recipe.ingredients,
    });
  });

  //console.log("final result: " + finalResults);

  res.json({ result: "success", body: finalResults });
});

app.post("/ingredients", async function (req, res) {
  // const ingredients = await op.getAllIngredients();
  // var unit_length = 0;
  // console.log("Total " + ingredients.Items.length + " items.");

  // await asyncForEach(ingredients.Items, async (ingredient) => {
  //   //console.log(ingredient);
  //   const rows = await op.fetchIngredients(ingredient.name);

  //   await asyncForEach(rows, async (row) => {
  //     await op.createUnit(
  //       ingredient.name,
  //       ingredient.id,
  //       row.IRDNT_CPCTY,
  //       row.RECIPE_ID
  //     );
  //     unit_length++;
  //   });
  // });

  // console.log("Total " + unit_length + " Units created.");

  // const recipeIDs = Array.from({ length: 100 }, (_, i) => i + req.body.id);
  // await asyncForEach(recipeIDs, async (id) => {
  //   const result = await op.fetchRecipe(id);
  //   await op.createRecipe(result[0]);
  // });

  res.json({ success: "post call succeed!", url: req.url, body: req.body });
});

/****************************
 * Example put method *
 ****************************/

app.put("/item", function (req, res) {
  // Add your code here
  res.json({ success: "put call succeed!", url: req.url, body: req.body });
});

app.put("/item/*", function (req, res) {
  // Add your code here
  res.json({ success: "put call succeed!", url: req.url, body: req.body });
});

/****************************
 * Example delete method *
 ****************************/

app.delete("/item", function (req, res) {
  // Add your code here
  res.json({ success: "delete call succeed!", url: req.url });
});

app.delete("/item/*", function (req, res) {
  // Add your code here
  res.json({ success: "delete call succeed!", url: req.url });
});

app.listen(3000, function () {
  console.log("App started");
});

// Export the app object. When executing the application local this does nothing. However,
// to port it to AWS Lambda we will create a wrapper around that will load the app from
// this file
module.exports = app;
