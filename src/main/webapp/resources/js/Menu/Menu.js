angular.module("givemeashow.menu", ['givemeashow.user.service'])

.directive('menuBar', ['EVENTS', 'MENUS','CREDENTIAL', function(EVENTS, MENUS, CREDENTIAL) {
	return {
		restrict : 'E',
		scope : {},
		controller :'menuBarController',
		link : function (scope, element, attrs) {
			
			scope.ctrlSelected = false;
			scope.aboutSelected = false;
			scope.videoSelected = true;
			scope.accountSelected = false;
			scope.selectedMenus = [];
			scope.isAdmin = CREDENTIAL.isAdmin;
			angular.extend(scope, {
				toggleCtrl : function()
				{
						scope.ctrlSelected = !scope.ctrlSelected;
						scope.videoSelected = false;
						scope.accountSelected = false;
						scope.sendEvent(EVENTS.menu.toggle, MENUS.controle)
				},
				
				toggleAbout : function()
				{
					scope.aboutSelected = !scope.aboutSelected;
					scope.videoSelected = false;
					scope.accountSelected = false;
					scope.sendEvent(EVENTS.menu.toggle, MENUS.about)
				},
				
				toggleAccount : function()
				{
					scope.accountSelected = !scope.accountSelected;
					scope.ctrlSelected = false;
					scope.aboutSelected = false;
					scope.videoSelected = false;
					scope.sendEvent(EVENTS.menu.toggle, MENUS.account);
				},
				
				toggleVideo : function()
				{
					scope.videoSelected = true;
					scope.ctrlSelected = false;
					scope.aboutSelected = false;
					scope.accountSelected = false;
					scope.sendEvent(EVENTS.menu.toggle, MENUS.video)
				}
			})
		},
		templateUrl: 'resources/js/Menu/Menu.html'
	};
}])

.controller('menuBarController', ['$scope', 'EVENTS', 'MENUS', 'UserService', '$rootScope',
								  function($scope, EVENTS, MENUS, UserService, $rootScope){					  
	angular.extend($scope, {
		sendEvent : function(event, menu) {
			$rootScope.$broadcast(event, menu);
		}
	})
	
	$scope.$on(EVENTS.credentials, function(event, CREDENTIALS) {
		$scope.isAdmin = CREDENTIALS.isAdmin;
		console.log("set to ", $scope.isAdmin);
		event.preventDefault();
	})
}]);





