'use strict';

angular.module('hiringdefinedApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('job', {
                parent: 'entity',
                url: '/job',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'Jobs'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/job/jobs.html',
                        controller: 'JobController'
                    }
                },
                resolve: {
                }
            })
            .state('jobDetail', {
                parent: 'entity',
                url: '/job/:id',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'Job'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/job/job-detail.html',
                        controller: 'JobDetailController'
                    }
                },
                resolve: {
                }
            });
    });
