$(document).ready(function() {

	$("#registrationForm").validate({
		rules : {
			username : {
				required : true,
				rangelength : [ 6, 18 ]
			},
			email : {
				required : true,
				email : true
			},
			password : {
				required : true,
				rangelength : [ 6, 18 ]
			},
			confirm_password : {
				equalTo : "#password"
			}

		},
		messages : {
			email : {
				required : "Please enter email address",
				email : "Please enter a valid email address"
			},
			password : {
				required : "Please enter password",
				rangelength : "Password must be between 6 and 18 characters"
			},
			username : {
				required : "Please enter username",
				rangelength : "Username must be between 6 and 18 characters"
			},
			confirm_password : {
				equalTo : "Passwords don't match"
			}

		},
	});

	$("#registrationForm input").on("blur", function() {
		if ($(this).valid()) {
			$(this).parent().parent().addClass("has-success");
			$(this).parent().parent().removeClass("has-error");
			$("#submit").prop("disabled", false);
			$(this).css("color", "black");

		} else {
			$(this).parent().parent().removeClass("has-success");
			$(this).parent().parent().addClass("has-error");
			$("#submit").prop("disabled", "disabled");
			$(".error").css("color", "red");
		}

	});

});