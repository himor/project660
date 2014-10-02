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
 * Get input fields
 * @param object
 * @returns {Array}
 */
function getInputs(object)
{
	var fields = new Array();
	var values = new Array();

	fields = object.children(":input").serializeArray();
	$.each(fields, function(index,element){
		values.push(element.value);
	});
	
	return values;
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
	
	/**
	 * Insert node
	 */
	$('.form.insertNode form').on('submit', function(event) {
		event.preventDefault();
		var values = getInputs($(this));
		$.post(root + '/slave.jsp', {action:'insertNode', form:values}, function(data) {
			if (data.error == 0) {
				var count = data.count;
				for (_i = count - 1; _i >= 0; _i--)
					sys.addNode('node_' + (data.total - _i), {color: 'grey', shape: 'dot', label: (data.total - _i)});
			}
		});
	});
	
	/**
	 * Remove node
	 */
	$('.form.removeNode form').on('submit', function(event) {
		event.preventDefault();
		var values = getInputs($(this));
		$.post(root + '/slave.jsp', {action:'removeNode', form:values}, function(data) {
			if (data.error == 0) {
				sys.pruneNode('node_' + data.id);
			}
		});
	});
	
	/**
	 * Insert edge
	 */
	$('.form.insertEdge form').on('submit', function(event) {
		event.preventDefault();
		var values = getInputs($(this));
		$.post(root + '/slave.jsp', {action:'insertEdge', form:values}, function(data) {
			if (data.error == 0) {
				var from = data.from,
					to   = data.to;
				eval("window.edge_" + from + "_" + to + " = sys.addEdge('node_" + from + "', 'node_" + to + "', {type:\"arrow\", directed:true, color: '#333'} );\n");
			}
		});
	});
	
	
	
})();
