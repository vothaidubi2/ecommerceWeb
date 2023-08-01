let domain = "http://localhost:8080/api";
const app = angular.module("app", []);
app.controller("ctrl", function($scope, $http, $location, $rootScope) {
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

		
	
//---------------------------------
$rootScope.listItemCart = JSON.parse(localStorage.getItem("Cart"));	//Lấy giỏ hàng từ localstore
if(!$rootScope.listItemCart) $rootScope.listItemCart = [];	//Nếu chưa có trong localsotre thì tạo mảng rỗng
$scope.addToCart = function(idProduct){
	var item = $scope.items.find(i => i.id == idProduct);	//Lấy item từ items nếu trùng id
	item.Quan = 1;											//Gán số lượng mặc định là 1
	var notExist = true;									//Khởi tạo biến chứa 'chưa tồn tại'
	$rootScope.listItemCart.forEach(i => {					//Duyệt từng phần tử trong giỏ hàng
		if(i.id == idProduct){ 								//Nếu sản phẩm đã tồn tại <= id trùng với idProduct
			i.Quan += 1*1; notExist = false;				//Tăng số lượng lên 1, đánh dấu là tồn tại
		} 
	});
	if(notExist) $rootScope.listItemCart.push(item)		//Nếu không tồn tại thì thêm sản phầm vào giỏ hàng
	$scope.SaveCart();
	$scope.FillCart();
}

$scope.FillCart = function(){
	var sum = 0*1;
	document.getElementById("CartLength").innerText = $rootScope.listItemCart.length;
	document.getElementById("CartProducts").innerHTML = "";
	
	$rootScope.listItemCart.forEach(i => {
		sum += i.price*1;
		console.log(sum);
		document.getElementById("CartProducts").innerHTML += 
		`<div class="product">
			<div class="product-cart-details">
				<h4 class="product-title">
					<a href="product.html">${i.name}</a>
				</h4>
				<span class="cart-product-info">
					<span class="cart-product-qty"></span> $ ${i.price}
				</span>
			</div>
			<figure class="product-image-container">
				<a href="product.html" class="product-image">
					<img src="${i.image[0].url}" alt="product">
				</a>
			</figure>
		</div>`
		document.getElementById("totalCart").innerHTML = 
		`<div class="dropdown-cart-total">
			<span>Total</span> <span class="cart-total-price">$ ${sum}</span>
		</div>`
	});
}

$scope.SaveCart = function(){
	localStorage.setItem("Cart", JSON.stringify($rootScope.listItemCart));
}
//---------------------------------	

});


