var host = "http://localhost:8080/rest";
var app = angular.module("myApp", []);
app.controller("ctrl", function($scope, $http) {
	$scope.inputValue = "";
		$scope.inputValueLogin = "";
	$scope.phoneValue="";
	$scope.name = "";
	$scope.passValue="";
	$scope.passValueLogin="";
	$scope.repassValue="";
	$scope.emailCheck = function() {
		var url = host + "/findByEmail/" + $scope.inputValue
		if($scope.inputValue==""){
			$scope.errorEmail = ""
		}
		if ($http.get(url).then(resp => {
			if (resp.data == "") {
				if (isValidEmail($scope.inputValue)) {
					$scope.errorEmail = "Valid account"
					$scope.colorEmail="text-success"
				}else{
					$scope.errorEmail = "Incorrect email format"
						$scope.colorEmail="text-danger"
				}
			} else {
				$scope.errorEmail = "Email account already exists"
					$scope.colorEmail="text-danger"
			}
		}));
			
	

	};
		$scope.emailCheckLogin = function() {
		if($scope.inputValueLogin==""){
			$scope.errorEmailLogin = "";
		}
	console.log($scope.inputValueLogin)
		
				if (isValidEmail($scope.inputValueLogin)) {
					$scope.errorEmailLogin = "Valid account"
					$scope.colorEmailLogin="text-success"
				}else{
					$scope.errorEmailLogin = "Incorrect email format"
						$scope.colorEmailLogin="text-danger"
				}

	};
	$scope.phoneCheck=function(){
		console.log($scope.phoneValue);
		if(!/^\d+$/.test($scope.phoneValue)){
				$scope.errorPhone = "Incorrect phone format"
						$scope.colorPhone="text-danger"
		}else if($scope.phoneValue.length>11 || $scope.phoneValue.length<9 ){
				$scope.errorPhone = "Invalid number of characters"
						$scope.colorPhone="text-danger"
		
			}
			else{
				$scope.errorPhone = "valid phone"
						$scope.colorPhone="text-success"
		}
		if($scope.phoneValue==""){
			$scope.errorPhone = "";
		}
	}
	$scope.passCheck=function(){
		if($scope.passValue.length<8){
				$scope.errorPass = "Pass number must be more than 8 characters "
			$scope.colorPass="text-danger";
		}else{
				$scope.errorPass = "valid pass"
						$scope.colorPass="text-success"
		}
			if($scope.passValue==""){
			$scope.errorPass = "";
		}
	}
		$scope.passCheckLogin=function(){
		if($scope.passValueLogin.length<8){
				$scope.errorPassLogin = "Pass number must be more than 8 characters "
			$scope.colorPassLogin="text-danger";
		}else{
				$scope.errorPassLogin = "valid pass"
						$scope.colorPassLogin="text-success"
		}
			if($scope.passValue==""){
			$scope.errorPass = "";
		}
	}
	$scope.repassCheck=function(){
		if($scope.passValue!==$scope.repassValue){
				$scope.reerrorPass="Passwords are not the same";
					$scope.recolorPass="text-danger"
		}else{
				$scope.reerrorPass = "re-enter the correct password"
						$scope.recolorPass="text-success"
		}
			if($scope.repassValue==""){
			$scope.reerrorPas = "";
		}
		
	}
	function isValidEmail(email) {
		// Biểu thức chính quy để kiểm tra địa chỉ email
		const emailPattern = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
		return emailPattern.test(email);
	}

});