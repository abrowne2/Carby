'''
Carbon Footprint data from
https://carbonfund.org/how-we-calculate/

-> Carbon Emissions:
-- BUS TRAVEL: 0.055 kgs C02 per passenger mile
-- CAR: 0.36 kg C02 per mile

'''

#emission_factor = 8.89 kg / gallon

#green score algorithm
def greenScore(gallons):
    return 8.89 * gallons