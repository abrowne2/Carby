import algorithm.cost
import json, requests

#google maps direction api key
api_key = 'AIzaSyDHJZtEP1pvZuQfC6_I8gGMB1gBK9eXhHI'

def shotCaller(origin, destination):
    '''
    :param origin: where the user is coming from
    :param destination: user wants to go here.
    :return: JSON for our app to understand
    '''
    #we're going to return a big list object.
    bigData = {'car': processInput(origin, destination, 'driving'), 'bike': processInput(origin, destination, 'bicycling'),
        'walk': processInput(origin, destination, 'walking'), 'transit': processInput(origin, destination, 'transit')}
    return json.dumps(bigData)

def processInput(origin, destination, type):
    '''
    We're going to make 3-4 requests (not sure yet)
    1. Parse each of those requests (we can use list comprehension)
    '''
    try:
        r = requests.get('https://maps.googleapis.com/maps/api/directions/json?origin=' + origin + '&destination=' +\
                         destination + '&departure_time=now' + '&mode=' + type + '&provideRouteAlternatives=true' +
                         '&key=' + api_key)
    except requests.exceptions.ConnectionError as e:
        message = 'Connection to {0} failed. \n {1}'
        print(message.format(('Origin: ' + origin + ', Destination: ' + destination), e.args[0].args[1].args[1]))

    data = json.loads(r.text)

    #create the transport list.
    transportation = []
    #ensure that the data we're about to parse actually exists...
    #we might not have a public transit route, for example..
    if(data['status'] != 'ZERO_RESULTS'):
        #append the total distance taken and the duration of time
        gasFlag = True if type is 'driving' or 'transit' else False

        #store the end_location's latitude and longitutde, used for cost computation:
        if gasFlag:
            lat = data['routes'][0]['legs'][0]['end_location']['lat']
            lon = data['routes'][0]['legs'][0]['end_location']['lng']
        dist = data['routes'][0]['legs'][0]['distance']['text']

        #the first data should be that of the total distance, time, and cost required.
        transportation.append({'distance': dist, 'time':
            data['routes'][0]['legs'][0]['duration']['text'], 'cost':
            algorithm.cost.computeGas(lat, lon, float(dist.replace("mi", "").strip()), type)})

        #iterate over the response, creating individual dictionaries for each step.
        for steps in data['routes'][0]['legs'][0]['steps']:
            transportation.append({'step': maneuver(steps['maneuver']) if 'maneuver' in steps else '',
                            'dist': steps['distance'], 'time': steps['duration'], 'mode': steps['travel_mode'],
                            'words': stepParse(steps['html_instructions'])})
    return transportation

#use stepParse to parse the html instruction in the API response
def stepParse(instruction):
    #the instruction can contain more elaborate html.. improve later.
    instruction = instruction.replace("<b>", "")
    return instruction.replace("</b>", "")

#typically the maneuver involves "fork-right"
def maneuver(step):
    return ' '.join(step.split('-'))
