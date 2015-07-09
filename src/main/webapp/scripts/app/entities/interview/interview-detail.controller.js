'use strict';

angular.module('hiringdefinedApp')
    .controller('InterviewDetailController', function ($scope, $stateParams, Interview, InterviewStep, OpenPosition, User) {
        $scope.interview = {};
        $scope.load = function (id) {
            Interview.get({id: id}, function(result) {
              $scope.interview = result;
            });
        };
        $scope.load($stateParams.id);
    });
