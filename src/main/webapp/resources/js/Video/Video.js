angular.module('givemeashow.video.service', [])

.factory('VideoServices', ['$resource', '$q', '$http', function($resource, $q, $http){
    return $resource('webservices/video/:dest/:id', {id: '@id'}, 
    {
        shuffled : {method : 'GET', params : {dest : 'shuffled'}, isArray : true}
    });
}]);