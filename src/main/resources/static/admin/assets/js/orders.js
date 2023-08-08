var domain = "http://localhost:8080/admin/api";
const app = angular.module("app", ['angularUtils.directives.dirPagination']);
app.controller("ctrl", function($scope, $http) {
	$scope.items = []
	$scope.currentId = null;

	$scope.loadAll = function() {
		var url = `${domain}/orders`;
		$http.get(url).then(response => {
			$scope.items = response.data;
			console.log("data: ", response.data);
		}).catch(error => {
			console.log("Error: ", error);
		})
	}
	$scope.update = function(input) {
		$http.put(`${domain}/orders/${$scope.currentId}`, input)
	.then(function() {
		$scope.currentId = null;
		$scope.loadAll()
	})
	.catch(function(error) {
		console.error('Error :', error);
	});
	};

$scope.loadAll()

$scope.hideModal = function() {
	$('#exampleModal').modal('hide');
};
$scope.showModal = function(input) {
	// Reset biến lưu giá trị tìm kiếm khi mở modal
	$scope.currentId = input;
	$('#exampleModal').modal('show');
};
});