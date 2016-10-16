from flask import *
from app import algo
import config, json, traceback, requests


main = Blueprint('main', __name__)

@main.route('/api', methods=['GET'])
def main_route():
    if request.method == 'GET':
        origin = request.args.get('origin'); destination = request.args.get('destination')
        one = request.args.get('one'); two = request.args.get('two')
        three = request.args.get('three'); four = request.args.get('four')
        return algo.shotCaller(origin, destination)