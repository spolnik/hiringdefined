'use strict';

angular.module('hiringdefinedApp')
    .controller('CandidateDetailController', function ($scope, $stateParams, Candidate) {
        $scope.candidate = {};
        $scope.load = function (id) {
            Candidate.get({id: id}, function(result) {
              $scope.candidate = result;
            });
        };
        $scope.load($stateParams.id);
    });
