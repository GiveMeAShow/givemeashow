'use strict';

// Declare app level module which depends on filters, and services

angular.module('givemeashow', ['ngResource', 'ngRoute', 'ngSanitize',
							   'givemeashow.show', 'givemeashow.video'])

.config(['$routeProvider', function($routeProvider){
	$routeProvider.when('/user', {
		
	}).
	when('/', {
		templateUrl:'resources/js/angular/Views/Player.html',
		controller:'VideoPlayerController'
	}).
	otherwise({
		redirectTo: '/'
	});
}]);
