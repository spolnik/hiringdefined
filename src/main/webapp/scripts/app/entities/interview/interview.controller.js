'use strict';

angular.module('hiringdefinedApp')
    .controller('InterviewController', function ($scope, Interview) {
        $scope.interviews = [];
        $scope.loadAll = function() {
            Interview.query(function(result) {
               $scope.interviews = result;
            });
        };
        $scope.loadAll();

        $scope.showUpdate = function (id) {
            Interview.get({id: id}, function(result) {
                $scope.interview = result;
                $('#saveInterviewModal').modal('show');
            });
        };

        $scope.save = function () {
            if ($scope.interview.id != null) {
                Interview.update($scope.interview,
                    function () {
                        $scope.refresh();
                    });
            } else {
                Interview.save($scope.interview,
                    function () {
                        $scope.refresh();
                    });
            }
        };

        $scope.delete = function (id) {
            Interview.get({id: id}, function(result) {
                $scope.interview = result;
                $('#deleteInterviewConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            Interview.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteInterviewConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.refresh = function () {
            $scope.loadAll();
            $('#saveInterviewModal').modal('hide');
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.interview = {companyName: null, position: null, seniority: null, id: null};
            $scope.editForm.$setPristine();
            $scope.editForm.$setUntouched();
        };
    });
