'use strict';

angular.module('hiringdefinedApp')
    .controller('OpenPositionController', function ($scope, OpenPosition, Company, Interview) {
        $scope.openPositions = [];
        $scope.companys = Company.query();
        $scope.interviews = Interview.query();
        $scope.loadAll = function() {
            OpenPosition.query(function(result) {
               $scope.openPositions = result;
            });
        };
        $scope.loadAll();

        $scope.showUpdate = function (id) {
            OpenPosition.get({id: id}, function(result) {
                $scope.openPosition = result;
                $('#saveOpenPositionModal').modal('show');
            });
        };

        $scope.save = function () {
            if ($scope.openPosition.id != null) {
                OpenPosition.update($scope.openPosition,
                    function () {
                        $scope.refresh();
                    });
            } else {
                OpenPosition.save($scope.openPosition,
                    function () {
                        $scope.refresh();
                    });
            }
        };

        $scope.delete = function (id) {
            OpenPosition.get({id: id}, function(result) {
                $scope.openPosition = result;
                $('#deleteOpenPositionConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            OpenPosition.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteOpenPositionConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.refresh = function () {
            $scope.loadAll();
            $('#saveOpenPositionModal').modal('hide');
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.openPosition = {name: null, domain: null, level: null, location: null, description: null, requirements: null, state: null, id: null};
            $scope.editForm.$setPristine();
            $scope.editForm.$setUntouched();
        };
    });
