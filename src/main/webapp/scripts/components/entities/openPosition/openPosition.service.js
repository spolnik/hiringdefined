'use strict';

angular.module('hiringdefinedApp')
    .factory('OpenPosition', function ($resource, DateUtils) {
        return $resource('api/openPositions/:id', {}, {
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
