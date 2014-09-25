'use strict';

/* Directives */

angular.module('myApp.directives', []).
  directive('appVersion', function (version) {
    return function(scope, elm, attrs) {
      elm.text(version);
    };
  })
.directive('slideAfterRepeat', function(){
    return function(scope, element, attrs) {
          if(scope.$last)
        {
            // A bit strange, this hack should world without the need of timeout but it seems the dom elements are not took in count
            setTimeout(function(){
                slidr.create('slider-div', {breadcrumbs: true}).start();
            }, 25);
            
        }
    };
})
.directive("showViewer", function(){
	return {
	      restrict: 'AE',
	      replace: 'true',
	      templateUrl: "/GiveMeAShow/resources/html/showViewer.html",
	      controller: "showViewerController",
	      link: function($scope, $element, $attribute) {
	    	  
	      }
	  };
});
