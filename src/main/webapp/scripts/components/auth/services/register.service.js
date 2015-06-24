'use strict';

angular.module('hiringdefinedApp')
    .factory('Register', function ($resource) {
        return $resource('api/register', {}, {
        });
    });


