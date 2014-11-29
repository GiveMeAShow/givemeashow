angular.module('givemeashow.user.service', [])

.factory('UserService', ['$resource', '$q', '$http', 'CREDENTIAL',
						 function($resource, $q, $http, CREDENTIAL){
	var User = $resource('webservices/user/:dest/:id', {id: '@id'},
	{
		getAuth : {method : 'GET', params: {dest:''}}	
	});

	/* Store user and role information for the whole app */
	User.getAuth({}, function(datas){
		CREDENTIAL.isAdmin = datas.isAdmin;
		CREDENTIAL.userName = datas.login;
	});
	
    return User;
}]);