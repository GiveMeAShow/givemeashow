angular.module("givemeashow.menu", [])

.directive('menuBar', ['EVENTS', 'MENUS', function(EVENTS, MENUS) {
	return {
		restrict : 'E',
		scope : {},
		controller :'menuBarController',
		link : function (scope, element, attrs) {
			scope.aboutShown = "false";
			scope.ctrlSelected = false;
			scope.aboutSelected = false;
			scope.videoSelected = true;
			scope.selectedMenus = [];
			

			scope.toggleCtrl = function()
			{
				scope.ctrlSelected = !scope.ctrlSelected;
				scope.videoSelected = false;
				scope.sendEvent(EVENTS.menu.toggle, MENUS.controle)
			}
			
			scope.toggleAbout = function()
			{
				scope.aboutSelected = !scope.aboutSelected;
				scope.videoSelected = false;
				scope.sendEvent(EVENTS.menu.toggle, MENUS.about)
			}
			
			scope.toggleVideo = function()
			{
				scope.videoSelected = true;
				scope.ctrlSelected = false;
				scope.aboutSelected = false;
				scope.sendEvent(EVENTS.menu.toggle, MENUS.video)
			}
			
			
			scope.moveVideo = function()
			{
					/*$("#videoTitle").hide();
					$("#showChooser").hide();
					$("#videoClip").css("width", "320");
					$("#videoClip").css("height", "180");
					$("#videoClip").removeClass("col-xs-offset-2");
					$("#videoMenu").attr("onClick", "showVideo();");
					$("#controlsMenu").attr("onClick", "controlsClickHandler();");
					$("#aboutMenu").attr("onClick", "aboutClickHandler()");*/

			};
			
		},
		templateUrl: 'resources/js/Menu/Menu.html'
	};
}])

.controller('menuBarController', ['$scope', 'EVENTS', 'MENUS','$rootScope', function($scope, EVENTS, MENUS, $rootScope){					  
	angular.extend($scope, {
		sendEvent : function(event, menu) {
			$rootScope.$broadcast(event, menu);
		}
	})
}]);





