/**
 * Load info about the graph by its filename
 */
function loadInfo($filename)
{
	$.post(root + '/slave.jsp', {action:'loadInfo', filename: $filename}, function(data) {
		$('div.infoData').empty().append('<h3 class="strong">"' + $filename + '"</h3').append(data);
	});	
}

/**
 * Reloads list of graphs
 */
function updateTable()
{
	$.post(root + '/table_include.jsp', {}, function(data) {
		$('div.fulltable').empty().append(data);
	});	
}

/**
 * Siaf
 */
(function(){
	/**
	 * Graph name prediction
	 */
	$('#gen_nvalue, #gen_pvalue').on('change', function(){
		$('#gen_name').val($('#gen_nvalue').val() + '_' + $('#gen_pvalue').val());
	});
	
	if ($('div.fulltable').length && needUpdateTable) {
		setInterval(function(){updateTable()}, 10000);
	}
	
	
	
	
})();
