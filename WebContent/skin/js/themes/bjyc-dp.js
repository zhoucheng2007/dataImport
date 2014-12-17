/**
 * 北京烟草大屏pc版 theme for Highcharts JS
 * @author 崔玉华
 */
Highcharts.theme = {
		colors: ["#00FF00","#FCEE21","#00fffd","#FF8500","#000091","#663433","#3394f3","#7042a4",'#4572A7', '#AA4643', '#89A54E', '#80699B'],
		chart: {
			borderWidth:0,
			backgroundColor:'',
			borderRadius: 5,
			plotBackgroundColor: null,
			plotShadow: true,
			plotBorderWidth: 0,
			spacingTop: 50,
			style: {
				color:'#ffffff',
				fontFamily:'SimHei,simsun.ttc,Lucida Grande, Lucida Sans Unicode, Verdana, Arial, Helvetica, sans-serif'
            }
		},
		title: {
			y: -20,
			style: {
				color:'#ffffff',
				fontFamily:'SimHei,simsun.ttc,Lucida Grande, Lucida Sans Unicode, Verdana, Arial, Helvetica, sans-serif',
            	fontSize:'18px',
            	fontWeight:'normal' 
            }
		},
		subtitle: {
			style: {
				color:'#ffffff',
				fontFamily:'SimHei,simsun.ttc,Lucida Grande, Lucida Sans Unicode, Verdana, Arial, Helvetica, sans-serif',
            	fontSize:'16px',
            	fontWeight:'normal' 
            }
		},
		xAxis: {
			lineWidth:1,
			lineColor: '#dfe7ef',
			tickColor: '#dfe7ef',
			labels: {
                style: {
                	color:'#ffffff',
                	fontFamily:'SimHei,simsun.ttc,Lucida Grande, Lucida Sans Unicode, Verdana, Arial, Helvetica, sans-serif',
                	fontSize:'15px',
                	fontWeight:'normal' 
                },
                y: 20
            },
			title: {
				style: {
                	color:'#ffffff',
                	fontFamily:'SimHei,simsun.ttc,Lucida Grande, Lucida Sans Unicode, Verdana, Arial, Helvetica, sans-serif',
                	fontSize:'15px',
                	fontWeight:'normal' 
                }
			}
		},
		yAxis: {
			 labels: {
                 style: {
                 	color:'#ffffff',
                 	fontFamily:'SimHei,simsun.ttc,Lucida Grande, Lucida Sans Unicode, Verdana, Arial, Helvetica, sans-serif',
                 	fontSize:'14px',
                 	fontWeight:'normal' 
                 }
             },
             title: {
                 text: '',
                 style: {
                 	color:'#ffffff',
                 	fontFamily:'SimHei,simsun.ttc,Lucida Grande, Lucida Sans Unicode, Verdana, Arial, Helvetica, sans-serif',
                 	fontSize:'15px',
                 	fontWeight:'normal' 
                 }
             },
             gridLineWidth: 1,
			 gridLineColor:'#dfe7ef'
		},
		legend: {
			align: 'right', 
			verticalAlign: 'top', 
			floating:true ,
			y:-50,
            backgroundColor: '',
            borderWidth: 0,
			borderColor:'',
			itemStyle: {
				color: '#ffffff',
				fontFamily:'SimHei,simsun.ttc,Lucida Grande, Lucida Sans Unicode, Verdana, Arial, Helvetica, sans-serif',
             	fontSize:'15px',
             	fontWeight:'normal' 
			},
			itemHoverStyle:{color: '#FCEE21'}
		},
		labels: {
			style: {
				color: '#ffffff'
			}
		},
		 tooltip: {//信息提示
             style: {
            	 fontFamily:'SimHei,simsun.ttc,Lucida Grande, Lucida Sans Unicode, Verdana, Arial, Helvetica, sans-serif',
              	fontSize:'15px'
             }
         },
		plotOptions: {
			line: {
				dataLabels: {
					color: '#ffffff'
				},
				marker: {
					lineColor: '#4F59B0'
				}
			},
			column: {
				borderWidth: 0,
				dataLabels: {
					color: '#ffffff'
				},
				marker: {
					lineColor: '#4F59B0'
				}
			},
			bar: {
				borderWidth: 0,
				dataLabels: {
					color: '#ffffff'
				}
			},
			spline: {
				dataLabels: {
					color: '#ffffff'
				},
				marker: {
					lineColor: '#333'
				}
			},
			scatter: {
				dataLabels: {
					color: '#ffffff'
				},
				marker: {
					lineColor: '#333'
				}
			},
			candlestick: {
				dataLabels: {
					color: '#ffffff'
				},
				lineColor: 'white'
			}
		},

		toolbar: {
			itemStyle: {
				color: '#CCC'
			}
		},

		navigation: {
			buttonOptions: {
				backgroundColor: {
					linearGradient: { x1: 0, y1: 0, x2: 0, y2: 1 },
					stops: [
						[0.4, '#edf0f5'],
						[0.6, '#edf0f5']
					]
				},
				borderColor: '#edf0f5',
				symbolStroke: '#45686c',
				hoverSymbolStroke: '#45686c'
			}
		},

		exporting: {		
			enabled:false, 
			buttons: {
				exportButton: {
					symbolFill: '#bdd0d7'
				},
				printButton: {
					symbolFill: '#bdd0d7'
				}
			}
		},

		// scroll charts
		rangeSelector: {
			buttonTheme: {
				fill: {
					linearGradient: { x1: 0, y1: 0, x2: 0, y2: 1 },
					stops: [
						[0.4, '#888'],
						[0.6, '#555']
					]
				},
				stroke: '#000000',
				style: {
					color: '#CCC',
					fontWeight: 'bold'
				},
				states: {
					hover: {
						fill: {
							linearGradient: { x1: 0, y1: 0, x2: 0, y2: 1 },
							stops: [
								[0.4, '#BBB'],
								[0.6, '#888']
							]
						},
						stroke: '#000000',
						style: {
							color: 'white'
						}
					},
					select: {
						fill: {
							linearGradient: { x1: 0, y1: 0, x2: 0, y2: 1 },
							stops: [
								[0.1, '#000'],
								[0.3, '#333']
							]
						},
						stroke: '#000000',
						style: {
							color: 'yellow'
						}
					}
				}
			},
			inputStyle: {
				backgroundColor: '#333',
				color: 'silver'
			},
			labelStyle: {
				color: 'silver'
			}
		},

		navigator: {
			handles: {
				backgroundColor: '#666',
				borderColor: '#AAA'
			},
			outlineColor: '#CCC',
			maskFill: 'rgba(16, 16, 16, 0.5)',
			series: {
				color: '#7798BF',
				lineColor: '#A6C7ED'
			}
		},

		scrollbar: {
			barBackgroundColor: {
					linearGradient: { x1: 0, y1: 0, x2: 0, y2: 1 },
					stops: [
						[0.4, '#888'],
						[0.6, '#555']
					]
				},
			barBorderColor: '#CCC',
			buttonArrowColor: '#CCC',
			buttonBackgroundColor: {
					linearGradient: { x1: 0, y1: 0, x2: 0, y2: 1 },
					stops: [
						[0.4, '#888'],
						[0.6, '#555']
					]
				},
			buttonBorderColor: '#CCC',
			rifleColor: '#FFF',
			trackBackgroundColor: {
				linearGradient: { x1: 0, y1: 0, x2: 0, y2: 1 },
				stops: [
					[0, '#000'],
					[1, '#333']
				]
			},
			trackBorderColor: '#666'
		},

		// special colors for some of the demo examples
		legendBackgroundColor: 'rgba(48, 48, 48, 0.8)',
		legendBackgroundColorSolid: 'rgb(70, 70, 70)',
		dataLabelsColor: '#444',
		textColor: '#E0E0E0',
		maskColor: 'rgba(255,255,255,0.3)'
};

// Apply the theme
var highchartsOptions = Highcharts.setOptions(Highcharts.theme);
