'use strict';

angular.module('hiringdefinedApp')
    .controller('LogoutController', function (Auth) {
        Auth.logout();
    });
