$(document).ready(function() {

	var token = $("#csrf_token").attr("content");

	$.ajaxSetup({
		beforeSend : function(xhr) {
			xhr.setRequestHeader("X-CSRF-TOKEN", token);
		}
	});

	$("#saveBtn").click(function(evt) {

		
		evt.preventDefault();
		
		var subcategory = $("#nameOfTheSubcategory").val();
		var category = $("#category").val();
		var color = $("#chosenColor").val();
		
		
		$.ajax({
			url : "/saveSubcategory",
			type : "POST",
			data : JSON.stringify({
				"subcategory" : subcategory,
				"category" : category,
				"color" : color
			}),
			contentType : "application/json",
			success : function(data) {
				$("#tableForDisplaySubcategories").append(data);
				$("#modalForAddingSubcategory").modal("hide");
			},
			error : function() {
				alert("Please log in to add subcategory");
			}

		});
	});
});