import sys
sys.path.insert(0, './algorithm')
from algorithm.algorithm import shotCaller

def returnJSON(orig, dest):
    return shotCaller(orig, dest)
