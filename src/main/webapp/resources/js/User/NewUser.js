angular.module("givemeashow.user.newUser", ['givemeashow.user.service'])

.directive('showChooser', ['$stateProvider', '$urlRouterProvider', function($stateProvider, $urlRouterProvider) {
	return {
		restrict : 'E',
		scioe : {},
		controller:'NewUserController',
		link : function (scope, element, attrs) {
			
		},
		templateUrl : '/resources/js/User/NewUser.html'
	}
}])

.controller('NewUserController', ['$scope', '$state','UserService', 'shuffledPlaylist',
	 function($scope, $state, UserService) {
		 $scope.user = new UserService();
	 }]);