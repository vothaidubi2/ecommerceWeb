var host = "http://localhost:8080/api/user";
var app = angular.module("myApp", []);
app.controller("ctrl", function($scope, $http) {
	$scope.inputValue = "";
	$scope.inputValueLogin = "";
	$scope.phoneValue = "";
	$scope.name = "";
	$scope.passValue = "";
	$scope.passValueLogin = "";
	$scope.repassValue = "";
	$scope.cheklogin = 1;
	$scope.validateLogin = 1;
	$scope.emailCheck = function() {
		var url = host + "/findByEmail/" + $scope.inputValue
		if ($scope.inputValue == "") {
			$scope.errorEmail = ""
		}
		if ($http.get(url).then(resp => {
			if (resp.data == "") {
				if (isValidEmail($scope.inputValue)) {
					$scope.errorEmail = "Valid"
					$scope.colorEmail = "text-success"
				} else {
					$scope.errorEmail = "Incorrect email format"
					$scope.colorEmail = "text-danger"
					$scope.cheklogin = 1;
				}
			} else {
				$scope.errorEmail = "Email account already exists"
				$scope.colorEmail = "text-danger"
				$scope.cheklogin = 1;
			}
			if (checkInput()) {
				$scope.cheklogin = 0;
			} else {
				$scope.cheklogin = 1;
			}
		}));



	};
	$scope.emailCheckLogin = function() {


		if (isValidEmail($scope.inputValueLogin)) {
			$scope.errorEmailLogin = "Valid"
			$scope.colorEmailLogin = "text-success"
		} else {
			$scope.errorEmailLogin = "Incorrect email format"
			$scope.colorEmailLogin = "text-danger"
		}
		if ($scope.inputValueLogin == "") {
			$scope.errorEmailLogin = "";
		}
		if (checkLogin()) {
			$scope.validateLogin = 0;
		} else {
			$scope.validateLogin = 1;
		}

	};
	$scope.phoneCheck = function() {
		if (!/^\d+$/.test($scope.phoneValue)) {
			$scope.cheklogin = 1;
			$scope.errorPhone = "Incorrect phone format"
			$scope.colorPhone = "text-danger"
		} else if ($scope.phoneValue.length > 11 || $scope.phoneValue.length < 9) {
			$scope.cheklogin = 1;
			$scope.errorPhone = "Invalid number of characters"
			$scope.colorPhone = "text-danger"

		}
		else {
			$scope.errorPhone = "Valid"
			$scope.colorPhone = "text-success"
		}
		if ($scope.phoneValue == "") {
			$scope.errorPhone = "";
		}
		if (checkInput()) {
			$scope.cheklogin = 0;
		} else {
			$scope.cheklogin = 1;
		}
	}
	$scope.passCheck = function() {
		if ($scope.passValue.length < 8) {
			$scope.cheklogin = 1;
			$scope.errorPass = "Pass number must be more than 8 characters "
			$scope.colorPass = "text-danger";
		} else {
			$scope.errorPass = "Valid"
			$scope.colorPass = "text-success"
		}
		if ($scope.passValue == "") {
			$scope.errorPass = "";
		}
		if (checkInput()) {
			$scope.cheklogin = 0;
		} else {
			$scope.cheklogin = 1;
		}
	}
	$scope.passCheckLogin = function() {
		if ($scope.passValueLogin.length < 8) {
			$scope.checksignin = 1;
			$scope.errorPassLogin = "Pass number must be more than 8 characters "
			$scope.colorPassLogin = "text-danger";
		} else {
			$scope.errorPassLogin = "Valid"
			$scope.colorPassLogin = "text-success"
		}
		if ($scope.passValueLogin == "") {
			$scope.errorPassLogin = "";
		}
		if (checkLogin()) {
			$scope.validateLogin = 0;
		} else {
			$scope.validateLogin = 1;
		}
	}
	$scope.repassCheck = function() {
		if ($scope.passValue !== $scope.repassValue) {
			$scope.cheklogin = 1;
			$scope.reerrorPass = "Passwords are not the same";
			$scope.recolorPass = "text-danger"
		} else {
			$scope.reerrorPass = "Valid"
			$scope.recolorPass = "text-success"
		}
		if ($scope.repassValue == "") {
			$scope.reerrorPas = "";
		}
		if (checkInput()) {
			$scope.cheklogin = 0;
		} else {
			$scope.cheklogin = 1;
		}

	}
	//		$scope.inputValue = "";
	//		$scope.inputValueLogin = "";
	//	$scope.phoneValue="";
	//	$scope.name = "";
	//	$scope.passValue="";
	//	$scope.passValueLogin="";
	//	$scope.repassValue="";

	function checkInput() {
		if ($scope.errorEmail == "Valid" && $scope.errorPhone == "Valid" && $scope.errorPass == "Valid" && $scope.reerrorPass == "Valid") {
			return true;
		} else {
			return false;
		}

	}
	function checkLogin() {
		if ($scope.errorEmailLogin == "Valid" && $scope.errorPassLogin === "Valid") {
			return true;
		} else { return false; }
	}
	function isValidEmail(email) {
		// Biểu thức chính quy để kiểm tra địa chỉ email
		const emailPattern = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
		return emailPattern.test(email);
	}

});