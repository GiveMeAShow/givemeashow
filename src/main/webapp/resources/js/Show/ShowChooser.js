angular.module("givemeashow.show.showChooser", ['givemeashow.show.service'])

.directive('showChooser', [function() {
	return {
		restrict : 'E',
		scope : {},
		controller:'ShowChooserController',
		link : function (scope, element, attrs) {
			console.log("showChooser");
		},
		templateUrl : 'resources/js/Show/Show.html'
	}
}])

.controller('ShowChooserController', ['$scope','showServices',
	 function($scope, showService) {
		 $scope.shows = showService.query();
		 
		 $scope.addToList = function(show)
		 {
			showService.toggleFromQueryList(show);
		 };
	 }]);