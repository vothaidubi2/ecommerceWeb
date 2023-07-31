let domain = "http://localhost:8080/api";
const app = angular.module("app", ['ui.bootstrap']);
app.controller("ctrl", function($scope, $http, $location) {
	$scope.items = [];
	$scope.keywords = null;
	$scope.sorttype = null;
	$scope.category = null;
	$scope.producer = null;
	$scope.minprice = null;
	$scope.maxprice = null;
	$scope.toltal = null;
	$scope.curPage = 1;
	$scope.itemsPerPage = 9;
	$scope.countPage = null;
	$scope.listPage = null;
	$scope.paramPage = null;

	$scope.loadAll = function() {
		//search
		if ($location.absUrl().indexOf('?') !== -1) {
			var queryParamsPart = $location.absUrl().split('?')[1];
			var param = queryParamsPart.split('&')[0].split('=')[1];
			$scope.keywords = param;
		};
		let temp = $scope.keywords == null ? "" : `keywords=${$scope.keywords}`
		var url = `${domain}/products?${$scope.sorttype}&${temp}&${$scope.category}&${$scope.producer}&${$scope.minprice}&${$scope.maxprice}&${$scope.paramPage}`;
		$http.get(url).then(response => {
			$scope.items = response.data.data;
			$scope.total = response.data.total;
			$scope.countPage = $scope.total % $scope.itemsPerPage == 0 ? $scope.total / $scope.itemsPerPage : Math.ceil($scope.total / $scope.itemsPerPage);
			$scope.listPage = $scope.getRange($scope.countPage);
			console.log("Success", response.data.data[0].image[0].url);
		}).catch(error => {
			console.log("Error: ", error);
		})
	}
	$scope.loadAll();

	//filter category
	$scope.filterCategory = function(event) {
		if (event.target.value == 'all') {
			$scope.category = null;
			$scope.loadAll();
		} else {
			$scope.category = `categoryId=${event.target.value}`;
			$scope.loadAll();
		}
	}

	//filter brand
	$scope.filterProducer = function(event) {
		if (event.target.value == 'all') {
			$scope.producer = null;
			$scope.loadAll();
		} else {
			$scope.producer = `producerId=${event.target.value}`;
			$scope.loadAll();
		}
	}

	//filter by price
	$scope.filterPrice = function(event) {
		if (event.target.id == "minprice") {
			$scope.minprice = `minrange=${event.target.value}`;
			$scope.loadAll();
		} else {
			$scope.maxprice = `maxrange=${event.target.value}`;
			$scope.loadAll();
		}
	}

	//sort
	$("#sortby").change(function() {
		if (this.value == 'asc') {
			$scope.sorttype = 'sort=price,asc';
			$scope.loadAll();
		} else if (this.value == 'desc') {
			$scope.sorttype = 'sort=price,desc';
			$scope.loadAll();
		}
	});

	// pagination
	$scope.getRange = function(input) {
		let temp = [];
		for (var i = 0; i < input; i++) {
			temp.push(i + 1);
		}
		return temp;
	};

	//changepage
	$scope.changePage = function(input) {
		$scope.curPage = input;
		$scope.paramPage = `page=${input - 1}`
		$scope.loadAll();
	}
});
$(document).ready(function() {
	// Add 'active' class to the first <li> initially
	$('.repeat-item li:first-child').addClass('active');

	// Add click event listener to each <li>
	$('.repeat-item li').click(function() {
		// Remove 'active' class from all <li> elements
		$('.repeat-item li').removeClass('active');

		// Add 'active' class to the clicked <li> element
		$(this).addClass('active');
	});
});

