$(document).ready(function() {
	
	$("#searchForm").validate({
		rules : {
			startDate : {
				date : true
			},
			endDate : {
				date: true
			}
		},
		messages: {
			startDate : {
				date : "Wrong format. Correct Format mm/dd/yy"
			},
			endDate : {
				date : "Wrong format. Correct Format mm/dd/yy"
			}
		}
	});
	
	
	$("#fileUploadForm").validate({
		rules: {
			amountOfExpense : {
				required : true,
				number : true
			}
			
		},
		messages : {
			amountOfExpense : {
				required : "The amount is required",
				number: "The amount can consist only of numbers"
			}
		}
	});
	
});