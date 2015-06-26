'use strict';

angular.module('hiringdefinedApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('interview', {
                parent: 'entity',
                url: '/interview',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'Interviews'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/interview/interviews.html',
                        controller: 'InterviewController'
                    }
                },
                resolve: {
                }
            })
            .state('interviewDetail', {
                parent: 'entity',
                url: '/interview/:id',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'Interview'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/interview/interview-detail.html',
                        controller: 'InterviewDetailController'
                    }
                },
                resolve: {
                }
            });
    });
