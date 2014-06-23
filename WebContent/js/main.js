/**
 * Main.js
 */

function loadInfo($filename)
{
	$.post(root + '/slave.jsp', {action:'loadInfo', filename: $filename}, function(data) {
		$('div.info').empty().append('<h3>"' + $filename + '"</h3').append(data);
	});	
}