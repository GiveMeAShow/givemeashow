'use strict';

/* Controllers */

angular.module('myApp.controllers', []).
  controller('AppCtrl', function ($scope, $http) {

  })
  .controller('MyCtrl1', function ($scope) {

  })
  
  .controller("showViewerController", ["$scope", "$http", "$q", function($scope, $http, $q) {
	  var showsDeffer = $q.defer();
	  $scope.shows = showsDeffer.promise;
	  
	  $http.get("/GiveMeAShow/rest/shows/list").success(function(response){
		  showsDeffer.resolve(response);
	  }).error(function(response){
		  console.log("error while trying to get shows")
	  });
  }])
 .controller('projectsViewCrl', function($scope) {
        
        
  });
