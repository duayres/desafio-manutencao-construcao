"use strict";
var app = angular.module("salasDeReuniao", ['ngRoute', 'ngMaterial', 'eits-ng', 'ngSanitize', 'ngMaterialDatePicker', 'angularUtils.directives.dirPagination']).config( 
    function($importServiceProvider) {
        //-------
        //Broker configuration
        //-------
        $importServiceProvider.setBrokerURL("./dwr/interface/");
    }
);