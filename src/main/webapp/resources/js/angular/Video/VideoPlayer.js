angular.module("givemeashow.videoPlayer", ['ui-router', 'givemeashow.video.service', 'givemeashow.show.service'])

.config(['$stateProvider', '$urlRouterProvider', function($stateProvider, $urlRouterProvider) { 

    $stateProvider
    
    .state('videoPlayer', {
        url : '/videoPlayer',
        templateUrl : '',
		
		resolve: {
			videosSerivces : 'video',
			shuffledPlaylist : function(videoServices) {
				return videoServices.shuffled();
			}
		},
		
		controller : ['$scope', '$state','videosSerivces', 'showService',
					 function($scope, $state, videosSerivces, showService) {
						 
						 
						 $scope.shuffledPlayList = videoServices.shuffled();
						 var shuffledIndex = 0;
						 $scope.currentVideo = $scope.shuffledPlayList[shuffledIndex];
						 
						 var setVideoFromShuffledIndex = function()
						 {
							 $scope.currentVideo = $scope.shuffledPlayList[shuffledIndex]; 
						 }
						 
						 $scope.nextVideo = function() {
							 if(shuffledIndex < $scope.shuffledPlaylist.length)
							 {
								shuffledIndex = shuffledIndex + 1;
							 	setVideoFromShuffledIndex();
							 }
							 else {
								 setVideoFromShuffledIndex();
							 }
						 };
						 
						 $scope.previousVideo = function() {
							 if (shuffledIndex > 0)
							 {
								 shuffledIndex = shuffledIndex - 1;
								 setVideoFromShuffledIndex();
							 }
							 else {
								 setVideoFromShuffledIndex(); 
							 }
						 };
					 }]
    })

}]);