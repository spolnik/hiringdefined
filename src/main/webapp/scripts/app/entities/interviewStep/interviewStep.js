'use strict';

angular.module('hiringdefinedApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('interviewStep', {
                parent: 'entity',
                url: '/interviewStep',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'InterviewSteps'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/interviewStep/interviewSteps.html',
                        controller: 'InterviewStepController'
                    }
                },
                resolve: {
                }
            })
            .state('interviewStepDetail', {
                parent: 'entity',
                url: '/interviewStep/:id',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'InterviewStep'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/interviewStep/interviewStep-detail.html',
                        controller: 'InterviewStepDetailController'
                    }
                },
                resolve: {
                }
            });
    });
