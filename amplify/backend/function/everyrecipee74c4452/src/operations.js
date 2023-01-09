var uuid = require("uuid");
var moment = require("moment-timezone");
const AWS = require("aws-sdk");
const docClient = new AWS.DynamoDB.DocumentClient();

var MYDATA_SECRET =
  "46ce91228950cbfd667232fe73811a0565bf70b583fd31de357a7aa639a5f300";

const BASE_URL =
  "http://211.237.50.150:7080/openapi/" + MYDATA_SECRET + "/json";
const ING_URL_PREFIX =
  BASE_URL + "/Grid_20150827000000000227_1/1/100?IRDNT_NM=";
const RECIPE_URL_PREFIX = BASE_URL + "/Grid_20150827000000000226_1/";

const axios = require("axios");

async function asyncForEach(array, callback) {
  for (let index = 0; index < array.length; index++) {
    await callback(array[index], index, array);
  }
}

const fetchIngredients = async (name) => {
  const response = await axios.get(encodeURI(ING_URL_PREFIX + name));
  return response.data.Grid_20150827000000000227_1.row;
};

exports.fetchIngredients = fetchIngredients;

exports.fetchRecipe = async (id) => {
  const response = await axios.get(
    encodeURI(RECIPE_URL_PREFIX + id + "/" + id)
  );

  return response.data.Grid_20150827000000000226_1.row;
};

exports.getIngredientsByName = async (item) => {
  //name 에 대한 Ingredients 테이블 스캔, 각각에 대해 row 테이블 검색, 결과 없으면 공공데이타에서 가져오기
  const ingredients = await getIngredientssByFood(item.id);

  var rows = new Array();

  //name 에 대한 ingredients 각각에 대해 오픈API 호출, rows 에 추가
  // await asyncForEach(ingredients.Items, async (ingredient) => {
  //   const items = await fetchIngredients(ingredient.name);
  //   rows = rows.concat(items);
  // });

  //name 에 대한 ingredients 각각에 대해 UNIT 테이블 검색, row에 추가
  await asyncForEach(ingredients.Items, async (ingredient) => {
    const items = await getUnitsByName(ingredient.name);
    rows = rows.concat(items.Items);
  });

  //console.log(rows);

  return rows;
};

// exports.getRecipeByID = async (recipeId) => {
//   const options = {
//     url: RECIPE_URL_PREFIX + recipeId + "/" + recipeId,
//   };

//   const result = await (async () => {
//     try {
//       const response = await axios.get(encodeURI(options.url));
//       return response.data.Grid_20150827000000000226_1.row;
//     } catch (error) {
//       console.log("error: " + JSON.stringify(error));
//       return error;
//     }
//   })();

//   return result;
// };

exports.createIngredient = async (name) => {
  try {
    const id = uuid.v1();
    await docClient
      .put({
        TableName: process.env.API_EVERYRECIPE_INGREDIENTTABLE_NAME,
        Item: {
          id,
          name: name,
          category: "채소류",
          createdAt: moment().tz("Asia/Seoul").format(),
          updatedAt: moment().tz("Asia/Seoul").format(),
        },
      })
      .promise();
    return id;
  } catch (err) {
    console.log(err);
    return err;
  }
};

exports.updateIngredientFoodID = async (id, foodID) => {
  try {
    await docClient
      .update({
        TableName: process.env.API_EVERYRECIPE_INGREDIENTTABLE_NAME,
        Key: {
          id: id,
        },
        UpdateExpression: "set foodID = :foodID",
        ExpressionAttributeValues: {
          ":foodID": foodID,
        },
        ReturnValues: "UPDATED_NEW",
      })
      .promise();
  } catch (err) {
    console.log(err);
    return err;
  }
};

exports.createCategory = async (name) => {
  try {
    const id = uuid.v1();
    await docClient
      .put({
        TableName: process.env.API_EVERYRECIPE_CATEGORYTABLE_NAME,
        Item: {
          id,
          name: name,
          description: name,
          createdAt: moment().tz("Asia/Seoul").format(),
          updatedAt: moment().tz("Asia/Seoul").format(),
        },
      })
      .promise();
    return id;
  } catch (err) {
    console.log(err);
    return err;
  }
};

exports.createFood = async (name, categoryID) => {
  try {
    const id = uuid.v1();
    await docClient
      .put({
        TableName: process.env.API_EVERYRECIPE_FOODTABLE_NAME,
        Item: {
          id,
          name: name,
          categoryID: categoryID,
          createdAt: moment().tz("Asia/Seoul").format(),
          updatedAt: moment().tz("Asia/Seoul").format(),
        },
      })
      .promise();
    return id;
  } catch (err) {
    console.log(err);
    return err;
  }
};

exports.createUnit = async (name, ingredientID, cpcty, recipeID) => {
  // console.log("create UNIT");
  // console.log(name);
  // console.log(ingredientID);
  // console.log(cpcty);
  // console.log(recipeID);
  try {
    const id = uuid.v1();
    await docClient
      .put({
        TableName: process.env.API_EVERYRECIPE_UNITTABLE_NAME,
        Item: {
          id,
          name: name,
          ingredientID: ingredientID,
          cpcty: cpcty,
          recipeID: recipeID.toString(),
          createdAt: moment().tz("Asia/Seoul").format(),
          updatedAt: moment().tz("Asia/Seoul").format(),
        },
      })
      .promise();
    return id;
  } catch (err) {
    console.log(err);
    return err;
  }
};

exports.createRecipe = async (recipe) => {
  //console.log(recipe);
  try {
    // const id = uuid.v1();
    await docClient
      .put({
        TableName: process.env.API_EVERYRECIPE_RECIPETABLE_NAME,
        Item: {
          id: recipe.RECIPE_ID.toString(),
          name: recipe.RECIPE_NM_KO,
          description: recipe.SUMRY,
          nation: recipe.NATION_NM,
          type: recipe.TY_NM,
          cookingTime: recipe.COOKING_TIME,
          Calorie: recipe.CALORIE,
          Quantity: recipe.QNT,
          imageUrl: recipe.IMG_URL,
          createdAt: moment().tz("Asia/Seoul").format(),
          updatedAt: moment().tz("Asia/Seoul").format(),
        },
      })
      .promise();
  } catch (err) {
    console.log(err);
    return err;
  }
};

exports.getAllIngredients = async () => {
  var params = {
    TableName: process.env.API_EVERYRECIPE_INGREDIENTTABLE_NAME,
  };

  try {
    const data = await docClient.scan(params).promise();
    return data;
  } catch (err) {
    console.log(err);
    return err;
  }
};

const getIngredientssByName = async (name) => {
  var params = {
    TableName: process.env.API_EVERYRECIPE_INGREDIENTTABLE_NAME,
    IndexName: "byName",
    KeyConditionExpression: "#name = :name",
    ExpressionAttributeValues: { ":name": name },
    ExpressionAttributeNames: { "#name": "name" },
  };

  try {
    const data = await docClient.query(params).promise();
    return data;
  } catch (err) {
    console.log(err);
    return err;
  }
};

const getUnitsByName = async (name) => {
  var params = {
    TableName: process.env.API_EVERYRECIPE_UNITTABLE_NAME,
    IndexName: "byName",
    KeyConditionExpression: "#name = :name",
    ExpressionAttributeValues: { ":name": name },
    ExpressionAttributeNames: { "#name": "name" },
  };

  try {
    const data = await docClient.query(params).promise();
    return data;
  } catch (err) {
    console.log(err);
    return err;
  }
};

exports.getRecipeByID = async (id) => {
  var params = {
    TableName: process.env.API_EVERYRECIPE_RECIPETABLE_NAME,
    Key: {
      id,
    },
  };

  try {
    const data = await docClient.get(params).promise();
    return data;
  } catch (err) {
    console.log(err);
    return err;
  }
};

const getIngredientssByFood = async (id) => {
  var params = {
    TableName: process.env.API_EVERYRECIPE_INGREDIENTTABLE_NAME,
    IndexName: "byFood",
    KeyConditionExpression: "#foodID = :foodID",
    ExpressionAttributeValues: { ":foodID": id },
    ExpressionAttributeNames: { "#foodID": "foodID" },
  };

  try {
    const data = await docClient.query(params).promise();
    return data;
  } catch (err) {
    console.log(err);
    return err;
  }
};

exports.getAllFoodss = async () => {
  var params = {
    TableName: process.env.API_EVERYRECIPE_FOODTABLE_NAME,
  };

  try {
    const data = await docClient.scan(params).promise();
    return data;
  } catch (err) {
    console.log(err);
    return err;
  }
};

exports.getAllCategories = async () => {
  var params = {
    TableName: process.env.API_EVERYRECIPE_CATEGORYTABLE_NAME,
  };

  try {
    const data = await docClient.scan(params).promise();
    return data;
  } catch (err) {
    console.log(err);
    return err;
  }
};
