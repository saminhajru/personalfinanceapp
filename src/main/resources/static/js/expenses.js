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
					$("#tableForDisplaying tbody").append(data);
					$("#modalForAddingExpense").modal("hide");
				},
				error : function() {
					alert("Error");
				}
	
			});
			
	});
	
	//search
	
	$("#categorySelect").on("change", function() {

		$("#subcategorySelectContainer").empty();

		var category = $("#categorySelect").val();

		$.ajax({
			url : "/sendingCategory",
			type : "POST",
			contentType : "application/json",
			data : JSON.stringify({
				"category" : category
			}),
			success : function(data) {
				$("#subcategorySelectContainer").append(data);
			},
			error : function() {
				
			}

		});

	});
	
	
	$("#startDate, #endDate").datepicker(
			{dateFormat: 'mm/dd/yy' })
			.on("change blur", function() {
				if($(this).valid()) {
					$(this).parent().parent().addClass("has-success");
					$(this).parent().parent().removeClass("has-error");
					$("#submitSearchQuery").prop("disabled", false);
					$(this).css("color", "black");
					
			} else {
					$(this).parent().parent().removeClass("has-success");
					$(this).parent().parent().addClass("has-error");
					$("#submitSearchQuery").prop("disabled", "disabled");
					$(".error").css("color", "red");		
			}
	
			});
			
	$("#submitSearchQuery").click(function(event) {
		
		event.preventDefault();
		
			var subcategory = $("#subcategory").val();
			var orderBy = $("#orderBy").val();
			
			var form = $("#searchForm")[0];
			
			var formData = new FormData(form);
			formData.append("subcategory", subcategory);
			formData.append("orderBy", orderBy);
			
			$.ajax({
				
				type: "POST",
	            url : "sendingPropertiesForQueryingExpense",
	            data : formData,
				processData: false,
				contentType: false,
				cache: false,
				timeout: 600000,
			
				success : function(data) {
					$("#tableForDisplaying tbody").empty();
					$("#tableForDisplaying tbody").append(data);
				},
				error : function() {
					alert("Error");
				}
	
			});
			
	});
	
	
	var $table = $("#tableForDisplaying");
	
	$table.tablesorter();

	$table.floatThead();
	
	
	$("#fileUploadForm input").on("blur", function() {
		
		if($(this).valid()) {
			$(this).parent().addClass("has-success");
			$(this).parent().removeClass("has-error");
			$("#saveExpense").prop("disabled", false);
			$(this).css("color", "black");
			
		} else {
			$(this).parent().removeClass("has-success");
			$(this).parent().addClass("has-error");
			$("#saveExpense").prop("disabled", "disabled");
			$(".error").css("color", "red");		
		}
		
	});
});
