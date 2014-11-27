angular.module("givemeashow.video.player", ['givemeashow.video.service'])

.directive('videoPlayer', [function() {
	return {
		restrict : 'AE',
		link : function (scope, element, attrs) {
			angular.extend(scope, {
				
				changeVideo : function() 
				{
					scope.addVideoToHistory(document.getElementById("videoClip_html5_api").getAttribute("src"));
   
					if (playList.length == 0)
					{
						$.getJSON("webservices/video/shuffled", function(result){
							playList = result;
							scope.changeToNextVideo();
						});
					}
					else
					{
						scope.changeToNextVideo();
					}

					return false;
				},
				
				setVideo : function (url)
				{
					scope.videoPlayer.src({type: "video/webm", src: url});
					scope.videoPlayer.currentTime(0);
					scope.videoPlayer.ready(function(){
						var vp = this;
						vp.play();
					});
				}
					
			});
			
			
			scope.videoPlayer = videojs("videoClip");
			scope.videoPlayer.on("ended", scope.changeVideo);
			//scope.videoPlayer.on("fullscreenchange", removeVideoPlayerOffset);
			scope.videoPlayer.on("error", scope.changeVideo);
			scope.changeVideo();
			console.log("videop");
			
		}
	}
}])

.controller('VideoPlayerController', ['$scope', function($scope){
	var _videosHystory = [];
	
	angular.extend($scope, {
		
		addVideoToHistory : function(videoPath) 
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
			var url = "/GiveMeAShow/" + playList[index].url;
			console.log(url);
			$scope.setVideo(url);
			index = index + 1;
		}
	})
	
	
	
		console.log("mdaire");
}]);





