import sys
sys.path.insert(0, '.')
from algorithm import algorithm

def returnJSON(orig, dest):
    return algorithm.shotCaller(orig, dest)
