KPS.graphs = KPS.graphs || {};

(function (cls, $) {
	var expensesChartId = "expensesChart";
	var profitsChartId  = "profitsChart";
	var eventsTimeId 	= "eventsTimeChart";
	var financesTimeId  = "financesTimeChart";
	
	var colors = Highcharts.getOptions().colors;
	
	var totalInternational = 0;
	var totalDomestic = 0;
	var total = 0;
	
	var numberOfEventCategories = 5;
	
	var expensesChart 		= $("<div id=\""+expensesChartId+"\" class='graph'></div>");
	var revenueChart 		= $("<div id=\""+profitsChartId+"\"  class='graph'></div>");
	var eventsTimeChart 	= $("<div id=\""+eventsTimeId+"\"    class='graph'></div>");
	var financesTimeChart 	= $("<div id=\""+financesTimeId+"\"  class='graph'></div>");
	
	var chartRevenue = undefined;
	var chartExpenses = undefined;
	var chartEventsTime = undefined;
	var chartFinances = undefined;
	
	var duration_one_day = 1000*60*60*24;
	
	cls.refreshExpensesChart = function(){
		var chartOpts = getDonutOpts(expensesChartId,"Expenses","Locations","");
		resetCounters();
		chartOpts.series[0].data = KPS.graphs.donut.expenditure.inner;
		chartOpts.series[1].data = KPS.graphs.donut.expenditure.outer;
		chartExpenses = new Highcharts.Chart(chartOpts);
	};
	cls.refreshRevenueChart = function(){
		var chartOpts = getDonutOpts(profitsChartId,"Revenue","Locations",""); 
		resetCounters();
		chartOpts.series[0].data = KPS.graphs.donut.revenue.inner;
		chartOpts.series[1].data = KPS.graphs.donut.revenue.outer;
		chartRevenue = new Highcharts.Chart(chartOpts);
	};
	cls.refreshFinancesOverTime = function(){
		var financeData = getFinanceData();
		var chartOpts = getFinancesOverTimeOpts(financesTimeId,financeData.vals,financeData.cats);
		chartFinances = new Highcharts.Chart(chartOpts);
	};
	cls.refreshEventsTimeChart = function(){
		var eventTimeData = getTimeCategories();
		var chartOpts = getEventsOverTimeOpts(eventsTimeId,eventTimeData.cats,eventTimeData.values);
		chartEventsTime = new Highcharts.Chart(chartOpts);
	};
	cls.dirtyRefresh = function(){
		if(! refreshIfExists(chartFinances)   ) cls.refreshFinancesOverTime();
		if(! refreshIfExists(chartEventsTime) ) cls.refreshEventsTimeChart();
		if(! refreshIfExists(chartExpenses)   ) cls.refreshExpensesChart();
		if(! refreshIfExists(chartRevenue)    ) cls.refreshRevenueChart();
	};
	
	function refreshIfExists(chart){
		if(chart && chart){
			chart.redraw();
			return true;
		}
		return false;
	}
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
	
	$(window).resize(function(){
		$(".tab-pane").css({width:$(".tabbable").first().width()});
	});
	
	function getDay(date) {
		date.setMilliseconds(0);
		date.setSeconds(0);
		date.setMinutes(0);
		date.setHours(0);
		
		return date;
	}
	
	function loopOverEventsByTime(data, push) {
		var interval = duration_one_day;
		
		var lastEvent = {};
		
		var eventIdx = 0;
		if(data.length > 0) {
			var event = data[eventIdx];
			eventIdx++;
			
			var start = getDay(new Date(event.timestamp));
			var end = new Date(start.getTime()+interval);
			
			while(event) {
				var count = 0;
				while(event && event.timestamp < end.getTime()) {
					lastEvent = event;
					
					event = data[eventIdx];
					eventIdx++;
					
					count++;
				}
				
				push(start, count, lastEvent);
				
				if(event) {
					while(true) {
						start = new Date(start.getTime()+interval);
						end = new Date(start.getTime()+interval);
						
						if(event.timestamp > end.getTime()) {
							push(start, 0, lastEvent);
						}
						else {
							break;
						}
					}
				}
			}
						
			var now = getDay(new Date());
			
			while(start < now) {
				start = new Date(start.getTime()+interval);
				end = new Date(start.getTime()+interval);
				
				push(start, 0, lastEvent);
			}
		}
	}
	
	function getFinanceData(){
		var revenue = [];
		var expenditure = [];
		var categories = [];
		
		loopOverEventsByTime(KPS.graphs.revenueexpenditure, function(date, count, lastEvent) {
			categories.push(date);
			revenue.push(lastEvent.revenue);
			expenditure.push(lastEvent.expenditure);
		});
		
		return {vals:{'revenue': revenue, 'expenditure': expenditure},cats:categories};
	}
	
	function getTimeCategories(){
		var cats = [];
		var eventCounts = [];
		
		loopOverEventsByTime(KPS.graphs.events, function(date, count, lastEvent) {
			cats.push(date);
			eventCounts.push(count);
		});
				
		return {cats:cats,values:eventCounts};
	}
	
	function resetCounters(){
		totalInternational = 0;
		totalDomestic = 0;
		total = 0;
	}
	
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

	
	
	$(document).ready(function(){
		$.getScript("dashboard?chartdata&atevent="+KPS.dashboard.currentEvent, function() {
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
		
					} else if (document.createEventObject) {
					    window.fireEvent('onresize');
					}
				}, 1);
			});
			
			cls.refreshCharts();
		});
	});
}(KPS.graphs, jQuery));
