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