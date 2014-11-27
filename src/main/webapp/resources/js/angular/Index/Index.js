angular.module('givemeashow.index', [ 'givemeashow.show', 'givemeashow.video'])

.directive('index', [function() {
	return {
		restrict : 'AE',
		link : function (scope, element, attrs) {
			console.log("xd");
			$(".tab_content").hide();
            $(".file_content").hide();
            $("#aboutContent").hide();
            $("#controlsContent").hide();
            scope.aboutShown = "false";
            scope.playList = [];
            scope.controlsShown = "false";
          	/*document.onkeydown = changeOnKeyDown;*/

			scope.aboutClickHandler = function()
			{
				if ((scope.aboutShown === "true") && (scope.controlsShown === "true"))
				{
					scope.aboutShown = "false";
					$("#aboutContent").hide("slide", {direction: "right"}, 400);  
					$("#aboutLI").removeClass("active");
				}
				else if (scope.aboutShown === "false" && scope.controlsShown == "true")
				{
					scope.aboutShown = "true";
					$("#aboutContent").show("slide", {direction: "right"}, 400);
					$("#aboutLI").addClass("active");
				}
				else if (scope.aboutShown === "false" && scope.controlsShown == "false")
				{
					scope.aboutShown = "true";
					$("#aboutContent").show("slide", {direction: "right"}, 400);
					$("#aboutLI").addClass("active");
				}
			};

			scope.controlsClickHandler = function()
			{
				if ((scope.aboutShown === "true") && (scope.controlsShown === "true"))
				{
					scope.controlsShown = "false";
					$("#controlsContent").hide("slide", {direction: "right"}, 400);
					$("#controlsLI").removeClass("active");
				}
				else if (scope.controlsShown === "false" && scope.aboutShown == "true")
				{
					scope.controlsShown = "true";
					$("#controlsContent").show("slide", {direction: "right"}, 400);
					$("#controlsLI").addClass("active");
				}
				else if (scope.controlsShown === "false" && scope.aboutShown == "false")
				{
					scope.controlsShown = "true";
					$("#controlsContent").show("slide", {direction: "right"}, 400);
					$("#controlsLI").addClass("active");
				}
			};
			
		}
	}
}])

.controller('IndexController', ['$scope', function($scope){
	
}]);