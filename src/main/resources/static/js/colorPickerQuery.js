$(document).ready(function() {
	
		$("#colorPicker").ColorPicker({
			
			onChange: function(hsb, hex, rgb) {
				$("#chosenColor").val("#" + hex);
			}
			
		});
		
});