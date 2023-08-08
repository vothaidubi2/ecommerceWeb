const app = angular.module("cartApp", []);
app.controller("cartCtrl", function($scope) {
	console.log("cart view");
	$scope.cartItems = JSON.parse(localStorage.getItem("@cart")) || [];
	
	
	/*$scope.addToLocal = function(productId, price, image, name) {
		let cart = JSON.parse(localStorage.getItem("@cart")) || [];

		const qty = 1;

		if (cart.find(c => c.productId === productId)) {
			cart = cart.map(c => ({
				...c,
				qty: c.productId === productId ? c.qty + qty : qty,
				price,
				image,
				name
			}));
		} else {
			cart.push({
				productId,
				qty,
				price: Number(price),
				image,
				name
			});
		}
		localStorage.setItem("@cart", JSON.stringify(cart));
		dispatchEvent(cartChangeEvent);
	}
	const cartChangeEvent = new Event("on-cart-change");
	$scope.FillCart = function() {
		var sum = 0 * 1;
		document.getElementById("CartLength").innerText = $rootScope.listItemCart.length;
		document.getElementById("CartProducts").innerHTML = "";

		$rootScope.listItemCart.forEach(i => {
			sum += i.price * 1;
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
*/
	
});