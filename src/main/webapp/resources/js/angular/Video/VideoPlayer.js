angular.module("givemeashow.video.player", ['givemeashow.video.service'])

.directive('videoPlayer', [function() {
	return {
		restrict : 'AE',
		scope : {},
		link : function (scope, element, attrs) {
			$(window).load(function() {
				scope.videoPlayer = videojs("videoClip");
				scope.videoPlayer.on("ended", changeVideo);
            	scope.videoPlayer.on("fullscreenchange", removeVideoPlayerOffset);
            	scope.videoPlayer.on("error", changeVideo);
           });
			
            //changeVideo();
            
			
			console.log("lol");
		}
	}
}])

.controller('VideoPlayerController', ['$scope', function($scope){
		console.log("mdaire");
}]);