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
			type : "POST",
			enctype : 'multipart/form-data',
			url : "saveExpenseAndImage",
			data : formData,
			processData : false,
			contentType : false,
			cache : false,
			timeout : 600000,
			success : function(data) {
				$("#tableForDisplaying tbody").append(data);
				$("#modalForAddingExpense").modal("hide");
			}
		});
	});

	// Used for getting values from user for search purposes
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
			}
		});
	});

	$("#startDate, #endDate").datepicker({
		dateFormat : 'mm/dd/yy'
	}).on("change blur", function() {
		if ($(this).valid()) {
			$(this).closest(".row")
				.addClass("has-success")
				.removeClass("has-error");
			$("#submitSearchQuery").prop("disabled", false);
			$(this).css("color", "black");

		} else {
			$(this).closest(".row")
				.removeClass("has-success");
				.addClass("has-error");
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
			type : "POST",
			url : "sendingPropertiesForQueryingExpense",
			data : formData,
			processData : false,
			contentType : false,
			cache : false,
			timeout : 600000,
			success : function(data) {
				$("#tableForDisplaying tbody").empty();
				$("#tableForDisplaying tbody").append(data);
			}
		});
	});

	var $table = $("#tableForDisplaying");

	$table.tablesorter();

	$table.floatThead();

	$("#fileUploadForm input").on("blur", function() {

		if ($(this).valid()) {
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

	$("#csv").click(function() {

		confirm("CSV file download");
		var csv = $("#csv").val();
		
		$.ajax({
			url : "/csvFile",
			type : "POST",
			contentType : "application/json",
			data : JSON.stringify({
				"csv" : csv
			}),
			success : function(data) {
				alert("Successfully");
			},
			error : function() {
				alert("Unsuccessfully");
			}
		});
	});

	$("#excel").click(function() {

		confirm("Excel file download");
		var excel = $("#excel").val();

		$.ajax({
			url : "/excelFile",
			type : "POST",
			contentType : "application/json",
			data : JSON.stringify({
				"excel" : excel
			}),
			success : function(data) {
				alert("Successfully");
			},
			error : function() {
				alert("Unsuccessfully");
			}

		});
	});
	
	$("#csvInport").click(function(event) {

		event.preventDefault();
		var csvFile = $("#csvFile").val();

		if (csvFile.endsWith(".csv")) {

			var form = $("#csvFileUpload")[0];
			var formData = new FormData(form);

			$.ajax({
				type : "POST",
				url : "csvFileUpload",
				data : formData,
				processData : false,
				contentType : false,
				cache : false,
				timeout : 600000,

				success : function(data) {
					alert(data);
				},
				error : function() {
					alert("Unsuccessfully Uploaded");
				}
			});

		} else {
			alert("File must be in csv format");
		}
	});
});
