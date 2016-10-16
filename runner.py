import sys
sys.path.insert(0, 'algorithm/')
from algorithm import algo

def returnJSON(orig, dest):
    return algo.shotCaller(orig, dest)