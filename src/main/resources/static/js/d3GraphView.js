var margin = {"top": 30,"bottom" : 40, "left" : 25 , "rigth" : 40};
var width = 600 - margin.left - margin.rigth;
var height = 500 - margin.top - margin.bottom;


d3.json("/getExpensesAmountAndDate", function(data) {
	
var timeParse = d3.timeParse("%m/%d/%Y");
var dateLength = data.length;

data.forEach(function(data) {
	data.date = timeParse(data.date);
});

var extentAmount = d3.extent(data, function(d) {
	return d.amount;
});

var extentDate = d3.extent(data, function(d) {
	return d.date;
});

var yScale = d3.scaleLinear()
.domain(extentAmount)
.range([height, 0]);

var heightScale = d3.scaleLinear()
.domain(extentAmount)
.range([0, height - 10]);
	
var xScale = d3.scaleTime()
	.domain(extentDate)
	.range([0, width]);
	
var xRectPosition = d3.scaleLinear()
.domain([1, dateLength])
.range([0, width]);
	
var yAxis = d3.axisLeft(yScale).ticks(20);	
var xAxis = d3.axisBottom(xScale).ticks(dateLength);
	
var bar = d3.select("#chart")
	.append("svg")
		.attr("width", width + margin.left + margin.rigth)
		.attr("height", height + margin.top + margin.bottom)
		.attr("viewbox", "0 0 " + width + margin.left + margin.rigth + " " +  height + margin.top + margin.bottom)
	.append("g")
		.attr("transform", "translate(" + margin.left + "," + margin.top +")");
		
	bar.selectAll("rect")
	.data(data)
	.enter()
	.append("rect")
		.attr("x", function(d, i) { return xRectPosition(i) + 3})
		.attr("width", 15)
		.attr("y", function(d) { return height - heightScale(d.amount)})
		.attr("fill", "red")
		.attr("height", function(d) { return heightScale(d.amount)});


bar.call(yAxis);

	bar.append("g")
		.attr("transform", "translate(10," + height + ")")
		.call(xAxis)
		.selectAll("text")
		.style("text-anchor", "end")
		.attr("transform", "rotate(-45)");

});
				