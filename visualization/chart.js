/**
 * @author Evan James
 * @version 1.0
 *
 * Adapted from Dimitry Kudrayvtsev's "D3.js Gantt Chart, example 3"
 * http://bl.ocks.org/dk8996/5538271
 */

// The readJSON function invokes the whole shebang
readJSON("../../JSON_output/Plans/Plan-SunApr14171418MDT2019.json");

function readJSON(path) {
    var xhr = new XMLHttpRequest();
    xhr.open('GET', path, true);
    xhr.responseType = 'blob';
    xhr.onload = function(e) {
        if (this.status == 200) {
            var file = new File([this.response], 'temp');
            var fileReader = new FileReader();
            fileReader.addEventListener('load', function(){
                //do stuff with fileReader.result
                var json = fileReader.result;
                createChartFromStateLog(json);
            });
            fileReader.readAsText(file);
        }
    };
    xhr.send();
}

function createChartFromStateLog(json_string) {
    var viz_div = d3.select("#visualization");
    window.viz_div_width = viz_div.node().getBoundingClientRect().width;
    window.gantt_chart_width = viz_div_width * (5/8);
    window.gantt_chart_height = 450;
    var json = JSON.parse(json_string);
    window.init_state = json['initialState'];
    window.goal_state = json['goalState'];
    window.plan = json['planBlockList'];
    // Used to stop the async function of animating the chart
    window.animation = false;

    // Keeping track of property values at each time, for use in value charts
    window.properties = {};
    init_state['properties'].forEach(function(property) {
        properties[property['name']] = [];
    });
    // Add property values at initial time
    init_state.properties.forEach(function(property) {
        var time_value_tuple = {"time": init_state.time, "value": property.value};
        properties[property.name].push(time_value_tuple);
    });

    window.task_dict = {};
    window.state_dict = {};
    var dict_key = 0;
    // Creating dictionaries to be able to reference tasks/states at certain times
    for(let i = 0; i < plan.length; i++) {
        task_dict[dict_key] = plan[i].task;
        state_dict[plan[i].state.time] = plan[i].state;
        dict_key += plan[i].task.duration;
    }

    // Declaring as window.<variable> makes it global? Kind of?
    window.start = Date.now();
    window.tasks = [
        {
            "startDate": start + 60000 * -10,
            "endDate": createTaskTime(state_dict[Object.keys(state_dict)[0]].time, false),
            "taskName": "System State",
            "status": "INITSTATE",
            "info": {"tooltip": {'name': "Initial State", 'beginning': 0, 'end': Object.keys(state_dict)[0].time},
                        "more": init_state
                    }
        },
        {
            "startDate": start + (60000 * goal_state['time']),
            "endDate": start + (60000 * goal_state['time']) + (60000 * 10),
            "taskName": "System State",
            "status": "GOALSTATE",
            "info": {
                "tooltip": {"name": "Goal State", "beginning": goal_state['time'], "end": "Next Execution"},
                "more": goal_state
                }
        }
        ];

    window.taskStatus = {
        "COMPLETED" : "bar-completed",
        "RUNNING" : "bar-running",
        "QUEUED" : "bar-queued",
        "STATE" : "bar-state",
        "INITSTATE": "bar-init-state",
        "GOALSTATE": "bar-goal-state"
    };

    window.taskNames = ["Executed Task", "System State"];
    tasks.sort(function(a, b) {
        return a.endDate - b.endDate;
    });
    var format = "%H:%M";

    // Invoke the gantt chart
    window.gantt = d3.gantt().selector('#visualization').taskTypes(taskNames).taskStatus(taskStatus).tickFormat(format).height(gantt_chart_height).width(gantt_chart_width).beginning_exec(start);

    // Set time domain for chart
    gantt.timeDomainMode("fixed");
    gantt.timeDomain([ d3.time.hour.offset(getEndDate(), -3), getEndDate() ]);

    var maxDate = tasks[tasks.length - 1].endDate;
    tasks.sort(function(a, b) {
        return a.startDate - b.startDate;
    });
    var minDate = tasks[0].startDate;

    var animation_checkbox = d3.select("#animation-checkbox");
    var start_line_checkbox = d3.select("#start-line-checkbox");
    var reload_checkbox = d3.select("#reload-when-done").hidden = true;
    createChart();
    showStartLine();
    animation_checkbox.on("change", createChart);
    start_line_checkbox.on("change", showStartLine);
}

function createChart() {
    d3.select(".chart").remove();
    d3.select("#barInfo").remove();
    if(d3.select("#animation-checkbox").property("checked")) {
        animateChart(state_dict, task_dict);
        return true;
    } else {
        staticChart(state_dict, task_dict);
        return true;
    }
}

function showStartLine() {
    var line = d3.select(".start-line");
    if(d3.select("#start-line-checkbox").property("checked")) {
        line.style("visibility", null);
        return true
    } else {
        line.style("visibility", "hidden");
        return true
    }
}

function staticChart(state_dict, task_dict) {
    var clock = d3.select("#clock");
    if(!clock.empty()) {
        clock.remove();
    }
    animation = false;
    window.tasks = [
        {
            "startDate": start + 60000 * -10,
            "endDate": createTaskTime(Object.keys(state_dict)[0], false),
            "taskName": "System State",
            "status": "INITSTATE",
            "info": {"tooltip": {'name': "Initial State", 'beginning': 0, 'end': Object.keys(state_dict)[0]},
                "more": init_state
            }
        },
        {
            "startDate": start + (60000 * goal_state['time']),
            "endDate": start + (60000 * goal_state['time']) + (60000 * 10),
            "taskName": "System State",
            "status": "GOALSTATE",
            "info": {
                "tooltip": {"name": "Goal State", "beginning": goal_state['time'], "end": "Next Execution"},
                "more": goal_state
            }
        }
    ];
    gantt(tasks);
    // Create states
    const states_array = Object.entries(state_dict);
    const tasks_array = Object.entries(task_dict);
    for(var i = 0; i < states_array.length; i++) {
        // task contains the task executed, and the state after execution
        var state = states_array[i][1];
        var task = tasks_array[i];
        var state_end_time;
        var info_time;
        // Add properties for state time to the properties dict
        state['properties'].forEach(function (property) {
            var time_value_tuple = {"time": state.time, "value": property.value};
            properties[property.name].push(time_value_tuple);
        });
        // Create end time for state
        if(i == states_array.length - 1) {
            state_end_time = createTaskTime(10, false);
            info_time = state['time'] + 10;
        } else {
            state_end_time = createTaskTime(states_array[i+1][1]['time'], false);
            info_time = states_array[i+1][1]['time'];
        }
        var gantt_state = {
            "startDate": createTaskTime(state.time, false),
            "endDate": state_end_time,
            "taskName": "System State",
            "status": "STATE",
            "info": {"tooltip": {'name': 'System State', 'beginning': state['time'], 'end': info_time},
                "more": state
            }
        };
        var gantt_task = {
            "startDate": createTaskTime(task[0], false),
            "endDate": createTaskTime((+task[0] + task[1]['duration']), false),
            "taskName": "Executed Task",
            "status": "COMPLETED",
            "info": {"tooltip": {"name": task[1]['name'], "duration": task[1]['duration']},
                "more": task[1]
            }
        };

        addTask(gantt_task);
        addTask(gantt_state);
    }

    d3.selectAll(".task-tooltip").remove();
    d3.selectAll(".state-tooltip").remove();
    interaction();
    return true
}

async function animateChart(state_dict, task_dict) {
    animation = true;
    // Add checkbox to reload on completion

    // Remove all information panels
    d3.select("#barInfo").remove();
    d3.select("#property-graph").remove();
    const states_array = Object.entries(state_dict);
    for(var i = 0; i < states_array.length; i++) {
        var state = states_array[i];
        var ending_time;
        if(i == states_array.length - 1) {
            ending_time = +state[0] + 10;
        } else {
            ending_time = +states_array[i+1][0];
        }
        console.log("adding ending time " + ending_time + " to " + state[0]);
        state_dict[state[0]]['ending'] = ending_time;
    }
    console.log("Animating the chart population");
    var end_duration = goal_state['time'];
    var time = 1;
    // Clearing the static chart
    d3.select(".chart").remove();
    d3.selectAll(".task-tooltip").remove();
    d3.selectAll(".state-tooltip").remove();
    tasks = [
        {
            "startDate": start + 60000 * -10,
            "endDate": createTaskTime(Object.keys(state_dict)[0], false),
            "taskName": "System State",
            "status": "INITSTATE",
            "info": {"tooltip": {'name': "Initial State", 'beginning': 0, 'end': Object.keys(state_dict)[0]},
                "more": init_state
            }
        }
    ];
    gantt(tasks);

    var viz = d3.select("#visualization")
        .append("svg")
        .attr("id", "clock")
        .attr("transform", "translate(0,-" + gantt_chart_height/2 + ")")
        .style("text-anchor", "middle");

    viz.append("text")
        .attr("class", "clock-text")
        .text("Time: 0")
        .attr('transform', 'translate(150, 50)');

    // Always push the task at time 0
    var task_in_progress = task_dict[0].name;
    var task_object = {
        "startDate": createTaskTime(0, false),
        "endDate": createTaskTime(task_dict[0]['duration'], false),
        "taskName": "Executed Task",
        "status": "RUNNING",
        "info": {"tooltip": {"name": task_dict[0]['name'], 'duration': task_dict[0]['duration']},
            "more": task_dict[0]}
    };
    addTask(task_object);
    await sleep(1000);

    while(time <= end_duration) {
        if(!animation) {
            break;
        }

        var clock = d3.select(".clock-text")
            .text("Time: " + time);

        var this_task = task_dict[time];
        var new_state = state_dict[time];
        if(this_task != undefined) {
            // Change the task_in_progress to completed
            var bar_in_progress = d3.select(".bar-running")
                .attr("class", "bar-completed");
            // Store the name of this task to be able to change the color later
            task_in_progress = this_task.name;
            // Create new task object to push to tasks
            task_object = {
                "startDate": createTaskTime(time, false),
                "endDate": createTaskTime(time + this_task['duration'], false),
                "taskName": "Executed Task",
                "status": "RUNNING",
                "info": {"tooltip": {"name": this_task['name'], 'duration': this_task['duration']},
                    "more": this_task}
            };
            addTask(task_object);
        }
        if(new_state != undefined) {
            // Create state for after previous task was completed
            var state_status;
            var tooltip_text;
            console.log(new_state);
            console.log(states_array);
            if(new_state['time'] == +states_array[states_array.length - 1][0]) {
                state_status = "GOALSTATE";
                tooltip_text = "Goal State";
            } else {
                state_status = "STATE";
                tooltip_text = "System State";
            }
            var state_object = {
                "startDate": createTaskTime(state_dict[time].time, false),
                "endDate": createTaskTime(new_state['ending'], false),
                "taskName": "System State",
                "status": state_status,
                "info": {
                    "tooltip": {'name': tooltip_text, 'beginning': new_state['time'], 'end': new_state['ending']},
                    "more": new_state}
            };
            addTask(state_object);
        }

        await sleep(1000);
        time++;


    }
    bar_in_progress = d3.select(".bar-running")
        .attr("class", "bar-completed");
    location.reload();
    interaction();
}

function sleep(duration) {
    return new Promise(resolve => setTimeout(resolve, duration));
}

function getEndDate() {
    var lastEndDate = Date.now();
    if (tasks.length > 0) {
        lastEndDate = tasks[tasks.length - 1].endDate;
    }
    return lastEndDate;
}

function addTask(task) {
    tasks.push(task);
    gantt.redraw(tasks);
}

/*
 * Used to create the task time since everything needs a clock time. Update is optional, will update the running time.
 * Need to update before looping to add next task
 */
function createTaskTime(duration, update) {
    if(update) {
        start += 60000*duration;
        return start
    }
    else {
        return start + 60000*duration;
    }
}

function interaction() {
    var svg = d3.select('.chart');

    // ***** TOOLTIPS *****
    var task_tooltip = d3.select("body")
        .append("div")
        .attr('class', 'task-tooltip');

    var state_tooltip = d3.select("body")
        .append("div")
        .attr('class', 'state-tooltip');

    // Task tooltip
    task_tooltip.append('div').attr('class', 'name');
    task_tooltip.append('div').attr('class', 'duration');
    svg.selectAll(".bar-queued, .bar-running, .bar-completed")
        .on('mouseover', function(d) {
            task_tooltip.select('.name').html("<b>Task name: " + d.info.tooltip.name + "</b>");
            task_tooltip.select('.duration').html("<b>Duration: " + d.info.tooltip.duration + "</b>");
            task_tooltip.style('display', 'block');
            task_tooltip.style('opacity', 2);
        })
        .on('mousemove', function(d) {
            task_tooltip.style('top', (d3.event.layerY + 10) + 'px')
                .style('left', (d3.event.layerX - 25) + 'px');
        })
        .on('mouseout', function() {
            task_tooltip.style('display', 'none');
            task_tooltip.style('opacity',0);
        });

    // State tooltip
    state_tooltip.append('div').attr('class', 'name');
    state_tooltip.append('div').attr('class', 'beginning');
    state_tooltip.append('div').attr('class', 'end');
    svg.selectAll(".bar-state, .bar-init-state, .bar-goal-state")
        .on('mouseover', function(d) {
            state_tooltip.select('.name').html("<b>" + d.info.tooltip.name + "</b>");
            state_tooltip.select('.beginning').html("<b>Beginning time: " + d.info.tooltip.beginning + "</b>");
            state_tooltip.select('.end').html("<b>End time: " + d.info.tooltip.end + "</b>");
            state_tooltip.style('display', 'block');
            state_tooltip.style('opacity', 2);
        })
        .on('mousemove', function(d) {
            state_tooltip.style('top', (d3.event.layerY + 10) + 'px')
                .style('left', (d3.event.layerX - 25) + 'px');
        })
        .on('mouseout', function() {
            state_tooltip.style('display', 'none');
            state_tooltip.style('opacity',0);
        });

    // ***** INFO PANEL *****
    var viz_width = d3.select("#visualization").node().getBoundingClientRect().width;
    var full_chart_width = d3.select(".chart").node().getBoundingClientRect().width;
    var full_chart_height = d3.select(".chart").node().getBoundingClientRect().height;
    var info_div = d3.select('#visualization')
        .append("svg")
        .attr("id", "barInfo")
        .attr("width", viz_width - full_chart_width - 50)
        .attr("height", full_chart_height)
        .attr("class", "infoHidden");

    // Task info panels
    svg.selectAll(".bar-queued, .bar-running, .bar-completed")
        .on('click', function(d) {
            d3.select("#clock").remove();
            var taskName = d.info.tooltip.name.replace(" ", "-");
            var barInfoClass = d3.select("#barInfo").attr("class");
            // Open info panel
            if(barInfoClass != "taskInfo " + taskName) {
                console.log("Opening new taskInfo panel");
                d3.select("#barInfo")
                    .style("opacity", 1)
                    .attr("class", "taskInfo " + taskName);
                taskInfoPanel(d,);
            // Close the info panel
            } else {
                console.log("Closing info panel");
                d3.select("#barInfo")
                    .style("opacity", 0)
                    .attr('class', 'infoHidden');
            }
        });

    // State info panels
    svg.selectAll(".bar-state, .bar-init-state, .bar-goal-state")
        .on('click', function(d) {
            d3.select("#clock").remove();
            var stateUID = d.info.more.uid;
            var barInfoClass = d3.select("#barInfo").attr("class");
            // Open info panel
            if(barInfoClass != "stateInfo " + stateUID) {
                console.log("Opening new stateInfo panel");
                d3.select("#barInfo")
                    .style("opacity", 1)
                    .attr("class", "stateInfo " + stateUID);
                stateInfoPanel(d);
            } else {
                console.log("Closing info panel");
                d3.select("#barInfo")
                    .style("opacity", 0)
                    .attr('class', 'infoHidden');
            }
        });

}

function stateInfoPanel(data) {
    var svg = d3.select("#barInfo");
    svg.selectAll("text").remove();
    svg.append("text")
        .attr("class", "info-title")
        .text("System State: Time " + data.info.more.time)
        .attr('transform', 'translate(25,50)');
    // Populate the properties
    svg.append("text")
        .attr("class", "property-header")
        .text("Property Information")
        .attr('transform', 'translate(50, 90)');
    var properties = data.info.more.properties;
    for(var i =0; i < properties.length; i++) {
        svg.append("text")
            .attr("class", "property")
            .text(properties[i].name + ":      " + properties[i].value)
            .attr('transform', 'translate(70, ' + (120 + i*30) + ')')
            .on("click", function() {
                var property = d3.select(this);
                var property_name = property.text().split(":")[0];
                resourceChart(property_name, data.info.more.time);
            })
    }
    if(data.info.more.previousTask != null) {
        svg.append("text")
            .attr("class", "previous-task-header")
            .text("Previous Task")
            .attr('transform', 'translate(270, 90)');
        svg.append("text")
            .attr("class", "previous-task")
            .text(data.info.more.previousTask.name)
            .attr('transform', 'translate(290, 120)');
    }
}

function taskInfoPanel(data) {
    var svg = d3.select("#barInfo");
    svg.selectAll("text").remove();
    svg.append("text")
        .attr("class", "info-title")
        .text("Task: " + data.info.more.name)
        .attr('transform', 'translate(25,50)');
    svg.append("text")
        .attr("class", "impacts-header")
        .text("Property Impacts")
        .attr('transform', 'translate(50, 90)');
    var property_impacts = data.info.more.properties;
    for(var i = 0; i < property_impacts.length; i++) {
        var text = svg.append("text")
            .attr("class", "property-impact")
            .text(property_impacts[i].name + ":      " + property_impacts[i].value)
            .attr('transform', 'translate(70, ' + (120 + i*30) + ')')
            .on("click", function() {
                var property = d3.select(this);
                var property_name = property.text().split(":")[0];
                resourceChart(property_name, data.info.more.time);
            })

    }

}

function resourceChart(property_name, time) {
    // Add graph svg if there isn't one
    if(d3.select("#property-graph").empty()) {
        d3.select("#property-graph-div")
            .append("svg")
            .attr("width", viz_div_width - 35)
            .attr("height", gantt_chart_height)
            .attr("id", "property-graph")
            .attr("class", "graph-" + property_name);
    } else {
        var prop_graph_class = d3.select("#property-graph").attr("class");
        // If the current graph is for same prop as clicked, remove graph
        if(prop_graph_class === "graph-"+property_name) {
            console.log("removing");
            d3.select("#property-graph").remove();
            return;
        }
        d3.select("#property-graph").remove();
        // Add new graph
        console.log("Opening new property graph: " + property_name);
        d3.select("#property-graph-div")
            .append("svg")
            .style("opacity", 1)
            .attr("width", viz_div_width - 35)
            .attr("height", gantt_chart_height)
            .attr("id", "property-graph")
            .attr("class", "graph-" + property_name);
    }
    // Clear current line and axes
    d3.select(".line").remove();
    d3.select(".x-axis").remove();
    d3.select(".y-axis").remove();
    d3.select("#x-axis-label").remove();
    d3.select("#y-axis-label").remove();
    var margin = {left: 50, right: 25, top: 25, bottom: 50};
    var resource_chart_height = d3.select("#property-graph").node().getBoundingClientRect().height - margin.top - margin.bottom;
    var resource_chart_width = d3.select("#property-graph").node().getBoundingClientRect().width - margin.left - margin.right;
    var property_data = properties[property_name];

    // Check the max value of the data, or if it's boolean
    var maxY = -1;
    var interpolate_type;
    var y;
    if((typeof property_data[0].value) === "boolean") {
        interpolate_type = "step-after";
        y = d3.scale.ordinal().domain(["False", "True"]).range([resource_chart_height, 0]);
    } else {
        for(var i = 0; i < property_data.length; i++) {
            if(property_data[i].value > maxY) {
                maxY = property_data[i].value;
            }
        }
        interpolate_type = "linear";
        y = d3.scale.linear().domain([0, maxY]).range([resource_chart_height, 0]);
    }


    var graph = d3.select("#property-graph")
        .append("g")
        .attr("class", "graph")
        .attr("transform", "translate(" + 50 + "," + 25 + ")");
    var x = d3.scale.linear().domain([0, property_data[property_data.length - 1].time]).range([0, resource_chart_width]);
    var xAxis = d3.svg.axis().orient("bottom").scale(x);
    var yAxis = d3.svg.axis().orient("left").scale(y);

    var line = d3.svg.line()
        .x(function(d) { return x(d.time); })
        .y(function(d) { return y(d.value); })
        .interpolate(interpolate_type);

    // Add data line
    graph.append("path")
        .attr("class", "line")
        .attr("d", function(d) { return line(property_data); });

    // Add point circles
    property_data.forEach(function(d) {
        var circle = d3.select("g.graph")
            .append("circle")
            .attr("cx", x(d.time))
            .attr("cy", y(d.value))
            .attr("r", 7)
            .attr('stroke','black')
            .attr('stroke-width',1)
            .on('mouseover', function () {
                d3.select(this)
                    .transition()
                    .duration(250)
                    .attr('r',14)
                    .attr('stroke-width',3)
            })
            .on('mouseout', function () {
                d3.select(this)
                    .transition()
                    .duration(250)
                    .attr('r',7)
                    .attr('stroke-width',1)
            })
            .append('title') // Tooltip
            .text("Time: " + d.time + "\nValue: " + d.value)
    });

    // Add x axis
    graph.append("g")
        .attr("class", "x-axis")
        .attr("transform", "translate(0," + resource_chart_height + ")")
        .call(xAxis);

    // X axis label
    graph.append("text")
        .attr("id", "x-axis-label")
        .attr("transform", "translate(" + (resource_chart_width/2) + "," + (resource_chart_height + margin.top + 20) + ")")
        .style("text-anchor", "middle")
        .text("Time");

    // Add y axis
    graph.append("g")
        .attr("class", "y-axis")
        .call(yAxis);

    // y axis label
    graph.append("text")
        .attr("id", "y-axis-label")
        .attr("transform", "rotate(-90)")
        .attr("y", 0 - margin.left)
        .attr("x", 0 - (resource_chart_height / 2))
        .attr("dy", "1em")
        .style("text-anchor", "middle")
        .text(property_name);


}
