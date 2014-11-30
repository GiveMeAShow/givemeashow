var pkgjson = require('./package.json');

var config = {
    pkg: pkgjson,
    dist: 'src/main/webapp/resources/jsLibs'
};

module.exports = function (grunt) {

    // Configuration
    grunt.initConfig({
        config: config,
        pkg: config.pkg,
        bower: grunt.file.readJSON('./.bowerrc'),
        copy: {
            dist: {
                files: [{
                    expand: true,
                    cwd: 'bower_components/videojs/dist/',
                    src: ['video-js/video.js', 'video-js/video-js.css'],
                    dest: '<%= config.dist %>'
                },{
                    expand: true,
                    cwd: 'bower_components/videojs/dist/',
                    src: ['video-js/font/**'],
                    dest: '<%= config.dist %>'
                }, {
                    expand: true,
                    src: 'bower_components/angular*/*.min.js',
                    dest: '<%= config.dist %>',
					flatten: true
                },{
                    expand: true,
                    src: 'bower_components/angularjs-toaster/toaster*',
                    dest: '<%= config.dist %>',
					flatten: true
                }, {
                    expand: true,
                    cwd: 'bower_components/bootstrap/dist/',
					src: ['js/bootstrap.js', 'css/bootstrap.css'],
                    dest: '<%= config.dist %>/bootstrap'
                }, {
                    expand: true,
                    cwd: 'bower_components/jquery/dist/',
					src: ['jquery.min.js'],
                    dest: '<%= config.dist %>/jquery'
                }, {
                    expand: true,
                    cwd: 'bower_components/jquery-ui/',
					src: ['jquery-ui.min.js', 'themes/ui-lightness/jquery-ui.css'],
                    dest: '<%= config.dist %>/jquery-ui'
                }, {
                    expand: true,
                    cwd: 'bower_components/gsap/src/minified',
					src: ['tweenmax.min.js'],
                    dest: '<%= config.dist %>/gsap'
                }]
            }
        },
		includeSource: {
			options: {
				basePath: 'src/main/webapp/resources/js/angular',
				baseUrl: 'resources/'
			},
			myTarget: {
				files: {
					'src/main/webapp/WEB-INF/pages/index.html' : 'src/main/webapp/WEB-INF/pages/index.tlp.html',
					'src/main/webapp/WEB-INF/pages/login.html' : 'src/main/webapp/WEB-INF/pages/login.tlp.html'
				}
			}
		}
    });

    grunt.loadNpmTasks('grunt-contrib-copy');
    grunt.loadNpmTasks('grunt-contrib-uglify');
	grunt.loadNpmTasks('grunt-include-source');

    grunt.registerTask('default', [
        'copy',
		'includeSource'
    ]);
};