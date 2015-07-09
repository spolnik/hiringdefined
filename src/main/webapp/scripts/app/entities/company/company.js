'use strict';

angular.module('hiringdefinedApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('company', {
                parent: 'entity',
                url: '/company',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'Companys'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/company/companys.html',
                        controller: 'CompanyController'
                    }
                },
                resolve: {
                }
            })
            .state('companyDetail', {
                parent: 'entity',
                url: '/company/:id',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'Company'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/company/company-detail.html',
                        controller: 'CompanyDetailController'
                    }
                },
                resolve: {
                }
            });
    });
