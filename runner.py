import sys
sys.path.insert(0, './algo')
from algo.algorithm import shotCaller

def returnJSON(orig, dest):
    return shotCaller(orig, dest)
