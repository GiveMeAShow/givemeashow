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
            scope.aboutShown = "false";
            scope.playList = [];
            scope.controlsShown = "false";
          	/*document.onkeydown = changeOnKeyDown;*/
			$("#controlPage").hide();
			$("#aboutPage").hide();
			
			
			scope.toggle = function(value, id)
			{
				var elementId = "#" + id;
				var el = $(elementId);
				if (value)
				{
					
					$(elementId).hide("slide", {direction: "right"}, 400);
				}
				else
				{
					$(elementId).show("slide", {direction: "right"}, 400);
				}
				
			}			
			
		}
	}
}])

.controller('IndexController', ['$scope', 'EVENTS', 'MENUS', function($scope, EVENTS, MENUS){
	
	
	
	
	$scope.$on(EVENTS.menu.toggle, function(event, menu) {
		console.log(menu);
		if (menu === MENUS.controle)
		{
			console.log(menu);	
			$scope.controlHidden = !$scope.controlHidden;
			$scope.toggle($scope.controlHidden, 'controlPage');
		}
		else if (menu === MENUS.about)
		{
			console.log(menu);
			$scope.aboutHidden = !$scope.aboutHidden;
			$scope.toggle($scope.aboutHidden, 'aboutPage');
		}
		else if(menu === MENUS.video)
		{
			console.log(menu);
			$scope.controlHidden = true;
			$scope.aboutHidden = true;
		}
	})
}]);