angular.module('givemeashow.index', [ 'givemeashow.show', 'givemeashow.video', 'ngAnimate'])

.directive('index', ['$animate', function($animate) {
	return {
		restrict : 'AE',
		link : function (scope, element, attrs) {
			scope.playerHidden = false;
			scope.controlHidden = true;
			scope.aboutHidden = true;
			console.log("xd");
			$(".tab_content").hide();
            $(".file_content").hide();
            scope.playList = [];
          	/*document.onkeydown = changeOnKeyDown;*/
			$("#controlPage").hide();
			$("#aboutPage").hide();
			
			
			scope.toggle = function(value, id)
			{
				var elementId = "#" + id;
				var el = $(elementId);
				if (value)
				{
					
					
					if(scope.aboutHidden && scope.controlHidden)
					{
						console.log("show");
						$(elementId).hide("slide", {direction: "right"}, 400, scope.showVideo);
					}
					else
					{
						$(elementId).hide("slide", {direction: "right"}, 400);
					}
				}
				else
				{
					$(elementId).show("slide", {direction: "right"}, 400);
					if(!scope.aboutHidden || !scope.controlHidden)
					{
						console.log("slide");
						scope.slideVideo();
					}
				}
				
			};
			
			scope.reset = function()
			{
				scope.controlHidden = true;
				scope.aboutHidden = true;
				scope.toggle(scope.controlHidden, 'controlPage');
				scope.toggle(scope.aboutHidden, 'aboutPage');
				
			}
			
			scope.slideVideo = function()
			{
				/*$("#videoTitle").hide();*/
				$("#showChooser").hide();
				$("#videoClip").css("width", "320");
				$("#videoClip").css("height", "180");
				$("#videoClip").removeClass("col-xs-offset-2");
			};
			
			scope.showVideo = function()
			{
				/*$("#videoTitle").hide();*/
				$("#showChooser").hide();
				$("#videoClip").css("width", "640");
				$("#videoClip").css("height", "360");
				$("#videoClip").addClass("col-xs-offset-2");
			};
			
		}
	}
}])

.controller('IndexController', ['$scope', 'EVENTS', 'MENUS', function($scope, EVENTS, MENUS){
	
	
	
	
	$scope.$on(EVENTS.menu.toggle, function(event, menu) {
		if (menu === MENUS.controle)
		{
			$scope.controlHidden = !$scope.controlHidden;
			
			$scope.video
			$scope.toggle($scope.controlHidden, 'controlPage');
		}
		else if (menu === MENUS.about)
		{
			$scope.aboutHidden = !$scope.aboutHidden;
			$scope.toggle($scope.aboutHidden, 'aboutPage');
		}
		else if(menu === MENUS.video)
		{
			$scope.reset();
		}
	})
}]);