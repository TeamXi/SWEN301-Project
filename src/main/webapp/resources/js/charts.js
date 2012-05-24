KPS.graphs = KPS.graphs || {};

/**
 * Initialise the KPS.graphs object.
 * This object stores the chart objects rendered by HighCharts, the refresh functions,
 * and the graph initialization functions.
 * This anonymous function is called as soon as this script is loaded.
 */
(function (cls, $) {
	
	/** 
	 * Ids used to identify chart objects on the page 
	 **/
	var expensesChartId = "expensesChart";
	var profitsChartId  = "profitsChart";
	var eventsTimeId 	= "eventsTimeChart";
	var financesTimeId  = "financesTimeChart";
	
	
	/**
	 * Actual HTML chart components. Wrapped as jQuery objects.
	 */
	var expensesChart 		= $("<div id=\""+expensesChartId+"\" class='graph'></div>");
	var revenueChart 		= $("<div id=\""+profitsChartId+"\"  class='graph'></div>");
	var eventsTimeChart 	= $("<div id=\""+eventsTimeId+"\"    class='graph'></div>");
	var financesTimeChart 	= $("<div id=\""+financesTimeId+"\"  class='graph'></div>");
	
	/**
	 * HighChart objects.
	 */
	var chartRevenue  	= undefined;
	var chartExpenses 	= undefined;
	var chartEventsTime = undefined;
	var chartFinances 	= undefined;
	

	var numberOfEventCategories = 5;
	var duration_one_day = 1000*60*60*24;
	
	/**
	 * Functions to initialize/refresh the various charts.
	 */
	
	/**
	 * Refreshes the expenses chart. Gets chart options, parses data and creates a new HighCharts object
	 */
	cls.refreshExpensesChart = function(){
		var chartOpts = getDonutOpts(expensesChartId,"Expenses","Locations","");
		resetCounters();
		chartOpts.series[0].data = KPS.graphs.donut.expenditure.inner;
		chartOpts.series[1].data = KPS.graphs.donut.expenditure.outer;
		chartExpenses = new Highcharts.Chart(chartOpts);
	};
	
	/**
	 * Refreshes the revenue chart. Gets chart options, parses data and creates a new HighCharts object
	 */
	cls.refreshRevenueChart = function(){
		var chartOpts = getDonutOpts(profitsChartId,"Revenue","Locations",""); 
		resetCounters();
		chartOpts.series[0].data = KPS.graphs.donut.revenue.inner;
		chartOpts.series[1].data = KPS.graphs.donut.revenue.outer;
		chartRevenue = new Highcharts.Chart(chartOpts);
	};
	
	/**
	 * Refreshes the finances chart. Gets chart options, parses data and creates a new HighCharts object
	 */
	cls.refreshFinancesOverTime = function(){
		var financeData = getFinanceData();
		var chartOpts = getFinancesOverTimeOpts(financesTimeId,financeData.vals,financeData.cats);
		chartFinances = new Highcharts.Chart(chartOpts);
	};
	
	/**
	 * Refreshes the events/time chart. Gets chart options, parses data and creates a new HighCharts object
	 */
	cls.refreshEventsTimeChart = function(){
		var eventTimeData = getTimeCategories();
		var chartOpts = getEventsOverTimeOpts(eventsTimeId,eventTimeData.cats,eventTimeData.values);
		chartEventsTime = new Highcharts.Chart(chartOpts);
	};
	
	/**
	 * Reflows a chart if it already exists. Otherwise does a hard refresh.
	 */
	cls.dirtyRefresh = function(){
		if(! refreshIfExists(chartFinances)   ) cls.refreshFinancesOverTime();
		if(! refreshIfExists(chartEventsTime) ) cls.refreshEventsTimeChart();
		if(! refreshIfExists(chartExpenses)   ) cls.refreshExpensesChart();
		if(! refreshIfExists(chartRevenue)    ) cls.refreshRevenueChart();
	};
	
	/**
	 * Refreshes a chart object if it exists.
	 * @param chart		The chart to refresh
	 */
	function refreshIfExists(chart){
		if(chart && chart){
			chart.redraw();
			return true;
		}
		return false;
	}
	
	/**
	 * Refreshes all chart objects. Ensures the size matches the page dimensions
	 */
	cls.refreshCharts = function(){
		var chartHeight = $(revenueChart).width()*0.7;
		
		$(revenueChart).css('height',chartHeight+"px");
		$(expensesChart).css('height',chartHeight+"px");
		
		cls.dirtyRefresh();
		$("rect[rx=\"3\"]").hide();
		$("text[style=\"font-family:\"Lucida Grande\", \"Lucida Sans Unicode\", Verdana, Arial, Helvetica, sans-serif;font-size:10px;cursor:pointer;color:#909090;fill:#909090;\"]").hide();
		$("path[d=\"M 5.5 16.5 L 17.5 16.5 17.5 14.5 5.5 14.5 Z M 11.5 14.5 L 15.5 9.5 10.5 9.5 10.5 4.5 12.5 4.5 12.5 9.5 7.5 9.5 Z\"]").hide();
		$("path[d=\"M 5.5 12.5 L 17.5 12.5 17.5 9.5 5.5 9.5 Z M 7.5 9.5 L 7.5 4.5 15.5 4.5 15.5 9.5 Z M 7.5 12.5 L 5.5 16.5 17.5 16.5 15.5 12.5 Z\"]").hide();
	};
	
	/**
	 * Ties tabbable charts widths to that of the window, so charts reflow appropriately.
	 */
	$(window).resize(function(){
		$(".tab-pane").css({width:$(".tabbable").first().width()});
	});
	
	/**
	 * Returns a clean date object with all current day-time values for that date set to 0
	 */
	function getDay(date) {
		date.setMilliseconds(0);
		date.setSeconds(0);
		date.setMinutes(0);
		date.setHours(0);
		
		return date;
	}
	

	/**
	 * Loop over a timestamped dataset in daily increments
	 * 
	 * @param data the array of timestapmed objects to loop over, each object must have .timestamp property
	 * @param push a function that is called with a summary for each day, must have the form function(startDate, eventCountForDay, lastEvent)
	 * 			Note that last event may be last event seen, not last event on a day (eg if a day has no events)
	 */
	function loopOverEventsByTime(data, push) {
		// Spacing of x values on graph
		var interval = duration_one_day;
		
		var lastEvent = {};
		
		var eventIdx = 0;

		if(data.length > 0) {
			var event = data[eventIdx];

			eventIdx++;
			
			// Get start and end of first time interval
			var start = getDay(new Date(event.timestamp));
			var end = new Date(start.getTime()+interval);
			
			// Loop through and store all time intervals between first and last event as a 'category' for the chart.
			while(event) {
				var count = 0;
				// While there are events for the current day
				while(event && event.timestamp < end.getTime()) {
					lastEvent = event;
					
					event = data[eventIdx];
					eventIdx++;
					
					count++;
				}
				
				// Push the day
				push(start, count, lastEvent);
				
				// If there are still events
				if(event) {
					// Push empty days until next day which has events on it
					while(true) {
						start = new Date(start.getTime()+interval);
						end = new Date(start.getTime()+interval);
						
						if(event.timestamp > end.getTime()) {
							// Push the day
							push(start, 0, lastEvent);
						}
						else {
							break;
						}
					}
				}
			}
			
			var now = getDay(new Date());
			
			// Add empty days until current date
			while(start < now) {
				start = new Date(start.getTime()+interval);
				end = new Date(start.getTime()+interval);
				
				// Push the day
				push(start, 0, lastEvent);
			}
		}
	}
	
	function getFinanceData(){
		var revenue = [];
		var expenditure = [];
		var categories = [];
		
		// Loop over the revenue/expenditure data, storing dayily summaries
		loopOverEventsByTime(KPS.graphs.revenueexpenditure, function(date, count, lastEvent) {
			categories.push(date);
			revenue.push(lastEvent.revenue);
			expenditure.push(lastEvent.expenditure);
		});
		
		return {vals:{'revenue': revenue, 'expenditure': expenditure},cats:categories};
	}
	
	
	/**
	 * Gets all event times, as a collection of time categories, and event indices. 
	 */
	function getTimeCategories(){
		var cats = [];
		var eventCounts = [];
		
		// Loop over the events data, storing counts
		loopOverEventsByTime(KPS.graphs.events, function(date, count, lastEvent) {
			cats.push(date);
			eventCounts.push(count);
		});
				
		return {cats:cats,values:eventCounts};
	}
	
	/**
	 * Resets counters for total international and domestic events.
	 */
	function resetCounters(){
		totalInternational = 0;
		totalDomestic = 0;
		total = 0;
	}
	
	/**
	 * Gets the options in the appropriate format for the donut charts.
	 * @param container The id of the container to render in
	 * @param title 	The visible title of the chart
	 * @param sub		The visible sub-title of the chart.
	 * @param yaxis		The visible label for the y-axis	(Vertical) 
	 */
	function getDonutOpts(container, title, sub, yaxis){
		  var chartOpts = {
            chart: {
                renderTo: container,
                type: 'pie',
                plotBackgroundColor: "rgba(255,255,255,0)",
                backgroundColor: null
            },
            title: {
                text: title
            },
            reflow: true,
            yAxis: {
                title: {
                    text: 'Total percent market share'
                }
            },
            plotOptions: {
                pie: {
                    shadow: false
                }
            },
            tooltip: {
                formatter: function() {
                    return this.point.name+'<br/><b>NZD $ </b>' +this.point.value.toFixed(2);
                }
            },
            series: [{
                name: title,
                data: null,
                size: '60%',
                dataLabels: {
                    formatter: function() {
                        return this.point.name;
                    },
                    color: 'white',
                    distance: -35
                }
            }, {
                name: sub,
                data: null,
                innerSize: '60%',
                dataLabels: {
                    formatter: function() {
                        // display only if larger than 1
                        return (this.point.y > 0.04)? '<b>NZD $ </b>' +this.point.value.toFixed(2):null;
                    }
                }
            }]
        };
		  return chartOpts;
	}
	
	/**
	 * Gets options to compare events over time in the appropriate format for a HighCharts chart of type 'line'
	 * @param container		HTML Container id to render in
	 * @param categories	Collection of categories the hold the data for the chart
	 * @param values		Collection of values to show on the chart
	 */
	function getEventsOverTimeOpts(container,categories,values){
		var chartOpts = {
            chart: {
                renderTo: container,
                type: 'line'
            },
            title: {
                text: 'Total Events over Time'
            },
            reflow: true,
            subtitle: {
                text: 'KPSmart'
            },
            xAxis: {
                categories: categories,
                min: graphMin(),
                max: graphMax(),
                labels: {
            		step: 5,
            		y: 33,
            		rotation: -45,
                    formatter: function() {
                    	if(this.value) {
                    		return KPS.data.format.shortDate(this.value); // clean, unformatted number for year
                    	}
                    	else {
                    		return '';
                    	}
                    }
                }
            },
            yAxis: {
                title: {
                    text: 'Number of Events'
                },
	            min: 0
            },
            tooltip: {
                enabled: true,
                formatter: function() {
                    return "<b>"+this.y+" "+(this.y==1?'event':'events')+"</b> on "+KPS.data.format.shortDate(this.point.category);
                }
            },
            plotOptions: {
                line: {
                    marker: {
                        enabled: false
                    }
                }
            },
            series: [
                     {name: "Events/Time",
                      data: values
                    	 }
                     ]
        };
		return chartOpts;
	}
	
	/**
	 * Returns the number of days up to the date of the current state (Or today if it is showing current data)
	 */
	function dayCount() {

		var now;
		if(KPS.graphs.currentEvent == KPS.graphs.events.length) {
			now = getDay(new Date());
		}
		else {
			now = getDay(new Date(KPS.graphs.events[KPS.graphs.currentEvent-1].timestamp));
		}
		
		return Math.floor((now.getTime()-getDay(new Date(KPS.graphs.events[0].timestamp)).getTime())/duration_one_day);
	}
	
	function graphMin() {
		return Math.max(dayCount()-90, 0);
	}
	
	function graphMax() {
		return dayCount();
	}
	
	/**
	 * Gets options to compare finances over time in the appropriate format for a HighCharts chart of type 'line'
	 * @param container		HTML Container id to render in
	 * @param financeData	Collection of categories the hold the data for the chart
	 * @param categories	Collection of values to show on the chart
	 */
	function getFinancesOverTimeOpts(container,financeData,categories){
		var colors = Highcharts.getOptions().colors; //gets the colours so rev and exp can be same colour as the reported values
		
		var series;
		
		if(financeData.revenue[financeData.revenue.length-1] > financeData.expenditure[financeData.expenditure.length-1]) {
			series = [{
            	name:"Revenue",
            	data:financeData.revenue,
            	color:'#468847'
            }, {
            	name:"Expenditure",
            	data:financeData.expenditure,
            	color: colors[1]
            }];
		}
		else {
			series = [{
            	name:"Expenditure",
            	data:financeData.expenditure,
            	color: colors[1]
            }, {
            	name:"Revenue",
            	data:financeData.revenue,
            	color:'#468847'
            }];
		}
		
		var chartOpts = {
            chart: {
                renderTo: container,
                type: 'area'
            },
            title: {
                text: 'Expenditure and Revenue over Time'
            },
            reflow: true,
            subtitle: {
                text: 'KPSmart'
            },
            xAxis: {
            	categories: categories,
            	min: graphMin(),
            	max: graphMax(),
            	title: {
                    text: 'Date'
                },
            	labels: {
            		step: 5,
            		y: 33,
            		rotation: -45,
                    formatter: function() {
                    	if(this.value) {
                    		return KPS.data.format.shortDate(this.value); // clean, unformatted number for year
                    	}
                    	else {
                    		return '';
                    	}
                    }
                }
            },
            yAxis: {
                title: {
                    text: 'Amount ($NZD)'
                },
                labels: {
                    formatter: function() {
                        return "NZD $"+this.value;
                    }
                }
            },
            tooltip: {
                enabled: true,
                formatter: function() {
                    return "<b>NZD $"+this.y.toFixed(2)+" </b><br/>"+this.series.name+" on "+KPS.data.format.shortDate(this.x);
                }
            },
            plotOptions: {
                area: {
                    pointStart: 0,
                    marker: {
                        enabled: false
                    }
                }
            },
            series: series
        };
		return chartOpts;
	}

	
	/**
	 * Initialise the charts on document load. 
	 * Empties the chart sections, appends charts,
	 * and sets refresh/resize events for each of them.
	 */
	$(document).ready(function(){
		$.getScript("dashboard?chartdata", function() {
			$revExpSection = $("#dashboard-tab-revenue-expenditure").empty();
			$noEventsSection = $("#dashboard-tab-no-of-events").empty();
	
			$revExpSection.append(financesTimeChart);
			$revExpSection.append(expensesChart);
			$revExpSection.append(revenueChart);
			
			$noEventsSection.append(eventsTimeChart);
			
			$(".activate-graph").click(function(){
				setTimeout(function() {
					cls.refreshCharts();
					
					if (document.createEvent) {
						var evt = document.createEvent('UIEvents');
						evt.initUIEvent('resize', true, false,window,0);
						window.dispatchEvent(evt);
		
					}
					else if (document.createEventObject) {
					    window.fireEvent('onresize');
					}
				}, 1);
			});
			
			cls.refreshCharts();
		});
	});
}(KPS.graphs, jQuery));
