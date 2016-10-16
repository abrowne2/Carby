from flask import *
import sys
sys.path.insert(0, '.')
import config, json, traceback, requests


main = Blueprint('main', __name__)

@main.route('/api', methods=['GET'])
def main_route():
    #only handle get requests.
    if request.method == 'GET':
        origin = request.args.get('origin')
        destination = request.args.get('destination')
        return "YEAH"