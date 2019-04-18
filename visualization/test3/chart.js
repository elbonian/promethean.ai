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
    var json = JSON.parse(json_string);
    window.init_state = json['initialState'];
    window.goal_state = json['goalState'];
    window.plan = json['planBlockList'];
    // Used to stop the async function of animating the chart
    window.animation = false;

    window.task_dict = {};
    window.state_dict = {};
    var dict_key = 0;
    for(let i = 0; i < plan.length; i++) {
        task_dict[dict_key] = plan[i].task;
        state_dict[plan[i].state.time] = plan[i].state;
        dict_key += plan[i].task.duration;
    }

    console.log(state_dict);
    console.log(task_dict);

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
    window.gantt = d3.gantt().selector('#visualization').taskTypes(taskNames).taskStatus(taskStatus).tickFormat(format).height(450).width(800).beginning_exec(start);

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
    createChart();
    showStartLine();
    animation_checkbox.on("change", createChart);
    start_line_checkbox.on("change", showStartLine);
    // createChart();
}

function createChart() {
    d3.select(".chart").remove();
    d3.select("#barInfo").remove();
    if(d3.select("#animation-checkbox").property("checked")) {
        animateChart(state_dict, task_dict);
        return true;
    } else {
        staticChartFromPlan(state_dict, task_dict);
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

function staticChartFromPlan(state_dict, task_dict) {
    animation = false;
    console.log("Creating static chart");
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
    console.log(states_array.length);
    console.log(states_array);
    console.log(tasks_array);
    for(var i = 0; i < states_array.length; i++) {
        // task contains the task executed, and the state after execution
        var state = states_array[i][1];
        var task = tasks_array[i];
        var state_end_time;
        var info_time;
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
    const states_array = Object.entries(state_dict);
    // var state = states_array[i][1];
    // if(i == states_array.length - 1) {
    //     state_end_time = createTaskTime(10, false);
    //     info_time = state['time'] + 10;
    // } else {
    //     state_end_time = createTaskTime(states_array[i+1][1]['time'], false);
    //     info_time = states_array[i+1][1]['time'];
    // }
    console.log("Animating the chart population");
    console.log(state_dict);
    console.log(task_dict);
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
        var this_task = task_dict[time];
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
            // Create state for after previous task was completed
            // state_object = {
            //     "startDate": createTaskTime(state_dict[time].time, false),
            //     "endDate": state_end_time,
            //     "taskName": "System State",
            //     "status": "STATE",
            //     "info": {
            //         "tooltip": {'name': 'System State', 'beginning': state_dict[0]['time'], 'end': info_time},
            //         "more": state}
            // };
            addTask(task_object);
        }
        await sleep(1000);
        console.log("Blah" + time);
        console.log(this_task);
        time++;


    }
    bar_in_progress = d3.select(".bar-running")
        .attr("class", "bar-completed");
    interaction();
    return
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
    var svg = d3.select('svg')
        .attr("style", "outline: thin solid red");

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

    // ***** BAR INFO PANEL *****
    var info_div = d3.select('#bar-info')
        .append("svg")
        .attr("id", "barInfo")
        .attr("width", svg.node().getBoundingClientRect().width + "px")
        .attr("height", "350px")
        .attr("class", "infoHidden");

    // Task info panels
    svg.selectAll(".bar-queued, .bar-running, .bar-completed")
        .on('click', function(d) {
            var taskName = d.info.tooltip.name.replace(" ", "-");
            var barInfoClass = d3.select("#barInfo").attr("class");
            // Open info panel
            if(barInfoClass != "taskInfo " + taskName) {
                console.log("Opening new taskInfo panel");
                d3.select("#barInfo")
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
            var stateUID = d.info.more.uid;
            var barInfoClass = d3.select("#barInfo").attr("class");
            // Open info panel
            if(barInfoClass != "stateInfo " + stateUID) {
                console.log("Opening new stateInfo panel");
                d3.select("#barInfo")
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
    svg.attr("style", "background: grey")
        .append("text")
        .attr("class", "info-title")
        .text("System State: Time " + data.info.more.time)
        .attr('transform', 'translate(25,50)');
    // Populate the properties
    svg.append("text")
        .attr("class", "property-header")
        .text("Property Information")
        .attr('transform', 'translate(50, 90)');
    var properties = data.info.more.properties;
    console.log(data);
    if(data.info.more.previousTask != null) {
        svg.append("text")
            .attr("class", "previous-task-header")
            .text("Previous Task")
            .attr('transform', 'translate(450, 90)');
        svg.append("text")
            .attr("class", "previous-task")
            .text(data.info.more.previousTask.name)
            .attr('transform', 'translate(470, 120)');
    }
    for(var i =0; i < properties.length; i++) {
        svg.append("text")
            .attr("class", "property")
            .text(properties[i].name + ":      " + properties[i].value)
            .attr('transform', 'translate(70, ' + (120 + i*30) + ')');
    }
}

function taskInfoPanel(data) {
    var svg = d3.select("#barInfo");
    svg.selectAll("text").remove();
    svg.attr("style", "background: grey")
        .append("text")
        .attr("class", "info-title")
        .text("Task: " + data.info.more.name)
        .attr('transform', 'translate(25,50)');
    console.log(data.info.more);
    svg.append("text")
        .attr("class", "impacts-header")
        .text("Property Impacts")
        .attr('transform', 'translate(50, 90)');
    var property_impacts = data.info.more.properties;
    for(var i = 0; i < property_impacts.length; i++) {
        svg.append("text")
            .attr("class", "property-impact")
            .text(property_impacts[i].name + ":      " + property_impacts[i].value)
            .attr('transform', 'translate(70, ' + (120 + i*30) + ')');
    }

}
