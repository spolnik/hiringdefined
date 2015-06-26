'use strict';

angular.module('hiringdefinedApp')
    .controller('InterviewStepController', function ($scope, InterviewStep) {
        $scope.interviewSteps = [];
        $scope.loadAll = function() {
            InterviewStep.query(function(result) {
               $scope.interviewSteps = result;
            });
        };
        $scope.loadAll();

        $scope.showUpdate = function (id) {
            InterviewStep.get({id: id}, function(result) {
                $scope.interviewStep = result;
                $('#saveInterviewStepModal').modal('show');
            });
        };

        $scope.save = function () {
            if ($scope.interviewStep.id != null) {
                InterviewStep.update($scope.interviewStep,
                    function () {
                        $scope.refresh();
                    });
            } else {
                InterviewStep.save($scope.interviewStep,
                    function () {
                        $scope.refresh();
                    });
            }
        };

        $scope.delete = function (id) {
            InterviewStep.get({id: id}, function(result) {
                $scope.interviewStep = result;
                $('#deleteInterviewStepConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            InterviewStep.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteInterviewStepConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.refresh = function () {
            $scope.loadAll();
            $('#saveInterviewStepModal').modal('hide');
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.interviewStep = {name: null, description: null, stageNr: null, id: null};
            $scope.editForm.$setPristine();
            $scope.editForm.$setUntouched();
        };
    });
