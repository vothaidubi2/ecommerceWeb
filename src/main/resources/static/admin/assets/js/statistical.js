document.addEventListener("DOMContentLoaded", function() {
	const startDateInput = document.getElementById("startDate");
	const endDateInput = document.getElementById("endDate");
	startDateInput.max = new Date().toISOString().split("T")[0];
	endDateInput.max = new Date().toISOString().split("T")[0];

	async function fetchRevenueData() {
		const startDate = startDateInput.value;
		const endDate = endDateInput.value;
		if (startDate && endDate) {
			try {
				const response = await fetch("http://localhost:8080/admin/api/statistics/revenue?startDate=" + startDate + "&endDate=" + endDate);
				const response1 = await fetch("http://localhost:8080/admin/api/statistics/listorder?startDate=" + startDate + "&endDate=" + endDate);
				if (!response.ok) {
					throw new Error('Network response was not ok');
				}
				const data = await response.json();
				const data1 = await response1.json();
				const splitDataArray = data1.data.map(item => {
					const [id, type, phoneNumber, address, date, amount] = item.split(',');
					var dateFormat = new Date(date).toLocaleDateString("en-US");
					return {
						id: id,
						user: type,
						phoneNumber: phoneNumber,
						address: address,
						date: dateFormat,
						amount: amount
					};
				});
				var totalAmount = 0;
					document.getElementById("listItem").innerHTML ='';
				for (const item of splitDataArray) {
					totalAmount += Number.parseFloat(item.amount);
					document.getElementById("listItem").innerHTML += `
						<tr>
							<td>
								<div class="text-center">
									<h6 class="text-sm mb-0">${item.id}</h6>
								</div>
							</td>
							<td>
								<div class="text-center">
									<h6 class="text-sm mb-0">${item.user}</h6>
								</div>
							</td>
							<td>
								<div class="text-center">
									<h6 class="text-sm mb-0">${item.phoneNumber}</h6>
								</div>
							</td>
							<td>
								<div class="text-center">
									<h6 class="text-sm mb-0">${item.address}</h6>
								</div>
							</td>
							<td>
								<div class="text-center">
									<h6 class="text-sm mb-0">${item.date}</h6>
								</div>
							</td>
							<td>
								<div class="text-center">
									<h6 class="text-sm mb-0">$${item.amount}</h6>
								</div>
							</td>
						</tr>
					`
				}
				document.getElementById("orderInfo").innerHTML = `
				<h6 class="mb-2">List Order</h6>
					<h6 class="mb-2" style="color: #F990A5">Amount: $${totalAmount}</h6>`;
				console.log(splitDataArray);
				const dates = [];
				const values = [];

				for (const str of data.data) {
					const [date, value] = str.split(',');
					dates.push(date);
					values.push(parseFloat(value));
				}
				var arrayOfObjects = [];
				dates.map((date, index) => ({ date, amount: values[index] })).forEach(temp => arrayOfObjects.push(temp));
				// arrayOfObjects = dates.map((date, index) => ({ date, amount: values[index] }));

				console.log('data:', arrayOfObjects);
				return arrayOfObjects;
			} catch (error) {
				console.error('Error fetching revenue data:', error);
				return [];
			}
		} else {
			alert("Please select both start and end dates.");
		}
	}

	function aggregateWeekly(data) {
		const aggregatedData = {};

		data.forEach((entry) => {
			const date = new Date(entry.date);
			const weekStartDate = new Date(date.getFullYear(), date.getMonth(), date.getDate() - date.getDay());
			const weekEndDate = new Date(weekStartDate.getFullYear(), weekStartDate.getMonth(), weekStartDate.getDate() + 6);
			const weekKey = weekStartDate.toISOString().slice(0, 10);

			if (!aggregatedData[weekKey]) {
				aggregatedData[weekKey] = { date: weekKey, amount: entry.amount };
			} else {
				aggregatedData[weekKey].amount += entry.amount;
			}
		});

		return Object.values(aggregatedData);
	}

	let revenueChart = null;
	async function updateChartWithData() {
		const startDate = new Date(document.getElementById('startDate').value);
		const endDate = new Date(document.getElementById('endDate').value);
		const dateDiff = (endDate - startDate) / (1000 * 60 * 60 * 24);

		let chartData;

		if (dateDiff <= 14) {
			// Show full data
			chartData = (await fetchRevenueData()).filter((entry) => {
				const entryDate = new Date(entry.date);
				return entryDate >= startDate && entryDate <= endDate;
			});
		} else {
			// Aggregate into weekly clusters
			chartData = aggregateWeekly(await fetchRevenueData());
		}

		const chartLabels = chartData.map((entry) => entry.date);
		const chartValues = chartData.map((entry) => entry.amount);

		const ctx = document.getElementById('revenueChart').getContext('2d');
		if (revenueChart) {
			revenueChart.destroy();
		}

		var gradientStroke1 = ctx.createLinearGradient(0, 230, 0, 50);
		gradientStroke1.addColorStop(1, 'rgba(94, 114, 228, 0.2)');
		gradientStroke1.addColorStop(0.2, 'rgba(94, 114, 228, 0.0)');
		gradientStroke1.addColorStop(0, 'rgba(94, 114, 228, 0)');
		revenueChart = new Chart(ctx, {
			type: 'line',
			data: {
				labels: chartLabels,
				datasets: [{
					data: chartValues,
					label: "Revenue",
					tension: 0.4,
					borderWidth: 0,
					pointRadius: 0,
					borderColor: "#5e72e4",
					backgroundColor: gradientStroke1,
					borderWidth: 3,
					fill: true,
					maxBarThickness: 6
				}]
			},
			//            options: {
			//                title: {
			//                    display: true,
			//                    text: `Revenue Board`
			//                }
			//            }
			options: {
				responsive: true,
				maintainAspectRatio: false,
				plugins: {
					legend: {
						display: false,
					}
				},
				interaction: {
					intersect: false,
					mode: 'index',
				},
				scales: {
					y: {
						grid: {
							drawBorder: false,
							display: true,
							drawOnChartArea: true,
							drawTicks: false,
							borderDash: [5, 5]
						},
						ticks: {
							display: true,
							padding: 10,
							color: '#fbfbfb',
							font: {
								size: 11,
								family: "Open Sans",
								style: 'normal',
								lineHeight: 2
							},
						}
					},
					x: {
						grid: {
							drawBorder: false,
							display: false,
							drawOnChartArea: false,
							drawTicks: false,
							borderDash: [5, 5]
						},
						ticks: {
							display: true,
							color: '#ccc',
							padding: 20,
							font: {
								size: 11,
								family: "Open Sans",
								style: 'normal',
								lineHeight: 2
							},
						}
					},
				},
			},
		});
	}

	document.getElementById('fetchDataButton').addEventListener('click', updateChartWithData);
});
