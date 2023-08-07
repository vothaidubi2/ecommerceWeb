var domain = "http://localhost:8080/admin/api";
const app = angular.module("app", ['angularUtils.directives.dirPagination']);
app.controller("ctrl", function($scope, $http) {
	$scope.items = []

	$scope.loadAll = function() {
		var url = `${domain}/orders`;
		$http.get(url).then(response => {
			$scope.items = response.data;
			console.log("data: ", response.data);
		}).catch(error => {
			console.log("Error: ", error);
		})
	}
	$scope.loadAll()
	
	$scope.hideModal = function() {
			$('#exampleModal').modal('hide');
		};
		$scope.showModal = function() {
			// Reset biến lưu giá trị tìm kiếm khi mở modal
			$scope.searchInput = '';
			$('#exampleModal').modal('show');
		};
});