angular.module("givemeashow.show.showChooser", ['ui-router', 'givemeashow.show.service'])

.directive('showChooser', ['$stateProvider', '$urlRouterProvider', function($stateProvider, $urlRouterProvider) {
	return {
		restrict : 'E',
		scioe : {},
		controller:'ShowChooserController',
		link : function (scope, element, attrs) {
			
		},
		templateUrl : '/resources/js/angular/Show/Show.html'
	}
}])

.controller('ShowChooserController', ['$scope', '$state','showService', 'shuffledPlaylist',
	 function($scope, $state, showService) {
		 $scope.shows = showService.query();
		 
		 $scope.addToList = function(show)
		 {
			showService.toggleFromQueryList(show);
		 };
	 }]);