1. Take input from user (Starting point, destination, priority rank for Carbon-footprint, distance, cost, time)

2. Make request to Maps api to get information about the list of routes (including ferries, flights, drives, walks, etc).
ctd: more requests may need to be made if you decide to split the route up into multiple pieces, so you can route not point 
a->b but from a->c c->d d->b with different transportion modes used for each leg of the journey.

3. Data set 1: Collect information and compute the carbon footprint for the entire route, plus information on distance, time, cost, etc.

Data set 2: Collect & compute information on how to offset carbon footprint for ride (with number of trees planted, walking/cycling/using public transport for X miles. 

4. present data set 1 on GUI to the user in the form of an interactive graph

5. present data set 2 pictographically with trees/car routes/etc.

bottom: Search another route. 


