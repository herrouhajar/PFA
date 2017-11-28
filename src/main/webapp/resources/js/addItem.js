var app = angular.module('myApp', []);

  app.controller('AddInput', function($scope) {

  $scope.items = [{id:1}];
  
  $scope.addNewItem = function() {
    var newItemNo = $scope.items.length+1;
    $scope.items.push({'id':newItemNo});
  };
    
  $scope.removeItem = function() {
    var lastItem = $scope.items.length-1;
    $scope.items.splice(lastItem);
  };
  
});