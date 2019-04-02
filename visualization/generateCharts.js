'use strict';

// mean and deviation for time interval
var meanMs = 1000, // milliseconds
    dev = 150;

// define time scale
var timeScale = d3.scale.linear()
    .domain([300, 1700])
    .range([300, 1700])
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

var json_data = getJSONdata("../JSON_output/SystemStates/SystemState-SunMar31151138MDT2019.json");

// create the real time chart
generateChart("Test1");
generateChart("Test2");
generateChart("Test3");

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

function getJSONdata(file, callback) {
    var xobj = new XMLHttpRequest();
    xobj.overrideMimeType("application/json");
    xobj.open('GET', file, true);
    xobj.onreadystatechange = function() {
        if(xobj.readyState == 4 && xobj.status == "200") {
            callback(xobj.responseText);
        }
    };
    xobj.send(null);
}

function generateChart(propName) {
    var chart = realTimeChart()
        .title(propName)
        .yTitle("Y Scale")
        .xTitle("X Scale")
        .border(true)
        .width(600)
        .height(290)
        .barWidth(1)
        .initialData(data);

    console.log("Generating chart: ", chart.title());

    var chartDiv = d3.select("#viewDiv").append("div")
        .attr("id", "chartDiv")
        .call(chart);
}

function getJSONdata(file) {
    var request = new XMLHttpRequest();
    request.open("GET", file, true);
    request.send(null);
    var my_JSON_object = JSON.parse(request.responseText);
    return my_JSON_object;
}

// start the data generator
dataGenerator();