angular.module("givemeashow.video.player", ['givemeashow.video.service'])

.directive('videoPlayer', [function() {
	return {
		restrict : 'E',
		scope : {},
		controller :'VideoPlayerController',
		link : function (scope, element, attrs) {
			angular.extend(scope, {
				
				setVideo: function (url)
				{
					scope.videoPlayer.src({type: "video/webm", src: url});
					scope.videoPlayer.currentTime(0);
					scope.videoPlayer.ready(function(){
						var vp = this;
						vp.play();
					});
				},
				
				removeVideoPlayerOffset: function ()
				{
					console.log($("#videoClip").hasClass("vjs-fullscreen"));
					if ($("#videoClip").hasClass("vjs-fullscreen"))
					{
						  $("#videoClip").removeClass("col-xs-offset-2"); 
					}
					else
					{
						$("#videoClip").addClass("col-xs-offset-2");  
					}
				}
					
			});
			
			scope.videoPlayer = videojs("videoClip");
			scope.videoPlayer.on("ended", scope.changeVideo);
			scope.videoPlayer.on("fullscreenchange", scope.removeVideoPlayerOffset);
			scope.videoPlayer.on("error", scope.changeVideo);
			scope.changeVideo();
			
		},
		templateUrl: 'resources/js/Video/VideoPlayer.html'
	};
}])

.controller('VideoPlayerController', ['$scope', 'VideoServices', function($scope, VideoServices){					  
	var _videosHystory = [];
	
	$scope.playList = VideoServices.shuffled($scope.changeToNextVideo);
	$scope.index = 0;
	
	
	angular.extend($scope, {
		
		refreshPlaylist: function() 
		{
			$scope.playList = VideoServices.shuffled($scope.changeToNextVideo);	
		},
		
		addVideoToHistory: function(videoPath) 
		{
			if (videoPath != null)
			{
				if (_videosHystory == null)
				{
					_videosHystory = new Array();
					_videosHystory.push(videoPath);
				}
				else
				{
					_videosHystory.push(videoPath);
				}
			}
		},
		
		changeToNextVideo: function ()
		{
			var url = "/GiveMeAShow/" + $scope.playList[index].url;
			console.log(url);
			$scope.setVideo(url);
			$scope.index = $scope.index + 1;
		},
		
		decreaseIndex : function()
		{
			if (index > 0)
			{
				$scope.index = $scope.index - 1;
			}
			else
			{
				$scope.index = 0;
			}
		},
		
		changeToPreviousVideo: function()
		{
			var url = "/GiveMeAShow/" + $scope.playList[index].url;
			console.log(url);
			$scope.setVideo(url);
			$scope.decreaseIndex();
		},
		
		changeVideo: function() 
		{
			$scope.addVideoToHistory($scope.playList[$scope.index.url]);
			if ($scope.playList.length == 0)
			{
				$scope.refreshPlaylist();
			}
			else
			{
				scope.changeToNextVideo();
			}

			return false;
		}
		
	})
	
	
	
		console.log("mdaire");
}]);





