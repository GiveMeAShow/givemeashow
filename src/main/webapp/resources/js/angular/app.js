'use strict';

// Declare app level module which depends on filters, and services

angular.module('givemeashow', ['ngResource', 'ngRoute', 'ngSanitize',
							   'givemeashow.index'])

.config(['$routeProvider', function($routeProvider){
	$routeProvider.
	/*when('/', {
		templateUrl:'resources/js/angular/Views/Player.html',
		controller:'VideoPlayerController'
	}).*/	
	when('/', {
		templateUrl:'resources/js/angular/Views/index.html',
		controller: 'IndexController'
	}).
	otherwise({
		redirectTo: '/'
	});
}]);
