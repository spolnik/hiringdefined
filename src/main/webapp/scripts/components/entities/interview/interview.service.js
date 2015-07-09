'use strict';

angular.module('hiringdefinedApp')
    .factory('Interview', function ($resource, DateUtils) {
        return $resource('api/interviews/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    });
