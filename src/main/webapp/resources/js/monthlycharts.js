KPS.graphs = KPS.graphs || {};
KPS.graphs.monthly = KPS.graphs.monthly || {};


(function(cls, $, undefined) {
	var chart;
	
	function getOptions(container) {
		return {
			chart: {
                renderTo: container,
                type: 'column'
            },
            title: {
                text: 'Monthly overview'
            },
            xAxis: {
                categories: [],
                labels: {
            		y: 40,
            		rotation: -45
                }
            },

            yAxis: {
                min: 0,
                title: {
                    text: 'Amount ($NZD)'
                }
            },
            tooltip: {
                formatter: function() {
                    return '<b>NZD $'+this.point.y.toFixed(2)+'</b><br/>'+this.point.series.name+' for '+this.point.category;
                }
            },
            series: [{
                name: 'Revenue',
                data: []
            }, {
                name: 'Expenditure',
                data: []
            }]
		};
	}
	
	cls.refresh = function(){
		var container = document.getElementById('monthly-chart');
		var html = document.getElementById('monthly-overview-table');
		
		if(container && html) {
			var chartOpts = getOptions(container);
			var revIndex = -1;
			var expIndex = -1;
			$('thead > tr > th', html).each(function(index, child) {
				if(child.innerHTML.toLowerCase() == 'revenue') {
					revIndex = index;
				}
				else if(child.innerHTML.toLowerCase() == 'expenditure') {
					expIndex = index;
				}
			});
			
			if(revIndex > 0 && expIndex > 0) {
				$('tbody > tr > td:first-child', html).each(function(index, child) {
					chartOpts.xAxis.categories.push(child.innerHTML);
				});
				$('tbody > tr > td:nth-child('+(revIndex+1)+')', html).each(function(index, child) {
					chartOpts.series[0].data.push(parseFloat(child.innerHTML.substring(1).replace(',','')));
				});
				$('tbody > tr > td:nth-child('+(expIndex+1)+')', html).each(function(index, child) {
					chartOpts.series[1].data.push(parseFloat(child.innerHTML.substring(1).replace(',','')));
				});
				chart = new Highcharts.Chart(chartOpts);
			}
			else {
				console.log("Unable to find revenue & expenditure columns");
			}
		}
	};
}(KPS.graphs.monthly, jQuery));