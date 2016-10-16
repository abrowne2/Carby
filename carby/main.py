from flask import *
import sys
sys.path.insert(0, '.')
import config, json, traceback, requests


main = Blueprint('main', __name__)

@main.route('/', methods=['GET', 'POST'])
def main_route():
    if request.method == 'POST':
        try:
            payload = request.get_json()
            print(payload)
            #parse the payload below, and then we'll use our 'computation' module

            return "okay"
        except Exception as e:
            print(type(e))
            print(e.args)

    elif request.method == 'GET':
        return "Yeah, this is a get request"