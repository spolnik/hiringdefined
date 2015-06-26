'use strict';

angular.module('hiringdefinedApp')
    .controller('InterviewStepDetailController', function ($scope, $stateParams, InterviewStep) {
        $scope.interviewStep = {};
        $scope.load = function (id) {
            InterviewStep.get({id: id}, function(result) {
              $scope.interviewStep = result;
            });
        };
        $scope.load($stateParams.id);
    });
