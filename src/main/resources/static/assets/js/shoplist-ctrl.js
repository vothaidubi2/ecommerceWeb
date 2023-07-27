let domain = "http://localhost:8080/api";
const app = angular.module("app", []);
app.controller("ctrl", function($scope,$http,$location) {
	$scope.items = [];
	$scope.keyword = $location.search().keywords;
	let temp = $scope.keyword==null ? "" : `keywords=${$scope.keyword}`
	$scope.loadAll = function() {
		console.log("http",$scope.keyword)
		var url = `${domain}/products?${temp}`;
		$http.get(url).then(response => {
			$scope.items = response.data;
			console.log("Success", response.data);
		}).catch(error => {
			console.log("Error: ", error);
		})
	};
	$scope.loadAll();

	//sort
	$("#sortby").change(function() {
		if (this.value == 'asc') {
			var url = `${domain}/products?sort=price,asc`;
			$http.get(url).then(response => {
				$scope.items = response.data;
				console.log("Success", response.data);
			}).catch(error => {
				console.log("Error: ", error);
			})
		} else if (this.value == 'desc') {
			var url = `${domain}/products?sort=price,desc`;
			$http.get(url).then(response => {
				$scope.items = response.data;
				console.log("Success", response.data);
			}).catch(error => {
				console.log("Error: ", error);
			})
		}
	});
});

