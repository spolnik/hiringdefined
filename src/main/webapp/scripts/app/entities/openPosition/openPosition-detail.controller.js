'use strict';

angular.module('hiringdefinedApp')
    .controller('OpenPositionDetailController', function ($scope, $stateParams, OpenPosition, Company) {
        $scope.openPosition = {};
        $scope.companies = [];

        $scope.load = function (id) {
            OpenPosition.get({id: id}, function(result) {
              $scope.openPosition = result;
            });
            Company.query(function(result) {
                $scope.companies = result.map(function(company) {
                    return company.companyName;
                });
            });
        };
        $scope.load($stateParams.id);
    });
