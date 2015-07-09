'use strict';

angular.module('hiringdefinedApp')
    .controller('CompanyController', function ($scope, Company, Candidate, OpenPosition, User) {
        $scope.companys = [];
        $scope.candidates = Candidate.query();
        $scope.openpositions = OpenPosition.query();
        $scope.users = User.query();
        $scope.loadAll = function() {
            Company.query(function(result) {
               $scope.companys = result;
            });
        };
        $scope.loadAll();

        $scope.showUpdate = function (id) {
            Company.get({id: id}, function(result) {
                $scope.company = result;
                $('#saveCompanyModal').modal('show');
            });
        };

        $scope.save = function () {
            if ($scope.company.id != null) {
                Company.update($scope.company,
                    function () {
                        $scope.refresh();
                    });
            } else {
                Company.save($scope.company,
                    function () {
                        $scope.refresh();
                    });
            }
        };

        $scope.delete = function (id) {
            Company.get({id: id}, function(result) {
                $scope.company = result;
                $('#deleteCompanyConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            Company.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteCompanyConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.refresh = function () {
            $scope.loadAll();
            $('#saveCompanyModal').modal('hide');
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.company = {name: null, contactPerson: null, contactEmail: null, url: null, id: null};
            $scope.editForm.$setPristine();
            $scope.editForm.$setUntouched();
        };
    });
