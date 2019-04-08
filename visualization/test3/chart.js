/**
 * @author Evan James
 * @version 1.0
 *
 * Adapted from Dimitry Kudrayvtsev's "D3.js Gantt Chart, example 3"
 * http://bl.ocks.org/dk8996/5538271
 */

// The readJSON function invokes the whole shebang
readJSON("../../JSON_output/Plans/Plan-MonApr01191906MDT2019.json");

function createChartFromStateLog(json_string) {
    var json = JSON.parse(json_string);
    var init_state = json['initialState'];
    var goal_state = json['goalState'];
    var plan = json['planBlockList'];

    // Declaring as window.<variable> makes it global? Kind of?
    window.start = Date.now();
    window.tasks = [
        {
            "startDate": start + 60000 * -10,
            "endDate": start,
            "taskName": "System State",
            "status": "STATE",
            "info": {"tooltip": {'name': "Initial State", 'beginning': 0, 'end': 0},
                        "more": init_state
                    }
        }
        ];

    window.taskStatus = {
        "COMPLETED" : "bar-completed",
        "RUNNING" : "bar-running",
        "QUEUED" : "bar-queued",
        "STATE" : "bar-state"
    };

    for(var i = 0; i < plan.length; i++) {
        console.log(plan[i]);
        // task contains the task executed, and the state after execution
        var task = plan[i]['task'];
        var state = plan[i]['state'];

        console.log(state['previousTask']['duration']);

        // Create state object for the state before this task was executed
        var gantt_state = {
            "startDate": start,
            "endDate": createTaskTime(state['previousTask']['duration'], false),
            "taskName": "System State",
            "status": "STATE",
            "info": {"tooltip": {'name': 'System State', 'beginning': state['previousState']['time'], 'end': state['time']},
                        "more": state
                    }
        };

        var gantt_task = {
            "startDate": start,
            "endDate": createTaskTime(task['duration'], true),
            "taskName": "Executed Task",
            "status": "QUEUED",
            "info": {"tooltip": {"name": task['name'], "duration": task['duration']},
                        "more": task
                    }
        };

        tasks.push(gantt_state);
        tasks.push(gantt_task);
    }

    window.taskNames = ["Executed Task", "System State"];

    tasks.sort(function(a, b) {
        return a.endDate - b.endDate;
    });
    var maxDate = tasks[tasks.length - 1].endDate;
    tasks.sort(function(a, b) {
        return a.startDate - b.startDate;
    });
    var minDate = tasks[0].startDate;

    var format = "%H:%M";
    window.timeDomainString = "3hr";

    window.gantt = d3.gantt().selector('#visualization').taskTypes(taskNames).taskStatus(taskStatus).tickFormat(format).height(450).width(800);

    gantt.timeDomainMode("fixed");
    changeTimeDomain(timeDomainString);

    gantt(tasks);
    interaction();

}

function changeTimeDomain(timeDomainString) {
    this.timeDomainString = timeDomainString;
    switch (timeDomainString) {
        case "1hr":
            format = "%H:%M:%S";
            gantt.timeDomain([ d3.time.hour.offset(getEndDate(), -1), getEndDate() ]);
            break;
        case "3hr":
            format = "%H:%M";
            gantt.timeDomain([ d3.time.hour.offset(getEndDate(), -3), getEndDate() ]);
            break;

        case "6hr":
            format = "%H:%M";
            gantt.timeDomain([ d3.time.hour.offset(getEndDate(), -6), getEndDate() ]);
            break;

        case "1day":
            format = "%H:%M";
            gantt.timeDomain([ d3.time.day.offset(getEndDate(), -1), getEndDate() ]);
            break;

        case "1week":
            format = "%a %H:%M";
            gantt.timeDomain([ d3.time.day.offset(getEndDate(), -7), getEndDate() ]);
            break;
        default:
            format = "%H:%M"

    }
    gantt.tickFormat(format);
    gantt.redraw(tasks);
}


function getEndDate() {
    var lastEndDate = Date.now();
    if (tasks.length > 0) {
        lastEndDate = tasks[tasks.length - 1].endDate;
    }

    return lastEndDate;
}

function addTask(task) {

    var lastEndDate = getEndDate();
    var taskStatusKeys = Object.keys(taskStatus);
    var taskStatusName = taskStatusKeys[Math.floor(Math.random() * taskStatusKeys.length)];
    var taskName = taskNames[Math.floor(Math.random() * taskNames.length)];

    tasks.push({
        "startDate" : d3.time.minute.offset(lastEndDate, Math.ceil(1 * Math.random())),
        "endDate" : d3.time.minute.offset(lastEndDate, (Math.ceil(Math.random() * 3)) + 1),
        "taskName" : taskName,
        "status" : taskStatusName
    });

    changeTimeDomain(timeDomainString);
    gantt.redraw(tasks);
}

function removeTask() {
    tasks.pop();
    changeTimeDomain(timeDomainString);
    gantt.redraw(tasks);
}

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
    svg.selectAll(".bar-state")
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
        .attr("width", svg.node().getBoundingClientRect().width)
        .attr("height", 350)
        .attr("class", "infoHidden")

    // Task info panels
    svg.selectAll(".bar-queued, .bar-running, .bar-completed")
        .on('click', function(d) {
            var taskName = d.info.tooltip.name.replace(" ", "-");
            var barInfoClass = d3.select("#barInfo").attr("class");
            var active = !barInfo.active;
                if(barInfoClass != "taskInfo " + taskName) {
                console.log("Opening new taskInfo panel");
                d3.select("#barInfo")
                    .attr("class", "taskInfo " + taskName);
                infoGraph(d, "blue");
            // Close the info panel
            } else {
                console.log("Closing info panel");
                d3.select("#barInfo")
                    .style("opacity", 0)
                    .attr('class', 'infoHidden');
            }
        });

    // State info panels
    svg.selectAll(".bar-state")
        .on('click', function(d) {
            console.log(d);
            var stateUID = d.info.more.uid;
            var barInfoClass = d3.select("#barInfo").attr("class");
            var active = !barInfo.active;
                if(barInfoClass != "stateInfo " + stateUID) {
                console.log("Opening new stateInfo panel");
                d3.select("#barInfo")
                    .attr("class", "stateInfo " + stateUID);
                infoGraph(d, "purple");
            } else {
                console.log("Closing info panel");
                d3.select("#barInfo")
                    .style("opacity", 0)
                    .attr('class', 'infoHidden');
            }
        });
}

function infoGraph(data, color) {
    console.log(data);
    d3.select("#barInfo")
        .attr("style", "background: " + color);
}
