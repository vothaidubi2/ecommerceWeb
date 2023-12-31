var host = "http://localhost:8080/api/specification";
var app = angular.module("app", []);
app.controller("ctrl", function($scope, $http) {
	$scope.validateAdd = 1;
	$scope.validateUpdate = 1
	$scope.currentPage = 0;
	var pageSize = 5; // Số lượng bản ghi trên mỗi trang

	// Hàm để lấy dữ liệu từ RESTful API
	function getData(page) {
		$http.get(host + "/findall?page=" + page + '&size=' + pageSize)
			.then(function(response) {
				$scope.specifications = response.data.content;
			});
	}

	$scope.key = function findByKeys(page) {
		$http.get(host + "/findByKey/" + `${$scope.searchByKeys}` + "?page=" + page + '&size=' + pageSize)
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


		// Thêm dữ liệu mới
		$http.post(host + "/create", $scope.newSpecification)
			.then(resp => {
				console.log($scope.newSpecification)
				getData($scope.currentPage);
				$scope.newSpecification = {};
				console.log("success", resp.data)
			}).catch(error => {
				console.log("error", error)
			})
		validate()




	};
	function checkKeyAndValue() {
		if (($scope.newSpecification.keys == undefined || $scope.newSpecification.value == undefined) || ($scope.newSpecification.keys.length == 0 || $scope.newSpecification.value.length == 0)) {
			$scope.validateUpdate = 1
			$scope.validateAdd = 1

		} else {
			validate()
		}
	}

	$scope.value = function() {
		checkKeyAndValue()
	}
	$scope.keys = function() {
		checkKeyAndValue()

	}
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
	}
	$scope.reset = function() {


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
	};

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
	//Thêm dữ liệu
});