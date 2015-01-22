var pkgjson = require('./package.json');

var config = {
	pkg : pkgjson,
	dist : 'src/main/webapp/resources/jsLibs'
};

module.exports = function(grunt) {

	// Configuration
	grunt
			.initConfig({
				config : config,
				pkg : config.pkg,
				bower : grunt.file.readJSON('./.bowerrc'),
				copy : {
					dist : {
						files : [
								{
									expand : true,
									cwd : 'bower_components/videojs/dist/video-js/',
									src : [ 'video.js', 'video-js.css' ],
									dest : '<%= config.dist %>',
									flatten : true
								},
								{
									expand : true,
									cwd : 'bower_components/videojs/dist/video-js/font/',
									src : [ '*' ],
									dest : '<%= config.dist %>/font',
									flatten : true
								},
								{
									expand : true,
									src : 'bower_components/angular/angular.min.js',
									dest : '<%= config.dist %>/includeFirst',
									flatten : true
								},
								{
									expand : true,
									src : 'bower_components/angular*/angular-*.min.js',
									dest : '<%= config.dist %>',
									flatten : true
								},
								{
									expand : true,
									src : 'bower_components/angularjs-toaster/toaster*',
									dest : '<%= config.dist %>',
									flatten : true
								},
								{
									expand : true,
									cwd : 'bower_components/bootstrap/dist/',
									src : [ 'js/bootstrap.js',
											'css/bootstrap.css' ],
									dest : '<%= config.dist %>',
									flatten : true
								},
								{
									expand : true,
									cwd : 'bower_components/jquery/dist/',
									src : [ 'jquery.min.js' ],
									dest : '<%= config.dist %>/includeFirst',
									flatten : true
								},
								{
									expand : true,
									cwd : 'bower_components/jquery-ui/',
									src : [ 'jquery-ui.min.js',
											'themes/ui-lightness/jquery-ui.css' ],
									dest : '<%= config.dist %>/includeFirst',
									flatten : true
								}, {
									expand : true,
									cwd : 'bower_components/gsap/src/minified',
									src : [ 'tweenmax.min.js' ],
									dest : '<%= config.dist %>',
									flatten : true
								} ]
					}
				},
				includeSource : {
					options : {
						basePath : 'src/main/webapp/resources/js/angular',
						baseUrl : 'resources/'
					},
					includeAngular : {
						files : {
							'src/main/webapp/WEB-INF/pages/index.html' : 'Templates/html/index.tlp.html',
							'src/main/webapp/WEB-INF/pages/login.html' : 'Templates/html/login.tlp.html'
						}
					}
				},
				includereplace : {
					dist : {
						options : {
							prefix : "<!-- @@",
							suffix : "-->",
							includesDir : "Templates/jsp/",
							globals : {
								'libresources' :'/resources/jsLibs/',
								'includeFirst' : '/resources/jsLibs/includeFirst/',
								'jsresources' : '/resources/js/',
								'cssresources' : '/resources/css/'
							}
						},
						files : [{
							src : "*.jsp",
							dest : "src/main/webapp/WEB-INF/pages/shared/",
							expand : true,
							cwd : "Templates/jsp/shared/"
						} ]
					}
				}
			});

	grunt.loadNpmTasks('grunt-contrib-copy');
	grunt.loadNpmTasks('grunt-contrib-uglify');
	grunt.loadNpmTasks('grunt-include-source');
	grunt.loadNpmTasks('grunt-include-replace');

	grunt
			.registerTask('default', [ 'copy', 'includeSource',
					'includereplace' ]);
};