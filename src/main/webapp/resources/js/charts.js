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
	
	var expensesChart 		= $("<div id=\""+expensesChartId+"\" class='graph' ></div>");
	var revenueChart 		= $("<div id=\""+profitsChartId+"\"  class='graph'></div>");
	var eventsTimeChart 	= $("<div id=\""+eventsTimeId+"\"    class='graph'></div>");
	var financesTimeChart 	= $("<div id=\""+financesTimeId+"\"  class='graph'></div>");
	
	var chartRevenue;
	var chartExpenses;
	var chartEventsTime;
	var chartFinances;
	
	cls.refreshExpensesChart = function(){
		var chartOpts = getDonutOpts(expensesChartId,"Expenses","Locations","");
		resetCounters();
		chartOpts.series[0].data = parseFinancesGlobal('expenditure');
		chartOpts.series[1].data = parseFinancesByRoute('expenditure');
		chartExpenses = new Highcharts.Chart(chartOpts);
	};
	cls.refreshRevenueChart = function(){
		var chartOpts = getDonutOpts(profitsChartId,"Revenue","Locations",""); 
		resetCounters();
		chartOpts.series[0].data = parseFinancesGlobal('revenue');
		chartOpts.series[1].data = parseFinancesByRoute('revenue');
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
	}
	
	function refreshIfExists(chart){
		if(chart && chart){
			chart.redraw();
			return true;
		}
		return false;
	}
	cls.refreshCharts = function(){
		$(revenueChart).css('height',$(revenueChart).width()+"px");
		$(expensesChart).css('height',$(expensesChart).width()+"px");
		
		cls.dirtyRefresh();
		$("rect[rx=\"3\"]").hide();
		$("text[style=\"font-family:\"Lucida Grande\", \"Lucida Sans Unicode\", Verdana, Arial, Helvetica, sans-serif;font-size:10px;cursor:pointer;color:#909090;fill:#909090;\"]").hide();
		$("path[d=\"M 5.5 16.5 L 17.5 16.5 17.5 14.5 5.5 14.5 Z M 11.5 14.5 L 15.5 9.5 10.5 9.5 10.5 4.5 12.5 4.5 12.5 9.5 7.5 9.5 Z\"]").hide();
		$("path[d=\"M 5.5 12.5 L 17.5 12.5 17.5 9.5 5.5 9.5 Z M 7.5 9.5 L 7.5 4.5 15.5 4.5 15.5 9.5 Z M 7.5 12.5 L 5.5 16.5 17.5 16.5 15.5 12.5 Z\"]").hide();
	};
	
	$(window).resize(function(){
		$(".tab-pane").css({width:$(".tabbable").first().width()});
	});
	function getFinanceData(){
		var revenue = [];
		var expenditure = [];
		var categories = [];
		
		var lastRevenue = 0;
		var lastExenditure = 0;
		
		var mailCount = 0;
		for(var n=0;n<KPS.graphs.currentEvent;n++) {
			var event = KPS.graphs.events[n];
			
			var currentMail = KPS.graphs.revenueexpenditure[mailCount] ? KPS.graphs.revenueexpenditure[mailCount] : {eventId: -1};
			
			if(currentMail.eventId == event.id) {
				lastRevenue = currentMail.revenue;
				lastExenditure = currentMail.expenditure;
				mailCount++;
			}
			
			revenue.push(lastRevenue);
			expenditure.push(lastExenditure);
			categories.push(event.id);
			
		}
		return {vals:{'revenue': revenue, 'expenditure': expenditure},cats:categories};
	}
	function getTimeCategories(){
	
		var last = KPS.graphs.events[KPS.graphs.currentEvent-1].timestamp;
		var first = KPS.graphs.events[0].timestamp;
		var diff = (last - first)/numberOfEventCategories;
		var eventIdx = 0;
		var cats = [];
		var eventCounts = [];
		for(var i = 0;i< numberOfEventCategories; i++){
			for(;eventIdx < KPS.graphs.currentEvent; eventIdx++){
				if(KPS.graphs.events[eventIdx].timestamp >= first){
					cats.push(KPS.graphs.events[eventIdx].timestamp);
					eventCounts.push(eventIdx);
					break;
				}
				
			}
			first += diff;
		}
		eventCounts[eventCounts.length - 1] = KPS.graphs.currentEvent;
		return {cats:cats,values:eventCounts};
	}
	function resetCounters(){
		totalInternational = 0;
		totalDomestic = 0;
		total = 0;
	}
	function parseFinancesGlobal(type){
		var finances = KPS.graphs.finances[type];
		
		var parsedValues = [];
		var finance;
		for(var fiIdx in finances.international){
			finance = finances.international[fiIdx];
			total += parseFloat(finance.amount);
			totalInternational += parseFloat(finance.amount);
		}
		for(var fiIdx in finances.domestic){
			finance = finances.domestic[fiIdx];
			total += parseFloat(finance.amount);
			totalDomestic += parseFloat(finance.amount);
			
		}
		parsedValues.push({name:"International",y:(totalInternational/total),color:colors[3],value: totalInternational});
		parsedValues.push({name:"Domestic",		y:(totalDomestic/total),	 color:colors[2],value: totalDomestic});
		return parsedValues;
	};
	function parseFinancesByRoute(type){
		var finances = KPS.graphs.finances[type];
		var parsedValues = [];
		var colorIdx = 0;
		for(var fiIdx in finances.international){
			finance = finances.international[fiIdx];
			parsedValues.push({
				name: finance.startPoint+" -> "+finance.endPoint+" ("+finance.priority+")",
				color: colors[(colorIdx++%colors.length)],
				y:parseFloat(finance.amount)/total,
				value: parseFloat(finance.amount)
			});
		}
		for(var fiIdx in finances.domestic){
			finance = finances.domestic[fiIdx];
			parsedValues.push({
				name: finance.startPoint+" -> "+finance.endPoint+" ("+finance.priority+")",
				color: colors[(colorIdx++%colors.length)],
				y:parseFloat(finance.amount)/total,
				value: parseFloat(finance.amount)
			});
		}
		return parsedValues;
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
		for(var catIdx in categories){
			var timestamp = categories[catIdx];
			categories[catIdx] =  KPS.data.format.date(new Date(timestamp));
		};
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
                categories: categories
            },
            yAxis: {
                title: {
                    text: 'Number of Events'
                }
            },
            tooltip: {
                enabled: false,
                formatter: function() {
                    return '';
                }
            },
            plotOptions: {
                line: {
                    dataLabels: {
                        enabled: true
                    },
                    enableMouseTracking: false
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
            	title: {
                    text: 'Event Number'
                },
            	labels: {
                    formatter: function() {
                        return this.value; // clean, unformatted number for year
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
                    return "<b>NZD $"+this.y.toFixed(2)+" </b><br/>"+this.series.name+"  at event #"+this.x;
                }
            },
            plotOptions: {
                area: {
                    pointStart: 0,
                    marker: {
                        enabled: false,
                        symbol: 'circle',
                        radius: 2,
                        states: {
                            hover: {
                                enabled: true
                            }
                        }
                    }
                }
            },
            series: series
        };
		return chartOpts;
	}

	
	
	$(document).ready(function(){
		$revExpSection = $("#dashboard-tab-revenue-expenditure");
		$noEventsSection = $("#dashboard-tab-no-of-events");

		$revExpSection.append(financesTimeChart);
		$revExpSection.append(expensesChart);
		$revExpSection.append(revenueChart);
		$noEventsSection.append(eventsTimeChart);
		
		$(".activate-graph").click(function(){
			setTimeout(function() {
				if (document.createEvent) {
					var evt = document.createEvent('UIEvents');
					evt.initUIEvent('resize', true, false,window,0);
					window.dispatchEvent(evt);
	
				} else if (document.createEventObject) {
				    window.fireEvent('onresize');
				}
			}, 1);
			
			cls.refreshCharts();
		});	
	});
	
	})
(KPS.graphs, jQuery);
