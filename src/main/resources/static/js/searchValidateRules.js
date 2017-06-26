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
});