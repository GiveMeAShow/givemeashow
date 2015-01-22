angular.module('givemeashow.user.myAccount', [ 'givemeashow.user.service'])

.directive('myAccount', [function() {
	return {
		restrict : 'AE',
		scope: {},
		controller: 'MyAccountController',
		link : function (scope, element, attrs) {
			scope.changePassword = true;
			scope.inviteFriendsHidden = true;
			angular.extend(scope, {
				togglePFields : function(callBack)
				{
					scope.changePassword = !scope.changePassword;
					if(callBack) callBack();
				},
				toggleIFields : function()
				{
					scope.inviteFriendsHidden = !scope.inviteFriendsHidden;
				}
			});
		},
		templateUrl :'resources/js/User/MyAccount.html'
	}
}])

.controller('MyAccountController', ['$scope', 'EVENTS', 'UserService', function($scope, EVENTS, UserService){
	$scope.user = UserService.getMe();
	console.log($scope.user);
	$scope.friendsEmail = [];
	
	
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
		UserService.changePW($scope.newPassword, function(datas) {
			$scope.clearPass();
			$scope.changePassword = true;
			$scope.$emit(EVENTS.toaster, {"status" : "success", "title" : "Password changed!", "message" : "AwwwWwwWW YeeAaaAaaAh !"});
		}, function(error) {
			
		});
	}
	
	$scope.inviteFriends = function()
	{
		UserService.invite($scope.friendsEmail, function(datas) {
			$scope.$emit(EVENTS.toaster, {"status" : "success", "title" : "Invites sent", "message" : "MORE FRIENDS §§"});
		}, function(error) {
			
		});
	}
	
	$scope.clearPass();
}]);