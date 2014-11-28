angular.module('givemeashow.show.service', [])

.factory('showServices', ['$resource', '$q', '$http', function($resource, $q, $http){
	var Shows = $resource('webservices/show/:dest/:id', {id: '@id'});
	
	// shared show list. Updated by showChooser, used by videoPlayer
	var _queryList = Shows.query();
	
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
	})
	
    return Shows;
}]);