import json, requests


#google maps direction api key
api_key = 'AIzaSyDHJZtEP1pvZuQfC6_I8gGMB1gBK9eXhHI'

def processInput(origin, destination):
    try:
        r = requests.get('https://maps.googleapis.com/maps/api/directions/json?origin=' + origin + '&destination=' +\
                         destination + '&key=' + api_key)
    except requests.exceptions.ConnectionError as e:
        message = 'Connection to {0} failed. \n {1}'
        print(message.format(('Origin: ' + origin + ', Destination: ' + destination), e.args[0].args[1].args[1]))

    data = json.loads(r.text)

def computeGreenScore():
    pass

#processInput("9605 Tanager Drive, Chardon, OH","437 Sumner Street, Akron, OH")