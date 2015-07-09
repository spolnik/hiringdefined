'use strict';

angular.module('hiringdefinedApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('openPosition', {
                parent: 'entity',
                url: '/openPosition',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'OpenPositions'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/openPosition/openPositions.html',
                        controller: 'OpenPositionController'
                    }
                },
                resolve: {
                }
            })
            .state('openPositionDetail', {
                parent: 'entity',
                url: '/openPosition/:id',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'OpenPosition'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/openPosition/openPosition-detail.html',
                        controller: 'OpenPositionDetailController'
                    }
                },
                resolve: {
                }
            });
    });
