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
                    src: 'bower_components/*/*.min.js',
                    dest: '<%= config.dist %>',
                    flatten: true
                }, {
                    expand: true,
                    cwd: 'bower_components/*/dist/',
                    src: ['video-js/video.js', 'video-js/font/**', 'video-js/video-js.css'],
                    dest: '<%= config.dist %>'
                }]
            }
        }
    });

    grunt.loadNpmTasks('grunt-contrib-copy');
    grunt.loadNpmTasks('grunt-contrib-uglify');

    grunt.registerTask('default', [
        'copy'
    ]);
};