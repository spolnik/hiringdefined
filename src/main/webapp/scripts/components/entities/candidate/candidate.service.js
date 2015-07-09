'use strict';

angular.module('hiringdefinedApp')
    .factory('Candidate', function ($resource, DateUtils) {
        return $resource('api/candidates/:id', {}, {
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
