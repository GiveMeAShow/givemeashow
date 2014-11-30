angular.module('givemeashow.user.myAccount', [ 'givemeashow.user.service'])

.directive('myAccount', [function() {
	return {
		restrict : 'AE',
		scope: {},
		controller: 'MyAccountController',
		link : function (scope, element, attrs) {
			scope.changePassword = true;
			angular.extend(scope, {
				togglePFields : function(callBack)
				{
					scope.changePassword = !scope.changePassword;
					if(callBack) callBack();
				}
			});
		},
		templateUrl :'resources/js/User/MyAccount.html'
	}
}])

.controller('MyAccountController', ['$scope', 'EVENTS', 'UserService', function($scope, EVENTS, UserService){
	$scope.user = UserService.getMe();

	$scope.$watch('newPassword', function(){
		if ($scope.newPassword.new != "" && $scope.newPassword.new === $scope.newPassword.confirm)
		{
			$scope.newPassword.match = true;
		}
		else
		{
			$scope.newPassword.match = false;
		}
	}, true);
	
	$scope.clearPass = function()
	{
		$scope.user.password = "";
		$scope.newPassword = { "new": "", "confirm" : "" , "match": false, "old" : ""};
	}

	$scope.sendNewPassword = function()
	{
		$scope.newPassword.login = $scope.user.login;
		UserService.changePW({"newPassword" : $scope.newPassword}, function(datas) {
			$scope.clearPass();
			$scope.changePassword = true;
			$scope.$emit(EVENTS.toaster, {"status" : "success", "title" : "Sucess", "message" : "Password changed !"});
		}, function(error) {
			
		});
	}
	
	$scope.clearPass();
}]);