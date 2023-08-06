var host = "http://localhost:8080/api/user";

function date() {
	return currentDate = new Date().toJSON().slice(0, 10);
}
var app = angular.module("app", []);
app.controller("ctrl", function($scope, $http) {
	$scope.validateAdd = 1;
	$scope.validateUpdate = 1
	$scope.currentPage = 0;
	var pageSize = 5; // Số lượng bản ghi trên mỗi trang

	// Hàm để lấy dữ liệu từ RESTful API
	function getData(page) {
		console.log(page)
		$http.get(host + "/findall?page=" + page + '&size=' + pageSize)
			.then(function(response) {
				$scope.specifications = response.data.content;
			});
	}


	//	$scope.key = function findByKeys() {
	//		$http.get(host + "/findByEmail/" + $scope.searchByKeys)
	//	
	//			.then(function(response) {
	//					console.log()
	//		$scope.specifications =response.data;
	//				
	//			});
	//
	//	}
	$scope.key = function findByKeys(page) {
		$http.get(host + "/findTableByEmail/" + `${$scope.searchByKeys}` + "?page=" + page + '&size=' + pageSize)
			.then(function(response) {
				$scope.specifications = response.data.content;
			});

	}


	// Gọi hàm để lấy dữ liệu ban đầu
	getData($scope.currentPage);

	// Hàm phân trang - trang trước
	$scope.prevPage = function() {

		if ($scope.currentPage > 0) {
			$scope.currentPage--;
			if ($scope.searchByKeys == null || $scope.searchByKeys == "") {
				getData($scope.currentPage);
			} else { $scope.key($scope.currentPage) }

		}
	};

	// Hàm phân trang - trang kế tiếp
	$scope.nextPage = function() {
		$scope.currentPage++;
		if ($scope.searchByKeys == null || $scope.searchByKeys == "") {
			getData($scope.currentPage);
		} else { $scope.key($scope.currentPage) }
		if ($scope.specifications.length == 0) {
			if ($scope.searchByKeys == null || $scope.searchByKeys == "") {
				return getData($scope.currentPage--);
			} else { return $scope.key($scope.currentPage--) }
		}
	};
	$scope.add = function() {
		$scope.newSpecification.date = date();
		$scope.newSpecification.status = true;
		//		 Thêm dữ liệu mới
		$http.post(host + "/create", $scope.newSpecification)
			.then(resp => {
				console.log($scope.newSpecification)

				getData($scope.currentPage);
				$scope.newSpecification = {};
				console.log("success", resp.data)
			}).catch(error => {
				console.log("error", error)
			})
		//		validate()

		resetvalid()



	};

	$scope.update = function(id) {
		$http.put(host + `/update/${id}`, $scope.newSpecification)
			.then(resp => {
				console.log($scope.newSpecification)
				getData($scope.currentPage);
				console.log("success", resp.data)
			}).catch(error => {
				console.log("error", error)
			})
		console.log(id)
		validate()
		resetvalid()

	}
	$scope.reset = function() {

		resetvalid()
		$scope.newSpecification = {};
		$scope.validateUpdate = 1
		$scope.validateAdd = 1
	}
	$scope.editSpecification = function(specification) {

		// Copy dữ liệu của bản ghi muốn sửa vào biến newSpecification
		$scope.newSpecification = angular.copy(specification);
		// Chuyển trạng thái sang sửa
		$scope.isAdding = false;
		console.log($scope.newSpecification)
		validate()
		resetvalid()
	};
	$scope.emailCheck = function() {
		var url = host + "/findByEmail/" + $scope.newSpecification.email
		if ($scope.newSpecification.email == "") {
			$scope.errorEmail = ""
		}
		if ($http.get(url).then(resp => {
			if (resp.data == "") {
				if (isValidEmail($scope.newSpecification.email)) {
					$scope.errorEmail = "Valid"
					$scope.colorEmail = "text-success"
				} else {
					$scope.errorEmail = "Incorrect email format"
					$scope.colorEmail = "text-danger"
				}
			} else {
				if ($scope.newSpecification.id == null) {
					$scope.errorEmail = "Email account already exists"
					$scope.colorEmail = "text-danger"
				} else {
					$scope.errorEmail = "Valid"
					$scope.colorEmail = "text-success"
				}

			}
			validForm()
			console.log($scope.errorEmail == "Valid")

		}));



	};
	$scope.passCheck = function() {
		if ($scope.newSpecification.password.length < 8) {
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
		validForm()

	}
	$scope.phoneCheck = function() {
		if (!/^\d+$/.test($scope.newSpecification.phone)) {
			$scope.cheklogin = 1;
			$scope.errorPhone = "Incorrect phone format"
			$scope.colorPhone = "text-danger"
		} else if ($scope.newSpecification.phone.length > 11 || $scope.newSpecification.phone.length < 9) {
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
		validForm()

	}
	// Hàm xóa dữ liệu
	$scope.delete = function(spec) {

		$http.put(host + `/delete`, spec)
			.then(function(resp) {
				$scope.newSpecification = {};

				getData($scope.currentPage);
				validate()
				$scope.validateAdd = 1;
			}

			);
		resetvalid()
	};
	function validate() {

		if ($scope.newSpecification.id != null) {
			$scope.validateAdd = 1
			$scope.validateUpdate = 0;
		} else {
			$scope.validateAdd = 0;
			$scope.validateUpdate = 1;

		}

	}
	function validForm() {
		if ($scope.errorPhone == "Valid" && $scope.errorPass == "Valid" && $scope.errorEmail == "Valid") {
			validate();
		} else {
			$scope.validateAdd = 1;
		}
		
	}
	function isValidEmail(email) {
		// Biểu thức chính quy để kiểm tra địa chỉ email
		const emailPattern = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
		return emailPattern.test(email);
	}
	function resetvalid() {
		$scope.errorPhone = ""
		$scope.errorPass = ""
		$scope.errorEmail = ""
	}

	//Thêm dữ liệu
});