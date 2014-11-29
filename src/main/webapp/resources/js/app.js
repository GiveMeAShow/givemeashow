'use strict';

// Declare app level module which depends on filters, and services

angular.module('givemeashow', ['ngResource', 'ngRoute', 'ngAnimate', 'ngSanitize',
							   'givemeashow.index', 'givemeashow.menu', 'givemeashow.user.service'])

.config(['$routeProvider', function($routeProvider){
	$routeProvider.	
	when('/', {
		templateUrl:'resources/js/Views/index.html',
		controller: 'IndexController'
	}).
	otherwise({
		redirectTo: '/'
	});
}])

.value('CREDENTIAL', {
	isAdmin : false,
	userName : ""
})

.constant('EVENTS', {
	'menu' : {
		'toggle' : 'toggle'
	}
})

.constant('MENUS', {
	'controle' : 'controle',
	'about' : 'about',
	'video' : 'video'
});
