$(document).ready(function() {

	var token = $("#token_csrf").attr("content");

	$.ajaxSetup({
		beforeSend : function(xhr) {
			xhr.setRequestHeader("X-CSRF-TOKEN", token);
		}
	});

	$("#categoryRow").on("change", function() {

		$("#subcategoryRow").empty();

		var category = $("#categoryRow").val();

		$.ajax({
			url : "/sendingCategory",
			type : "POST",
			contentType : "application/json",
			data : JSON.stringify({
				"category" : category
			}),
			success : function(data) {
				$("#subcategoryRow").append(data);
			},
			error : function() {
				
			}

		});

	});

	$("#saveExpense").click(function(event) {
				
		event.preventDefault();
		
			var subcategory = $("#subcategory").val();
			
			var form = $("#fileUploadForm")[0];
			
			var formData = new FormData(form);
			formData.append("subcategory", subcategory);
		
			$.ajax({
				
				type: "POST",
	            enctype: 'multipart/form-data',
	            url : "saveExpenseAndImage",
	            data : formData,
				processData: false,
				contentType: false,
				cache: false,
				timeout: 600000,
			
				success : function(data) {
					$("#tableForDisplayExpensesBodyContainer table").append(data);
					$("#modalForAddingExpense").modal("hide");
				},
				error : function() {
					alert("Error");
				}
	
			});
			
	});
});
