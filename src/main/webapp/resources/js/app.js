'use strict';

// Declare app level module which depends on filters, and services

angular.module('givemeashow', ['ngResource', 'ngRoute', 'ngAnimate', 'ngSanitize', 'toaster',
							   'givemeashow.index', 'givemeashow.menu', 'givemeashow.user'])

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
	},
	'toaster' : 'toaster',
	'credentials' : 'credentials.loaded'
})

.constant('MENUS', {
	'controle' : 'controle',
	'about' : 'about',
	'video' : 'video',
	'account' : 'account'
});
