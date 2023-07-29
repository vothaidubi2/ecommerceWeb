let domain = "http://localhost:8080/api";
const app = angular.module("app", ['ui.bootstrap']);

app.controller("ctrl", function($scope, $http, $location) {
	alert("AngularJS initialized")
	$scope.addToCart = function(idProduct){
		$http.get()
	}
});