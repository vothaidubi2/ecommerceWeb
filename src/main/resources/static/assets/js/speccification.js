var host = "http://localhost:8080/api/specification";
var app = angular.module("app", []);
app.controller("ctrl", function($scope, $http) {
	$scope.validate = 1
	$scope.currentPage = 0;
	var pageSize = 5; // Số lượng bản ghi trên mỗi trang

	// Hàm để lấy dữ liệu từ RESTful API
	function getData(page) {
		$http.get(host + "/findall?page=" + page + '&size=' + pageSize)
			.then(function(response) {
				$scope.specifications = response.data.content;
			});
	}
	
$scope.key=function findByKeys(page){
	$http.get(host+"/findByKey/"+`${$scope.searchByKeys}`+"?page=" +page+'&size=' + pageSize)
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
			if($scope.searchByKeys==null || $scope.searchByKeys==""){
							getData($scope.currentPage);
			}else{$scope.key($scope.currentPage)}

		}
	};

	// Hàm phân trang - trang kế tiếp
	$scope.nextPage = function() {
		$scope.currentPage++;
	if($scope.searchByKeys==null || $scope.searchByKeys==""){
							getData($scope.currentPage);
			}else{$scope.key($scope.currentPage)}
		if ($scope.specifications.length == 0) {
				if($scope.searchByKeys==null || $scope.searchByKeys==""){
							return getData($scope.currentPage--);
			}else{return $scope.key($scope.currentPage--)}
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
		if ($scope.newSpecification.id != null) {
			$scope.validate = 1
		} else {
			$scope.validate = 0
		}




	};


	$scope.value = function() {
		if (!($scope.newSpecification.keys == "" && $scope.newSpecification.value == "")) {

			if (($scope.newSpecification.keys == "" || $scope.newSpecification.value == "")) {
				$scope.validate = 1

			} else {
				if ($scope.newSpecification.id != null) {
					$scope.validate = 1
				} else {
					$scope.validate = 0
				}

			}

		}
	}
	$scope.keys = function() {
		if (($scope.newSpecification.keys == "" || $scope.newSpecification.value == "")) {
			$scope.validate = 1

		} else {
			if ($scope.newSpecification.id != null) {
				$scope.validate = 1
			} else {
				$scope.validate = 0
			}

		}

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
		if ($scope.newSpecification.id != null) {
			$scope.validate = 1
		} else {
			$scope.validate = 0
		}
	}
	$scope.reset = function() {

		$scope.newSpecification = {};
		$scope.validate = 1
	}
	$scope.editSpecification = function(specification) {

		// Copy dữ liệu của bản ghi muốn sửa vào biến newSpecification
		$scope.newSpecification = angular.copy(specification);
		// Chuyển trạng thái sang sửa
		$scope.isAdding = false;
		console.log($scope.newSpecification)
		if ($scope.newSpecification.id != null) {
			$scope.validate = 1
		} else {
			$scope.validate = 0
		}
	};

	// Hàm xóa dữ liệu
	$scope.delete = function(id) {

		$http.delete(host + `/delete/${id}`)
			.then(function(resp) {
				$scope.newSpecification = {};
				getData($scope.currentPage);
				if ($scope.newSpecification.id != null) {
					$scope.validate = 1
				} else {
					$scope.validate = 0
				}
			}

			);
	};
	//Thêm dữ liệu
});