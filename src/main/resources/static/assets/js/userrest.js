var host = "http://localhost:8080/api/user";

function date() {
	return currentDate = new Date().toJSON().slice(0, 10);
}
var app = angular.module("app", []);
app.controller("ctrl", function($scope, $http) {
	$scope.validateUpdate = 1
	$scope.currentPage = 0;
	var pageSize = 5; // Số lượng bản ghi trên mỗi trang

	// Hàm để lấy dữ liệu từ RESTful API
	function getData(page) {
		console.log(page)
		$http.get(host + "/findall?page=" + page + '&size=' + pageSize)
			.then(function(response) {
				$scope.users = response.data.content;
			});
	}

	$scope.key = function findByKeys(page) {
		$http.get(host + "/findTableByEmail/" + `${$scope.searchByKeys}` + "?page=" + page + '&size=' + pageSize)
			.then(function(response) {
				$scope.users = response.data.content;
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
		if ($scope.users.length == 0) {
			if ($scope.searchByKeys == null || $scope.searchByKeys == "") {
				return getData($scope.currentPage--);
			} else { return $scope.key($scope.currentPage--) }
		}
	};

	$scope.update = function(id) {
		$http.put(host + `/update/${id}`, $scope.newUser)
			.then(resp => {
				console.log($scope.newUser)
				getData($scope.currentPage);
				console.log("success", resp.data)
			}).catch(error => {
				console.log("error", error)
			})
		resetvalid()

	}
	$scope.reset = function() {

		resetvalid()
		$scope.newUser = {};
		$scope.validateUpdate = 1;
	}
	$scope.editUser = function(user) {

		// Copy dữ liệu của bản ghi muốn sửa vào biến newUser
		$scope.newUser = angular.copy(user);
		// Chuyển trạng thái sang sửa
		$scope.isAdding = false;
		console.log($scope.newUser)
			$scope.validateUpdate = 0;
		resetvalid()
	};
	$scope.emailCheck = function() {
		var url = host + "/findByEmail/" + $scope.newUser.email
		if ($scope.newUser.email == "") {
			$scope.errorEmail = ""
		}
		if ($http.get(url).then(resp => {
					if (!isValidEmail($scope.newUser.email)) {
			$scope.errorEmail = "Incorrect email format"
					$scope.colorEmail = "text-danger"
					validateUpdate()
				} 
			if (resp.data == "") {
					$scope.errorEmail = "Email account haven't exists"
					$scope.colorEmail = "text-danger"
validateUpdate()
		
			} else {

					$scope.errorEmail = "Valid"
					$scope.colorEmail = "text-success"
					validateUpdate()
			}

		}));



	};
	function validateUpdate(){
		if($scope.colorPhone == "text-danger" || $scope.colorEmail == "text-danger"){
				$scope.validateUpdate = 1;
		}else{$scope.validateUpdate = 0;}
	}
	$scope.phoneCheck = function() {
		if (!/^\d+$/.test($scope.newUser.phone)) {
			
			$scope.errorPhone = "Incorrect phone format"
			$scope.colorPhone = "text-danger"
			validateUpdate()
		} else if ($scope.newUser.phone.length > 11 || $scope.newUser.phone.length < 9) {
		
			$scope.errorPhone = "Invalid number of characters"
			$scope.colorPhone = "text-danger"
				validateUpdate()

		}
		else {
		
			$scope.errorPhone = "Valid"
			$scope.colorPhone = "text-success"
				validateUpdate()
		}
		if ($scope.newUser.phone == "") {
			$scope.errorPhone = "";
		}


	}
	// Hàm xóa dữ liệu
	$scope.delete = function(spec) {

		$http.put(host + `/delete`, spec)
			.then(function(resp) {
				$scope.newUser = {};

				getData($scope.currentPage);
				$scope.validateAdd = 1;
			}

			);
		resetvalid()
	};

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