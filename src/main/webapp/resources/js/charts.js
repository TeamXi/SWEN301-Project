KPS.graphs = KPS.graphs || {};
KPS.graphs.finances = KPS.graphs.finanaces || {};
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
		var financeData = getDummyFinanceData();
		var chartOpts = getFinancesOverTimeOpts(financesTimeId,financeData);
		chartFinances = new Highcharts.Chart(chartOpts);
	};
	cls.refreshEventsTimeChart = function(){
		var eventTimeData = getTimeCategories();
		var chartOpts = getEventsOverTimeOpts(eventsTimeId,eventTimeData.cats,eventTimeData.values);
		chartEventsTime = new Highcharts.Chart(chartOpts);
	};
	cls.dirtyRefresh = function(){
		if(chartFinances && chartFinances.refresh) chartFinances.refresh();
		if(chartEventsTime && chartEventsTime.refresh) chartEventsTime.refresh();
		if(chartExpenses && chartExpenses.refresh) chartExpenses.refresh();
		if(chartRevenue && chartRevenue.refresh) chartRevenue.refresh();
	}
	cls.refreshCharts = function(){
		$.get(KPS.siteRoot+"/charts",{},function(data){
			$data = $(data);
			$("body").append($(data));
			$data.load(function(){
				$data.remove();
			});
			$(revenueChart).css('height',$(revenueChart).width()+"px");
			$(expensesChart).css('height',$(expensesChart).width()+"px");
			//Comment out line below to use real data
			//cls.setFinancesDummyData();
			cls.refreshExpensesChart();
			cls.refreshRevenueChart();
			cls.refreshEventsTimeChart();
			cls.refreshFinancesOverTime();
			$("rect[rx=\"3\"]").hide();
			$("path[d=\"M 5.5 16.5 L 17.5 16.5 17.5 14.5 5.5 14.5 Z M 11.5 14.5 L 15.5 9.5 10.5 9.5 10.5 4.5 12.5 4.5 12.5 9.5 7.5 9.5 Z\"]").hide();
			$("path[d=\"M 5.5 12.5 L 17.5 12.5 17.5 9.5 5.5 9.5 Z M 7.5 9.5 L 7.5 4.5 15.5 4.5 15.5 9.5 Z M 7.5 12.5 L 5.5 16.5 17.5 16.5 15.5 12.5 Z\"]").hide();
		});
	};
	
	cls.setFinancesDummyData = function(){
		/**
		 * This is just dummy data, so graphs arent boring and empty when service is restarted, comment out line above to use real data.
		 */
		KPS.graphs.finances = {
				expenditure:{
					international:[
						{startPoint:"Dunedin", 		endPoint: "China", amount:"52.30", priority: "Air" },
						{startPoint:"Wellington", 	endPoint: "Russia", amount:"122.30", priority: "Air" },
						{startPoint:"Dunedin", 		endPoint: "China", amount:"42.30", priority: "Land" },
						{startPoint:"Dunedin", 		endPoint: "Rome" , amount:"17", priority: "Air" },
						{startPoint:"Auckland", 	endPoint: "Tai Pang", amount:"89", priority: "Air" },
					],
						domestic:[
						{startPoint:"Dunedin", 		endPoint: "Wellington", amount:"15.30", priority: "Land" },
						{startPoint:"Wellington", 	endPoint: "Auckland", amount:"12.30", priority: "Air" },
						{startPoint:"Invercargil", 	endPoint: "Rotorua", amount:"37.5", priority: "Land" },
						{startPoint:"Rotorua", 		endPoint: "Wellington" , amount:"17", priority: "Land" },
						{startPoint:"Auckland", 	endPoint: "Wellington", amount:"25", priority: "Air" },
	
						]
				},
				revenue:{
						international:[
									{startPoint:"Dunedin", 		endPoint: "China", amount:"22.30", priority: "Air" },
									{startPoint:"Wellington", 	endPoint: "Russia", amount:"82.30", priority: "Air" },
									{startPoint:"Dunedin", 		endPoint: "China", amount:"32.30", priority: "Land" },
									{startPoint:"Dunedin", 		endPoint: "Rome" , amount:"67", priority: "Air" },
									{startPoint:"Auckland", 	endPoint: "Tai Pang", amount:"189", priority: "Air" },
								],
									domestic:[
									{startPoint:"Dunedin", 		endPoint: "Wellington", amount:"5.30", priority: "Land" },
									{startPoint:"Wellington", 	endPoint: "Auckland", amount:"12.30", priority: "Air" },
									{startPoint:"Invercargil", 	endPoint: "Rotorua", amount:"37.5", priority: "Land" },
									{startPoint:"Rotorua", 		endPoint: "Wellington" , amount:"17", priority: "Land" },
									{startPoint:"Auckland", 	endPoint: "Wellington", amount:"25", priority: "Air" },
				
									]
				}
				
		};
	};
	
	function getDummyFinanceData(){
		return {
			revenue:[0,100,145,416,922,1046,1138,2001,2002,2333,3123,3600],
			expenditure:[0,5,11,244,516,2098,2145,2345,2444,2445,2446,3000]
		};
	}
	function getTimeCategories(){
	
		var last = events[events.length-1].timestamp;
		var first = events[0].timestamp;
		var diff = (last - first)/numberOfEventCategories;
		var eventIdx = 0;
		var cats = [];
		var eventCounts = [];
		for(var i = 0;i< numberOfEventCategories; i++){
			for(;eventIdx < events.length; eventIdx++){
				if(events[eventIdx].timestamp >= first){
					cats.push(events[eventIdx].timestamp);
					eventCounts.push(eventIdx);
					break;
				}
				
			}
			first += diff;
		}
		eventCounts[eventCounts.length - 1] = events.length;
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
				name: finance.startPoint+" -> "+finance.endPoint+" ( "+finance.priority.replace(/_/g,"")+" )",
				color: colors[(colorIdx++%colors.length)],
				y:parseFloat(finance.amount)/total,
				value: parseFloat(finance.amount)
			});
		}
		for(var fiIdx in finances.domestic){
			finance = finances.domestic[fiIdx];
			parsedValues.push({
				name: finance.startPoint+" -> "+finance.endPoint+" ( "+finance.priority.replace(/_/g,"")+" )",
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
                    return this.point.name+'<br/><b>NZD $ </b>' +this.point.value;
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
                        return (this.point.y > 0.04)? '<b>NZD $ </b>' +(this.point.value +"").substring(0,9):null;
                    }
                }
            }]
        };
		  return chartOpts;
	}
	function getEventsOverTimeOpts(container,categories,values){
		for(var catIdx in categories){
			var timestamp = categories[catIdx];
			categories[catIdx] =  new Date(timestamp).toGMTString();
		};
		var chartOpts = {
            chart: {
                renderTo: container,
                type: 'line'
            },
            title: {
                text: 'Total Events over Time'
            },
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
	function getFinancesOverTimeOpts(container,financeData){
		
		var chartOpts = {
            chart: {
                renderTo: container,
                type: 'area'
            },
            title: {
                text: 'Expenditure and Revenue over Time'
            },
            subtitle: {
                text: 'KPSmart'
            },
            xAxis: {
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
                    return "<b>NZD $"+this.y+" </b><br/>"+this.series.name+"  at event #"+this.x;
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
            series: [	{
            	name:"Revenue",
            	data:financeData.revenue
            },
            {
            	name:"Expeniture",
            	data:financeData.expenditure
            }
                     ]
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

		
		
		$(".activate-graph").click(function(){cls.refreshCharts();});
		
		
	});
	
	})
(KPS.graphs, jQuery);
