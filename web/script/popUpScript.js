$(document).ready(function () {
		setInterval(function() {
			$('#rectangle').fadeOut(1000).fadeIn(1000);
		}, 1000);
	});
	
	$(document).ready(function () {
		$('#popUp').hide();
	});
	
	$(document).on('click', '#showPopUp', function() {
		$('#popUp').show(1000);
	});
	
	$(document).on('click', '#popUp', function() {
		$('#popUp').hide(1000);
	});	