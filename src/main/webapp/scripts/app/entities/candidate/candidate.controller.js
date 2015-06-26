'use strict';

angular.module('hiringdefinedApp')
    .controller('CandidateController', function ($scope, Candidate, ParseLinks) {
        $scope.candidates = [];
        $scope.page = 1;
        $scope.loadAll = function() {
            Candidate.query({page: $scope.page, per_page: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.candidates = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
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
            $scope.candidate = {fullName: null, email: null, linkedIn: null, github: null, motivation: null, id: null};
            $scope.editForm.$setPristine();
            $scope.editForm.$setUntouched();
        };
    });
