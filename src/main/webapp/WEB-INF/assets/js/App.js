"use strict";
var app = angular.module("agendamentoManutencao", ['ngRoute', 'ngMaterial', 'eits-ng', 'ngSanitize', 'ngMaterialDatePicker', 'angularUtils.directives.dirPagination']).config( 
    function($importServiceProvider,$mdThemingProvider) {
        //-------
        //Broker configuration
        //-------
        $importServiceProvider.setBrokerURL("./dwr/interface/");
        
        /*
         * Tema..
         */
    	$mdThemingProvider.theme('default')
        .primaryPalette('blue-grey')
        .accentPalette('orange');
    }
);

app.filter('capitalize', function() {
    return function(input, scope) {
    	return input.charAt(0).toUpperCase() + input.slice(1).toLowerCase();
    }
});

/*String.prototype.capitalize = function() {
    return this.charAt(0).toUpperCase() + this.slice(1).toLowerCase();
}*/