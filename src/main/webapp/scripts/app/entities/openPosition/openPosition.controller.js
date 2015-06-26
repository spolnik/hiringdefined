'use strict';

angular.module('hiringdefinedApp')
    .controller('OpenPositionController', function ($scope, OpenPosition, ParseLinks) {
        $scope.openPositions = [];
        $scope.page = 1;
        $scope.loadAll = function() {
            OpenPosition.query({page: $scope.page, per_page: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.openPositions = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
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
            $scope.openPosition = {companyName: null, position: null, seniority: null, location: null, description: null, requirements: null, state: null, id: null};
            $scope.editForm.$setPristine();
            $scope.editForm.$setUntouched();
        };
    });
