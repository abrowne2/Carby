from flask import *
import config, json, traceback, requests, sys
sys.path.insert(0, '.')
from runner import returnJSON

#setup the flask blueprint
main = Blueprint('main', __name__)

#our api endpoint route. users have parameter(s): origin, destination, one -> four
@main.route('/api', methods=['GET'])
def main_route():
    if request.method == 'GET':
        origin = request.args.get('origin'); destination = request.args.get('destination')
        one = request.args.get('one'); two = request.args.get('two')
        three = request.args.get('three'); four = request.args.get('four')
        return returnJSON(origin, destination)