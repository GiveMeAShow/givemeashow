angular.module('givemeashow.index', [ 'givemeashow.show', 'givemeashow.video', 'givemeashow.user.myAccount', 'ngAnimate', 'toaster'])

.directive('index', ['$animate', 'toaster', function($animate, toaster) {
	return {
		restrict : 'AE',
		link : function (scope, element, attrs) {
			scope.playerHidden = false;
			scope.controlHidden = true;
			scope.aboutHidden = true;
			scope.accountHidden = true;
			console.log("xd");
			$(".tab_content").hide();
            $(".file_content").hide();
            scope.playList = [];
          	/*document.onkeydown = changeOnKeyDown;*/
			$("#controlPage").hide();
			$("#aboutPage").hide();
			$("#accountPage").hide();
			
			
			scope.toggle = function(value, id)
			{
				var elementId = "#" + id;
				var el = $(elementId);
				if (value)
				{
					if(scope.aboutHidden && scope.controlHidden && scope.accountHidden)
					{
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
					if(!scope.aboutHidden || !scope.controlHidden || !scope.accountHidden)
					{
						scope.slideVideo();
					}
				}
				
			};
			
			scope.reset = function()
			{
				scope.controlHidden = true;
				scope.aboutHidden = true;
				scope.accountHidden = true;
				scope.toggle(scope.controlHidden, 'controlPage');
				scope.toggle(scope.aboutHidden, 'aboutPage');
				scope.toggle(scope.aboutHidden, 'accountPage');
			};
			
			scope.slideVideo = function()
			{
				
				$("#centerElement").css("width", "1200");
				$("#showChooser").hide();
				$("#videoClip").css("width", "320");
				$("#videoClip").css("height", "180");
			};
			
			scope.showVideo = function()
			{
				
				$("#centerElement").css("width", "1000");
				$("#showChooser").show(400);
				$("#videoClip").css("width", "640");
				$("#videoClip").css("height", "360");
			};
			
			scope.toast = function(datas)
			{
				toaster.pop(datas.status, datas.title, datas.message);
			}
			
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
			$("#accountPage").hide("slide", {direction: "right"}, 400);
		}
		else if (menu === MENUS.about)
		{
			$scope.aboutHidden = !$scope.aboutHidden;
			$scope.toggle($scope.aboutHidden, 'aboutPage');
			$("#accountPage").hide("slide", {direction: "right"}, 400);
		}
		else if(menu === MENUS.video)
		{
			$scope.reset();
		}
		else if (menu === MENUS.account)
		{
			$scope.accountHidden = !$scope.accountHidden;
			$scope.controlHidden = true;
			$scope.aboutHidden = true;
			$scope.toggle($scope.controlHidden, 'controlPage');
			$scope.toggle($scope.aboutHidden, 'aboutPage');
			$scope.toggle($scope.accountHidden, 'accountPage');
		}
	});
	
	$scope.$on(EVENTS.toaster, function(event, datas) {
		$scope.toast(datas);
	});
}]);