'use strict';

angular.module('hiringdefinedApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('candidate', {
                parent: 'entity',
                url: '/candidate',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'Candidates'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/candidate/candidates.html',
                        controller: 'CandidateController'
                    }
                },
                resolve: {
                }
            })
            .state('candidateDetail', {
                parent: 'entity',
                url: '/candidate/:id',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'Candidate'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/candidate/candidate-detail.html',
                        controller: 'CandidateDetailController'
                    }
                },
                resolve: {
                }
            });
    });
