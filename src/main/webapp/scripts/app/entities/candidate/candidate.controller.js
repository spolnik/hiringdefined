'use strict';

angular.module('hiringdefinedApp')
    .controller('CandidateController', function ($scope, Candidate, Company) {
        $scope.candidates = [];
        $scope.companys = Company.query();
        $scope.loadAll = function() {
            Candidate.query(function(result) {
               $scope.candidates = result;
            });
        };
        $scope.loadAll();

        $scope.showUpdate = function (id) {
            Candidate.get({id: id}, function(result) {
                $scope.candidate = result;
                $('#saveCandidateModal').modal('show');
            });
        };

        $scope.save = function () {
            if ($scope.candidate.id != null) {
                Candidate.update($scope.candidate,
                    function () {
                        $scope.refresh();
                    });
            } else {
                Candidate.save($scope.candidate,
                    function () {
                        $scope.refresh();
                    });
            }
        };

        $scope.delete = function (id) {
            Candidate.get({id: id}, function(result) {
                $scope.candidate = result;
                $('#deleteCandidateConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            Candidate.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteCandidateConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.refresh = function () {
            $scope.loadAll();
            $('#saveCandidateModal').modal('hide');
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.candidate = {fullName: null, email: null, linkedin: null, github: null, motivation: null, id: null};
            $scope.editForm.$setPristine();
            $scope.editForm.$setUntouched();
        };
    });
