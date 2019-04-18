// Author: Evan James, evja3477@colorado.edu
// Adapted from Bo Ericsson's "D3 Based Real Time Chart" via https://bl.ocks.org/boeric/
//      Inspiration from numerous examples by Mike Bostock, http://bl.ocks.org/mbostock
//      and example by Andy Aiken, http://blog.scottlogic.com/2014/09/19/interactive.html
'use strict';

// mean and deviation for time interval
var meanMs = 1000, // milliseconds
    dev = 150;

// define time scale
var timeScale = d3.scale.linear()
    .domain([0, 1700])
    .range([0, 1700])
    .clamp(true);

// define value scale
var valueScale = d3.scale.linear()
    .domain([0, 1])
    .range([30, 95]);

// generate initial data
var normal = d3.random.normal(1000, 150);
var currMs = new Date().getTime() - 300000 - 4000;
var data = d3.range(300).map(function(d, i, arr) {
    var value = valueScale(Math.random()); // random data
    //var value = Math.round((d % 60) / 60 * 95); // ramp data
    var interval = Math.round(timeScale(normal()));
    currMs += interval;
    var time = new Date(currMs);
    var obj = { interval: interval, value: value, time: time, ts: currMs }
    return obj;
})

d3.json("../../JSON_output/SystemStates/SystemState-SunMar31151138MDT2019.json", function(data) {
    processSystemState(data);
});

// alternative invocation
//chart(chartDiv);

// drive data into the chart roughly every second
// in a normal use case, real time data would arrive through the network or some other mechanism
var d = 0;
function dataGenerator() {

    var timeout = Math.round(timeScale(normal()));

    setTimeout(function() {

        // create new data item
        var now = new Date();
        var obj = {
            value: valueScale(Math.random()), // random data
            //value: Math.round((d++ % 60) / 60 * 95), // ramp data
            time: now,
            color: "red",
            ts: now.getTime(),
            interval: timeout
        };

        // send the datum to the chart
        chart.datum(obj);

        // do forever
        dataGenerator();

    }, timeout);
}

function generateChart(propName, maxTime, init_data) {
    var chart = realTimeChart()
        .title(propName)
        .yTitle("Property Value")
        .xTitle("Time")
        .border(true)
        .width(750)
        .height(300)
        .barWidth(1)
        .maxTime(maxTime)
        .initialData([init_data]);

    console.log("Generating chart: ", chart.title());

    var chartDiv = d3.select("#viewDiv").append("div")
        .attr("id", "chartDiv")
        .attr("class", propName)
        .call(chart);
}

function processSystemState(data) {
    var init_props = data[0]['properties'];
    var maxTime = data[data.length-1]['time'];
    // Use initial state to generate graphs
    for(var i=0; i<init_props.length; i++) {
        var prop_name = init_props[i]['name'];
        generateChart(prop_name, maxTime, init_props[i]["value"]);
    }
    // Start feeding in each successive system state
    // TODO: Feed the system state to every chart
    for(var i=0; i<data.length; i++) {
        var propertyselector = "." + data[i]['properties'][0]['name'];
        var chart = d3.select("#chartDiv");
        console.log(chart.text());
        chart.datum(data[i]['properties'][0]['value']);
    }
}

// start the data generator
// dataGenerator();