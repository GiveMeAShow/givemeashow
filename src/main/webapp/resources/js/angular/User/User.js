angular.module('givemeashow.user.service', [])

.factory('UserService', ['$resource', '$q', '$http', function($resource, $q, $http){
	var User = $resource('webservices/show/:dest/:id', {id: '@id'});
	
	// shared show list. Updated by showChooser, used by videoPlayer
	/*	var _queryList = Shows.query();
	_queryList.prototype.getVideoWithShowFilter = function(videoList) {
		
	}
	
	angular.extend(Shows.prototype, {
		addShowToQueryList : function(show)
		{
			if (_queryList.indexOf(show) == -1)
			{
				_queryList.push(show);
			}
			else
			{
				_queryList.splice(_queryList.indexOf(show), 1);
			}
		},
		
		getQueryList : function()
		{
			return _queryList;
		}
	})*/
	
    return User;
}]);