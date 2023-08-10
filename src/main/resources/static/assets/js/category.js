var host = "http://localhost:8080/api/category";
var app = angular.module("app", []);
app.controller("ctrl", function($scope, $http) {
	$scope.validateAdd = 1
	$scope.currentPage = 0;
	$scope.validateUpdate = 1;
	var pageSize = 5; // Số lượng bản ghi trên mỗi trang

	// Hàm để lấy dữ liệu từ RESTful API
	function getData(page) {
		$http.get(host + "/findallStatusTrue?page=" + page + '&size=' + pageSize)
			.then(function(response) {
				$scope.categories = response.data.content;


			});
	}
	getData($scope.currentPage);
	// Gọi hàm để lấy dữ liệu ban đầu


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
		if ($scope.categories.length == 0) {

			return getData($scope.currentPage--);
		} else { return getData($scope.currentPage); }

	};
	$scope.add = function() {


		// Thêm dữ liệu mới
		$http.post(host + "/create", $scope.newUser)
			.then(resp => {
				console.log($scope.newUser)
				getData($scope.currentPage);
				$scope.validateAdd = 1
				$scope.newUser = {};
				console.log("success", resp.data)
			}).catch(error => {
				console.log("error", error)
			})
		validate()
	};
	$scope.keys = function() {


		if (($scope.newUser.name == "")) {
			$scope.validateAdd = 1
			$scope.validateUpdate = 1;

		} else {
			validate()

		}

	}
	$scope.update = function(id) {
		$http.put(host + `/update/${id}`, $scope.newUser)
			.then(resp => {
				getData($scope.currentPage);
				console.log("success", resp.data)
			}).catch(error => {
				console.log("error", error)
			})

		validate();
	}
	$scope.reset = function() {

		$scope.newUser = {};
		$scope.validateUpdate = 1
		$scope.validateAdd = 1
	}

	$scope.editSpecification = function(category) {

		// Copy dữ liệu của bản ghi muốn sửa vào biến newUser
		$scope.newUser = angular.copy(category);
		// Chuyển trạng thái sang sửa
		$scope.isAdding = false;
		validate()

	};

	// Hàm xóa dữ liệu
	$scope.delete = function(specification) {

		$http.put(host + `/delete`, specification)
			.then(function(resp) {
				$scope.newUser = {};
				validate();
				$scope.validateAdd = 1

				getData($scope.currentPage);

			}

			);
	};
	function validate() {

		if ($scope.newUser.id != null) {
			$scope.validateAdd = 1
			$scope.validateUpdate = 0;
		} else {
			$scope.validateAdd = 0;
			$scope.validateUpdate = 1;

		}

	}
	//Thêm dữ liệu
});