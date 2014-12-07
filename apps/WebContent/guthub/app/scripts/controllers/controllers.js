'use strict';
//单页面应用
var app = angular.module('guthub',
    ['guthub.directives', 'guthub.services']);

app.config(['$routeProvider', function($routeProvider) {
    $routeProvider.
      when('/', {
        controller: 'ListCtrl',//页面使用的controller
        resolve: {//提前准备数据
          recipes: ["MultiRecipeLoader", function(MultiRecipeLoader) {
            return MultiRecipeLoader();
          }]
        },
        templateUrl:'views/list.html'//路由到的位置
      }).when('/edit/:recipeId', {
        controller: 'EditCtrl',
        resolve: {
          recipe: ["RecipeLoader", function(RecipeLoader) {
            return RecipeLoader();
          }]
        },
        templateUrl:'views/recipeForm.html'
      }).when('/view/:recipeId', {
        controller: 'ViewCtrl',
        resolve: {
          recipe: ["RecipeLoader", function(RecipeLoader) {
            return RecipeLoader();
          }]
        },
        templateUrl:'views/viewRecipe.html'
      }).when('/new', {
        controller: 'NewCtrl',
        templateUrl:'views/recipeForm.html'
      }).when('/haha', {
          controller: 'NewCtrl',
          templateUrl:'views/1.html'
      }) .when('/life', {
          controller: 'NewCtrl',
          templateUrl:'views/234.html'
      }).when('/test', {
          controller: 'NewCtrl',
          templateUrl:'views/test.html'
      }).otherwise({redirectTo:'/'});
}]);
//变量范围$scope两个页面 $rootscope全局
app.controller('ListCtrl', ['$scope', 'recipes',
    function($scope, recipes) {
  $scope.recipes = recipes;
}]);

app.controller('ViewCtrl', ['$scope', '$location', 'recipe',
    function($scope, $location, recipe) {
  $scope.recipe = recipe;

  $scope.edit = function() {
    $location.path('/edit/' + recipe.id);//edit方法，路由切换
  };
}]);

app.controller('EditCtrl', ['$scope', '$location', 'recipe',
    function($scope, $location, recipe) {
  $scope.recipe = recipe;
  
  $scope.save = function() {
    $scope.recipe.$save(function(recipe) {//$scope下的变量继承$resource。参照restful接口的命名，具有get query remove save等功能
      $location.path('/view/' + recipe.id);
    });
  };

  $scope.remove = function() {
    delete $scope.recipe;
    $location.path('/');
  };
}]);

app.controller('NewCtrl', ['$scope', '$location', 'Recipe',
    function($scope, $location, Recipe) {
  $scope.recipe = new Recipe({
    ingredients: [ {} ]
  });

  $scope.save = function() {
    $scope.recipe.$save(function(recipe) {
    $location.path('/');
    });
  };
}]);

app.controller('IngredientsCtrl', ['$scope',
    function($scope) {
  $scope.addIngredient = function() {
    var ingredients = $scope.recipe.ingredients;
    ingredients[ingredients.length] = {};
  };
  $scope.removeIngredient = function(index) {
    $scope.recipe.ingredients.splice(index, 1);
  };
}]);
