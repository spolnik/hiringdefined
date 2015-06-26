'use strict';

angular.module('hiringdefinedApp')
    .controller('CompanyDetailController', function ($scope, $stateParams, Company) {
        $scope.company = {};
        $scope.load = function (id) {
            Company.get({id: id}, function(result) {
              $scope.company = result;
            });
        };
        $scope.load($stateParams.id);
    });
