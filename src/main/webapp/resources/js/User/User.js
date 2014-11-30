angular.module('givemeashow.user.service', [])

.factory('UserService', ['$resource', '$q', '$http', 'CREDENTIAL',
						 function($resource, $q, $http, CREDENTIAL){
	var User = $resource('webservices/user/:dest/:id', {id: '@id'},
	{
		getAuth      : {method: 'GET', params: {dest:''}},
		getMyInfos   : {method: 'GET', params: {dest:'me'}},
		changePW	 : {method: 'POST', params: {dest: 'changePassword'}},
		invite		 : {method: 'POST', params: {dest: 'invite'}}
	});
							 
	var _me = User.getMyInfos();
							 
	angular.extend(User, {
		getMe : function() { return _me }
	})

	/* Store user and role information for the whole app */
	User.getAuth({}, function(datas){
		CREDENTIAL.isAdmin = datas.isAdmin;
		CREDENTIAL.userName = datas.login;
	});

    return User;
}]);