'use strict';

angular.module('hiringdefinedApp')
    .controller('OpenPositionDetailController', function ($scope, $stateParams, OpenPosition) {
        $scope.openPosition = {};
        $scope.load = function (id) {
            OpenPosition.get({id: id}, function(result) {
              $scope.openPosition = result;
            });
        };
        $scope.load($stateParams.id);
    });
