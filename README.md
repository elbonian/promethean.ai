# promethean.ai [![Build Status](https://travis-ci.com/promethean-jpl/promethean.ai.svg?branch=master)](https://travis-ci.com/promethean-jpl/promethean.ai)
Current manual control of spacecraft is resource consuming and communication travel time can inhibit a spacecraft’s ability to make time-sensitive decisions. To solve this problem we have created an artificial intelligence planning system to run onboard a variety of spacecraft. This planning system utilizes network of executable tasks to create a plan that allows a spacecraft to autonomously transition from its current state through intermediate states to a defined goal state. The input to this system is a problem-state file defined by a user friendly JSON planning language. The language is flexible enough to model any problem state and accommodate user-specified optimizations. The optimized plan is then executed in a simulation system that represents the theoretical state of the spacecraft. To demonstrate the value of the planning system in real world situations, the simulation system will inject perturbations into the execution of the plan. The planning system is not aware of these perturbations at any point in the planning process. The planning system will autonomously replan in real time to adapt to these perturbations, demonstrating its robustness and capability of handling unplanned disruptions. The automation of spaceflight is incredibly beneficial and this system serves as a stepping stone for translating these ideas from simulation systems to actual spacecraft. Every aspect of this system has been created with the computational limitations of actual spacecraft in mind. This system is optimized to run on any modern spacecraft’s onboard computers. 

### Running the CLI
To run the CLI:
   - `java -jar PROMETHEAN-v1.0.0.jar <command>`
   
### CLI user guide
#### Commands:
#### plan - create (and optionally execute) a plan given input json

`-i, --in-file “path/to/file.json”`
- JSON input file for planning system. Must contain at least an initial state, a goal state, and list of tasks

`--in-string “{json: string}”`
- JSON string input for planning system

`-x, --execute`
- Simulate execution after planning (including perturbations)

`-s <double>, --stop <double>`
- Maximum runtime in seconds

`--[no]clf`
- If clf is enabled, the planner will not consider the stop time until backtracking occurs

`-v, --verbose`
- Enables logging

`-l, --logs`
- Write logs to specific directory

`--print-logs`
- Print any logs to the command line (enables verbose by default)

`--plan-output`
- Directory to write generated plans (planner)

`--states-output`
- Directory to write simulated states (exec agent)

#### testgen - generate a test input file given initial and goal states
`-i, --in-file “path/to/file.json”`
- JSON input file for the test generator. Must contain an initial state and a goal state

`--in-string “{json: string}”`
- JSON string input for test generator

`-n, --num-tasks <int>`
- Number of tasks to generate for test case

`-p, --perturbations`
- Number of perturbations to generate for test case

`--optimal-plan`
- If true, generated test will contain a specific, optimal plan

`-v, --verbose`
- Enables logging

`-l, --logs`
- Write logs to specific directory

`--print-logs`
- Print any logs to the command line (enables verbose by default)

`-o, --output`
- Output filename for generated test case


### Build Instructions
Promethean uses Gradle to build executables. To build a new executable:
- `./gradlew build`
- The jar file will be in the `/build/lib` directory and named `promethean.ai-version-SNAPSHOT-all.jar`.
