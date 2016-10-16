from bs4 import BeautifulSoup
import requests
import algorithm.green
'''
https://truecostblog.com/2010/05/27/fuel-efficiency-modes-of-transportation-ranked-by-mpg/

Toyota Camry 2016
    ~35.7 MPG
    ~17 GAL Tank
    -> Distance travelled

Arbitrary Bus (Transit)
    ~38.3 / NUM of People (bus becomes more efficient)
    ~65 GAL tank
    -> Distance travelled
'''

#for the car option and the transit option
def computeGas(lat, lon, dist, type):
    # gasResponse = requests.get('http://na-api.mobile.inrix.com/MobileGateway/Mobile.ashx?fuelType=regular'+
    #                            '&sortBy=price&isAdhoc=true&action=Mobile.GasStation.Radius&Center='+ str(lat) +'%7C'+ str(lon)
    #                            +'&Radius=10' +'&token=qbtBwGgjx*qvmbQupKFMWhYJJbCCbrT8GdTqPx2hlV8%7C')
    #
    # Regular = BeautifulSoup(gasResponse.text, "html.parser").find(attrs={"type": "Regular"})['price']
    Regular = '2.224'

    #gas dictionary that we will return.
    gas = {}

    if type == 'driving':
        tank = 17; mpg = 35.7

        #how far the user can go.
        on_tank = tank * mpg

        #how many refuels they'll need to make.
        refuels = dist / on_tank

        #total cost of journey.
        gas['cost'] = (tank * float(Regular)) * refuels
        #number of refuels, if greater than 1.
        gas['refuel'] = refuels if refuels >= 1.0 else 0
        gas['green'] = greenScore(refuels, dist, type)
        #how many trees needed to plant
        gas['trees'] = 0.0584 * dist

    elif type == 'transit':
        tank = 65; mpg = 38.3 / 6.5

        on_tank = tank * mpg
        #number of refuels
        refuels = dist / on_tank

        #build the gas dict..
        gas['cost'] = (tank * float(Regular)) * refuels
        gas['refuel'] = refuels if refuels >= 1.0 else 0
        #compute the green score.
        gas['green'] = greenScore(refuels, dist, type)
        #how many trees you would need to plant (0.0584 per mile)
        gas['trees'] = 0.0584 * dist

    elif type == 'walking':
        gas['green'] = greenScore(0, dist, type)
    elif type == 'bicycling':
        gas['green'] = greenScore(0, dist, type)

    #gas can also contain the green score...
    return gas
